package com.yuanvv.mawen.service;

import com.yuanvv.mawen.enums.CommentType;
import com.yuanvv.mawen.exception.CustomizeErrorCode;
import com.yuanvv.mawen.exception.CustomizeException;
import com.yuanvv.mawen.mapper.CommentMapper;
import com.yuanvv.mawen.mapper.QuestionMapper;
import com.yuanvv.mawen.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Transactional
    public void insert(Comment comment) {

        // 判断 comment 的合法性
        if (comment.getType() == null || !CommentType.isExist(comment.getType())) {
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }

        if (comment.getParentId() == null) {
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }

        if (comment.getType().equals(CommentType.COMMENT.getType())) {
            // 回复评论
            if (commentMapper.getCommentById(comment.getParentId()) == null) {
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insert(comment);
        }

        if (comment.getType().equals(CommentType.QUESTION.getType())) {
            // 回复问题
            if (questionMapper.getQuestionById(comment.getParentId().intValue()) == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insert(comment);
            // 问题的回复数 +1
            questionMapper.incCommentCountById(comment.getParentId(), 1);
        }

    }
}
