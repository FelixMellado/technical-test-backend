package com.playtomic.tests.wallet.exceptions;

public class PaymentAmountNegativeException extends RuntimeException{

    public PaymentAmountNegativeException(String notRegistered) {
        super(notRegistered);
    }
}
