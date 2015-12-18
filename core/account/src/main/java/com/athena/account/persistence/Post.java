package com.athena.account.persistence;

import java.util.Date;

/**
 * Created by brook.xi on 12/16/2015.
 */
public interface Post {
    Long getId();

    void setId(Long id);

    Date getDate();

    void setDate(Date date);

    String getMessage();

    void setMessage(String message);
}
