package com.eureka.events.exception;

public class ResourceNotFoundException extends RuntimeException {

    private final String exceptionMsg;

    public ResourceNotFoundException(String exceptionMsg) {
        this.exceptionMsg = exceptionMsg;
    }

    public String getExceptionMsg() {
        return exceptionMsg;
    }
}
