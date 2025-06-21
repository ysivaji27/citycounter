package com.citycounter.service.impl;

import com.citycounter.httpclient.OkHttpClientService;
import com.citycounter.model.WeatherResponse;
import com.citycounter.service.CityCounterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static com.citycounter.util.CityCounterUtil.*;

@Slf4j
@Service
public class CityCounterServiceImpl implements CityCounterService {

    private final OkHttpClientService  okHttpClientService;

    public CityCounterServiceImpl(OkHttpClientService okHttpClientService) {
        this.okHttpClientService = okHttpClientService;
    }

    @Override
    public int  getCityCountByLetter(String cityName) throws IOException {
        log.info(REQ_RECEIVED_MSG, CityCounterServiceImpl.class.getCanonicalName(), cityName);
        WeatherResponse weatherResponse = okHttpClientService.getWeatherData(cityName);
        if(weatherResponse!= null && weatherResponse.list() != null) {
            log.info(WEATHER_RESPONSE, CityCounterServiceImpl.class.getCanonicalName(), weatherResponse.list().size());
            return (int) weatherResponse.list().stream()
                    .filter(weatherData -> {
                        String name = weatherData.name();
                        return name != null && name.toLowerCase().startsWith(cityName.toLowerCase());
                    })
                    .count();
        } else {
            log.info(WEATHER_RESPONSE, CityCounterServiceImpl.class.getCanonicalName(), 0);
            return 0; // Return 0 if the response is null or empty
        }
    }

}
