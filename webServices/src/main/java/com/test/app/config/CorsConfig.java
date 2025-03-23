package com.test.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Allow CORS for all endpoints
                .allowedOrigins("http://localhost:4200") // Allow your frontend origin
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Allow these HTTP methods
                .allowedHeaders("*") // Allow any headers
                .allowCredentials(true); // Allow credentials (cookies, authorization headers)
    }
}