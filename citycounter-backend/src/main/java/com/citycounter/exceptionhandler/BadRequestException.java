package com.citycounter.exceptionhandler;

import org.springframework.http.HttpStatus;

public class BadRequestException extends WeatherApiException {
    public BadRequestException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}