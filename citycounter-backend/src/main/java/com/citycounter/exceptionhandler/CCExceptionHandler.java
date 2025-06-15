package com.citycounter.exceptionhandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.citycounter.util.CityCounterUtil.*;

@ControllerAdvice
public class CCExceptionHandler {

    Logger logger = LoggerFactory.getLogger(CCExceptionHandler.class);

    @ExceptionHandler(CCClientException.class)
    public ResponseEntity<String>   handleClientException(CCClientException ex) {
        logger.error(CLIENT_ERROR_RESPONSE,CCExceptionHandler.class.getCanonicalName(), ex.getMessage());
        return  getErrorResponse(ex.getMessage());
    }
    @ExceptionHandler(CCServerException.class)
    public ResponseEntity<String> handleServerException(CCServerException ex) {
        logger.error(SERVER_ERROR_RESPONSE,CCExceptionHandler.class.getCanonicalName(), ex.getMessage());
        return new ResponseEntity<>(getErrorResponse(ex.getMessage()).getStatusCode());
    }

    private ResponseEntity<String> getErrorResponse(String message) {
        return   switch (message) {
            case HTTP_400 -> new ResponseEntity<>(HttpStatus.BAD_REQUEST.getReasonPhrase(),  HttpStatus.BAD_REQUEST);
            case HTTP_401 -> new ResponseEntity<>(HttpStatus.UNAUTHORIZED.getReasonPhrase(),  HttpStatus.UNAUTHORIZED);
            case HTTP_404 -> new ResponseEntity<>(HttpStatus.NOT_FOUND.getReasonPhrase(),  HttpStatus.NOT_FOUND);
            case HTTP_500 -> new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),  HttpStatus.INTERNAL_SERVER_ERROR);
            default -> new ResponseEntity<>(HttpStatus.REQUEST_TIMEOUT.getReasonPhrase(),  HttpStatus.REQUEST_TIMEOUT);
        };
    }
}
