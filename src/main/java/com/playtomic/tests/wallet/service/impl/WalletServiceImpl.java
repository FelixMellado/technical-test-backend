package com.playtomic.tests.wallet.service.impl;

import com.playtomic.tests.wallet.exceptions.PaymentAmountNegativeException;
import com.playtomic.tests.wallet.exceptions.WalletAlreadyExistsException;
import com.playtomic.tests.wallet.exceptions.WalletNotRegisterException;
import com.playtomic.tests.wallet.model.Payment;
import com.playtomic.tests.wallet.model.Wallet;
import com.playtomic.tests.wallet.repository.PaymentRepository;
import com.playtomic.tests.wallet.repository.WalletRepository;
import com.playtomic.tests.wallet.service.StripeService;
import com.playtomic.tests.wallet.service.WalletService;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class WalletServiceImpl implements WalletService {

    public static final String WALLET_NOT_REGISTERED = "Wallet not registered";
    public static final String AMOUNT_MUST_BE_POSITIVE = "Amount must be positive";
    private final WalletRepository walletRepository;
    private final PaymentRepository paymentRepository;
    private final StripeService stripeService;

    public WalletServiceImpl(WalletRepository walletRepository, PaymentRepository paymentRepository, StripeService stripeService) {
        this.walletRepository = walletRepository;
        this.paymentRepository = paymentRepository;
        this.stripeService = stripeService;
    }


    public Wallet createWallet(Wallet wallet) {

        if (walletRepository.findByEmailId(wallet.getEmailId()).isPresent()) {
            throw new WalletAlreadyExistsException(wallet.getEmail());
        }
        // Maybe better hash
        wallet.setEmailId(UUID.randomUUID());
        return walletRepository.save(wallet);
    }

    public Payment topUp(Payment payment) {

        validatePayment(payment);

        Wallet wallet = walletRepository.findByEmailId(payment.getEmailId())
                .orElseThrow(() -> new WalletNotRegisterException(WALLET_NOT_REGISTERED));

        Payment chargedPayment = stripeService.charge(payment.getCreditCard(), payment.getAmount());

        wallet.setAmount(wallet.getAmount().add(chargedPayment.getAmount()));
        walletRepository.save(wallet);

        chargedPayment.setCreditCard(payment.getCreditCard());
        chargedPayment.setAmount(payment.getAmount());
        chargedPayment.setEmailId(payment.getEmailId());
        paymentRepository.save(chargedPayment);

        return chargedPayment;
    }

    public Wallet getWallet(@NonNull UUID id){

        return walletRepository.findByEmailId(id)
                .orElseThrow(() -> new WalletNotRegisterException(WALLET_NOT_REGISTERED));

    }

    private void validatePayment(Payment payment) {
        if (payment.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new PaymentAmountNegativeException(AMOUNT_MUST_BE_POSITIVE);
        }
    }

}
