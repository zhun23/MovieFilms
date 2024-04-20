package com.example.dev.exceptions;

public class UserEmailExistsException extends RuntimeException {
    public UserEmailExistsException(String message) {
        super(message);
    }
}
