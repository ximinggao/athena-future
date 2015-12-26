package com.athena.account.service;

import com.athena.account.persistence.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by brook.xi on 12/16/2015.
 */
@Transactional(readOnly = true)
public abstract class GenericService<T extends Post, U extends JpaRepository<T, Long>> {
    @Autowired
    protected U postRepo;

    /**
     *  Retrieves a single post.
     *  <p>
     *  Access-control will be evaluated after this method is invoked.
     *  returnObject refers to the returned object.
     */
    @PostAuthorize("hasPermission(returnObject, 'WRITE')")
    public T getSingle(Long id) {
        return postRepo.findOne(id);
    }

    /**
     *  Retrieves all posts.
     *  <p>
     *  Access-control will be evaluated after this method is invoked.
     *  filterObject refers to the returned object list.
     */
    @PostFilter("hasPermission(filterObject, 'READ')")
    public List<T> getAll() {
        return postRepo.findAll();
    }

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
    @Transactional
    public T add(T post) {
        return postRepo.save(post);
    }

    /**
     * Edits a post.
     * <p>
     * Access-control will be evaluated before this method is invoked.
     * <b>#post</b> refers to the current object in the method argument.
     */
    @PreAuthorize("hasPermission(#post, 'WRITE')")
    @Transactional
    public T edit(T post) {
        return postRepo.save(post);
    }

    /**
     * Deletes a post.
     * <p>
     * Access-control will be evaluated before this method is invoked.
     * <b>#post</b> refers to the current object in the method argument.
     */
    @PreAuthorize("hasPermission(#post, 'WRITE')")
    @Transactional
    public void delete(T post) {
        postRepo.delete(post);
    }
}
