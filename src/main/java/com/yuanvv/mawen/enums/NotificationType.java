package com.yuanvv.mawen.enums;

public enum NotificationType {
    REPLY_QUESTION(1, "回复了问题"),
    REPLY_COMMENT(2, "回复了评论");

    private Integer type;
    private String name;

    NotificationType(Integer type, String name) {
        this.type = type;
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public static String nameOfType(Integer type) {
        for (NotificationType notificationType : NotificationType.values()) {
            if (notificationType.getType().equals(type)) {
                return notificationType.getName();
            }
        }
        return "";
    }
}
