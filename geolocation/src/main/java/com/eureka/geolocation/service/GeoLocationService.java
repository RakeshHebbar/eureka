package com.eureka.geolocation.service;

import com.eureka.common.dto.GeoData;

public interface GeoLocationService {


    String ABSTRACT_API_ROOT = "https://ipgeolocation.abstractapi.com/v1";
    String ABSTRACT_API_KEY = "134b18f468a34c1c98e0077ad0365271";
    String ABSTRACT_API_URL = ABSTRACT_API_ROOT + "/?api_key=" + ABSTRACT_API_KEY;

    /**
     * Gets geo data using ip address
     * @param ipAddress ip of the client
     * @return geo data
     */
    GeoData getGeoData(String ipAddress);

}
