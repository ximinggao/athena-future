package com.athena.common.user;

import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

/**
 * Created by brook.xi on 11/18/2015.
 */
public final class CurrentUser {
    private static AthenaUserDetails getUserDetails() {
        return (AthenaUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static final Long getId() {
        return getUserDetails().getId();
    }

    public static final Long getMobile() {
        return getUserDetails().getMobile();
    }

    public static final Optional<String> getNickName() {
        return Optional.of(getUserDetails().getNickName());
    }
}
