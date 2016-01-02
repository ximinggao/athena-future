package com.athena.common.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

/**
 * Created by brook.xi on 12/15/2015.
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AthenaGlobalMethodSecurityConfiguration extends GlobalMethodSecurityConfiguration {
    @Autowired
    private AclBasedMethodSecurityExpressionHandler expressionHandler;

    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        return expressionHandler;
    }
}
