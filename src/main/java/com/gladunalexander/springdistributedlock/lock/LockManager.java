package com.gladunalexander.springdistributedlock.lock;

public interface LockManager {

    boolean tryLock(String key);

    void unlock(String key);
}
