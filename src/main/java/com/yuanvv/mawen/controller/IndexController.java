package com.yuanvv.mawen.controller;

import com.yuanvv.mawen.mapper.UserMapper;
import com.yuanvv.mawen.model.User;
import com.yuanvv.mawen.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author yuanvv
 * @date 2021/6/18
 */

@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model) {

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    User user = userMapper.findByToken(cookie.getValue());
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                    }
                }
            }
        }
        model.addAttribute("questions", questionService.getList());

        return "index";
    }
}
