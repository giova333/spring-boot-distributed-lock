package com.gladunalexander.springdistributedlock;

import com.gladunalexander.springdistributedlock.account.Account;
import com.gladunalexander.springdistributedlock.account.AccountRepository;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

@SpringBootApplication
public class SpringDistributedLockApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringDistributedLockApplication.class, args);
    }

    @Bean
    SmartInitializingSingleton init(AccountRepository repository) {
        return () -> {
            repository.save(
                    Account.builder()
                            .ownerName("Bob")
                            .balance(BigDecimal.valueOf(100))
                            .build()
            );

            repository.save(
                    Account.builder()
                            .ownerName("Alice")
                            .balance(BigDecimal.valueOf(100))
                            .build()
            );

            repository.save(
                    Account.builder()
                            .ownerName("John")
                            .balance(BigDecimal.valueOf(100))
                            .build()
            );

        };
    }

}
