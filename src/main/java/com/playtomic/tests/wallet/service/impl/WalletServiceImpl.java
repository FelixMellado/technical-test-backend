package com.playtomic.tests.wallet.service.impl;

import com.playtomic.tests.wallet.exceptions.WalletAlreadyExistsException;
import com.playtomic.tests.wallet.exceptions.WalletNotRegisterException;
import com.playtomic.tests.wallet.model.Payment;
import com.playtomic.tests.wallet.model.Wallet;
import com.playtomic.tests.wallet.repository.WalletRepository;
import com.playtomic.tests.wallet.service.StripeService;
import com.playtomic.tests.wallet.service.WalletService;
import org.springframework.stereotype.Service;

@Service
public class WalletServiceImpl implements WalletService {

    private WalletRepository walletRepository;

    private StripeService stripeService;

    public WalletServiceImpl(WalletRepository walletRepository, StripeService stripeService) {
        this.walletRepository = walletRepository;
        this.stripeService = stripeService;
    }


    public Wallet createWallet(Wallet wallet) {

        if (walletRepository.findByEmailId(wallet.getEmailId()) != null) {
            throw new WalletAlreadyExistsException(wallet.getEmail());
        }
        return walletRepository.save(wallet);
    }

    public Payment topUp(Payment payment) {

        if (walletRepository.findByEmailId(payment.getEmailId()) != null) {
            return stripeService.charge(payment.getCreditCard(), payment.getAmount());
        }
        throw new WalletNotRegisterException(payment.getEmailId().toString());

    }


}
