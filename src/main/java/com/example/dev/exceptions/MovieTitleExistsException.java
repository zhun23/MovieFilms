package com.example.dev.exceptions;

public class MovieTitleExistsException extends RuntimeException {
    public MovieTitleExistsException(String message) {
        super(message);
    }

    public MovieTitleExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}

