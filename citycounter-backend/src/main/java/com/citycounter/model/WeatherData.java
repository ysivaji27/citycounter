package com.citycounter.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record WeatherData(
        long id,
        String name,
        Coord coord,
        Main main,
        long dt,
        Wind wind,
        Rain rain,
        Clouds clouds,
        List<Weather> weather
) {}
