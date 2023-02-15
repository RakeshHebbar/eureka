package com.eureka.geolocation.service;

import com.eureka.common.HttpUtil;
import com.eureka.common.RestRequest;
import com.eureka.common.dto.GeoData;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;

@Service
@Slf4j
public class GeoLocationServiceImpl implements GeoLocationService {

    @Override
    public GeoData getGeoData(String ipAddress) {

        String apiUrl = ABSTRACT_API_URL;
        if(ipAddress != null)
            apiUrl = apiUrl + "&ip_address=" + ipAddress;

        Type type = new TypeToken<GeoData>() {}.getType();

        RestRequest restRequest = new RestRequest(apiUrl);
        try {
            return HttpUtil.makeGetRequest(restRequest, type);
        }catch (Exception e) {
            log.error("Error in getting geo location data", e);
            throw new RuntimeException("INTERNAL_SERVER_ERROR");
        }
    }
}
