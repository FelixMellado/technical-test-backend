package com.playtomic.tests.wallet.model.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class PaymentDTO {

    private String credit_card;
    private BigDecimal amount;
}
