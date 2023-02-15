package com.eureka.events.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class EventsRequest {
    private String ip;
    private List<String> eventIds;
    private Integer limit;
    private Integer offset;
}
