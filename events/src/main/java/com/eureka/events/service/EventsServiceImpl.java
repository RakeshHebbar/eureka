package com.eureka.events.service;

import com.eureka.common.HttpUtil;
import com.eureka.common.RestRequest;
import com.eureka.common.dto.*;
import com.eureka.events.config.EventsConfig;
import com.eureka.events.dao.EventsDao;
import com.eureka.events.dao.InterestedEventsDao;
import com.eureka.events.dao.VenueDao;
import com.eureka.events.dto.*;
import com.eureka.events.exception.ResourceNotFoundException;
import com.eureka.events.model.EventModel;
import com.eureka.events.model.InterestedEventModel;
import com.eureka.events.model.VenueModel;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@SuppressWarnings(value = "unchecked")
public class EventsServiceImpl implements EventsService {

    @Autowired
    private EventsDao eventsDao;

    @Autowired
    private VenueDao venueDao;

    @Autowired
    private InterestedEventsDao interestedEventsDao;

    @Autowired
    private EventsConfig eventsConfig;

    @Override
    public EventsResponse getEvents(EventsRequest request, String searchKey) {

        if(request.getLimit() == null) {
            request.setLimit(DEFAULT_LIMIT);
        }

        if(request.getOffset() == null) {
            request.setOffset(DEFAULT_OFFSET);
        }

        String tmAPI = TICKET_MASTER_API + "&page=" + request.getOffset()
                + "&size=" + request.getLimit();

        if(request.getIp() != null) {
            String latLong = getLatLong(request.getIp());
            if(latLong != null) {
                tmAPI += "&latlong=" + latLong;
            }
        }

        if(searchKey != null) {
            tmAPI += "&keyword=" + searchKey;
        }

        if(request.getEventIds() != null && !request.getEventIds().isEmpty()) {
            StringBuilder eventsParam = getEventIdParam(request.getEventIds());
            tmAPI += "&id=" + eventsParam;
        }

        System.out.println("TM API = " + tmAPI);
        RestRequest restRequest = new RestRequest(tmAPI);
        restRequest.setIsStringResponse(true); // to parse string output

        String apiResponse;
        try {
            apiResponse = HttpUtil.makeGetRequest(restRequest, null);
        }catch (Exception e) {
            // log error and return empty list
            log.error("Error while getting response from ticket master", e);
            return null;
        }

        List<TMEventsResponse> events = new ArrayList<>();
        Pagination pagination = new Pagination();

        if(apiResponse != null) {
            JSONObject obj = new JSONObject(apiResponse);


            JSONArray eventList = obj.getJSONObject("_embedded").getJSONArray("events");

            for(int i = 0; i < eventList.length(); i++) {
               JSONObject event = eventList.getJSONObject(i);

               TMEventsResponse response = new TMEventsResponse();
               response.setEventId(event.optString("id", null));
               response.setEventName(event.optString("name", null));
               response.setEventUrl(event.optString("url", null));
               if(event.has("distance"))
                   response.setDistance(event.optInt("distance", 0));
               response.setUnit(event.optString("units", null));


               List<TMEventVenues> venues = new ArrayList<>();
               JSONArray venueList = event.getJSONObject("_embedded").getJSONArray("venues");
               for(int j = 0; j < venueList.length(); j++ ) {
                   JSONObject venue = venueList.getJSONObject(j);

                   TMEventVenues eventVenue = new TMEventVenues();
                   eventVenue.setId(venue.optString("id", null));
                   eventVenue.setName(venue.optString("name", null));
                   eventVenue.setPostalCode(venue.optString("postalCode", null));
                   if(venue.has("city")) {
                       eventVenue.setCity(venue.getJSONObject("city").optString("name", null));
                   }

                   if(venue.has("country")) {
                       eventVenue.setCountry(venue.getJSONObject("country").optString("name", null));
                   }

                   if(venue.has("address")) {
                       eventVenue.setAddress(venue.getJSONObject("address").optString("line1", null));
                   }
                   venues.add(eventVenue);
               }
               response.setVenues(venues);

               // add it to the response list
               events.add(response);
            }

            JSONObject page = obj.getJSONObject("page");

            pagination.setSize(page.optInt("size", 0));
            pagination.setTotalElements(page.optInt("totalElements", 0));
            pagination.setTotalPages(page.optInt("totalPages", 0));
            pagination.setNumber(page.optInt("number", 0));
        }

        return new EventsResponse(events, pagination);
    }

