package com.athena.account;

import com.athena.AthenaUserDetails;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Date;
import java.util.UUID;

@SpringBootApplication
@EnableRedisHttpSession
@EnableEurekaClient
@RestController
@RequestMapping("/")
public class AccountApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountApplication.class, args);
    }

    public static String DNA = UUID.randomUUID().toString();

    @RequestMapping(method = RequestMethod.GET)
    public String home() {
        return "Hello World from Account-Service " + DNA + " at " + new Date();
    }

    @RequestMapping(value = "/username", method = RequestMethod.GET)
    public String username(final HttpServletRequest request, Authentication auth) {
        AthenaUserDetails user = (AthenaUserDetails) auth.getPrincipal();
        return "Username: " + auth.getName() + ", mobile: " + user.getMobile() + " at " + DNA + ". " + new Date();
    }
}
