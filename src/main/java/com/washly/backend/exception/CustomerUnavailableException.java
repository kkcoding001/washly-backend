package com.washly.backend.exception;

// Represents a customer account that is missing or inactive.
public class CustomerUnavailableException extends RuntimeException {

    public CustomerUnavailableException(String message) {
        super(message);
    }
}