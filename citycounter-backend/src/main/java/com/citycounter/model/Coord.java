package com.citycounter.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Coord(
        double lon,
        double lat
) {}
