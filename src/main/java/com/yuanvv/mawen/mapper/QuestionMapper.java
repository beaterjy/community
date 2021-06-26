package com.yuanvv.mawen.mapper;

import com.yuanvv.mawen.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuestionMapper {

    @Insert("INSERT INTO question (title, description, tag, creator, gmt_create, gmt_modified) " +
            "VALUES (#{title}, #{description}, #{tag}, #{creator}, #{gmtCreate}, #{gmtModified});")
    void create(Question question);

    @Select("SELECT * FROM question ORDER BY gmt_create DESC;")
    List<Question> latestList();
}
