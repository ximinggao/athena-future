package com.athena.edge.account;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by brook.xi on 11/13/2015.
 */

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode
public class User {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private Long mobile;

    @Column(nullable = false)
    private String password;

    @Column(nullable = true)
    private String nickName;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "USER_ROLES",
        joinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "ID"),
        inverseJoinColumns = @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID"),
        uniqueConstraints = @UniqueConstraint(columnNames = {"USER_ID", "ROLE_ID"}))
    private List<Role> roles;

    @Column(updatable = false, insertable = false, nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date createdAt;
}
