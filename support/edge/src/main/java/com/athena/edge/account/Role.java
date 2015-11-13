package com.athena.edge.account;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * Created by brook.xi on 11/13/2015.
 */
@Entity
@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode
public class Role {
    @Id
    @GeneratedValue
    @Column
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "roles")
    private List<User> users;
}
