package com.athena.account.controller;

import com.athena.account.persistence.AdminPost;
import com.athena.account.service.AdminPostService;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by brook.xi on 12/26/2015.
 */
@RestController
@RequestMapping("/post/admin")
public class AdminPostController {
    @Autowired
    private AdminPostService adminPostService;

    @Autowired
    private MapperFacade mapper;

    @RequestMapping(method = RequestMethod.POST)
    public AdminPost addPost(@RequestBody Post post) {
        AdminPost adminPost = mapper.map(post, AdminPost.class);
        return adminPostService.add(adminPost);
    }
}
