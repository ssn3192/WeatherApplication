package com.test.app.model;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Builder(toBuilder = true)
@Jacksonized
@Value
public class WeatherResponse {
  int id;
  int cod;
  TemperatureDetails main;
  List<WeatherType> weather;

  @Builder(toBuilder = true)
  public record WeatherType(int id, String description){}

  @Builder(toBuilder = true)
  public record TemperatureDetails(double temp,
                                   double feelsLike,
                                   double temp_min,
                                   double temp_max,
                                   double humidity
  ){}
}
