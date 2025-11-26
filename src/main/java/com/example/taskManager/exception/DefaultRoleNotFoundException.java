package com.example.taskManager.exception;

public class DefaultRoleNotFoundException extends Exception {
    public DefaultRoleNotFoundException(String message) {
        super(message);
    }

    public DefaultRoleNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
