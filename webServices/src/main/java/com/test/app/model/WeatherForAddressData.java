package com.test.app.model;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Builder(toBuilder = true)
@Jacksonized
@Value
public class WeatherForAddressData {
    int id;
    double temp;
    double high;
    double low;
    String description;

    public static WeatherForAddressData fromWeatherResponse(WeatherResponse  weatherResponse) {
        return WeatherForAddressData.builder()
                .temp(weatherResponse.getMain().temp())
                .high(weatherResponse.getMain().temp_max())
                .low(weatherResponse.getMain().temp_min())
                .description(weatherResponse.getWeather().getFirst().description()) // fetching data for one address only
                .id(weatherResponse.getId())
                .build();
    }
}
