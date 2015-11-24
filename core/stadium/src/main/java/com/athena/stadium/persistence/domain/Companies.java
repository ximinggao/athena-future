package com.athena.stadium.persistence.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * 公司信息
 *
 * Created by Lingfeng on 2015/10/13.
 */
@Data
@Entity
@Table(name = "companies")
public class Companies {

    @Id
    @GeneratedValue
    private Long idCompany;

    @Column(length = 45)
    private String name;
}
