package com.example.taskManager.exception;

/**
 * Custom exception thrown when a requested Role is not found.
 */
public class RoleNotFoundException extends Exception {
    public RoleNotFoundException() {
        super();
    }

    public RoleNotFoundException(String message) {
        super(message);
    }

    public RoleNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
