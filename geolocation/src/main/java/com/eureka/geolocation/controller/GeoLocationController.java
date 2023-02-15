package com.eureka.geolocation.controller;

import com.eureka.geolocation.service.GeoLocationService;
import com.eureka.common.dto.GeoData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class GeoLocationController {

    @Autowired
    private GeoLocationService geoLocationService;

    @GetMapping(value = "/geolocation", consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GeoData> geoLocate(@RequestParam(value = "ip_address", required = false) String ipAddress) {
        return ResponseEntity.ok(geoLocationService.getGeoData(ipAddress));
    }


}
