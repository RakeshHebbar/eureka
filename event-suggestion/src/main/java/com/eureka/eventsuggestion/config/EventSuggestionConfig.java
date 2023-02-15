package com.eureka.eventsuggestion.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class EventSuggestionConfig {

    @Value("${geo.api}")
    private String geoApiUrl;
    @Value("${events.api}")
    private String eventApiUrl;
}
