package com.backend.Lakshya.customException;

public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
