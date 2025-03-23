package com.test.app.model;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Builder(toBuilder = true)
@Jacksonized
@Value
public class WeatherForAddressResponse {
    boolean fromCache;
    WeatherForAddressData data;
    @Builder.Default
    List<String> errors = List.of();

    public static WeatherForAddressResponse getInvalidAddressResponse() {
        var errors = List.of("Please provide valid address");
        return WeatherForAddressResponse.builder()
                .errors(errors)
                .build();
    }
}
