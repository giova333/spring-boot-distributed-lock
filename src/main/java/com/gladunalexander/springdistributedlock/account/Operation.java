package com.gladunalexander.springdistributedlock.account;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Data
@NoArgsConstructor
public class Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private OperationType type;

    private BigDecimal amount;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;

    private Instant occurredAt = Instant.now();

    public enum OperationType {
        DEPOSIT, WITHDRAW
    }

    public Operation(Account account, OperationType type, BigDecimal amount) {
        this.account = account;
        this.type = type;
        this.amount = amount;
    }
}
