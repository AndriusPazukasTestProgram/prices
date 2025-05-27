package com.service.price.domain.exception;

public class InvalidParameterException extends RuntimeException{

    public InvalidParameterException(String field, String message) {
        super(String.format("Invalid parameter '%s': %s", field, message));
    }
}
