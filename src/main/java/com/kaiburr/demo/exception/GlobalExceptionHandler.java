package com.kaiburr.demo.exception;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleTaskNotFoundException(TaskNotFoundException ex, WebRequest request) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Task not found");
        errorResponse.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(UnsafeCommandException.class)
    public ResponseEntity<Map<String, String>> handleUnsafeCommandException(UnsafeCommandException ex, WebRequest request) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Unsafe command");
        errorResponse.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<Map<String, String>> handleDatabaseException(DataAccessException ex, WebRequest request) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Database connection failed");
        errorResponse.put("message", "MongoDB is not available. Please check database connection.");
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericException(Exception ex, WebRequest request) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Internal server error");
        errorResponse.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
