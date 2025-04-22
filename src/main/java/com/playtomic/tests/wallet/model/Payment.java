package com.playtomic.tests.wallet.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Payment {

    @Id
    private UUID id;

    private UUID emailId;

    @NonNull
    private String creditCard;

    @NonNull
    private BigDecimal amount;

    @JsonCreator
    public Payment(@JsonProperty(value = "id", required = true) String id) {
        this.id = UUID.fromString(id);
    }
}