    @Override
    public InterestedEventResponse interestedEvent(InterestedEventsRequest request, Integer userId) {
        // fetch the event based on eventIds
        StringBuilder eventsParam = getEventIdParam(request.getEventIds());
        String tmAPI = TICKET_MASTER_API + "&id=" + eventsParam;
        RestRequest restRequest = new RestRequest(tmAPI);
        restRequest.setIsStringResponse(true); // to parse string output

        String apiResponse;
        try {
            apiResponse = HttpUtil.makeGetRequest(restRequest, null);
        }catch (Exception e) {
            // log error and return empty list
            log.error("Error while getting response from ticket master", e);
            throw new RuntimeException("Error getting response from ticket master");
        }

        if(apiResponse != null) {
            JSONObject obj = new JSONObject(apiResponse);
            if(obj.has("_embedded")) {

                InterestedEventResponse response = new InterestedEventResponse();

                // there will always be only one event in the list
                JSONArray events = obj.getJSONObject("_embedded")
                        .getJSONArray("events");

                for(int i = 0; i < events.length(); i++) {
                    JSONObject event = events.getJSONObject(i);

                    // check and create the event model
                    checkAndSaveEventModel(event);

                    JSONArray venueList = event.getJSONObject("_embedded").getJSONArray("venues");
                    for(int j = 0; j < venueList.length(); j++) {
                        JSONObject venue = venueList.getJSONObject(j);
                        // check and create the venue model
                        checkAndSaveVenueModel(venue, event.getString("id"));
                    }

                    // check if user has already saved this event as interested
                    InterestedEventModel model = interestedEventsDao.getModel(event.getString("id"), userId);
                    if(model != null) {
                        log.warn("User has already saved the model, not processing event with id = "
                                + event.getString("id"));
                    }else {
                        InterestedEventModel interestedEventModel = new InterestedEventModel();
                        interestedEventModel.setUserId(userId);
                        interestedEventModel.setEventId(event.getString("id"));
                        interestedEventsDao.save(interestedEventModel);
                        response.getIds().add(event.getString("id"));
                    }
                }

                response.setMessage("Events added to the interested list");
                response.setMessageKey("EVENTS.ADD.SUCCESS");

                return response;
            }else {
                // no event found, handle it
                throw new ResourceNotFoundException("eventIds doesn't exist");
            }
        }
        return null;
    }

    private void checkAndSaveVenueModel(JSONObject venue, String eventId) {
        VenueModel existingVenue = venueDao.getById(venue.getString("id"));
        if(existingVenue == null) {
            VenueModel venueModel = new VenueModel();
            venueModel.setId(venue.getString("id"));
            venueModel.setName(venue.optString("name", null));
            venueModel.setEventId(eventId);

            if(venue.has("location")) {
                venueModel.setLongitude(venue.getJSONObject("location").getString("longitude"));
                venueModel.setLatitude(venue.getJSONObject("location").getString("latitude"));
            }
            venueDao.save(venueModel);
        }
    }


    private void checkAndSaveEventModel(JSONObject event) {
        EventModel existingModel = eventsDao.getById(event.getString("id"));
        if(existingModel == null) {
            // create event and save
            EventModel eventModel = new EventModel();
            eventModel.setId(event.getString("id"));
            eventModel.setName(event.optString("name", null));
            eventModel.setEventUrl(event.optString("url", null));
            eventsDao.save(eventModel);
        }
    }


    private static StringBuilder getEventIdParam(List<String> eventIds) {
        StringBuilder eventsParam = new StringBuilder();
        eventIds.forEach(event -> eventsParam.append(event).append(","));
        eventsParam.deleteCharAt(eventsParam.length() - 1); // remove the last comma
        return eventsParam;
    }

    private String getLatLong(String ip) {
        String apiUrl = eventsConfig.getGeoApiUrl() + "/geolocation";
        String latLong = null;
        if(ip != null) {
            apiUrl = apiUrl + "?ip_address=" + ip;
        }
        try {
            RestRequest request = new RestRequest(apiUrl);
            GeoData geoData = HttpUtil.makeGetRequest(request, new TypeToken<GeoData>() {}.getType());
            if(geoData != null) {
                latLong = geoData.getLatitude() + "," + geoData.getLongitude();
            }
        }catch (Exception e) {
            log.error("Error while getting the lat/long from Geo Service", e);
            throw new RuntimeException("Internal error");
        }
        return latLong;
    }
}
