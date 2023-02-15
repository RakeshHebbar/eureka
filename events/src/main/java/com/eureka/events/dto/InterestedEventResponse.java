package com.eureka.events.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class InterestedEventResponse {

    private List<String> ids = new ArrayList<>();
    private String message;
    private String messageKey;

}
