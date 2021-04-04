package com.gladunalexander.springdistributedlock.account;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping
    public List<AccountResponse> getAccounts() {
        List<Account> accounts = accountService.getAccounts();
        return accounts.stream()
                .map(account -> new AccountResponse(account.getId(), account.getOwnerName(), account.getCurrentBalance()))
                .collect(Collectors.toList());

    }

    @Value
    static class AccountResponse {
        Long id;
        String ownerName;
        BigDecimal currentBalance;
    }

    @Value
    @Builder
    static class SendMoneyCommand {
        Long sourceAccountId;
        Long targetAccountId;
        BigDecimal amount;
    }
}
