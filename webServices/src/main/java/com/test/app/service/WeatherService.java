package com.test.app.service;

import com.test.app.config.GeoCodeAPIConfig;
import com.test.app.config.WeatherConfig;
import com.test.app.model.WeatherResponse;
import com.test.app.service.exception.ServiceException;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class WeatherService {
    private final WeatherConfig weatherConfig;
    private final RestTemplate httpClient;

    public WeatherService(WeatherConfig weatherConfig,
                          RestTemplate httpClient) {
        this.weatherConfig =  weatherConfig;
        this.httpClient = httpClient;
    }

    public WeatherResponse getWeatherByZip(String zipCode) {
        try {
            // Build the URL for open weather API
            UriComponentsBuilder weatherUriBuilder = UriComponentsBuilder.fromUriString(weatherConfig.getBaseUrl().concat("/weather"))
                .queryParam("zip", zipCode)
                .queryParam("units", "imperial")
                .queryParam("appid", weatherConfig.getKey());

            return httpClient.getForEntity(weatherUriBuilder.toUriString(), WeatherResponse.class)
                    .getBody();
        }catch (Exception e) {
            throw new ServiceException(String.format("Error occurred while fetching weather details for zip [%s]", zipCode), e);
        }
    }
}