package com.yuanvv.mawen.dto;

import lombok.Data;

@Data
public class CommentDTO {
    private Long parentId;
    private Integer type;
    private String content;
}
