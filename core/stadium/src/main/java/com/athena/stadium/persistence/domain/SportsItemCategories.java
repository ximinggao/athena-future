package com.athena.stadium.persistence.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Lingfeng on 2015/10/13.
 */
@Data
@Entity
@Table(name = "sports_item_categories")
public class SportsItemCategories {

    @Id
    @Column(nullable = false, length = 45)
    private String category;
}
