package com.playtomic.tests.wallet.model.dto;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.Data;

@Data
public class PaymentDto {

    private UUID id;

    private UUID emailId;

    private String creditCard;

    private BigDecimal amount;
}
