package com.test.app.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "weather.api")
@Data
public class WeatherConfig {
    private String key;
    private String baseUrl;
}
