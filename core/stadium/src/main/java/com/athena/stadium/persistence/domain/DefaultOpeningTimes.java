package com.athena.stadium.persistence.domain;

import com.athena.stadium.persistence.domain.combinedPk.DefaultOpeningTimesPk;
import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Lingfeng on 2015/10/13.
 */
@Data
@Entity
@Table(name = "default_opening_times")
public class DefaultOpeningTimes {

    @EmbeddedId
    private DefaultOpeningTimesPk pk;
}
