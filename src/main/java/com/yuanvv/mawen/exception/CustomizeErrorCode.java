package com.yuanvv.mawen.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode {
    SYS_ERROR(2000, "服务太热了，要不然稍等下再来试试！"),
    QUESTION_NOT_FOUND(2001, "你找的问题不在了，要不要换一个试试？"),
    NULL_VAL(2002, "空值了，请联系管理员。"),
    USER_NOT_FOUND(2003, "用户未登陆，请先登录再操作吧！"),
    COMMENT_NOT_FOUND(2004, "您找的评论不在了，抱歉。"),
    TYPE_PARAM_WRONG(2005, "请求的类型错误。"),
    TARGET_PARAM_NOT_FOUND(2006, "请求的问题或者评论错误！"),
    COMMENT_NULL_VAL(2007, "评论内容为空。");

    private Integer code;
    private String message;

    CustomizeErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
};
