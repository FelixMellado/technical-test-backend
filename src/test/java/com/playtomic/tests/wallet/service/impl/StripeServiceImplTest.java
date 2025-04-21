package com.playtomic.tests.wallet.service.impl;


import com.playtomic.tests.wallet.exceptions.StripeAmountTooSmallException;
import com.playtomic.tests.wallet.exceptions.StripeServiceException;

import com.playtomic.tests.wallet.model.Payment;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.web.client.RestTemplateBuilder;

import java.math.BigDecimal;
import java.net.URI;
import org.springframework.web.client.RestTemplate;

/**
 * This test is failing with the current implementation.
 *
 * How would you test this?
 */
public class StripeServiceImplTest {
    public static final String CREDIT_CARD = "4242 4242 4242 4242";
    public static final String URL = "http://how-would-you-test-me.localhost";
    @Mock
    private RestTemplate restTemplate;

    private StripeServiceImpl stripeService;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        URI testUri = URI.create(URL);

        RestTemplateBuilder builder = mock(RestTemplateBuilder.class);
        when(builder.errorHandler(any())).thenReturn(builder);
        when(builder.rootUri(anyString())).thenReturn(builder);
        when(builder.build()).thenReturn(restTemplate);

        stripeService = new StripeServiceImpl(testUri, testUri, builder);

        Payment mockPayment = new Payment();
        mockPayment.setAmount(new BigDecimal(15));
        mockPayment.setCreditCard(CREDIT_CARD);
        when(restTemplate.postForObject(any(URI.class), any(), eq(Payment.class)))
                .thenReturn(mockPayment);
    }

    @Test
    public void test_exception() {
        Assertions.assertThrows(StripeAmountTooSmallException.class, () -> {
            stripeService.charge(CREDIT_CARD, new BigDecimal(5));
        });
    }

    @Test
    public void test_ok() throws StripeServiceException {
        Payment payment = stripeService.charge(CREDIT_CARD, new BigDecimal(15));

        verify(restTemplate).postForObject(any(URI.class), any(), eq(Payment.class));
        assertEquals(CREDIT_CARD, payment.getCreditCard());
        assertEquals(new BigDecimal(15), payment.getAmount());

    }
}
