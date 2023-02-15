package com.eureka.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter @Setter
@NoArgsConstructor
public class RestRequest {

    private String url;
    private Map<String, String> headers; // optional
    private String requestBody; // optional, for POST requests
    private Boolean isStringResponse = false;

    public RestRequest(String url) {
        this.url = url;
    }

}
