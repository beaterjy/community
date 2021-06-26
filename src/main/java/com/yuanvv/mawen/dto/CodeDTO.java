package com.yuanvv.mawen.dto;

import lombok.Data;

/**
 * @author yuanvv
 * @date 2021/6/22
 */

@Data
public class CodeDTO{
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
}
