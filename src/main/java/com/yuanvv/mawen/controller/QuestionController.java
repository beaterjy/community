package com.yuanvv.mawen.controller;

import com.yuanvv.mawen.dto.QuestionDTO;
import com.yuanvv.mawen.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Integer id,
                                Model model) {
        QuestionDTO question = questionService.getQuestionById(id);
        if (question != null) {
            questionService.incView(question);
        }
        model.addAttribute("question", question);
        return "question";
    }
}
