package com.athena.edge;

import com.athena.common.user.AthenaUserDetails;
import com.athena.edge.account.AccountService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Created by brook.xi on 11/13/2015.
 */
@Component
public class AthenaUserDetailsService implements UserDetailsService {
    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private AccountService accountService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AthenaUserDetails> userDetails = accountService.findUserByItem(username);
        return userDetails.map(user -> {
            if (user.getAuthorities().isEmpty()) {
                logger.debug("User '" + username + "' has no authorities and will be treated as 'not found'");
                throw new UsernameNotFoundException("No GrantedAuthority with user " + username);
            }
            return user;
        }).orElseThrow(() -> {
            logger.debug("Query returned no results for user '" + username + "'");
            return new UsernameNotFoundException("User not found for " + username);
        });
    }
}
