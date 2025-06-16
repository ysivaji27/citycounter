package com.citycounter.exceptionhandler;

import org.springframework.http.HttpStatus;

public class ServiceUnavailableException extends WeatherApiException {
    public ServiceUnavailableException(String message) {
        super(message, HttpStatus.SERVICE_UNAVAILABLE);
    }

    public ServiceUnavailableException(String message, Throwable cause) {
        super(message, cause, HttpStatus.SERVICE_UNAVAILABLE);
    }
}
