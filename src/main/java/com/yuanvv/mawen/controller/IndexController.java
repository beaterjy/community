package com.yuanvv.mawen.controller;

import com.yuanvv.mawen.mapper.QuestionMapper;
import com.yuanvv.mawen.mapper.UserMapper;
import com.yuanvv.mawen.model.User;
import com.yuanvv.mawen.service.QuestionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.stream.Collectors;

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
    public String index(
            @RequestParam(name = "search", required = false) String search,
            @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
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

        // 如果有 search ，应该缓存到页面上
        if (search != null && StringUtils.isNotBlank(search)) {
            // 更改 search 格式
            String lowerCase = search.toLowerCase();
            String[] searchList = lowerCase.split(" ");
            String regexp = Arrays.stream(searchList).collect(Collectors.joining("|"));

            model.addAttribute("pagination", questionService.getPageBySearch(regexp, page, pageSize));
            model.addAttribute("search", search);
        }else {
            model.addAttribute("pagination", questionService.getPage(page, pageSize));
        }

        return "index";
    }
}
