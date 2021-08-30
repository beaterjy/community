package com.yuanvv.mawen.controller;

import com.yuanvv.mawen.dto.NotificationDTO;
import com.yuanvv.mawen.enums.NotificationStatus;
import com.yuanvv.mawen.enums.NotificationType;
import com.yuanvv.mawen.exception.CustomizeErrorCode;
import com.yuanvv.mawen.exception.CustomizeException;
import com.yuanvv.mawen.mapper.CommentMapper;
import com.yuanvv.mawen.model.Comment;
import com.yuanvv.mawen.model.User;
import com.yuanvv.mawen.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
public class NotificationController {

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    NotificationService notificationService;

    @GetMapping("/notification/{id}")
    public String readNotification(HttpServletRequest request, @PathVariable("id") Long id) {

        // 验证用户是否登录？
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:index";
        }

        // 修改已读
        NotificationDTO notificationDTO = notificationService.updateStatusById(id, NotificationStatus.READ.getStatus());

        // 跳转问题页面
        if (notificationDTO.getType().equals(NotificationType.REPLY_QUESTION.getType())) {
            // 回复问题（一级评论）
            return "redirect:/question/" + notificationDTO.getOuterId();
        }
        else if (notificationDTO.getType().equals(NotificationType.REPLY_COMMENT.getType())){
            // 回复评论（二级评论）
            Comment comment = commentMapper.getCommentById(notificationDTO.getOuterId());
            return "redirect:/question/" + comment.getParentId();
        }
        else {
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
    }
}
