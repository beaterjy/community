package com.yuanvv.mawen.enums;

public enum CommentType {
    QUESTION(1),
    COMMENT(2);

    private Integer type;

    CommentType(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

    public static boolean isExist(Integer type) {
        for (CommentType commentType : CommentType.values()) {
            if (type.equals(commentType.getType())) {
                return true;
            }
        }
        return false;
    }
}
