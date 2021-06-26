package com.yuanvv.mawen.service;

import com.yuanvv.mawen.dto.QuestionDTO;
import com.yuanvv.mawen.mapper.QuestionMapper;
import com.yuanvv.mawen.mapper.UserMapper;
import com.yuanvv.mawen.model.Question;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    public List<QuestionDTO> getList() {
        List<QuestionDTO> questionDTOs = new ArrayList<>();
        List<Question> questions = questionMapper.latestList();
        for (Question question : questions) {
            QuestionDTO q = new QuestionDTO();
            BeanUtils.copyProperties(question, q);
            q.setUser(userMapper.findById(q.getCreator()));

            questionDTOs.add(q);
        }
        return questionDTOs;
    }
}
