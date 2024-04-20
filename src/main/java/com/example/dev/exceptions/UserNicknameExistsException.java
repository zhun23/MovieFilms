package com.example.dev.exceptions;

public class UserNicknameExistsException extends RuntimeException {
    public UserNicknameExistsException(String message) {
        super(message);
    }
}
