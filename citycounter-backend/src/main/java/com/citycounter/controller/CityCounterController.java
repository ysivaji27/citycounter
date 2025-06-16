package com.citycounter.controller;

import com.citycounter.exceptionhandler.WeatherApiException;
import com.citycounter.service.CityCounterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static com.citycounter.util.CityCounterUtil.REQ_RECEIVED_MSG;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class CityCounterController {

    Logger logger = LoggerFactory.getLogger(CityCounterController.class);

    private final CityCounterService cityCounterService;

    public CityCounterController(CityCounterService cityCounterService) {
        this.cityCounterService = cityCounterService;
    }

    @GetMapping("/city-count")
    public ResponseEntity<Integer> getCityCountByLetter(@RequestParam("letter") String letter) {
        logger.info(REQ_RECEIVED_MSG, CityCounterController.class.getCanonicalName(), letter);
        try {
        return ResponseEntity.ok( cityCounterService.getCityCountByLetter(letter));
        } catch (IOException exception) {
            throw new WeatherApiException("An unexpected error occurred while processing weather data.", exception, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
