package com.gladunalexander.springdistributedlock.account;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.gladunalexander.springdistributedlock.account.Operation.OperationType.DEPOSIT;
import static com.gladunalexander.springdistributedlock.account.Operation.OperationType.WITHDRAW;

@Entity
@Data
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ownerName;

    @OrderBy(value = "occurredAt ASC")
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Operation> operations = new ArrayList<>();

    public Account(String ownerName) {
        this.ownerName = ownerName;
    }

    public void withdraw(BigDecimal amount) {
        if (!canWithdraw(amount)) {
            throw new IllegalArgumentException("Cannot withdraw so much money");
        }
        this.operations.add(new Operation(this, WITHDRAW, amount));
    }

    public void deposit(BigDecimal amount) {
         this.operations.add(new Operation(this, DEPOSIT, amount));
    }

    public BigDecimal getCurrentBalance() {
        BigDecimal currentBalance = BigDecimal.ZERO;
        for (Operation operation : operations) {
            currentBalance = operation.getType() == DEPOSIT
                    ? currentBalance.add(operation.getAmount())
                    : currentBalance.subtract(operation.getAmount());
        }
        return currentBalance;
    }

    private boolean canWithdraw(BigDecimal amount) {
        BigDecimal currentBalance = getCurrentBalance();
        return currentBalance.compareTo(amount) > 0;
    }
}
