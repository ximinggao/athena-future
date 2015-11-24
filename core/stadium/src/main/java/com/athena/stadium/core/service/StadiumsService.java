package com.athena.stadium.core.service;

import com.athena.stadium.persistence.domain.Stadiums;

/**
 * Created by Lingfeng on 2015/10/27.
 */
public interface StadiumsService {

    Stadiums findOne(Long id);
}
