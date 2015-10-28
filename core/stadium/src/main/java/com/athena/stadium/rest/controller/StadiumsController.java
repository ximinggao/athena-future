package com.athena.stadium.rest.controller;

import com.athena.stadium.common.util.StadiumUtil;
import com.athena.stadium.core.service.StadiumsService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Lingfeng on 2015/10/27.
 */
@RestController
@RequestMapping(value = "/stadium", produces = StadiumUtil.APPLICATION_JSON_UTF8)
public class StadiumsController {

    private static final Log logger = LogFactory.getLog(StadiumsController.class);

    @Autowired
    private StadiumsService stadiumsService;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String hello() {
        return "Hello World";
    }
}
