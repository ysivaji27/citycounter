package com.citycounter.controller;

import com.citycounter.exceptionhandler.WeatherApiException;
import com.citycounter.service.CityCounterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static com.citycounter.util.CityCounterUtil.REQ_RECEIVED_MSG;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/api")
public class CityCounterController {

    private final CityCounterService cityCounterService;

    public CityCounterController(CityCounterService cityCounterService) {
        this.cityCounterService = cityCounterService;
    }

    @GetMapping("/city-count")
    public ResponseEntity<Integer> getCityCountByLetter(@RequestParam("letter") String cityName) {
        log.info(REQ_RECEIVED_MSG, CityCounterController.class.getCanonicalName(), cityName);
        try {
        return ResponseEntity.ok( cityCounterService.getCityCountByLetter(cityName));
        } catch (IOException exception) {
            throw new WeatherApiException("An unexpected error occurred while processing weather data.", exception, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
