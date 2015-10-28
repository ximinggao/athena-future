package com.athena.stadium.common.exception;

/**
 * Base exception class for the whole Athena system. Any other Athena application exception calls should inherit from this.
 *
 * Created by Lingfeng on 2015/10/27.
 */
public class StadiumException extends RuntimeException {

    public StadiumException() {
        super();
    }

    public StadiumException(String message) {
        super(message);
    }

    public StadiumException(Throwable cause) {
        super(cause);
    }

    public StadiumException(String message, Throwable cause) {
        super(message, cause);
    }
}
