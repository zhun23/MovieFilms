package com.example.dev.exceptions;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.dev.model.ErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", "Error de validación");
        response.put("message", ex.getBindingResult().getFieldError().getDefaultMessage());
        return ResponseEntity.badRequest().body(response);
    }
	
	@ExceptionHandler(UserEmailExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserEmailExists(UserEmailExistsException ex) {
        ErrorResponse errorResponse = new ErrorResponse("Email Exists", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(UserNicknameExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserNicknameExists(UserNicknameExistsException ex) {
        ErrorResponse errorResponse = new ErrorResponse("Nickname Exists", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse("Internal Server Error", "An unexpected error occurred.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
    
    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ErrorResponse> handleSQLException(SQLException ex) {
        ErrorResponse errorResponse = new ErrorResponse("Database Error", "Error de base de datos: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
