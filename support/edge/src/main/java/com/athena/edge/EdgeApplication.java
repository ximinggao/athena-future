package com.athena.edge;

import io.undertow.servlet.api.DeploymentInfo;
import org.h2.tools.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.undertow.UndertowDeploymentInfoCustomizer;
import org.springframework.boot.context.embedded.undertow.UndertowEmbeddedServletContainerFactory;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
        return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9980");
    }

    // Configuration related to Zuul error handling in Undertow.
    // Refer to http://blog.jmnarloch.io/2015/09/16/spring-cloud-zuul-error-handling/
    @Bean
    public UndertowEmbeddedServletContainerFactory embeddedServletContainerFactory() {
        UndertowEmbeddedServletContainerFactory factory = new UndertowEmbeddedServletContainerFactory();
        factory.addDeploymentInfoCustomizers(new UndertowDeploymentInfoCustomizer() {
            @Override
            public void customize(DeploymentInfo deploymentInfo) {
                deploymentInfo.setAllowNonStandardWrappers(true);
            }
        });
        return factory;
    }
}
