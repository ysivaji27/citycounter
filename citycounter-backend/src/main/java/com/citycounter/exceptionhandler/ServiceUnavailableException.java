package com.citycounter.exceptionhandler;

import org.springframework.http.HttpStatus;

public class ServiceUnavailableException extends WeatherApiException {

    public ServiceUnavailableException(String message) {
        super(message, HttpStatus.SERVICE_UNAVAILABLE);
    }

    public ServiceUnavailableException(Throwable cause) {
        super(cause.getMessage(),cause, HttpStatus.SERVICE_UNAVAILABLE);
    }

}
