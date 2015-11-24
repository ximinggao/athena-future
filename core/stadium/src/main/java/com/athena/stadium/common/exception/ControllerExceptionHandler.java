package com.athena.stadium.common.exception;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.*;

/**
 * Created by Lingfeng on 2015/10/27.
 */
@ControllerAdvice
@ResponseBody
public class ControllerExceptionHandler {

    private static final Log logger = LogFactory.getLog(ControllerExceptionHandler.class);

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, Object> result = new HashMap<>();

        result.put("error", "VALIDATION_FAILURE");
        result.put("messages", convertConstraintViolations(ex));
        result.put("timestamp", new Date());

        return result;
    }

    @ExceptionHandler(StadiumException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApplicationError handleStadiumException(StadiumException ex) {
        ApplicationError error = new ApplicationError();

        error.setCode("stadium.error.code");
        error.setCause("cause");
        error.setMessage("message");

        logger.debug("Application exception caught ", ex);

        return error;
    }

    private List<String> convertConstraintViolations(MethodArgumentNotValidException ex) {
        List<String> result = new ArrayList<>();

        ex.getBindingResult().getAllErrors().forEach(error -> {
            if (error instanceof FieldError) {
                FieldError fe = (FieldError) error;

                logger.debug("Error code: " + fe.getCodes()[0]);
                result.add(messageSource.getMessage(
                        fe.getCodes()[0], new Object[] {fe.getField()}, fe.getDefaultMessage(), null));
            } else {
                logger.info("Non-FieldError encountered: " + error.getClass().getName());
            }
        });

        return result;
    }
}
