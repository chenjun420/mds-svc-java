package com.pts.mds.model;

import lombok.Data;

/**
 * User实体映射类
 */
@Data
public class User {

    private Integer id;
    private String name;
    private String password;
    private String phone;

}
