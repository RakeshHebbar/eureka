package com.eureka.events.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class InterestedEventsRequest {
    private List<String> eventIds;
}
