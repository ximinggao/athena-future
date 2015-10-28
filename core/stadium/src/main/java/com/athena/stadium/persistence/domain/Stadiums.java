package com.athena.stadium.persistence.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * 场馆信息
 *
 * Created by Lingfeng on 2015/10/13.
 */
@Data
@Entity
@Table(name = "stadiums")
public class Stadiums {

    @Id
    @GeneratedValue
    private Long idStadium;

    @ManyToOne(cascade = CascadeType.REFRESH, optional = false)
    @JoinColumn(name = "id_company")
    private Companies companies;

    @Column(nullable = false, length = 64)
    private String name;
}
