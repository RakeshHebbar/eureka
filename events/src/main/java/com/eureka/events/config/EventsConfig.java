package com.eureka.events.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class EventsConfig {

    @Value("${geo.api}")
    private String geoApiUrl;

}
