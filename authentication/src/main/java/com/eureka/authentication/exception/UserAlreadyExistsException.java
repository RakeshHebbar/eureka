package com.eureka.authentication.exception;

public class UserAlreadyExistsException extends RuntimeException {
    private final String messageStr;

    public UserAlreadyExistsException(String messageStr) {
        this.messageStr = messageStr;
    }

    public String getMessageStr() {
        return messageStr;
    }
}
