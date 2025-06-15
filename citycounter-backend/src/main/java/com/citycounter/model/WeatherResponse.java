package com.citycounter.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record WeatherResponse(
        String cod,
        double calctime,
        int cnt,
        List<WeatherData> list
) {}