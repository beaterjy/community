package com.yuanvv.mawen.controller;

import com.yuanvv.mawen.dto.CommentDTO;
import com.yuanvv.mawen.dto.QuestionDTO;
import com.yuanvv.mawen.enums.CommentType;
import com.yuanvv.mawen.service.CommentService;
import com.yuanvv.mawen.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Long id,
                                Model model) {
        QuestionDTO question = questionService.getQuestionById(id.intValue());
        if (question != null) {
            questionService.incViewCount(question);
        }
        List<CommentDTO> comments = commentService.listByTypeAndParentId(CommentType.QUESTION, id);
        List<QuestionDTO> relatedQuestions = commentService.getRelatedQuestions(question);

        // 返回问题
        model.addAttribute("question", question);
        // 返回评论列表
        model.addAttribute("comments", comments);
        // 返回相关问题
        model.addAttribute("relatedQuestions", relatedQuestions);

        return "question";
    }
}
