package com.playtomic.tests.wallet.api;

import com.playtomic.tests.wallet.model.Payment;
import com.playtomic.tests.wallet.model.Wallet;
import com.playtomic.tests.wallet.model.dto.PaymentDto;
import com.playtomic.tests.wallet.model.dto.PaymentMapper;
import com.playtomic.tests.wallet.model.dto.WalletDTO;
import com.playtomic.tests.wallet.model.dto.WalletMapper;
import com.playtomic.tests.wallet.service.StripeService;
import com.playtomic.tests.wallet.service.WalletService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WalletController {

    private final StripeService stripeService;

    private final WalletService walletService;
    private Logger log = LoggerFactory.getLogger(WalletController.class);

    public WalletController(StripeService stripeService, WalletService walletService) {
        this.stripeService = stripeService;
        this.walletService = walletService;
    }

    @RequestMapping("/")
    void log() {
        log.info("Logging from /");
    }


    @PostMapping("/wallets/create")
    public ResponseEntity<WalletDTO> createWallet(@RequestBody WalletDTO walletDTO){

        Wallet wallet = walletService.createWallet(WalletMapper.INSTANCE.walletDtoToWallet(walletDTO));

        return ResponseEntity.ok(WalletMapper.INSTANCE.walletToWalletDTO(wallet));
    }

    @PostMapping("wallets/topUp")
    public ResponseEntity<PaymentDto> topUp(@RequestBody PaymentDto paymentDto){

        Payment payment = walletService.topUp(PaymentMapper.INSTANCE.paymentDtoToPayment(paymentDto));

        return ResponseEntity.ok(PaymentMapper.INSTANCE.paymentToPaymentDTO(payment));
    }

    @GetMapping("wallet/{id}/allPayments")
    public ResponseEntity<List<Payment>> getAllPaymentsById(Wallet wallet){

        List<Payment> paymentList = stripeService.findPaymentsById(wallet.getEmailId());

        return ResponseEntity.ok(paymentList);

    }

}
