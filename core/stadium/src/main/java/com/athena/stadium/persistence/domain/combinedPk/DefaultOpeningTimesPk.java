package com.athena.stadium.persistence.domain.combinedPk;

import com.athena.stadium.persistence.domain.SportsItems;
import com.athena.stadium.persistence.domain.enums.DayOfWeekEnum;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * DefaultOpeningTimes联合主键
 *
 * Created by Lingfeng on 2015/10/13.
 */
@Data
@Embeddable
public class DefaultOpeningTimesPk implements Serializable {

    @ManyToOne(cascade = CascadeType.REFRESH, optional = false)
    @JoinColumn(name = "idSportsItem")
    private SportsItems sportsItems;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "ENUM('MON','TUE','WED','THU','FRI','SAT','SUN')")
    private DayOfWeekEnum dayOfWeek;

    @Temporal(TemporalType.TIME)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(nullable = false)
    private Date startTime;

    @Temporal(TemporalType.TIME)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(nullable = false)
    private Date stopTime;
}
