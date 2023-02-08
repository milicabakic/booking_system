package com.griddynamics.lidlbooking.commons.errors;

import java.util.UUID;

public abstract class BaseException extends RuntimeException {

    private final UUID uuid;

    public BaseException(final String message) {
        super(message);
        this.uuid = UUID.randomUUID();
    }

    public UUID getUUID() {
        return uuid;
    }
}
