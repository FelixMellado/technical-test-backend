package com.playtomic.tests.wallet.api;

import com.playtomic.tests.wallet.model.Payment;
import com.playtomic.tests.wallet.model.dto.PaymentDTO;
import com.playtomic.tests.wallet.model.dto.PaymentMapper;
import com.playtomic.tests.wallet.service.StripeService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WalletController {

    private final StripeService stripeService;
    private Logger log = LoggerFactory.getLogger(WalletController.class);

    public WalletController(StripeService stripeService) {
        this.stripeService = stripeService;
    }

    @RequestMapping("/")
    void log() {
        log.info("Logging from /");
    }


    @GetMapping("/allPayments")
    public ResponseEntity<List<Payment>> getAllPayments() {

        return ResponseEntity.ok(stripeService.getAllPayments());

    }

    @GetMapping("/{id}")
    public ResponseEntity<UUID> getPaymentById(@PathVariable("id") UUID id) {

        Optional<Payment> payment = stripeService.getPaymentById(id);

       return payment.map(value -> ResponseEntity.ok(payment.get().getId()))
               .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @PostMapping("/charge")
    public ResponseEntity<Payment> createCharge(@RequestBody PaymentDTO paymentDto) {

        Payment paymentAccepted = stripeService.charge(paymentDto.getCredit_card(), paymentDto.getAmount());

        return ResponseEntity.ok(paymentAccepted);

    }

}
