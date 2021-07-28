package com.yuanvv.mawen.mapper;

import com.yuanvv.mawen.model.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommentMapper {

    @Insert("INSERT INTO comment(parent_id, type, content, commentator, like_count, comment_count, gmt_create, gmt_modified) " +
            "VALUES(#{parentId}, #{type}, #{content}, #{commentator}, #{likeCount}, #{commentCount}, #{gmtCreate}, #{gmtModified});")
    void insert(Comment comment);

    @Select("SELECT * FROM comment WHERE id=#{id} LIMIT 1;")
    Comment getCommentById(@Param("id") Long id);

    @Select("SELECT * FROM comment WHERE type=#{type} AND parent_id=#{parentId} ORDER BY gmt_modified DESC;")
    List<Comment> listByTypeAndParentId(@Param("type") Integer type, @Param("parentId") Long parentId);

    @Update("UPDATE comment SET comment_count=comment_count+#{val} WHERE id=#{id};")
    void incCommentCountById(@Param("id") Long id, @Param("val") Integer val);

    @Update("UPDATE comment SET like_count=like_count+#{val} WHERE id=#{id};")
    void incLikeCountById(@Param("id") Long id, @Param("val") Integer val);
}
