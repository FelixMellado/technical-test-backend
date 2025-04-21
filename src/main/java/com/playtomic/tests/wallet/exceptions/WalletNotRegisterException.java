package com.playtomic.tests.wallet.exceptions;

public class WalletNotRegisterException extends RuntimeException {

    public WalletNotRegisterException(String notRegistered) {
        super(notRegistered);
    }
}
