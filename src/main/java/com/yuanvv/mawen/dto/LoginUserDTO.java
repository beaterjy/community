package com.yuanvv.mawen.dto;

import lombok.Data;

@Data
public class LoginUserDTO {
    private Long id;
    private String name;
    private String bio;
    private String avatar_url;
}
