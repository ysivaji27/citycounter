package com.citycounter;

import com.citycounter.controller.CityCounterController;
import com.citycounter.service.CityCounterService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(CityCounterController.class)
class CityCounterControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private CityCounterService cityCounterService;

    @Test
    @DisplayName("Should return city count from service")
    void getCityCount_shouldReturnCount() throws Exception {
        // Arrange: Define the behavior of the mocked service
        int expectedCount = 1; // Or whatever test value you want
        when(cityCounterService.getCityCountByLetter(anyString())).thenReturn(expectedCount);

        // Act & Assert
        mockMvc.perform(get("/api/city-count?letter=A"))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(expectedCount)));
    }

}