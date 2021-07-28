package com.yuanvv.mawen.controller;

import com.yuanvv.mawen.cache.TagCache;
import com.yuanvv.mawen.dto.TagDTO;
import com.yuanvv.mawen.mapper.QuestionMapper;
import com.yuanvv.mawen.mapper.UserMapper;
import com.yuanvv.mawen.model.Question;
import com.yuanvv.mawen.model.User;
import com.yuanvv.mawen.service.QuestionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
        // 获取默认的 tags
        List<TagDTO> tags = TagCache.getTags();
        model.addAttribute("tags", tags);
        return "publish";
    }

    @GetMapping("/publish")
    public String publish(Model model) {
        // 获取默认的 tags
        List<TagDTO> tags = TagCache.getTags();
        model.addAttribute("tags", tags);
        return "publish";
    }

    @PostMapping(value = {"/publish/{id}", "/publish"})
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
        // 获取默认的 tags
        List<TagDTO> tags = TagCache.getTags();
        model.addAttribute("tags", tags);

        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            if (title == null || title.equals("")) {
                model.addAttribute("error", "缺少标题");
                return "publish";
            }
            if (tag == null || "".equals(tag)) {
                model.addAttribute("error", "缺少标签");
                return "publish";
            }
            // 过滤出不合法的标签
            String invalid = TagCache.filterInvalid(tag);
            if (!StringUtils.isEmpty(invalid)) {
                model.addAttribute("error", "缺少标签：" + invalid);
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
