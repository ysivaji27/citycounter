package com.citycounter.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Main(
        double temp,
        @JsonProperty("temp_min") double tempMin,
        @JsonProperty("temp_max") double tempMax,
        double pressure,
        @JsonProperty("sea_level") double seaLevel,
        @JsonProperty("grnd_level") double grndLevel,
        int humidity
) {}
