package com.citycounter.model;

import java.util.List;

public record WeatherResponseList(
        List<WeatherResponse> weatherResponseList
) {}