package com.eureka.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TMEventsResponse {
    private String eventId;
    private String eventName;
    private String eventUrl;
    private Integer distance;
    private String unit;
    private List<TMEventVenues> venues;


}
