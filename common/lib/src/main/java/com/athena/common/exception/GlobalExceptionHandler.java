package com.athena.common.exception;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by brook.xi on 11/20/2015.
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    protected MessageSource messageSource;

    @ExceptionHandler(RequestProcessingException.class)
    public ResponseEntity<ErrorResponse> handleRequestProcessingException(HttpServletRequest request, RequestProcessingException ex) {
        String message = messageSource.getMessage(ex.getMessageCode(), ex.getArgs(), ex.getMessageCode(), null);
        ErrorResponse response = new ErrorResponse(ex.getStatus().value(), ex.getError(), message, request.getServletPath());
        return new ResponseEntity<>(response, ex.getStatus());
    }
}
