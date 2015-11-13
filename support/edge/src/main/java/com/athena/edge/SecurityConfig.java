package com.athena.edge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.*;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

/**
 * Created by brook.xi on 11/11/2015.
 */
@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    public static final String XSRF_TOKEN = "XSRF-TOKEN";

    @Autowired
    private AthenaUserDetailsService athenaUserDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(athenaUserDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .csrfTokenRepository(csrfTokenRepository())
                .and()
                .addFilterAfter(csrfHeaderFilter(), CsrfFilter.class)
                .formLogin()
                    .loginProcessingUrl("/login")
                    .permitAll()
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
                .and()
                .rememberMe()
                        //.userDetailsService(userDetailsService)
                .key(rememberMeKey)
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)
                .authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .authorizeRequests()
                .antMatchers("/", "/signup").permitAll()
                //.antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated();
    }

    private Filter csrfHeaderFilter() {
        return new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
                    throws IOException, ServletException {
                CsrfToken csrf = (CsrfToken)request.getAttribute(CsrfToken.class.getName());
                if (csrf != null) {
                    Cookie cookie = WebUtils.getCookie(request, XSRF_TOKEN);
                    String token = csrf.getToken();
                    if (cookie == null || (token != null && !token.equals(cookie.getValue()))) {
                        cookie = new Cookie(XSRF_TOKEN, token);
                        cookie.setPath("/");
                        response.addCookie(cookie);
                    }
                }
                filterChain.doFilter(request, response);
            }
        };
    }

    private CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repo = new HttpSessionCsrfTokenRepository();
        repo.setHeaderName("X-XSRF-TOKEN");
        return repo;
    }

    @Value("${athena.rememberMe.key}")
    private String rememberMeKey;

    private final AuthenticationSuccessHandler authenticationSuccessHandler = new SimpleUrlAuthenticationSuccessHandler() {
        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                            Authentication authentication) throws IOException, ServletException {
            response.setStatus(HttpStatus.NO_CONTENT.value());
            clearAuthenticationAttributes(request);
        }
    };

    private final AuthenticationFailureHandler authenticationFailureHandler =
            (HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) -> {
                setFailureResponse(response, HttpStatus.UNAUTHORIZED,
                        "Credentials you provided is invalid.",
                        "NULL",
                        "INVALID CREDENTIALS");
            };

    private final AccessDeniedHandler accessDeniedHandler =
            (HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) -> {
                if (accessDeniedException instanceof InvalidCsrfTokenException) {
                    setFailureResponse(response, HttpStatus.FORBIDDEN,
                            accessDeniedException.getMessage(),
                            request.getServletPath(),
                            "INVALID CSRF TOKEN");
                } else if (accessDeniedException instanceof MissingCsrfTokenException) {
                    setFailureResponse(response, HttpStatus.FORBIDDEN,
                            accessDeniedException.getMessage(),
                            request.getServletPath(),
                            "MISSING CSRF TOKEN");
                } else {
                    setFailureResponse(response, HttpStatus.FORBIDDEN, "" +
                                    "You are not privileged to access this resource.",
                            request.getServletPath(),
                            "AUTHORIZATION FAILURE");
                }
            };

    private final AuthenticationEntryPoint authenticationEntryPoint =
            (HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) -> {
                setFailureResponse(response, HttpStatus.UNAUTHORIZED,
                        "Full authentication is required to access this resource.",
                        request.getServletPath(),
                        "NOT AUTHENTICATED");
            };

    private void setFailureResponse(HttpServletResponse response, HttpStatus status, String message, String path, String error) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setStatus(status.value());

        StringBuilder builder = new StringBuilder();
        builder
                .append("{")
                .append("\"message\":\"").append(message).append("\", ")
                .append("\"path\":\"").append(path).append("\", ")
                .append("\"error\":\"").append(error).append("\"")
                .append("}");

        PrintWriter out = response.getWriter();
        out.print(builder.toString());
        out.flush();
        out.close();
    }

}
