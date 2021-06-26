package com.yuanvv.mawen.mapper;

import com.yuanvv.mawen.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Insert("INSERT INTO user (account_id, name, token, gmt_create, gmt_modified, avatar_url) " +
            "VALUES (#{accountId}, #{name}, #{token}, #{gmtCreate}, #{gmtModified}, #{avatarUrl});")
    void insert(User user);

    @Select("SELECT * FROM user WHERE token = #{token} LIMIT 1;")
    User findByToken(@Param("token") String token);

    @Select("SELECT * FROM user WHERE id = #{id} LIMIT 1;")
    User findById(@Param("id") Integer id);
}
