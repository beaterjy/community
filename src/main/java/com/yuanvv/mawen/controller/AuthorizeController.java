package com.yuanvv.mawen.controller;

import com.yuanvv.mawen.dto.CodeDTO;
import com.yuanvv.mawen.dto.GithubUserDTO;
import com.yuanvv.mawen.mapper.UserMapper;
import com.yuanvv.mawen.model.User;
import com.yuanvv.mawen.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

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

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletResponse response) {
        CodeDTO codeDTO = new CodeDTO();
        codeDTO.setClient_id(clientId);
        codeDTO.setClient_secret(clientSecret);
        codeDTO.setCode(code);
        codeDTO.setRedirect_uri(redirectUri);

        String accessToken = githubProvider.getAccessToken(codeDTO);
        GithubUserDTO githubUserDTO = githubProvider.getUser(accessToken);
        if (githubUserDTO != null) {
            User user = new User();
            user.setName(githubUserDTO.getName());
            user.setAccountId(String.valueOf(githubUserDTO.getId()));
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(System.currentTimeMillis());
            user.setAvatarUrl(githubUserDTO.getAvatar_url());
            user.setBio(githubUserDTO.getBio());
            // 登录成功，保存到数据库
            userMapper.insert(user);
            // 登录成功，保存 cookie
            response.addCookie(new Cookie("token", token));
            return "redirect:/";
        } else {
            // 登录失败，重新登录。
            return "redirect:/";
        }
    }
}
