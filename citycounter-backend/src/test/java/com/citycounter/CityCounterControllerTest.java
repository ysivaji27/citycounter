package com.citycounter;

import com.citycounter.controller.CityCounterController;
import com.citycounter.service.CityCounterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CityCounterControllerTest {

    private CityCounterService cityCounterService;
    private CityCounterController cityCounterController;

    @BeforeEach
    void setUp() {
        cityCounterService = mock(CityCounterService.class);
        cityCounterController = new CityCounterController(cityCounterService);
    }

    @Test
    @DisplayName("for Y Should return WeatherResponse on successful 200 OK")
    void testGetCityCountByLetter_Y_200_Status() {
        String letter = "y";
        int expectedCount = 1;
        when(cityCounterService.getCityCountByLetter(letter)).thenReturn(expectedCount);

        ResponseEntity<Integer> response = cityCounterController.getCityCountByLetter(letter);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expectedCount, response.getBody());
        verify(cityCounterService, times(1)).getCityCountByLetter(letter);
    }

    @Test
    @DisplayName("for Z Should return WeatherResponse on successful 200 OK")
    void testGetCityCountByLetter_Z_200_Status() {
        String letter = "z";
        int expectedCount = 3;
        when(cityCounterService.getCityCountByLetter(letter)).thenReturn(expectedCount);

        ResponseEntity<Integer> response = cityCounterController.getCityCountByLetter(letter);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expectedCount, response.getBody());
        verify(cityCounterService, times(1)).getCityCountByLetter(letter);
    }

}