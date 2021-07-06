package com.yuanvv.mawen.mapper;

import com.yuanvv.mawen.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    @Insert("INSERT INTO user (account_id, name, token, gmt_create, gmt_modified, avatar_url) " +
            "VALUES (#{accountId}, #{name}, #{token}, #{gmtCreate}, #{gmtModified}, #{avatarUrl});")
    void insert(User user);

    @Select("SELECT * FROM user WHERE token = #{token} LIMIT 1;")
    User findByToken(@Param("token") String token);

    @Select("SELECT * FROM user WHERE id = #{id} LIMIT 1;")
    User findById(@Param("id") Integer id);

    @Select("SELECT * FROM user WHERE account_id = #{accountId} LIMIT 1;")
    User findByAccountId(@Param("accountId") String accountId);

    @Select("UPDATE user SET name=#{name}, gmt_modified=#{gmtModified}, bio=#{bio}, avatar_url=#{avatarUrl} WHERE account_id=#{accountId};")
    void update(User user);
}
