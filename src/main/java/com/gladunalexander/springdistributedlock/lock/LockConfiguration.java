package com.gladunalexander.springdistributedlock.lock;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.time.Duration;

@Configuration
public class LockConfiguration {

    @Bean
    public LockManager lockManager(StringRedisTemplate redisTemplate,
                                   @Value("${redis.lock.expiration-time}") Duration expirationTime) {
        return new SimpleLockManager(redisTemplate, expirationTime);
    }
}
