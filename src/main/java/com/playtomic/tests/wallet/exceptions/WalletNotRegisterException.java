package com.playtomic.tests.wallet.exceptions;

public class WalletNotRegisterException extends RuntimeException {

    public WalletNotRegisterException(String email) {
        super("Wallet with email '" + email + "' is not registered");
    }
}
