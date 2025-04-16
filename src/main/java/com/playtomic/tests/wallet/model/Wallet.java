package com.playtomic.tests.wallet.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Entity
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(unique = true) // better Hash
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID emailId;

    @Column(unique = true)
    @NotBlank(message = "EmailId should not be empty")
    private String email;

    @NotBlank(message = "Name should not be empty")
    private String name;

    private BigDecimal amount;

}
