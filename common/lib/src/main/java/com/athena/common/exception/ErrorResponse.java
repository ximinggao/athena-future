package com.athena.common.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Date;

/**
 * Created by brook.xi on 11/20/2015.
 */
@Data
public class ErrorResponse {
    private final Integer status;
    private final String error;
    private final String message;
    private final String path;
    private final Date timestamp = new Date();
}
