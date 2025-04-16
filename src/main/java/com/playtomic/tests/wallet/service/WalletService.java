package com.playtomic.tests.wallet.service;

import com.playtomic.tests.wallet.model.Payment;
import com.playtomic.tests.wallet.model.Wallet;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

public interface WalletService {

    public Wallet createWallet(Wallet wallet);
    public Payment topUp(Payment payment);

}
