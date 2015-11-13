package com.athena.edge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.h2.tools.Server;
import java.sql.SQLException;
import java.util.Date;

@SpringBootApplication
@EnableRedisHttpSession
@EnableZuulProxy
@RestController
public class EdgeApplication {

    public static void main(String[] args) {
        SpringApplication.run(EdgeApplication.class, args);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home() {
        return "Hello World from Edge-Server at " + new Date();
    }

    // Config H2 tcp server when we're in development stage, in order to
    // enable inspecting the in-memory database content from outside.
    @Bean(name = "org.h2.tools.Server", initMethod = "start", destroyMethod = "stop")
    @Profile("dev")
    public Server h2TcpServer() throws SQLException {
        return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9999");
    }
}
