package com.conectacaboverde.exception;

public class UnauthorizedBusinessAccessException extends RuntimeException {

    public UnauthorizedBusinessAccessException(String message) {
        super(message);
    }
}