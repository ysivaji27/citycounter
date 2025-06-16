package com.citycounter.exceptionhandler;

import org.springframework.http.HttpStatus;

public class WeatherApiException extends RuntimeException {
    private final HttpStatus httpStatus;

    public WeatherApiException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public WeatherApiException(String message, Throwable cause, HttpStatus httpStatus) {
        super(message, cause);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
