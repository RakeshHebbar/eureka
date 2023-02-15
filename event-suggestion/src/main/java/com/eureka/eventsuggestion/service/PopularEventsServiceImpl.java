package com.eureka.eventsuggestion.service;

import com.eureka.common.HttpUtil;
import com.eureka.common.RestRequest;
import com.eureka.common.dto.EventsResponse;
import com.eureka.common.dto.GeoData;
import com.eureka.eventsuggestion.config.EventSuggestionConfig;
import com.eureka.eventsuggestion.dao.PopularEventsDao;
import com.eureka.eventsuggestion.dto.LatLong;
import com.eureka.eventsuggestion.dto.PostRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class PopularEventsServiceImpl implements PopularEventsService {

    @Autowired
    private PopularEventsDao popularEventsDao;

    @Autowired
    private EventSuggestionConfig config;

    @Override
    public EventsResponse suggestPopularEvents(String ip) {
        LatLong latLong = getLatLong(ip);
        List<String> events;
        EventsResponse response = new EventsResponse();
        if(Objects.nonNull(latLong)) {
            // suggest popular events based on 5 miles radius
           events = popularEventsDao.getEventsBasedOnPopularity(latLong);
           if(events != null && !events.isEmpty()) {
               RestRequest request = new RestRequest(config.getEventApiUrl() + "/discover");
               String jsonStr = new Gson().toJson(new PostRequest(events));
               request.setRequestBody(jsonStr);
               try {
                   response = HttpUtil.makePostRequest(request, EventsResponse.class);
               }catch (Exception e) {
                   log.error("Error in getting event data from event service", e);
                   throw new RuntimeException("Internal Error!!");
               }

           }
        }
        response.setPaginationInfo(null); // not needed
        return response;
    }


    private LatLong getLatLong(String ip) {
        String apiUrl = config.getGeoApiUrl() + "/geolocation";
        LatLong latLong = null;
        if(ip != null) {
            apiUrl = apiUrl + "?ip_address=" + ip;
        }
        try {
            RestRequest request = new RestRequest(apiUrl);
            GeoData geoData = HttpUtil.makeGetRequest(request, new TypeToken<GeoData>() {}.getType());
            if(geoData != null) {
               latLong = new LatLong(Double.valueOf(geoData.getLatitude()),
                       Double.valueOf(geoData.getLongitude()));
            }
        }catch (Exception e) {
            log.error("Error while getting the lat/long from Geo Service", e);
            throw new RuntimeException("Internal error");
        }
        return latLong;
    }
}
