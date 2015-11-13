package com.athena.edge.account;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by brook.xi on 11/13/2015.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findByMobile(Long mobile);
}
