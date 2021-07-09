package com.yuanvv.mawen.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode {
    QUESTION_NOT_FOUND("你找的问题不在了，要不要换一个试试？"),
    NULL_VAL("空值了，请联系管理员。");

    private String message;

    CustomizeErrorCode(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

}