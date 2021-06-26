package com.yuanvv.mawen.dto;

import com.yuanvv.mawen.model.User;
import lombok.Data;

@Data
public class QuestionDTO {
    private Integer id;
    private String title;
    private String description;
    private String tag;
    private Integer creator;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer commentCount;
    private Integer likeCount;
    private Integer viewCount;
    private User user;
}
