package com.athena.common.configuration;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import org.springframework.cache.ehcache.EhCacheFactoryBean;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.acls.domain.*;
import org.springframework.security.acls.model.AclCache;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 * Created by brook.xi on 11/26/2015.
 */
@Configuration
public class AclConfig {
//    @Autowired
//    private DataSource dataSource;
//
//    @Bean
//    public DefaultMethodSecurityExpressionHandler defaultMethodSecurityExpressionHandler() {
//        return new AclBasedMethodSecurityExpressionHandler();
//    }

//    @Bean
//    public PermissionEvaluator aclPermissionEvaluator(AclService aclService) {
//        return new AclPermissionEvaluator(aclService);
//    }
//
//    @Bean
//    public AclService aclService(LookupStrategy lookupStrategy, AclCache aclCache) {
//        return new JdbcMutableAclService(dataSource, lookupStrategy, aclCache);
//    }
//
//    @Bean
//    public LookupStrategy lookupStrategy(AclCache aclCache, AclAuthorizationStrategy aclAuthorizationStrategy, AuditLogger auditLogger) {
//        return new BasicLookupStrategy(dataSource, aclCache, aclAuthorizationStrategy, auditLogger);
//    }

    @Bean
    public AclCache aclCache(Ehcache ehcache, AclAuthorizationStrategy aclAuthorizationStrategy, AuditLogger auditLogger) {
        return new EhCacheBasedAclCache(ehcache, new DefaultPermissionGrantingStrategy(auditLogger), aclAuthorizationStrategy);
    }

    @Bean
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

    @Bean
    public AclAuthorizationStrategy aclAuthorizationStrategy() {
        SimpleGrantedAuthority roleAdmin = new SimpleGrantedAuthority("ROLE_ADMIN");
        AclAuthorizationStrategyImpl aclAuthorizationStrategy = new AclAuthorizationStrategyImpl(roleAdmin, roleAdmin, roleAdmin);
        return  aclAuthorizationStrategy;
    }

    @Bean
    public AuditLogger auditLogger() {
        return new ConsoleAuditLogger();
    }
}
