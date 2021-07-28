package com.yuanvv.mawen.service;

import com.yuanvv.mawen.dto.CommentDTO;
import com.yuanvv.mawen.dto.QuestionDTO;
import com.yuanvv.mawen.enums.CommentType;
import com.yuanvv.mawen.exception.CustomizeErrorCode;
import com.yuanvv.mawen.exception.CustomizeException;
import com.yuanvv.mawen.exception.ICustomizeErrorCode;
import com.yuanvv.mawen.mapper.CommentMapper;
import com.yuanvv.mawen.mapper.QuestionMapper;
import com.yuanvv.mawen.mapper.UserMapper;
import com.yuanvv.mawen.model.Comment;
import com.yuanvv.mawen.model.Question;
import com.yuanvv.mawen.model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    @Transactional
    public void insert(Comment comment) {

        // 判断 comment 的合法性
        if (comment.getType() == null || !CommentType.isExist(comment.getType())) {
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }

        if (comment.getParentId() == null) {
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }

        if (comment.getContent() == null || StringUtils.isEmpty(comment.getContent())) {
            throw new CustomizeException(CustomizeErrorCode.COMMENT_NULL_VAL);
        }

        if (comment.getType().equals(CommentType.COMMENT.getType())) {
            // 回复评论
            if (commentMapper.getCommentById(comment.getParentId()) == null) {
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insert(comment);
            // 一级评论的回复数 +1
            commentMapper.incCommentCountById(comment.getParentId(), 1);
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

    public List<CommentDTO> listByTypeAndParentId(CommentType commentType, Long id) {
        List<Comment> comments;
        if (CommentType.QUESTION.equals(commentType)) {
            comments = commentMapper.listByTypeAndParentId(CommentType.QUESTION.getType(), id);
        } else if (CommentType.COMMENT.equals(commentType)) {
            comments = commentMapper.listByTypeAndParentId(CommentType.COMMENT.getType(), id);
        } else {
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }

        if (comments == null || comments.size() == 0) {
            return null;
        }

        // 需要将 Comment 转换为 CommentDTO 类型，插入 user 属性。
        // 考虑到评论人的局部性，希望减少查找用户的次数，生成一个{commentator: User}。
        Map<Integer, User> userMap = comments.stream().map(Comment::getCommentator).distinct().collect(Collectors.toMap(x -> x, x -> userMapper.findById(x)));
        List<CommentDTO> commentDTOS = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTO);
            commentDTO.setUser(userMap.get(commentDTO.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());
        return commentDTOS;
    }

    public List<QuestionDTO> getRelatedQuestions(QuestionDTO queryDTO) {
        if (StringUtils.isEmpty(queryDTO.getTag()) || StringUtils.isBlank(queryDTO.getTag())) {
            // TODO:如果标签为空，推荐默认的问题
            return null;
        }
        String[] tags = queryDTO.getTag().split(",|，");
        String regexp = Arrays.stream(tags).collect(Collectors.joining("|"));
        Question question = new Question();
        question.setId(queryDTO.getId());
        question.setTag(regexp);
        List<Question> relatedQuestions = questionMapper.getRelatedQuestions(question);
        List<QuestionDTO> questionDTOS = relatedQuestions.stream().map(q -> {
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(q, questionDTO);
            questionDTO.setUser(queryDTO.getUser());
            return questionDTO;
        }).collect(Collectors.toList());
        return questionDTOS;
    }
}
