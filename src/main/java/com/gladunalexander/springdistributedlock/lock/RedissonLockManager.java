package com.gladunalexander.springdistributedlock.lock;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
public class RedissonLockManager implements LockManager {

    private final RedissonClient client;
    private final Duration expirationTime;

    @SneakyThrows
    @Override
    public boolean tryLock(String key) {
        RLock lock = client.getLock(key);
        return lock.tryLock(0, expirationTime.getSeconds(), TimeUnit.SECONDS);
    }

    @Override
    public void unlock(String key) {
        client.getLock(key).unlock();
    }
}
