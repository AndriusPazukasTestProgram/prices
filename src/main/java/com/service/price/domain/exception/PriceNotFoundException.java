package com.service.price.domain.exception;

import com.service.price.domain.model.PriceQuery;

public class PriceNotFoundException extends RuntimeException{


    public PriceNotFoundException(String message) {
        super(message);
    }

}
