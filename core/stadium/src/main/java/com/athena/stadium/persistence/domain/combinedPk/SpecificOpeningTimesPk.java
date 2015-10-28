package com.athena.stadium.persistence.domain.combinedPk;

import com.athena.stadium.persistence.domain.SportsItems;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * SpecificOpeningTimes联合主键
 *
 * Created by Lingfeng on 2015/10/13.
 */
@Data
@Embeddable
public class SpecificOpeningTimesPk implements Serializable {

    @ManyToOne(cascade = CascadeType.REFRESH, optional = false)
    @JoinColumn(name = "id_sports_item")
    private SportsItems sportsItems;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date date;

    @Temporal(TemporalType.TIME)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date startTime;

    @Temporal(TemporalType.TIME)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date stopTime;
}
