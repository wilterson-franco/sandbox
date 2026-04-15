package com.example.demo.token;

public class TokenTimeoutException extends RuntimeException {
    public TokenTimeoutException(String message) {
        super(message);
    }

    public TokenTimeoutException(String message, Throwable cause) {
        super(message, cause);
    }
}