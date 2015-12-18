package com.athena.account.service;

import com.athena.account.persistence.AdminPost;
import com.athena.account.persistence.AdminPostRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by brook.xi on 12/18/2015.
 */
@Service
@Transactional
public class AdminPostService implements GenericService<AdminPost> {
    private static Log logger = LogFactory.getLog(AdminPostService.class);

    @Autowired
    private AdminPostRepository postRepository;

    @Override
    public AdminPost getSingle(Long id) {
        logger.debug("Retrieving single admin post");
        return postRepository.findOne(id);
    }

    @Override
    public List<AdminPost> getAll() {
        logger.debug("Retrieving all admin posts");
        return postRepository.findAll();
    }

    @Override
    public AdminPost add(AdminPost post) {
        logger.debug("Adding new admin post");
        return postRepository.save(post);
    }

    @Override
    public AdminPost edit(AdminPost post) {
        logger.debug("Editing admin post");
        return postRepository.save(post);
    }

    @Override
    public void delete(AdminPost post) {
        logger.debug("Deleting admin post");
        postRepository.delete(post);
    }
}
