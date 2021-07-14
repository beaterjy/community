package com.yuanvv.mawen.mapper;

import com.yuanvv.mawen.model.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CommentMapper {

    @Insert("INSERT INTO comment(parent_id, type, content, commentator, like_count, gmt_create, gmt_modified) " +
            "VALUES(#{parentId}, #{type}, #{content}, #{commentator}, #{likeCount}, #{gmtCreate}, #{gmtModified});")
    void insert(Comment comment);

    @Select("SELECT * FROM comment WHERE id=#{id} LIMIT 1;")
    Comment getCommentById(@Param("id") Long id);

    @Select("SELECT * FROM comment WHERE type=#{type} AND parent_id=#{parentId};")
    List<Comment> listByTypeAndParentId(@Param("type") Integer type, @Param("parentId") Long parentId);
}
