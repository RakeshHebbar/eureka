package com.eureka.authentication.exception;

import com.eureka.common.dto.Message;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public Message userAlreadyExistsException(UserAlreadyExistsException ex) {
        return new Message(ex.getMessageStr(), "USER.ALREADY.EXISTS");
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public Message internalServerError(Exception ex) {
        return new Message(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.toString());
    }


    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public Message badCredentialException(BadCredentialsException ex) {
        return new Message(ex.getMessage(), "INVALID_CREDENTIALS");
    }


    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Message illegalArgEx(IllegalArgumentException ie) {
        return new Message(ie.getMessage(), HttpStatus.BAD_REQUEST.toString());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public Message resourceNotFound(UsernameNotFoundException ex) {
        return new Message(ex.getMessage(), HttpStatus.NOT_FOUND.toString());
    }

}
