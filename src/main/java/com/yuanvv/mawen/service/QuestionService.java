package com.yuanvv.mawen.service;

import com.yuanvv.mawen.dto.PaginationDTO;
import com.yuanvv.mawen.dto.QuestionDTO;
import com.yuanvv.mawen.exception.CustomizeErrorCode;
import com.yuanvv.mawen.exception.CustomizeException;
import com.yuanvv.mawen.mapper.QuestionMapper;
import com.yuanvv.mawen.mapper.UserMapper;
import com.yuanvv.mawen.model.Question;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    public PaginationDTO getPageBySearchAndTag(String search, String tag, Integer page, Integer pageSize) {
        // 处理 tag
        if (tag != null && StringUtils.isNotBlank(tag)) {
            tag = tag.toLowerCase();
        }

        // 处理 search
        if (search != null && StringUtils.isNotBlank(search)) {
            // 更改 search 格式
            String lowerCase = search.toLowerCase();
            String[] searchList = lowerCase.split(" ");
            search = Arrays.stream(searchList).collect(Collectors.joining("|"));
        }

        List<Question> questions;
        Integer totalCount = 0;
        if (search != null) {
            questions = tag != null ?
                    questionMapper.latestListBySearchAndTag(search, tag, (page - 1) * pageSize, pageSize) :
                    questionMapper.latestListBySearch(search, (page - 1) * pageSize, pageSize);
            totalCount = tag != null ?
                    questionMapper.countBySearchAndTag(search, tag) :
                    questionMapper.countBySearch(search);
        } else {
            questions = tag != null ? questionMapper.latestListByTag(tag, (page - 1) * pageSize, pageSize)
                    : questionMapper.latestList((page - 1) * pageSize, pageSize);
            totalCount = tag != null ? questionMapper.countByTag(tag) : questionMapper.count();
        }

        return paging(questions, page, pageSize, totalCount);
    }

    public PaginationDTO getPageById(Integer id, Integer page, Integer pageSize) {
        List<Question> questions = questionMapper.getListById(id, (page - 1) * pageSize, pageSize);
        Integer totalCount = questionMapper.countById(id);
        return paging(questions, page, pageSize, totalCount);
    }

    /***
     * 将问题封装成 PaginationDTO
     * @param questions
     * @param page
     * @param pageSize
     * @param totalCount
     * @return
     */
    public PaginationDTO paging(List<Question> questions, Integer page, Integer pageSize, Integer totalCount) {
        List<QuestionDTO> questionDTOs = new ArrayList<>();
        for (Question question : questions) {
            QuestionDTO q = new QuestionDTO();
            BeanUtils.copyProperties(question, q);
            q.setUser(userMapper.findById(q.getCreator()));
            questionDTOs.add(q);
        }

        // 封装成 PaginationDTO
        PaginationDTO paginationDTO = new PaginationDTO();
        paginationDTO.setData(questionDTOs);
        paginationDTO.setPagination(page, pageSize, totalCount);
        return paginationDTO;
    }

    public QuestionDTO getQuestionById(Integer id) {
        Question question = questionMapper.getQuestionById(id);
        if (question == null) {
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        QuestionDTO q = new QuestionDTO();
        BeanUtils.copyProperties(question, q);
        q.setUser(userMapper.findById(q.getCreator()));
        return q;
    }

    public void createOrUpdate(Question question) {
        Integer id = question.getId();
        if (id == null) {
            // 新建
            questionMapper.create(question);
        } else {
            // 更新
            questionMapper.update(question);
        }
    }

    public void incViewCount(QuestionDTO question) {
        Integer id = question.getId();
        if (id != null) {
            questionMapper.incViewCountById(id, 1);
        } else {
            throw new CustomizeException(CustomizeErrorCode.NULL_VAL);
        }
    }

}
