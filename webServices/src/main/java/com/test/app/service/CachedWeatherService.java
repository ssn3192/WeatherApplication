package com.test.app.service;

import com.test.app.model.ExtendedWeatherData;
import com.test.app.model.WeatherForAddressData;
import com.test.app.model.WeatherForAddressResponse;
import com.test.app.service.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

/**
 * This service will retrieve the weather data from cache, else retrieve from delegate
 */
@Service
@Slf4j
public class CachedWeatherService {
    private final CacheManager cacheManager;
    private final GeoCodingService geoCodingService;
    private final WeatherService delegate;

    public CachedWeatherService(CacheManager cacheManager, GeoCodingService geoCodingService, WeatherService delegate) {
        this.cacheManager = cacheManager;
        this.geoCodingService = geoCodingService;
        this.delegate = delegate;
    }

    public WeatherForAddressResponse getWeatherByAddress(String address) {
        try{
            // get zip code, which will be used to retrieve data from cache
            Optional<String> zipCodeOpt = geoCodingService.getZipForAddress(address);
            // invalid address case
            if(zipCodeOpt.isEmpty()) {
                return WeatherForAddressResponse.getInvalidAddressResponse();
            }
            var cache = cacheManager.getCache("weather_cache");
            if(cache != null){
                try{
                    var cacheValue = cache.get(zipCodeOpt.get());
                    if(Objects.nonNull(cacheValue)) {
                        log.info("Value retrieved from cache");
                        // deserialize the cache value
                        var cachedWeatherData = (WeatherForAddressData) cacheValue.get();
                        return WeatherForAddressResponse.builder()
                                .data(cachedWeatherData)
                                .fromCache(true)
                                .build();
                    }
                }catch(Exception e){
                    log.warn("Error occurred while fetching data from cache. Fetching actual data", e);
                }
            }
            log.info("Trying to fetch from actual data source");
            var actualWeather = delegate.getWeatherByZip(zipCodeOpt.get());
            var extendedWeather = delegate.getExtendedWetherByZip(zipCodeOpt.get());
            var extendedWeatherData = ExtendedWeatherData.fromExtendedWeatherResponse(extendedWeather);
            var weatherData = WeatherForAddressData.fromWeatherResponse(actualWeather, extendedWeatherData);
            if(Objects.nonNull(cache)) {
                // store zip code -> json response data
                cache.put(zipCodeOpt.get(), weatherData);
            }else {
                log.warn("Could not add data into cache.");
            }

            return WeatherForAddressResponse.builder()
                    .fromCache(false)
                    .data(weatherData)
                    .build();
        }catch(Exception e){
            throw new ServiceException(String.format("Error fetching weather data from cache for address [%s]", address), e);
        }
    }
}
