package com.athena.edge;

import com.athena.AthenaUserDetails;
import com.athena.edge.account.AccountService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

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
        AthenaUserDetails userDetails = accountService.findUserByItem(username);

        if (userDetails == null) {
            logger.debug("Query returned no results for user '" + username + "'");
            throw new UsernameNotFoundException("User not found for " + username);
        }
        if (userDetails.getAuthorities().isEmpty()) {
            logger.debug("User '" + username + "' has no authorities and will be treated as 'not found'");
            throw new UsernameNotFoundException("No GrantedAuthority with user " + username);
        }

        return userDetails;
    }
}
