package com.coffeeshopsystem.coffeeshopsystem.exception;

public class AuthenticationException extends RuntimeException {
    public AuthenticationException(String message) {
        super(message);
    }
}