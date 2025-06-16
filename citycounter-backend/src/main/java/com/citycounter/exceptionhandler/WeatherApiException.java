package com.citycounter.exceptionhandler;

import org.springframework.http.HttpStatus;

public class WeatherApiException extends RuntimeException {
    public final HttpStatus httpStatus;

    public WeatherApiException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public WeatherApiException(Throwable cause, HttpStatus httpStatus) {
        super(cause.getMessage(), cause);
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
