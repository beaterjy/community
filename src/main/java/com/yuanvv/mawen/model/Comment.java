package com.yuanvv.mawen.model;

import lombok.Data;

@Data
public class Comment {
    private Long id;
    private Long parentId;
    private Integer type;
    private String content;
    private Integer commentator;
    private Long likeCount;
    private Long commentCount;
    private Long gmtCreate;
    private Long gmtModified;
}
