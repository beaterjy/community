package com.yuanvv.mawen.mapper;

import com.yuanvv.mawen.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuestionMapper {

    @Insert("INSERT INTO question (title, description, tag, creator, gmt_create, gmt_modified) " +
            "VALUES (#{title}, #{description}, #{tag}, #{creator}, #{gmtCreate}, #{gmtModified});")
    void create(Question question);

    @Select("SELECT * FROM question LIMIT #{limit} OFFSET #{offset};")
    List<Question> latestList(@Param("offset") Integer offset, @Param("limit") Integer limit);

    @Select("SELECT COUNT(1) FROM question;")
    Integer count();

    @Select("SELECT * FROM question WHERE creator = #{id} LIMIT #{limit} OFFSET #{offset};")
    List<Question> getListById(@Param("id") Integer id, @Param("offset") Integer offset, @Param("limit") Integer limit);

    @Select("SELECT COUNT(1) FROM question WHERE creator = #{id};")
    Integer countById(@Param("id") Integer id);
}
