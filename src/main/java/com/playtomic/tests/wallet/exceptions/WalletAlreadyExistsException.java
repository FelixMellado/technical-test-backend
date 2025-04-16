package com.playtomic.tests.wallet.exceptions;

public class WalletAlreadyExistsException extends RuntimeException {

    public WalletAlreadyExistsException(String email) {
        super("Wallet with email '" + email + "' already exists");
    }

}
