package com.eureka.authentication.controller;

import com.eureka.authentication.dto.AuthResponse;
import com.eureka.authentication.dto.UserDto;
import com.eureka.authentication.service.AuthenticationService;
import com.eureka.common.dto.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping(value = "/signup", consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Message> signUp(@RequestBody UserDto userDto) throws Exception {
        return ResponseEntity.ok(authenticationService.signUp(userDto));
    }


    @PostMapping(value = "/authenticate", consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthResponse> authenticate(@RequestBody UserDto request) throws Exception {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

}
