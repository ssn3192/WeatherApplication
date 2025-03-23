package com.test.app;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.test.app.config.GeoCodeAPIConfig;
import com.test.app.config.WeatherConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
@EnableConfigurationProperties({
		WeatherConfig.class,
		GeoCodeAPIConfig.class
})
@EnableCaching
public class WeatherApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeatherApplication.class, args);
	}
}
