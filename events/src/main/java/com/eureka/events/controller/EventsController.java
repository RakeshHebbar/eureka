package com.eureka.events.controller;

import com.eureka.common.dto.EventsResponse;
import com.eureka.events.dto.EventsRequest;
import com.eureka.events.dto.InterestedEventResponse;
import com.eureka.events.dto.InterestedEventsRequest;
import com.eureka.events.service.EventsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class EventsController {

    @Autowired
    private EventsService eventsService;

    @GetMapping(value = "/ping", produces = MediaType.APPLICATION_JSON_VALUE)
    public String ping() {
        return "This is events service!!";
    }


    @PostMapping(value = "/discover", consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EventsResponse> getEventsNearBy(@RequestBody EventsRequest request,
                                                          HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(eventsService.getEvents(request, null));
    }


    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EventsResponse> findEvents(@RequestParam(value = "key") String key) {
        return ResponseEntity.ok(eventsService.getEvents(new EventsRequest(), key));
    }


    @PostMapping(value = "/interested")
    public ResponseEntity<InterestedEventResponse> interestedEvents(@RequestBody InterestedEventsRequest request,
                                                                    HttpServletRequest httpServletRequest) {
        String userHeader = httpServletRequest.getHeader("userId");
        if(userHeader == null) {
            throw new RuntimeException("userId context cannot be null");
        }
        Integer userId = Integer.parseInt(userHeader);
        return ResponseEntity.ok(eventsService.interestedEvent(request, userId));
    }
}
