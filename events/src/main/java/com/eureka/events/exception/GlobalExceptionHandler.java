package com.eureka.events.exception;

import com.eureka.common.dto.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public Message handleResourceNotFound(ResourceNotFoundException ex) {
        return new Message(ex.getMessage(), HttpStatus.NOT_FOUND.toString());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public Message internalServerError(RuntimeException ex) {
        log.error("internal error", ex);
        return new Message(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.toString());
    }

}
