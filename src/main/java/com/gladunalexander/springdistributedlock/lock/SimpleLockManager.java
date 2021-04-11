package com.gladunalexander.springdistributedlock.lock;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.time.Duration;
import java.util.UUID;

@RequiredArgsConstructor
public class SimpleLockManager implements LockManager {

    private final StringRedisTemplate redisTemplate;
    private final Duration expirationTime;

    @Override
    public boolean tryLock(String key) {
        return redisTemplate
                .opsForValue()
                .setIfAbsent(key, UUID.randomUUID().toString(), expirationTime);
    }

    @Override
    public void unlock(String key) {
        redisTemplate.delete(key);
    }
}
