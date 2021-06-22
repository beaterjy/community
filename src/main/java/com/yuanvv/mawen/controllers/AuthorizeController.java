package com.yuanvv.mawen.controllers;

import com.yuanvv.mawen.dto.CodeDTO;
import com.yuanvv.mawen.dto.GithubUserDTO;
import com.yuanvv.mawen.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yuanvv
 * @date 2021/6/21
 */

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.client.redirect_uri}")
    private String redirectUri;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request) {
        CodeDTO codeDTO = new CodeDTO();
        codeDTO.setClient_id(clientId);
        codeDTO.setClient_secret(clientSecret);
        codeDTO.setCode(code);
        codeDTO.setRedirect_uri(redirectUri);

        String accessToken = githubProvider.getAccessToken(codeDTO);
        GithubUserDTO userDTO = githubProvider.getUser(accessToken);
        if (userDTO != null) {
            // 登录成功，保存到 session 和 cookie
            request.getSession().setAttribute("user", userDTO);
            return "redirect:/";
        } else {
            // 登录失败，重新登录
            return "redirect:/";
        }
    }
}
