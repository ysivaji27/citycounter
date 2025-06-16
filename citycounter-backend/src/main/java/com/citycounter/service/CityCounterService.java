package com.citycounter.service;

import java.io.IOException;

public interface CityCounterService {
    int  getCityCountByLetter(String letter) throws IOException;
}
