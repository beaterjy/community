package com.yuanvv.mawen.model;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private Integer type;
    private String name;
    private String accountId;
    private String token;
    private Long gmtCreate;
    private Long gmtModified;
    private String avatarUrl;
    private String bio;

}
