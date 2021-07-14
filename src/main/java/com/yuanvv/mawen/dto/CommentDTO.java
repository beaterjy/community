package com.yuanvv.mawen.dto;

import com.yuanvv.mawen.model.User;
import lombok.Data;

@Data
public class CommentDTO {
    private Long id;
    private Long parentId;
    private Integer type;
    private String content;
    private Integer commentator;
    private Long likeCount;
    private Long gmtCreate;
    private Long gmtModified;
    private User user;
}
