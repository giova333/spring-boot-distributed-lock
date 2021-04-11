package com.gladunalexander.springdistributedlock.lock;

import org.redisson.Redisson;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.time.Duration;

@Configuration
public class LockConfiguration {

    @Bean
    public LockManager simpleLockManager(StringRedisTemplate redisTemplate,
                                         @Value("${redis.lock.expiration-time}") Duration expirationTime) {
        return new SimpleLockManager(redisTemplate, expirationTime);
    }

    @Bean
    @Primary
    public LockManager redissonLockManager(RedisProperties redisProperties,
                                           @Value("${redis.lock.expiration-time}") Duration expirationTime) {
        var config = new Config();

        config
                .useSingleServer()
                .setAddress(String.format("redis://%s:%d", redisProperties.getHost(), redisProperties.getPort()));

        var redissonClient = Redisson.create(config);
        return new RedissonLockManager(redissonClient, expirationTime);
    }
}
