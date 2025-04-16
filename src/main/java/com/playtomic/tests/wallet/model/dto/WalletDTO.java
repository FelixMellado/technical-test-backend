package com.playtomic.tests.wallet.model.dto;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.Data;

@Data
public class WalletDTO {

    private UUID id;

    private String email;

    private String name;

    private BigDecimal amount;

}
