package com.yuanvv.mawen.controller;

import com.yuanvv.mawen.dto.LoginUserDTO;
import com.yuanvv.mawen.enums.LoginUserType;
import com.yuanvv.mawen.factory.UserStrategyFactory;
import com.yuanvv.mawen.provider.GithubProvider;
import com.yuanvv.mawen.service.UserService;
import com.yuanvv.mawen.strategy.UserStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

    @Autowired
    private UserStrategyFactory userStrategyFactory;

    @GetMapping("/callback/{type}")
    public String callback(@PathVariable("type") String type,
                           @RequestParam(name = "code", required = false) String code,
                           @RequestParam(name = "state", required = false) String state,
                           HttpServletRequest request,
                           HttpServletResponse response) {


        // 用户策略工厂选取
        UserStrategy strategy = userStrategyFactory.getStrategy(type);
        LoginUserDTO userDTO = strategy.getUser(code, state);
        LoginUserType supportedType = strategy.getSupportedType();


        if (userDTO != null) {
            // 登录成功，创建或者更新
            String token = userService.createOrUpdate(userDTO, supportedType);
            // 登录成功，保存 cookie
            Cookie cookie = new Cookie("token", token);
            cookie.setMaxAge(60 * 60 * 24 * 30 * 6);    // maxAge 默认 -1，表示关闭浏览器即清除
            cookie.setPath("/");
            response.addCookie(cookie);
            return "redirect:/";
        } else {
            // 登录失败，重新登录。
            return "redirect:/";
        }
    }

//    @GetMapping("/callback")
//    public String callback(@RequestParam(name = "code") String code,
//                           @RequestParam(name = "state") String state,
//                           HttpServletResponse response) {
//        CodeDTO codeDTO = new CodeDTO();
//        codeDTO.setClient_id(clientId);
//        codeDTO.setClient_secret(clientSecret);
//        codeDTO.setCode(code);
//        codeDTO.setRedirect_uri(redirectUri);
//
//        String accessToken = githubProvider.getAccessToken(codeDTO);
//        GithubUserDTO githubUserDTO = githubProvider.getUser(accessToken);
//        if (githubUserDTO != null) {
//            // 登录成功，创建或者更新
//            String token = userService.createOrUpdate(githubUserDTO, );
//            // 登录成功，保存 cookie
//            response.addCookie(new Cookie("token", token));
//            return "redirect:/";
//        } else {
//            // 登录失败，重新登录。
//            return "redirect:/";
//        }
//    }


    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response) {
        // 删除 cookie, session
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals("token")) {
                cookie.setValue(null);
                cookie.setPath("/");
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }

        request.getSession().removeAttribute("user");
        return "redirect:/";
    }
}
