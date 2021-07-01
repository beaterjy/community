package com.yuanvv.mawen.controller;

import com.yuanvv.mawen.mapper.QuestionMapper;
import com.yuanvv.mawen.mapper.UserMapper;
import com.yuanvv.mawen.model.User;
import com.yuanvv.mawen.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(@RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
                        @RequestParam(name = "pageSize", required = false, defaultValue = "4") Integer pageSize,
                        HttpServletRequest request,
                        Model model) {
        // 修正 pageSize
        pageSize = pageSize >= 1 ? pageSize : 1;
        Integer totalCount = questionMapper.count();
        Integer totalpage = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
        // 修正 page
        page = page >= 1 ? page : 1;
        page = page <= totalpage ? page : totalpage;


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
        model.addAttribute("pagination", questionService.getPage(page, pageSize));

        return "index";
    }
}
