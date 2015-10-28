package com.athena.stadium;

import org.h2.tools.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.servlet.Filter;
import java.sql.SQLException;

/**
 * Created by Lingfeng on 2015/10/26.
 */
@SpringBootApplication
@EnableEurekaClient
@Profile("dbValid")
public class StadiumApplication {

    public static void main(String[] args) {
        SpringApplication.run(StadiumApplication.class, args);
    }

    // Config H2 tcp server when we're in development stage, in order to
    // enable inspecting the in-memory database content from outside.
    @Bean(name = "org.h2.tools.Server", initMethod = "start", destroyMethod = "stop")
    public Server h2TcpServer() throws SQLException {
        return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9999");
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

    @Bean
    public Filter characterEncodingFilter() {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        return filter;
    }
}
