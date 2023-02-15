package com.eureka.events.service;

import com.eureka.common.dto.EventsResponse;
import com.eureka.events.dto.EventsRequest;
import com.eureka.events.dto.InterestedEventResponse;
import com.eureka.events.dto.InterestedEventsRequest;

public interface EventsService {

    String TM_API_KEY = "MHqGC9u9P3SGEG9COZBzWbJSsGKby2dY";
    String TICKET_MASTER_API = "https://app.ticketmaster.com/discovery/v2/events.json?apikey="
            + TM_API_KEY;
    Integer DEFAULT_OFFSET = 0; // page number
    Integer DEFAULT_LIMIT = 100; // size


    String GEO_LOCATION_API = "http://localhost:8085/geolocation";


    /**
     * Fetches info from the ticket master APIs, select the required fields necessary
     * @param request Filter based on ip, set the limit and offset
     * @param searchKey search events based on key
     * @return Event details with pagination info
     */
    EventsResponse getEvents(EventsRequest request, String searchKey);


    /**
     * Saves the interested event to the DB
     */
    InterestedEventResponse interestedEvent(InterestedEventsRequest request, Integer userId);

}
