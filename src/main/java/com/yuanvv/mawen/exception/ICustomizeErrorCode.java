package com.yuanvv.mawen.exception;

public interface ICustomizeErrorCode {
    Integer code = null;
    String message = null;

    Integer getCode();
    String getMessage();
}
