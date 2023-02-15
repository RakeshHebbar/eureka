package com.eureka.eventsuggestion.controller;

import com.eureka.common.dto.EventsResponse;
import com.eureka.eventsuggestion.service.PopularEventsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EventSuggestionController {

    @Autowired
    private PopularEventsService popularEventsService;

    @GetMapping(value = "/popular-events", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EventsResponse> suggestPopularEvents(@RequestParam(required = true, name = "ip") String ipAddress) {
        return ResponseEntity.ok(popularEventsService.suggestPopularEvents(ipAddress));
    }

}
