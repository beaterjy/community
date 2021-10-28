package com.yuanvv.mawen.enums;

public enum AdPosType {
    NAV(1, "NAV"),
    SIDE(2, "SIDE"),
    HEADER(3, "HEADER"),
    FOOTER(4, "FOOTER");

    private Integer type;
    private String name;

    public Integer getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    AdPosType(Integer type, String name) {
        this.type = type;
        this.name = name;
    }
}
