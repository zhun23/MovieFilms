package com.example.dev.exceptions;

public class MySQLException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public MySQLException(String message, Throwable cause) {
        super(message, cause);
    }
}
