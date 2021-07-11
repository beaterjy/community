package com.yuanvv.mawen.controller;

import com.yuanvv.mawen.dto.CommentDTO;
import com.yuanvv.mawen.dto.ResultDTO;
import com.yuanvv.mawen.exception.CustomizeErrorCode;
import com.yuanvv.mawen.exception.CustomizeException;
import com.yuanvv.mawen.mapper.CommentMapper;
import com.yuanvv.mawen.model.Comment;
import com.yuanvv.mawen.model.User;
import com.yuanvv.mawen.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @ResponseBody
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public Object comment(@RequestBody CommentDTO commentDTO,
                          HttpServletRequest request) {
        // @RequestBody 自动反序列化为 Java 类型对象
        // @ResponseBody 返回 JSON 格式

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            throw new CustomizeException(CustomizeErrorCode.USER_NOT_FOUND);
        }

        Comment comment = new Comment();
        comment.setParentId(commentDTO.getParentId());
        comment.setType(commentDTO.getType());
        comment.setContent(commentDTO.getContent());
        comment.setCommentator(user.getId());
        comment.setLikeCount(0L);
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());

        commentService.insert(comment);

        // 请求成功，返回 ok 信息
        return ResultDTO.okOf();
    }
}
