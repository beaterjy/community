package com.yuanvv.mawen.controller;

import com.yuanvv.mawen.cache.HotTagCache;
import com.yuanvv.mawen.mapper.QuestionMapper;
import com.yuanvv.mawen.mapper.UserMapper;
import com.yuanvv.mawen.service.QuestionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yuanvv
 * @date 2021/6/18
 */

@Controller
public class IndexController {

    @Autowired
    private HotTagCache hotTagCache;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(
            @RequestParam(name = "search", required = false) String search,
            @RequestParam(name = "tag", required = false) String tag,
            @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(name = "pageSize", required = false, defaultValue = "4") Integer pageSize,
            HttpServletRequest request,
            Model model) {
        // 修正 pageSize
        pageSize = pageSize >= 1 ? pageSize : 1;
        Integer totalCount = questionMapper.count();
        Integer totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
        totalPage = totalPage >= 1 ? totalPage : 1;
        // 修正 page
        page = page >= 1 ? page : 1;
        page = page <= totalPage ? page : totalPage;

        model.addAttribute("pagination", questionService.getPageBySearchAndTag(search, tag, page, pageSize));

        // 如果有 search ，应该缓存到页面上
        if (search != null && StringUtils.isNotBlank(search)) {
            model.addAttribute("search", search);
        }

        // 如果有 tag，应该缓存到页面上
        if (tag != null && StringUtils.isNotBlank(tag)) {
            model.addAttribute("tag", tag);
        }

        // 页面缓存 hotTags
        if (hotTagCache.getHotTags() != null) {
            int numOfHotTags = Math.min(hotTagCache.getHotTags().size(), 5);
            model.addAttribute("hotTags", hotTagCache.getHotTags().subList(0, numOfHotTags));
        }


        return "index";
    }
}
