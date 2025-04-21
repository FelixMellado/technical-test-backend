package com.playtomic.tests.wallet.service;

import com.playtomic.tests.wallet.model.Payment;
import java.math.BigDecimal;
import lombok.NonNull;

public interface StripeService {

    public Payment charge(@NonNull String creditCardNumber, @NonNull BigDecimal amount);

    public void refund(@NonNull String paymentId);

}
