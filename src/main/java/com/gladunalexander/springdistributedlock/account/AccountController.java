package com.gladunalexander.springdistributedlock.account;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/transfer")
    public void transfer(@RequestBody SendMoneyCommand command) {
        accountService.sendMoney(
                command.sourceAccountId,
                command.targetAccountId,
                command.amount
        );
    }

    @Value
    @Builder
    static class SendMoneyCommand {
        Long sourceAccountId;
        Long targetAccountId;
        BigDecimal amount;
    }
}
