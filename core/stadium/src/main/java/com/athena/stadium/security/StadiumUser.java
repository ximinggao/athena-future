package com.athena.stadium.security;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.*;

/**
 * Created by Lingfeng on 2015/10/27.
 */
public class StadiumUser implements UserDetails, CredentialsContainer {

    private long id;
    private String password;

    private final String username;
    private final String mobilePhone;
    private final boolean enabled;
    private final boolean accountNonExpired;
    private final boolean accountNonLocked;
    private final boolean credentialsNonExpired;
    private final Set<GrantedAuthority> authorities;

    public StadiumUser(long id, String username, String mobilePhone, String password,
                       boolean enabled, Collection<? extends GrantedAuthority> authorities) {
        this(id, username, mobilePhone, password, enabled, true, true, true, authorities);
    }

    public StadiumUser(long id, String username, String mobilePhone, String password,
                       boolean enabled, boolean accountNonExpired, boolean accountNonLocked,
                       boolean credentialsNonExpired, Collection<? extends GrantedAuthority> authorities) {
        if ((username == null && mobilePhone == null) ||
                ("".equals(username) || "".equals(mobilePhone)) ||
                password == null) {
            throw new IllegalArgumentException(
                    "Username and mobile phone cannot be both null/empty or password cannot by null");
        }

        this.id = id;
        this.username = username;
        this.mobilePhone = mobilePhone;
        this.password = password;
        this.enabled = enabled;
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.authorities = Collections.unmodifiableSet(sortAuthorities(authorities));
    }

    @Override
    public void eraseCredentials() {
        password = null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public Long getId() {
        return id;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }


    private static SortedSet<GrantedAuthority> sortAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Assert.notNull(authorities, "Cannot pass a null GrantedAuthority collection");

        // Ensure array iteration order is predictable (as per UserDetails.getAuthorities() contract and SEC-717)
        SortedSet<GrantedAuthority> sortedAuthorities = new TreeSet<>(new AuthorityComparator());

        for (GrantedAuthority grantedAuthority : authorities) {
            Assert.notNull(grantedAuthority, "GrantedAuthority list cannot contain any null elements");

            sortedAuthorities.add(grantedAuthority);
        }

        return sortedAuthorities;
    }

    private static class AuthorityComparator implements Comparator<GrantedAuthority>, Serializable {
        private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

        @Override
        public int compare(GrantedAuthority g1, GrantedAuthority g2) {
            // Neither should ever be null as each entry is checked before adding it to the set.
            // If the authority is null, it is a custom authority and should precede others.
            if (null == g2.getAuthority()) return -1;
            if (null == g1.getAuthority()) return 1;

            return g1.getAuthority().compareTo(g2.getAuthority());
        }
    }

    /**
     * Returns the hashcode of the {@code id}.
     */
    @Override
    public int hashCode() {
        return ((Long) id).hashCode();
    }

    /**
     * Returns {@code true} if the supplied object is a {@code AthenaUser} instance with the
     * same {@code id} value.
     * <p>
     * In other words, the objects are equal if they have the same id, representing the
     * same principal.
     */
    @Override
    public boolean equals(Object rhs) {
        if (rhs instanceof StadiumUser) {
            return id == ((StadiumUser) rhs).id;
        }

        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(super.toString()).append(": ");
        sb.append(super.toString()).append(": ");
        sb.append("Id: ").append(this.id).append(": ");
        sb.append("Username: ").append(this.username).append("; ");
        sb.append("Mobile phone: ").append(this.mobilePhone).append(": ");
        sb.append("Password: [PROTECTED]; ");
        sb.append("Enabled: ").append(this.enabled).append("; ");
        sb.append("AccountNonExpired: ").append(this.accountNonExpired).append("; ");
        sb.append("AccountNonLocked: ").append(this.accountNonLocked).append("; ");
        sb.append("credentialsNonExpired: ").append(this.credentialsNonExpired).append("; ");

        if (!authorities.isEmpty()) {
            sb.append("Granted Authorities: ");

            boolean first = true;

            for (GrantedAuthority auth : authorities) {
                if (!first) sb.append(",");

                first = false;

                sb.append(auth);
            }
        } else {
            sb.append("Not Granted any authorities");
        }

        return sb.toString();
    }
}
