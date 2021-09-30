package com.yuanvv.mawen.model;

import lombok.Data;

@Data
public class Ad {
    private Integer id;
    private String title;
    private String url;
    private String image;
    private Long gmtCreate;
    private Long gmtModified;
    private Long gmtStart;
    private Long gmtEnd;
    private Integer status;
    private String pos;
}
