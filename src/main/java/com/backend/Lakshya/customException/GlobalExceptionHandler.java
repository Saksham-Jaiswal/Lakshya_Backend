package com.backend.Lakshya.customException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

//@ControllerAdvice is a global exception handler for Spring MVC applications.
@ControllerAdvice
public class GlobalExceptionHandler {

    // Handle shop not found
    @ExceptionHandler(ShopNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleShopNotFoundException(ShopNotFoundException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND); // HTTP 404
    }

    // Handle inventory update failure
    @ExceptionHandler(InventoryUpdateException.class)
    public ResponseEntity<Map<String, String>> handleInventoryUpdateException(InventoryUpdateException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR); // HTTP 500
    }

    @ExceptionHandler(SameShopTransferException.class)
    public ResponseEntity<Map<String, String>> handleSameShopTransferException(SameShopTransferException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND); // HTTP 404
    }

    // Handle validation errors from StockInRequest DTO
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST); // HTTP 400
    }
}


/*
Flow:

If an exception is thrown
(e.g., ShopNotFoundException in stockIn or MethodArgumentNotValidException in the controller),
Spring checks the @ControllerAdvice for a matching @ExceptionHandler.
If found, the handler processes the exception and returns a response
(e.g., JSON with error details). If no handler matches, the exception propagates,
typically resulting in a generic HTTP 500 error.
 */