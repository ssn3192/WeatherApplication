package com.test.app.service;

import com.test.app.config.GeoCodeAPIConfig;
import com.test.app.model.GeoCodeResponse;
import com.test.app.service.exception.ServiceException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.lang.String.format;

/**
 * This service is used get geolocation data from user address
 */
@Service
public class GeoCodingService {
    private final GeoCodeAPIConfig geoCodeAPIConfig;
    private final RestTemplate httpClient;

    public GeoCodingService(GeoCodeAPIConfig geoCodeAPIConfig, RestTemplate httpClient) {
        this.geoCodeAPIConfig = geoCodeAPIConfig;
        this.httpClient = Objects.requireNonNull(httpClient); // make sure httpclient initialized correctly
    }

    public Optional<String> getZipForAddress(String address) {
        try{
            UriComponentsBuilder geoCodeUriBuilder = UriComponentsBuilder.fromUriString(geoCodeAPIConfig.getBaseUrl().concat("/geocode/json"))
                    .queryParam("address", URLEncoder.encode(address, StandardCharsets.UTF_8))
                    .queryParam("key", geoCodeAPIConfig.getKey());
            var geoCodeResponse =  httpClient.getForEntity(geoCodeUriBuilder.toUriString(), GeoCodeResponse.class);

            if(!geoCodeResponse.hasBody() ||
                    Objects.isNull(geoCodeResponse.getBody()) ||
                    Objects.isNull(geoCodeResponse.getBody().getResults()) ||
                    geoCodeResponse.getBody().getResults().isEmpty()
            ) {
                return Optional.empty();
            }

            return geoCodeResponse.getBody().getResults()
                    .stream()
                    .flatMap(info -> info.addressComponents().stream()) // Flatten address components
                    .filter(component -> component.types().contains("postal_code")) // Find "postal_code"
                    .map(GeoCodeResponse.AddressComponent::shortName) // Extract short_name
                    .findFirst();
        }catch(Exception e) {
            throw new ServiceException(format("Error fetching geo location for address [%s]", address), e);
        }
    }
}
