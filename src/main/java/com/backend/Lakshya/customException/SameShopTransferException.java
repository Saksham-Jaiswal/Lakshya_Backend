package com.backend.Lakshya.customException;

public class SameShopTransferException extends RuntimeException {
    public SameShopTransferException(String message) {
        super(message);
    }
}