package com.eureka.eventsuggestion.service;

import com.eureka.common.dto.EventsResponse;

import java.util.List;

public interface PopularEventsService {


    String TM_API_KEY = "MHqGC9u9P3SGEG9COZBzWbJSsGKby2dY";

    String TICKET_MASTER_API = "https://app.ticketmaster.com/discovery/v2/events.json?apikey="
            + TM_API_KEY;

    String GEO_LOCATION_API = "http://localhost:8085/geolocation";
    String EVENT_SERVICE_API = "http://localhost:8086/discover";


   EventsResponse suggestPopularEvents(String ip);

}
