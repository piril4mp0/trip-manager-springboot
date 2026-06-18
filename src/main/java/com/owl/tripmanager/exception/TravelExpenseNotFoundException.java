package com.owl.tripmanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class TravelExpenseNotFoundException extends RuntimeException {
    public TravelExpenseNotFoundException(String message) {
        super(message);
    }
}
