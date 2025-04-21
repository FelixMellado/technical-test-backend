package com.playtomic.tests.wallet.service;

import com.playtomic.tests.wallet.model.Payment;
import com.playtomic.tests.wallet.model.Wallet;
import java.util.UUID;
import lombok.NonNull;

public interface WalletService {

    public Wallet createWallet(Wallet wallet);
    public Payment topUp(Payment payment);
    public Wallet getWallet(@NonNull UUID id);

}
