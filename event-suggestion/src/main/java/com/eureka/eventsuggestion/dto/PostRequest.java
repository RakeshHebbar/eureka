package com.eureka.eventsuggestion.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
public class PostRequest {
    private List<String> eventIds;
}
