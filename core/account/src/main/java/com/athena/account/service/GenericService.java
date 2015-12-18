package com.athena.account.service;

import com.athena.account.persistence.Post;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

/**
 * Created by brook.xi on 12/16/2015.
 */
public interface GenericService<T extends Post> {
    /**
     *  Retrieves a single post.
     *  <p>
     *  Access-control will be evaluated after this method is invoked.
     *  returnObject refers to the returned object.
     */
    @PostAuthorize("hasPermission(returnObject, 'WRITE')")
    T getSingle(Long id);

    /**
     *  Retrieves all posts.
     *  <p>
     *  Access-control will be evaluated after this method is invoked.
     *  filterObject refers to the returned object list.
     */
    @PostFilter("hasPermission(filterObject, 'READ')")
    List<T> getAll();

    /**
     * Adds a new post.
     * <p>
     * We don't provide any access control here because
     * the new object doesn't have an id yet.
     * <p>
     * Instead we place the access control on the URL-level because
     * the Add page shouldn't be visible in the first place.
     * <p>
     * There are two places where we can place this restriction:
     * <pre>
     * 1. At the controller method
     * 2. At the external spring-security.xml file</pre>
     * <p>
     *
     */
    T add(T post);

    /**
     * Edits a post.
     * <p>
     * Access-control will be evaluated before this method is invoked.
     * <b>#post</b> refers to the current object in the method argument.
     */
    @PreAuthorize("hasPermission(#post, 'WRITE')")
    T edit(T post);

    /**
     * Deletes a post.
     * <p>
     * Access-control will be evaluated before this method is invoked.
     * <b>#post</b> refers to the current object in the method argument.
     */
    @PreAuthorize("hasPermission(#post, 'WRITE')")
    void delete(T post);
}
