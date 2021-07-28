package com.yuanvv.mawen.mapper;

import com.yuanvv.mawen.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionMapper {

    @Insert("INSERT INTO question (title, description, tag, creator, gmt_create, gmt_modified) " +
            "VALUES (#{title}, #{description}, #{tag}, #{creator}, #{gmtCreate}, #{gmtModified});")
    void create(Question question);

    @Select("SELECT * FROM question ORDER BY gmt_modified DESC LIMIT #{limit} OFFSET #{offset};")
    List<Question> latestList(@Param("offset") Integer offset, @Param("limit") Integer limit);

    @Select("SELECT COUNT(1) FROM question;")
    Integer count();

    @Select("SELECT * FROM question WHERE creator = #{id} ORDER BY gmt_modified DESC LIMIT #{limit} OFFSET #{offset};")
    List<Question> getListById(@Param("id") Integer id, @Param("offset") Integer offset, @Param("limit") Integer limit);

    @Select("SELECT COUNT(1) FROM question WHERE creator = #{id};")
    Integer countById(@Param("id") Integer id);

    @Select("SELECT * FROM question WHERE id = #{id} LIMIT 1;")
    Question getQuestionById(@Param("id") Integer id);

    @Update("UPDATE question SET title=#{title}, description=#{description}, tag=#{tag}, gmt_modified=#{gmtModified} WHERE id=#{id};")
    void update(Question question);

    @Update("UPDATE question SET view_count=view_count+#{val} WHERE id=#{id};")
    void incViewCountById(@Param("id") Integer id, @Param("val") Integer val);

    @Update("UPDATE question SET comment_count=comment_count+#{val} WHERE id=#{id};")
    void incCommentCountById(@Param("id") Long id, @Param("val") Integer val);

    @Select("SELECT * FROM question WHERE tag REGEXP #{tag} AND id != #{id} ORDER BY gmt_modified DESC LIMIT 10;")
    List<Question> getRelatedQuestions(Question question);
}
