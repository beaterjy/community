package com.yuanvv.mawen.enums;

public enum LoginUserType {
    GITHUB_USER(0, "github"),
    GITEE_USER(1, "gitee");

    private Integer type;
    private String name;

    LoginUserType(Integer type, String name) {
        this.type = type;
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
