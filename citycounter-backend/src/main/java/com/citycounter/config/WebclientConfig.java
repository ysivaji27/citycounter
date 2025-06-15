package com.citycounter.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

@Configuration
public class WebclientConfig {

    @Value("${openweathermap.base.url}")
    private String addressBaseUrl;

    @Bean
    public WebClient webClient() {

        HttpClient httpClient = HttpClient.create()
                .responseTimeout(Duration.ofSeconds(15));  // Set a response timeout of 5 seconds

        return WebClient.builder().baseUrl(addressBaseUrl)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }
}
