package com.gladunalexander.springdistributedlock.lock;

import lombok.Getter;

public class LockAcquisitionException extends RuntimeException {

    public enum Type {
        UNABLE_TO_ACQUIRE_LOCK(1, "Unable to acquire lock for key %s");

        @Getter private final int code;
        @Getter private final String message;

        Type(int code, String message) {
            this.code = code;
            this.message = message;
        }
    }

    public LockAcquisitionException(Type type, Object... args) {
        super(String.format(type.message, args));
    }
}
