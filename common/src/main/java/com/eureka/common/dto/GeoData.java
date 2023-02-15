package com.eureka.common.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GeoData {

    @SerializedName(value = "ip_address")
    private String ipAddress;
    private String city;
    private String country;
    private String latitude;
    private String longitude;

}
