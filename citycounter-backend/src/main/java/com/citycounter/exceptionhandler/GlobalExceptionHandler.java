package com.citycounter.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Global exception handler for the CityCounter application.
 * This class handles exceptions thrown by the application and formats them into a consistent JSON response.
 * It uses Spring's @ControllerAdvice to apply to all controllers in the application.
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handles custom WeatherApiException and its subclasses (BadRequestException, ServiceUnavailableException).
     * This method catches exceptions that we explicitly throw from our service.
     */
    @ExceptionHandler(WeatherApiException.class)
    public ResponseEntity<Object> handleWeatherApiException(
            WeatherApiException ex, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", ex.getHttpStatus().value());
        body.put("error", ex.getHttpStatus().getReasonPhrase());
        body.put("message", ex.getMessage());
        body.put("path", request.getDescription(false).replace("uri=", "")); // Extract clean path

        // Log the exception details for debugging
        logger.error("Handling WeatherApiException:  ", ex);

        return new ResponseEntity<>(body, ex.getHttpStatus());
    }

    /**
     * Handles any other unexpected exceptions not specifically caught by other handlers.
     * This acts as a fallback for unhandled runtime errors.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllOtherExceptions(
            Exception exception, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        body.put("error", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        body.put("message", "An unexpected error occurred: " + exception.getMessage());
        body.put("path", request.getDescription(false).replace("uri=", ""));

        // Log the full stack trace for unexpected errors
        logger.error("Handling unexpected exception:",exception);

        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}