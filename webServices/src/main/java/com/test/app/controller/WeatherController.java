package com.test.app.controller;

import com.test.app.model.WeatherForAddressResponse;
import com.test.app.model.WeatherResponse;
import com.test.app.service.CachedWeatherService;
import com.test.app.service.WeatherService;

import com.test.app.service.exception.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/weather")
public class WeatherController {
    private final CachedWeatherService weatherService;

    public WeatherController(CachedWeatherService weatherService){
        this.weatherService = weatherService;
    }

    @GetMapping
    public ResponseEntity<WeatherForAddressResponse> weatherData(@RequestParam @NonNull String address) {
        try{
            if(address.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(WeatherForAddressResponse.getInvalidAddressResponse());
            }
            var response = weatherService.getWeatherByAddress(address);
            if(!response.getErrors().isEmpty()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(response);
            }

            return ResponseEntity.ok(response);
        }catch(ServiceException ex) {
            var errorResponse = WeatherForAddressResponse.builder()
                    .errors(List.of("Something went wrong.Please contact your administrator"))
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(errorResponse);
        }
    }

    /*
        get extended forecast weather
     */
    //@GetMapping

}