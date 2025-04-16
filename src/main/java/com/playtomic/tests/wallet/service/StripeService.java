package com.playtomic.tests.wallet.service;

import com.playtomic.tests.wallet.model.Payment;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.NonNull;

public interface StripeService {

    public Payment charge(@NonNull String creditCardNumber, @NonNull BigDecimal amount);

    public void refund(@NonNull String paymentId);

    public List<Payment> findPaymentsById(UUID emailId);

}
