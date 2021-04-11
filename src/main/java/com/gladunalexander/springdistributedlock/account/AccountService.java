package com.gladunalexander.springdistributedlock.account;

import com.gladunalexander.springdistributedlock.lock.LockAcquisitionException;
import com.gladunalexander.springdistributedlock.lock.LockManager;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static com.gladunalexander.springdistributedlock.lock.LockAcquisitionException.Type.UNABLE_TO_ACQUIRE_LOCK;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository repository;
    private final LockManager lockManager;

    @Transactional
    @SneakyThrows
    public void sendMoney(Long sourceAccountId,
                          Long targetAccountId,
                          BigDecimal amount) {

        if (!lockManager.tryLock(String.valueOf(sourceAccountId))) {
            throw new LockAcquisitionException(UNABLE_TO_ACQUIRE_LOCK, sourceAccountId);
        }
        var sourceAccount = repository
                .findById(sourceAccountId)
                .orElseThrow();

        if (!lockManager.tryLock(String.valueOf(targetAccountId))) {
            throw new LockAcquisitionException(UNABLE_TO_ACQUIRE_LOCK, targetAccountId);
        }
        var targetAccount = repository
                .findById(targetAccountId)
                .orElseThrow();

        sourceAccount.withdraw(amount);
        targetAccount.deposit(amount);

        Thread.sleep(5000);

        lockManager.unlock(String.valueOf(sourceAccountId));
        lockManager.unlock(String.valueOf(targetAccountId));
    }

    @Transactional(readOnly = true)
    public List<Account> getAccounts() {
        return repository.findAll();
    }
}
