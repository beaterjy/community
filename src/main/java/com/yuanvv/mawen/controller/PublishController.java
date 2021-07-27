package com.yuanvv.mawen.controller;

import com.yuanvv.mawen.mapper.QuestionMapper;
import com.yuanvv.mawen.mapper.UserMapper;
import com.yuanvv.mawen.model.Question;
import com.yuanvv.mawen.model.User;
import com.yuanvv.mawen.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id") Integer id,
                          Model model) {
        Question question = questionMapper.getQuestionById(id);
        model.addAttribute("title", question.getTitle());
        model.addAttribute("description", question.getDescription());
        model.addAttribute("tag", question.getTag());
        model.addAttribute("id", id);
        return "publish";
    }

    @GetMapping("/publish")
    public String publish() {
        return "publish";
    }

    @PostMapping("/publish/{id}")
    public String doPublish(
            @PathVariable(value = "id", required = false) Integer id,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("tag") String tag,
            HttpServletRequest request,
            Model model
    ) {
        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);
        model.addAttribute("id", id);

        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            if (title == null || title.equals("")) {
                model.addAttribute("error", "缺少标题");
                return "publish";
            } else if (tag == null || "".equals(tag)) {
                model.addAttribute("error", "缺少标签");
                return "publish";
            }
            Question question = new Question();
            question.setTitle(title);
            question.setDescription(description);
            question.setTag(tag);
            question.setCreator(user.getId());
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            question.setId(id);
            questionService.createOrUpdate(question);
            return "redirect:/";
        }
        return "publish";
    }

}
