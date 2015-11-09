package com.athena.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@SpringBootApplication
@EnableRedisHttpSession
@EnableEurekaClient
@RestController
public class AccountApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountApplication.class, args);
    }

    public static String DNA = UUID.randomUUID().toString();

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home() {
        return "Hello World from Account-Service: " + DNA;
    }
}
