package com.athena.stadium.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;


/**
 * Created by Lingfeng on 2015/10/27.
 */
@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Value("${stadium.rememberMe.key}")
    private String rememberMeKey;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(new StadiumUserDetailsService(dataSource));
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .formLogin()
                    .successHandler(authenticationSuccessHandler)
                    .failureHandler(authenticationFailureHandler)
                .and().rememberMe()
                    .key(rememberMeKey)
                .and().exceptionHandling()
                    .accessDeniedHandler(accessDeniedHandler)
                    .authenticationEntryPoint(authenticationEntryPoint)
                .and().authorizeRequests()
                    .antMatchers("/stadium/hello").permitAll()
                    .antMatchers("/restdoc/**").permitAll()
                    .antMatchers("/admin/**").hasRole("ADMIN")
                    .anyRequest().authenticated();
    }

    private final AuthenticationSuccessHandler authenticationSuccessHandler =
            new SimpleUrlAuthenticationSuccessHandler() {
                @Override
                public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                    Authentication authentication) throws IOException, ServletException {
                    response.setStatus(HttpStatus.NO_CONTENT.value());
                    clearAuthenticationAttributes(request);
                }
            };

    private final AuthenticationFailureHandler authenticationFailureHandler =
            (request, response, exception) -> setFailureResponse(
                    response, HttpStatus.UNAUTHORIZED,
                    "Credentials you provided is invalid.",
                    "NULL",
                    "INVALID CREDENTIALS");

    private final AccessDeniedHandler accessDeniedHandler =
            (request, response, accessDeniedException) -> setFailureResponse(
                    response, HttpStatus.FORBIDDEN,
                    "You are not privileged to access this resource.",
                    request.getServletPath(),
                    "AUTHORIZATION FAILURE");

    private final AuthenticationEntryPoint authenticationEntryPoint =
            (request, response, authException) -> setFailureResponse(
                    response, HttpStatus.UNAUTHORIZED,
                    "Full authentication is required to access this resource.",
                    request.getServletPath(),
                    "NOT AUTHENTICATED"
            );

    private void setFailureResponse(HttpServletResponse response, HttpStatus status,
                                    String message, String path, String error) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setStatus(status.value());

        StringBuilder sb = new StringBuilder();
        sb
                .append("{")
                .append("\"message\":\"").append(message).append("\", ")
                .append("\"path\":\"").append(path).append("\", ")
                .append("\"error\":\"").append(error).append("\"")
                .append("}");

        PrintWriter writer = response.getWriter();
        writer.print(sb.toString());
        writer.flush();
        writer.close();
    }
}
