package com.yuanvv.mawen.controller;

import com.yuanvv.mawen.dto.CodeDTO;
import com.yuanvv.mawen.dto.GithubUserDTO;
import com.yuanvv.mawen.mapper.UserMapper;
import com.yuanvv.mawen.model.User;
import com.yuanvv.mawen.provider.GithubProvider;
import com.yuanvv.mawen.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
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
    private UserService userService;

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
            // 登录成功，创建或者更新
            String token = userService.createOrUpdate(githubUserDTO);
            // 登录成功，保存 cookie
            response.addCookie(new Cookie("token", token));
            return "redirect:/";
        } else {
            // 登录失败，重新登录。
            return "redirect:/";
        }
    }


    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response) {
        // 删除 cookie, session
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);

        request.getSession().removeAttribute("user");
        return "redirect:/";
    }
}
