package com.playtomic.tests.wallet.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@Entity
@NoArgsConstructor
public class Payment {

    @NonNull
    @Id
    private UUID id;

    @NonNull
    private String creditCard;

    @NonNull
    private BigDecimal amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet_email", nullable = false)
    private Wallet wallet;
    @JsonCreator
    public Payment(@JsonProperty(value = "id", required = true) String id) {
        this.id = UUID.fromString(id);
    }
}
