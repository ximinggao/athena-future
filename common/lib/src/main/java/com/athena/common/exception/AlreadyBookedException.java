package com.athena.common.exception;

import org.springframework.http.HttpStatus;

/**
 * Created by brook.xi on 11/20/2015.
 */
public class AlreadyBookedException extends RequestProcessingException {
    public AlreadyBookedException(String messageCode, Object... args) {
        super(HttpStatus.CONFLICT, "ALREADY BOOKED", messageCode, args);
    }
}
