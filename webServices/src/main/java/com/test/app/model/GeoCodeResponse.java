package com.test.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;


@Builder(toBuilder = true)
@Jacksonized
@Value
public class GeoCodeResponse {
    List<Information> results;
    String status;

    @Builder(toBuilder = true)
    public record AddressComponent(
            @JsonProperty("long_name")
            String longName,
            @JsonProperty("short_name")
            String shortName,
            List<String> types
    ){}

    @Builder(toBuilder = true)
    public record Geometry(Location location){}

    @Builder(toBuilder = true)
    public record Location(double lat, double lng){}

    @Builder(toBuilder = true)
    public record Information(
            @JsonProperty("address_components")
            List<AddressComponent> addressComponents,
            Geometry geometry,
            String placeId //would be better option for cache key, as it will support city
    ){ }
}
