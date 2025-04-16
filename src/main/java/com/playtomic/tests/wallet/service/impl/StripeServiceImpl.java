package com.playtomic.tests.wallet.service.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.playtomic.tests.wallet.exceptions.StripeRestTemplateResponseErrorHandler;
import com.playtomic.tests.wallet.exceptions.StripeServiceException;
import com.playtomic.tests.wallet.model.Payment;
import com.playtomic.tests.wallet.repository.PaymentRepository;
import com.playtomic.tests.wallet.service.StripeService;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.net.URI;

import javax.swing.text.html.Option;


/**
 * Handles the communication with Stripe.
 *
 * A real implementation would call to String using their API/SDK.
 * This dummy implementation throws an error when trying to charge less than 10€.
 */
@Service
public class StripeServiceImpl implements StripeService {

    @NonNull
    private URI chargesUri;

    @NonNull
    private URI refundsUri;

    private PaymentRepository paymentRepository;

    @NonNull
    private RestTemplate restTemplate;

    public StripeServiceImpl(@Value("${stripe.simulator.charges-uri}") @NonNull URI chargesUri,
                             @Value("${stripe.simulator.refunds-uri}") @NonNull URI refundsUri,
                             PaymentRepository paymentRepository,
                             @NonNull RestTemplateBuilder restTemplateBuilder) {
        this.chargesUri = chargesUri;
        this.refundsUri = refundsUri;
        this.paymentRepository = paymentRepository;
        this.restTemplate =
                restTemplateBuilder
                .errorHandler(new StripeRestTemplateResponseErrorHandler())
                .build();
    }

    public Optional<Payment> getPaymentById(@NonNull UUID paymentId){

        return paymentRepository.findById(paymentId);
    }

    public List<Payment> getAllPayments(){

        return paymentRepository.findAll();
    }

    /**
     * Charges money in the credit card.
     *
     * Ignore the fact that no CVC or expiration date are provided.
     *
     * @param creditCardNumber The number of the credit card
     * @param amount The amount that will be charged.
     *
     * @throws StripeServiceException
     */
    public Payment charge(@NonNull String creditCardNumber, @NonNull BigDecimal amount) throws StripeServiceException {
        ChargeRequest body = new ChargeRequest(creditCardNumber, amount);
        return Optional.ofNullable(restTemplate.postForObject(chargesUri, body, Payment.class))
                .map(p -> {
                    p.setAmount(amount);
                    p.setCreditCard(creditCardNumber);
                    paymentRepository.save(p);
                    return p;
                })
                .orElse(null);

    }

    /**
     * Refunds the specified payment.
     */
    public void refund(@NonNull String paymentId) throws StripeServiceException {
        // Object.class because we don't read the body here.
        restTemplate.postForEntity(chargesUri.toString(), null, Object.class, paymentId);
    }

    @AllArgsConstructor
    private static class ChargeRequest {

        @NonNull
        @JsonProperty("credit_card")
        String creditCardNumber;

        @NonNull
        @JsonProperty("amount")
        BigDecimal amount;

    }
}
