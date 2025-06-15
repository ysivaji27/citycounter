package com.citycounter.exceptionhandler;

public class CCClientException extends RuntimeException {
    public CCClientException(String statusCode) {
        super(statusCode);
    }
}