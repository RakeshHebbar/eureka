package com.eureka.common;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.lang.reflect.Type;

@SuppressWarnings(value = "unchecked")
public class HttpUtil {

    private static final Gson gson = new Gson();

    public static <T> T makeGetRequest(RestRequest restRequest, Type responseType) throws Exception {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(restRequest.getUrl());
            request.addHeader("content-type", "application/json");

            // add request headers, if present
            if(restRequest.getHeaders() != null) {
                restRequest.getHeaders().forEach(request::addHeader);
            }

            try (CloseableHttpResponse response = httpClient.execute(request)) {

                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    // return it as a String
                    String result = EntityUtils.toString(entity);
                    return restRequest.getIsStringResponse() ? (T) result : gson.fromJson(result, responseType);
                }
            }
        }

        return null;
    }


    public static <T> T makePostRequest(RestRequest restRequest, Type responseType) throws Exception {

        try {
            HttpPost post = new HttpPost(restRequest.getUrl());
            post.addHeader("content-type", "application/json");

            // add request headers, if present
            if(restRequest.getHeaders() != null) {
                restRequest.getHeaders().forEach(post::addHeader);
            }

            // set body
            if(restRequest.getRequestBody() != null)
                post.setEntity(new StringEntity(restRequest.getRequestBody()));

            try (CloseableHttpClient httpClient = HttpClients.createDefault();
                 CloseableHttpResponse response = httpClient.execute(post)) {
                String result = EntityUtils.toString(response.getEntity());
                return gson.fromJson(result, responseType);
            }
        }catch (Exception e) {
            System.out.println("Error in POST http call " + e.getMessage());
            throw e;
        }
    }


//    public static void main(String[] args) throws Exception {
//        RestRequest request = new RestRequest();
//        request.setUrl("https://api.agify.io/?name=bella");
//
//        Type type = new TypeToken<Bella>() {}.getType();
//        Bella bella = makeGetRequest(request, type);
//        if(bella != null) {
//            System.out.println(bella.getAge());
//            System.out.println(bella.getCount());
//            System.out.println(bella.getName());
//        }
//
//    }
//
//    public static class Bella {
//        private Integer count;
//        private Integer age;
//        private String name;
//
//        public Integer getCount() {
//            return count;
//        }
//
//        public void setCount(Integer count) {
//            this.count = count;
//        }
//
//        public Integer getAge() {
//            return age;
//        }
//
//        public void setAge(Integer age) {
//            this.age = age;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//    }

}



