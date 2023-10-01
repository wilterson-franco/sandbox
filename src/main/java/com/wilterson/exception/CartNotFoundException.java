package com.wilterson.exception;

public class CartNotFoundException extends RuntimeException {

    public CartNotFoundException(String errorMessage) {
        super(errorMessage);
    }

    public CartNotFoundException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }
}
