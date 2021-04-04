package com.gladunalexander.springdistributedlock.account;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    @EntityGraph(attributePaths = "operations")
    Optional<Account> findById(Long id);

    @EntityGraph(attributePaths = "operations")
    List<Account> findAll();
}
