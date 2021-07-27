package com.yuanvv.mawen.controller;

import com.yuanvv.mawen.dto.CommentCreateDTO;
import com.yuanvv.mawen.dto.CommentDTO;
import com.yuanvv.mawen.dto.ResultDTO;
import com.yuanvv.mawen.enums.CommentType;
import com.yuanvv.mawen.exception.CustomizeErrorCode;
import com.yuanvv.mawen.exception.CustomizeException;
import com.yuanvv.mawen.model.Comment;
import com.yuanvv.mawen.model.User;
import com.yuanvv.mawen.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @ResponseBody
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public Object comment(@RequestBody CommentCreateDTO commentCreateDTO,
                          HttpServletRequest request) {
        // @RequestBody 自动反序列化为 Java 类型对象
        // @ResponseBody 返回 JSON 格式

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            throw new CustomizeException(CustomizeErrorCode.USER_NOT_FOUND);
        }

        Comment comment = new Comment();
        comment.setParentId(commentCreateDTO.getParentId());
        comment.setType(commentCreateDTO.getType());
        comment.setContent(commentCreateDTO.getContent());
        comment.setCommentator(user.getId());
        comment.setLikeCount(0L);
        comment.setCommentCount(0L);
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());

        commentService.insert(comment);

        // 请求成功，返回 ok 信息
        return ResultDTO.okOf();
    }

    @ResponseBody
    @GetMapping("/sub_comments/{id}")
    public ResultDTO<List> subCommentsById(@PathVariable("id") Long id) {
        List<CommentDTO> commentDTOS = commentService.listByTypeAndParentId(CommentType.COMMENT, id);
        return ResultDTO.okOf(commentDTOS);
    }

}
