package com.athena.stadium.persistence.repository;

import com.athena.stadium.persistence.domain.Stadiums;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Lingfeng on 2015/10/27.
 */
public interface StadiumsRepository extends JpaRepository<Stadiums, Long> {
}
