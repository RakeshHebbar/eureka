package com.eureka.common.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Message {

    private String message;
    private String messageKey;

    public Message() {};

    public Message(String message, String messageKey) {
        this.message = message;
        this.messageKey = messageKey;
    }
}
