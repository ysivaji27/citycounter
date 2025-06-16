package com.citycounter.service.impl;

import com.citycounter.exceptionhandler.WeatherApiException;
import com.citycounter.model.WeatherResponse;
import com.citycounter.service.CityCounterService;
import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

import static com.citycounter.util.CityCounterUtil.*;

@Service
public class CityCounterServiceImpl implements CityCounterService {

    Logger logger = LoggerFactory.getLogger(CityCounterServiceImpl.class);

    @Value("${openweathermap.url}")
    private String openweathermapUrl ;

    @Value("${requestTimeout}")
    private int  requestTimeout ;

    private final OkHttpClient httpClient;
    private final Gson gson;

    public CityCounterServiceImpl() {
        // Configure OkHttpClient with timeouts
        this.httpClient = new OkHttpClient.Builder().connectTimeout(requestTimeout, TimeUnit.SECONDS).build();
        this.gson = new Gson();
    }

    @Override
    public int  getCityCountByLetter(String letter) {
        logger.info(REQ_RECEIVED_MSG, CityCounterServiceImpl.class.getCanonicalName(), letter);
        Request request = new Request.Builder().url(openweathermapUrl).build();
        try (Response response = httpClient.newCall(request).execute()) {
            int statusCode = response.code();
            String responseBody = response.body().string();
            if (statusCode >= 200 && statusCode < 300) {
                WeatherResponse weatherResponse =  gson.fromJson(responseBody, WeatherResponse.class);
                if(weatherResponse!= null && weatherResponse.list() != null) {
                    logger.info(WEATHER_RESPONSE, CityCounterServiceImpl.class.getCanonicalName(), weatherResponse.list().size());
                    return (int) weatherResponse.list().stream()
                            .filter(weatherData -> {
                                String name = weatherData.name();
                                return name != null && name.toLowerCase().startsWith(letter.toLowerCase());
                            })
                            .count();
                } else {
                    logger.info(WEATHER_RESPONSE, CityCounterServiceImpl.class.getCanonicalName(), 0);
                    return 0; // Return 0 if the response is null or empty
                }

            } else if (statusCode >= 400 && statusCode < 500) {
                if (statusCode == HttpStatus.BAD_REQUEST.value()) {
                    throw new BadRequestException("Bad request to external weather API for identifier '" + letter + "': " + responseBody);
                }
                throw new WeatherApiException("Client error from external weather API: " + responseBody, HttpStatus.valueOf(statusCode));
            } else {
                String errorBody = response.body().string();
                logger.error("Unexpected HTTP status code  {} {}  ",statusCode,errorBody);
                throw new WeatherApiException("Unexpected response from external weather API: " + errorBody, HttpStatus.valueOf(statusCode));
            }
        } catch (Exception exception) {
            throw new WeatherApiException("An unexpected error occurred while processing weather data.", exception, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
