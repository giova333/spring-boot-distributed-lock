package com.gladunalexander.springdistributedlock.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue
    private Long id;

    private String ownerName;

    private BigDecimal balance;

    public void withdraw(BigDecimal amount) {
        if (amount.compareTo(balance) > 0) {
            throw new IllegalArgumentException("Cannot withdraw so much money");
        }
        this.balance = this.balance.subtract(amount);
    }

    public void deposit(BigDecimal amount) {
        this.balance = this.balance.add(amount);
    }
}
