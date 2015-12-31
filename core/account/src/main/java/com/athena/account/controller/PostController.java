package com.athena.account.controller;

import com.athena.account.persistence.Post;
import com.athena.account.service.AdminPostService;
import com.athena.account.service.PersonalPostService;
import com.athena.account.service.PublicPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by brook.xi on 12/31/2015.
 */
@RestController
@RequestMapping("/posts")
public class PostController {
    @Autowired
    private AdminPostService adminPostService;

    @Autowired
    private PersonalPostService personalPostService;

    @Autowired
    private PublicPostService publicPostService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Post> getAllPosts() {
        List<Post> result = new ArrayList<>();
        result.addAll(adminPostService.getAll());
        result.addAll(personalPostService.getAll());
        result.addAll(publicPostService.getAll());
        return result;
    }
}
