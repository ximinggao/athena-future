package com.athena.stadium.common.exception;

import lombok.Data;

/**
 * Created by Lingfeng on 2015/10/27.
 */
@Data
public class ApplicationError {

    private String code;
    private String cause;
    private String message;
}
