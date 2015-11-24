package com.athena.stadium.persistence.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Lingfeng on 2015/10/13.
 */
@Data
@Entity
@Table(name = "min_time_units")
public class MinTimeUnits {

    @Id
    private Long minTimeUnit;
}
