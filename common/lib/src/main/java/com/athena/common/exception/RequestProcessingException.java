package com.athena.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

/**
 * Created by brook.xi on 11/20/2015.
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class RequestProcessingException extends RuntimeException {
    protected HttpStatus status;
    protected String error;
    protected String messageCode;
    protected Object[] args;

    public RequestProcessingException(HttpStatus status, String error, String messageCode) {
        super(error);

        this.status = status;
        this.error = error;
        this.messageCode = messageCode;
    }

    public RequestProcessingException(HttpStatus status, String error, String messageCode, Object... args) {
        this(status, error, messageCode);
        this.args = args;
    }
}
