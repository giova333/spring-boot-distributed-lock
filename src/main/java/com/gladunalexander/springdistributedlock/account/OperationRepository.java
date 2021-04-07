package com.gladunalexander.springdistributedlock.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;
import java.util.List;

public interface OperationRepository extends JpaRepository<Operation, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Operation> findByAccountId(Long accountId);
}
