package com.yuanvv.mawen.model;

import lombok.Data;

@Data
public class Notification {
    private Long id;
    private Long notifier;
    private String notifierName;
    private Long receiver;
    private Long outerId;
    private String outerTitle;
    private Integer type;
    private Long gmtCreate;
    private Integer status;
}
