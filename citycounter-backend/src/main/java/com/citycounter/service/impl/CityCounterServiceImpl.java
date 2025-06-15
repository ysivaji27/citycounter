package com.citycounter.service.impl;

import com.citycounter.exceptionhandler.CCClientException;
import com.citycounter.exceptionhandler.CCServerException;
import com.citycounter.model.WeatherResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static com.citycounter.util.CityCounterUtil.*;

@Service
public class CityCounterServiceImpl implements com.citycounter.service.CityCounterService {

    Logger logger = LoggerFactory.getLogger(CityCounterServiceImpl.class);

    @Value("${openweathermap.context.path}")
    private String openweathermapUrl;

    private final WebClient webClient;

    // Constructor-based dependency injection for WebClient
    @Autowired
    public CityCounterServiceImpl(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public int getCityCountByLetter(String letter) {
        logger.info(REQ_RECEIVED_MSG,CityCounterServiceImpl.class.getCanonicalName(), letter);
        WeatherResponse weatherResponse = webClient.get().uri(openweathermapUrl)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        Mono.error(new CCClientException(response.statusCode().toString())))
                .onStatus(HttpStatusCode::is5xxServerError, response ->
                        Mono.error(new CCServerException(response.statusCode().toString())))
                .bodyToMono(WeatherResponse.class).block();
        logger.info(REQ_COMPLETED_MSG,CityCounterServiceImpl.class.getCanonicalName(), letter);

        // Check if the response is null
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

    }
}
