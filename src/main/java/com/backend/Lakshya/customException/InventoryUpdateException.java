package com.backend.Lakshya.customException;

public class InventoryUpdateException extends RuntimeException {
    public InventoryUpdateException(String message) {
        super(message);
    }
}