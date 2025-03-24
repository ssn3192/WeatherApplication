package com.test.app.model;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.text.SimpleDateFormat;
import java.util.*;


@Builder(toBuilder = true)
@Jacksonized
@Value
public class ExtendedWeatherData {

    List<WeatherForecastData> forecasts;

    @Builder(toBuilder = true)
    public record WeatherForecastData(
            Date dt,        // Timestamp of the forecast
            double temp_min, // Minimum temperature
            double temp_max  // Maximum temperature
    ) {}

    public static ExtendedWeatherData fromExtendedWeatherResponse(ExtendedWeatherResponse response) {
       /* List<WeatherForecastData> forecastDataList = response.getList().stream()
                .map(forecast -> WeatherForecastData.builder()
                        .dt(new Date(forecast.dt() * 1000))
                        .temp_min(forecast.main().temp_min())
                        .temp_max(forecast.main().temp_max())
                        .build())
                .toList();
        return ExtendedWeatherData.builder()
                .forecasts(forecastDataList)
                .build(); */

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Set<String> seenDates = new HashSet<>();
        List<WeatherForecastData> filteredForecasts = new ArrayList<>();

        for (var forecast : response.getList()) {
            Date date = new Date(forecast.dt() * 1000);
            String formattedDate = dateFormat.format(date);
            if (seenDates.add(formattedDate)) {  // Adds only if it's a new date
                filteredForecasts.add(new WeatherForecastData(date, forecast.main().temp_min(), forecast.main().temp_max()));
            }
        }

        return ExtendedWeatherData.builder().forecasts(filteredForecasts).build();
    }
}
