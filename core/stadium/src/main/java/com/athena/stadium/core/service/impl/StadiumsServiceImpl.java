package com.athena.stadium.core.service.impl;

import com.athena.stadium.core.service.StadiumsService;
import com.athena.stadium.persistence.domain.Stadiums;
import com.athena.stadium.persistence.repository.StadiumsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Lingfeng on 2015/10/27.
 */
@Service
public class StadiumsServiceImpl implements StadiumsService {

    @Autowired
    private StadiumsRepository stadiumsRepository;

    @Override
    public Stadiums findOne(Long id) {
        return null;
    }
}
