package com.athena.edge.account;

import com.athena.common.user.AthenaUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Created by brook.xi on 11/13/2015.
 */
@Service
public class AccountService {
    @Autowired
    private UserRepository userRepository;

    public Optional<AthenaUserDetails> findUserByItem(String item) {
        Optional<User> user = userRepository.findByMobile(Long.parseLong(item));
        return Optional.ofNullable(
                user.map(u -> {
                    Set<GrantedAuthority> authorities = new HashSet<>();
                    u.getRoles().forEach(role -> {
                        authorities.add(new SimpleGrantedAuthority(role.getName()));
                    });
                    AthenaUserDetails userDetails = new AthenaUserDetails(u.getId(), u.getMobile(), u.getNickName(), Collections.unmodifiableSet(authorities));
                    userDetails.setPassword(u.getPassword());
                    return userDetails;
                }).orElse(null)
        );
    }
}
