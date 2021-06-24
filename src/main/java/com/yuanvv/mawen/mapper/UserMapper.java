package com.yuanvv.mawen.mapper;

import com.yuanvv.mawen.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Insert("INSERT INTO user (account_id, name, token, gmt_create, gmt_modified) " +
            "VALUES (#{accountId}, #{name}, #{token}, #{gmtCreate}, #{gmtModified});")
    void insert(User user);

    @Select("SELECT * FROM user WHERE token = #{token} LIMIT 1;")
    User findByToken(@Param("token") String token);
}
