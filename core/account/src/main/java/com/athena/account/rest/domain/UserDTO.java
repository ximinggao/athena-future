package com.athena.account.rest.domain;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by Lingfeng on 2015/10/27.
 */
@Data
public class UserDTO {

    @NotNull
    private String name;

    @NotNull
    private String pwd;
}
