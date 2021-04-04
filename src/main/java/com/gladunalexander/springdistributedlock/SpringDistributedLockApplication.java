package com.gladunalexander.springdistributedlock;

import com.gladunalexander.springdistributedlock.account.Account;
import com.gladunalexander.springdistributedlock.account.AccountRepository;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.util.List;

@SpringBootApplication
public class SpringDistributedLockApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringDistributedLockApplication.class, args);
    }

    @Bean
    SmartInitializingSingleton init(AccountRepository repository) {
        return () -> {
            var bobAccount = new Account("Bob");
            var aliceAccount = new Account("Alice");
            var johnAccount = new Account("John");

            bobAccount.deposit(BigDecimal.valueOf(100));
            aliceAccount.deposit(BigDecimal.valueOf(100));
            johnAccount.deposit(BigDecimal.valueOf(100));

            repository.saveAll(List.of(bobAccount, aliceAccount, johnAccount));
        };
    }

}
