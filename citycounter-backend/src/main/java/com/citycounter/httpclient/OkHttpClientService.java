package com.citycounter.httpclient;

import com.citycounter.exceptionhandler.ServiceUnavailableException;
import com.citycounter.exceptionhandler.WeatherApiException;
import com.citycounter.model.WeatherResponse;
import com.citycounter.service.impl.CityCounterServiceImpl;
import com.google.gson.Gson;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static com.citycounter.util.CityCounterUtil.REQ_RECEIVED_MSG;

@Slf4j
@Component
public class OkHttpClientService {

    private OkHttpClient httpClient;
    private Request request;
    private Gson gson;

    @Value("${openweathermap.url}")
    private String openweathermapUrl;

    @Value("${requestTimeout}")
    private int requestTimeout;

    @Value("${maxIdleConnections}")
    private int maxIdleConnections;

    @Value("${keepAliveDuration}")
    private  long keepAliveDuration;


    @PostConstruct
    public void init() {
        this.gson = new Gson();
        this.httpClient = new OkHttpClient.Builder()
                .connectionPool(new ConnectionPool(maxIdleConnections, keepAliveDuration, TimeUnit.MINUTES))
                .connectTimeout(requestTimeout, TimeUnit.SECONDS)
                .readTimeout(requestTimeout, TimeUnit.SECONDS)
                .build();

        this.request = new Request.Builder()
                .url(openweathermapUrl)
                .get()
                .build();
    }

    @Cacheable(value = "weatherDataCache")
    public WeatherResponse getWeatherData(String cityName) throws IOException {
        log.info(REQ_RECEIVED_MSG, CityCounterServiceImpl.class.getCanonicalName(), cityName);
        try (Response response = httpClient.newCall(this.request).execute()) {
            int statusCode = response.code();
            String responseBody = response.body().string();
            if (statusCode >= 200 && statusCode < 300) {
                return gson.fromJson(responseBody, WeatherResponse.class);
            } else if (statusCode >= 400 && statusCode < 500) {
                throw new WeatherApiException("Client error from external weather API: " + responseBody, HttpStatus.valueOf(statusCode));
            } else {
                String errorBody = response.body().string();
                log.error("Unexpected HTTP status code  {} {}  ", statusCode, errorBody);
                throw new ServiceUnavailableException("Unexpected response from external weather API: " + errorBody);
            }
        }
    }

}