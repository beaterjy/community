package com.yuanvv.mawen.enums;

public enum NotificationStatus {
    UNREAD(0, "未读"),
    READ(1, "已读");

    private Integer status;
    private String name;

    NotificationStatus(Integer status, String name) {
        this.status = status;
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }
}
