package com.athena.edge.account;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by brook.xi on 11/13/2015.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByMobile(Long mobile);
}
