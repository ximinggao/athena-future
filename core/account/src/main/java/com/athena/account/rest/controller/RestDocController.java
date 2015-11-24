package com.athena.account.rest.controller;

import com.athena.account.rest.domain.UserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lingfeng on 2015/11/3.
 */
@RestController
@RequestMapping(value = "/restdoc", produces = "application/json;charset=UTF-8")
public class RestDocController {

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String hello() {
        return "Hello World";
    }

    @RequestMapping(value = "/obj", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> obj() {
        Map<String, Object> result = new HashMap<>();
        result.put("name", "name");
        result.put("pwd", "password");

        return result;
    }

    @RequestMapping(value = "/objJson", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> objJson() {
        Map<String, Object> result = new HashMap<>();
        result.put("name", "name");
        result.put("pwd", "password");

        Map<String, Object> subMap = new HashMap<>();
        subMap.put("username", "username");

        result.put("info", subMap);

        return result;
    }

    @RequestMapping(value = "/objParam", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public String objParam(@RequestBody UserDTO dto) {

        return new StringBuilder().append(dto.getName()).append(dto.getPwd()).toString();
    }

    @RequestMapping(value = "/{id}/{mobile}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String objPath(@PathVariable Long id, @PathVariable String mobile) {

        return new StringBuilder().append("parameters id is ").append(id)
                .append(", parameters mobile is ").append(mobile).toString();
    }

    @RequestMapping(value = "/headerParam", method = RequestMethod.GET, headers = "Authorization=Base dXNlcjpzWNyZXQ=")
    @ResponseStatus(HttpStatus.OK)
    public String headerParam(HttpServletResponse response) {

        response.addHeader("X-RateLimit-Limit", "10");
        response.addHeader("X-RateLimit-Remaining", "2");
        response.addHeader("X-RateLimit-Reset", "5");

        return "headers";
    }
}
