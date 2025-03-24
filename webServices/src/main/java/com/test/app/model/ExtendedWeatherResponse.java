package com.test.app.model;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Builder(toBuilder = true)
@Jacksonized
@Value
public class ExtendedWeatherResponse {
    List<WeatherForecast> list;

    @Builder(toBuilder = true)
    public record WeatherForecast(
            long dt, // Timestamp of the forecast
            TemperatureDetails main,
            List<WeatherType> weather
    ) {}

    @Builder(toBuilder = true)
    public record WeatherType(int id, String description) {}

    @Builder(toBuilder = true)
    public record TemperatureDetails(
            double temp, // Current temperature
            double temp_min, // Minimum temperature
            double temp_max, // Maximum temperature
            double feels_like, // Feels like temperature
            double humidity
    ) {}
}
