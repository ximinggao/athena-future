package com.athena.account;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.ehcache.EhCacheFactoryBean;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.acls.AclPermissionEvaluator;
import org.springframework.security.acls.domain.*;
import org.springframework.security.acls.jdbc.BasicLookupStrategy;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.acls.jdbc.LookupStrategy;
import org.springframework.security.acls.model.AclCache;
import org.springframework.security.acls.model.AclService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.sql.DataSource;

/**
 * Created by brook.xi on 11/26/2015.
 */
@Configuration
public class AclConfig {
    @Autowired
    private DataSource dataSource;

    @Bean
    @Autowired
    public DefaultMethodSecurityExpressionHandler defaultMethodSecurityExpressionHandler(PermissionEvaluator permissionEvaluator) {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_BOSS > ROLE_STAFF > ROLE_USER");

        DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
        expressionHandler.setRoleHierarchy(roleHierarchy);
        expressionHandler.setPermissionEvaluator(permissionEvaluator);
        return expressionHandler;
    }

    @Bean
    @Autowired
    public PermissionEvaluator aclPermissionEvaluator(AclService aclService) {
        return new AclPermissionEvaluator(aclService);
    }

    @Bean
    @Autowired
    public AclService aclService(LookupStrategy lookupStrategy, AclCache aclCache) {
        return new JdbcMutableAclService(dataSource, lookupStrategy, aclCache);
    }

    @Bean
    @Autowired
    public LookupStrategy lookupStrategy(AclCache aclCache) {
        return new BasicLookupStrategy(dataSource, aclCache, aclAuthorizationStrategy(), auditLogger());
    }

    @Bean
    @Autowired
    public AclCache aclCache(Ehcache ehcache) {
        return new EhCacheBasedAclCache(ehcache, new DefaultPermissionGrantingStrategy(auditLogger()), aclAuthorizationStrategy());
    }

    @Bean
    @Autowired
    public EhCacheFactoryBean ehCacheFactoryBean(CacheManager cacheManager) {
        EhCacheFactoryBean ehCacheFactoryBean = new EhCacheFactoryBean();
        ehCacheFactoryBean.setCacheManager(cacheManager);
        ehCacheFactoryBean.setCacheName("aclCache");
        return ehCacheFactoryBean;
    }

    @Bean
    public EhCacheManagerFactoryBean ehCacheManagerFactoryBean() {
        return new EhCacheManagerFactoryBean();
    }

    private AclAuthorizationStrategy aclAuthorizationStrategy() {
        SimpleGrantedAuthority roleAdmin = new SimpleGrantedAuthority("ROLE_ADMIN");
        AclAuthorizationStrategyImpl aclAuthorizationStrategy = new AclAuthorizationStrategyImpl(roleAdmin, roleAdmin, roleAdmin);
        return  aclAuthorizationStrategy;
    }

    private AuditLogger auditLogger() {
        return new ConsoleAuditLogger();
    }
}
