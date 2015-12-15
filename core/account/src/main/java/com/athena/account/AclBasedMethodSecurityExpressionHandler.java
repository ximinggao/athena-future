package com.athena.account;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.EmbeddedServletContainerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.acls.AclPermissionEvaluator;
import org.springframework.security.acls.domain.AclAuthorizationStrategy;
import org.springframework.security.acls.domain.AuditLogger;
import org.springframework.security.acls.jdbc.BasicLookupStrategy;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.acls.model.AclCache;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * Created by brook.xi on 12/15/2015.
 *
 * Why we need to configure the acl based method security expression handler here?
 * Because there is cyclic initialization dependencies problem related to the DataSource during the Spring startup.
 * Refer to:
 * https://gist.github.com/dsyer/ebeb25d5afbdd9242cd5
 * https://jira.spring.io/browse/SEC-2815
 *
 * To avoid this issue, we have to lazily set the DataSource after the servlet container has been initialized.
 */
@Component
public class AclBasedMethodSecurityExpressionHandler extends DefaultMethodSecurityExpressionHandler
        implements ApplicationListener<EmbeddedServletContainerInitializedEvent> {
    private static Log logger = LogFactory.getLog(AclBasedMethodSecurityExpressionHandler.class);

    @Autowired
    private AclCache aclCache;

    @Autowired
    private AclAuthorizationStrategy aclAuthorizationStrategy;

    @Autowired
    private AuditLogger auditLogger;

    @Override
    public void onApplicationEvent(EmbeddedServletContainerInitializedEvent event) {
        logger.debug("Configure AclBasedMethodSecurityExpressionHandler after the servlet container has been initialized.");

        // Set role hierarchy
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_BOSS > ROLE_STAFF > ROLE_USER");
        this.setRoleHierarchy(roleHierarchy);

        // Set permission evaluator
        DataSource dataSource = event.getApplicationContext().getBean(DataSource.class);
        BasicLookupStrategy lookupStrategy = new BasicLookupStrategy(dataSource, aclCache, aclAuthorizationStrategy, auditLogger);
        JdbcMutableAclService aclService = new JdbcMutableAclService(dataSource, lookupStrategy, aclCache);
        AclPermissionEvaluator permissionEvaluator = new AclPermissionEvaluator(aclService);
        this.setPermissionEvaluator(permissionEvaluator);
    }
}
