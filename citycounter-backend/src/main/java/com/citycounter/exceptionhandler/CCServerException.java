package com.citycounter.exceptionhandler;

public class CCServerException extends RuntimeException {
    public CCServerException(String message) {
        super(message);
    }
}