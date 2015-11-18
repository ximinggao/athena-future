package com.athena.common.user;

import lombok.Data;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;

/**
 * Created by brook.xi on 11/13/2015.
 */
@Data
public class AthenaUserDetails implements UserDetails, CredentialsContainer {
    private final Long id;
    private String password;
    private final Long mobile;
    private final String nickname;
    private final Set<GrantedAuthority> authorities;
    private final boolean accountNonExpired = true;
    private final boolean accountNonLocked = true;
    private final boolean credentialsNonExpired = true;
    private final boolean enabled = true;

    @Override
    public String getUsername() {
        return id.toString();
    }

    @Override
    public void eraseCredentials() {
        password = null;
    }
}
