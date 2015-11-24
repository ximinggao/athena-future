package com.athena.stadium.persistence.domain;

import com.athena.stadium.persistence.domain.combinedPk.SpecificOpeningTimesPk;
import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Lingfeng on 2015/10/13.
 */
@Data
@Entity
@Table(name = "specific_opening_times")
public class SpecificOpeningTimes {

    @EmbeddedId
    private SpecificOpeningTimesPk pk;


    private String description;
}
