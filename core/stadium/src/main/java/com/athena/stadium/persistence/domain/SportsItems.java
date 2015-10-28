package com.athena.stadium.persistence.domain;


import lombok.Data;

import javax.persistence.*;

/**
 * Created by Lingfeng on 2015/10/13.
 */
@Data
@Entity
@Table(name = "sports_items")
public class SportsItems {

    @Id
    @GeneratedValue
    private Long idSportsItem;

    @ManyToOne(cascade = CascadeType.REFRESH, optional = false)
    @JoinColumn(name = "id_stadium")
    private Stadiums stadiums;

    @Column(nullable = false, length = 45)
    private String name;

    @ManyToOne(cascade = CascadeType.REFRESH, optional = false)
    @JoinColumn(name = "category")
    private SportsItemCategories sportsItemCategories;

    private Long daysOrderAhead;
}
