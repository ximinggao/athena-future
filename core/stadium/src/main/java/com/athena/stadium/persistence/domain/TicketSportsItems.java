package com.athena.stadium.persistence.domain;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;

/**
 * Created by Lingfeng on 2015/10/13.
 */
@Data
@Entity
@Table(name = "ticket_sports_items")
public class TicketSportsItems {

    @Id
    @GeneratedValue(generator = "pkGenerator")
    @GenericGenerator(
            name = "pkGenerator",
            strategy = "foreign",
            parameters = @Parameter(name = "property", value = "sportsItems"))
    private Long idSportsItem;

    @OneToOne(cascade = CascadeType.REFRESH, optional = false)
    @PrimaryKeyJoinColumn
    private SportsItems sportsItems;
}
