package com.athena.account;

import com.athena.common.exception.AlreadyBookedException;
import com.athena.common.user.AthenaUserDetails;
import com.athena.common.user.CurrentUser;
import org.h2.tools.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.Date;
import java.util.UUID;

@SpringBootApplication
@EnableRedisHttpSession
@EnableEurekaClient
@RestController
@RequestMapping("/")
@ComponentScan("com.athena")
public class AccountApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountApplication.class, args);
    }

    // Config H2 tcp server when we're in development stage, in order to
    // enable inspecting the in-memory database content from outside.
    @Bean(name = "org.h2.tools.Server", initMethod = "start", destroyMethod = "stop")
    @Profile("dev")
    public Server h2TcpServer() throws SQLException {
        return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9990");
    }

    // It seems Spring Boot MessageSourceAutoConfiguration not working (need further checking),
    // so define a custom MessageSource here to work around.
    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames("messages");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setCacheSeconds(-1);
        return messageSource;
    }

    public static String DNA = UUID.randomUUID().toString();

    @RequestMapping(method = RequestMethod.GET)
    public String home() {
        return "Hello World from Account-Service " + DNA + " at " + new Date();
    }

    @RequestMapping(value = "/username", method = RequestMethod.GET)
    public String username(final HttpServletRequest request, Authentication auth) {
        AthenaUserDetails user = (AthenaUserDetails) auth.getPrincipal();
        return "Username: " + auth.getName()
                + ", mobile: " + user.getMobile()
                + ", nickName: " + CurrentUser.getNickname()
                + " at " + DNA + ". " + new Date();
    }

    @RequestMapping(value = "/exception", method = RequestMethod.GET)
    public void throwException() {
        throw new FakeException("Fake error");
    }

    @RequestMapping(value = "/exception2", method = RequestMethod.GET)
    public void throwException2() {
        throw new FakeException2("Fake error 2");
    }

    private class FakeException extends RuntimeException {
        public FakeException(String s) {
            super(s);
        }
    }

    @ResponseStatus(value = HttpStatus.CONFLICT, reason = "athena.fake.error")
    @ExceptionHandler(AccountApplication.FakeException.class)
    public void conflict() {
        // do nothing
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "athena.fake.error")
    private class FakeException2 extends RuntimeException {
        public FakeException2(String s) {
            super(s);
        }
    }

    @RequestMapping(value = "/booked", method = RequestMethod.GET)
    public void alreadyBooked() {
        throw new AlreadyBookedException("athena.already.booked", "Venue 1");
    }
}
