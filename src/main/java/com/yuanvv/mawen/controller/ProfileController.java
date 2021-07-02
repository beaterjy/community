package com.yuanvv.mawen.controller;

import com.yuanvv.mawen.model.User;
import com.yuanvv.mawen.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/profile/{section}")
    public String section(@PathVariable String section,
                          @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                          @RequestParam(value = "pageSize", required = false, defaultValue = "4") Integer pageSize,
                          HttpServletRequest request,
                          Model model) throws Exception {
        // 验证用户是否登录？
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:index";
        }

        // 判断选择的功能模块？
        switch (section) {
            case "questions":
                model.addAttribute("sectionName", "我的提问");
                break;
            case "replies":
                model.addAttribute("sectionName", "我的回答");
                break;
            default:
                throw new Exception("Not found section.");
        }
        model.addAttribute("section", section);

        // questions section
        // 通过用户 id 返回分页的问题
        model.addAttribute("pagination", questionService.getPageById(user.getId(), page, pageSize));
        return "profile";
    }
}
