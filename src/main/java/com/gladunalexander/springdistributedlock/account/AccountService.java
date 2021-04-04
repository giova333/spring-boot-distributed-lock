package com.gladunalexander.springdistributedlock.account;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository repository;

    @Transactional
    @SneakyThrows
    public void sendMoney(Long sourceAccountId,
                          Long targetAccountId,
                          BigDecimal amount) {

        var sourceAccount = repository
                .findById(sourceAccountId)
                .orElseThrow();

        var targetAccount = repository
                .findById(targetAccountId)
                .orElseThrow();

        sourceAccount.withdraw(amount);
        targetAccount.deposit(amount);

        Thread.sleep(5000);
    }
}
