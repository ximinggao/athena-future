package com.athena.edge.account;

import com.athena.common.user.AthenaUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by brook.xi on 11/13/2015.
 */
@Service
public class AccountService {
    @Autowired
    private UserRepository userRepository;

    public AthenaUserDetails findUserByItem(String item) {
        User user = userRepository.findByMobile(Long.parseLong(item));
        if (user != null) {
            Set<GrantedAuthority> authorities = new HashSet<>();
            user.getRoles().forEach(role -> {
                authorities.add(new SimpleGrantedAuthority(role.getName()));
            });
            AthenaUserDetails userDetails =
                    new AthenaUserDetails(user.getId(), user.getMobile().toString(), Collections.unmodifiableSet(authorities));
            userDetails.setPassword(user.getPassword());
            return userDetails;
        }
        return null;
    }
}
