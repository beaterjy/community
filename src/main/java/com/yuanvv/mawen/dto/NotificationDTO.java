package com.yuanvv.mawen.dto;

import lombok.Data;

@Data
public class NotificationDTO {
    private Long id;
    private Long notifier;
    private String notifierName;
    private Long receiver;
    private Long outerId;
    private String outerTitle;
    private Integer type;
    private String notificationType;
    private Long gmtCreate;
    private Integer status;
}
