package com.athena.account.persistence;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by brook.xi on 11/26/2015.
 */
@Entity
@Data
public class AdminPost implements Post {
    @Id
    @GeneratedValue
    private Long id;

    @Column(updatable = false, insertable = false, nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date date;

    @Column(nullable = false)
    private String message;
}
