package com.yuanvv.mawen.mapper;

import com.yuanvv.mawen.model.Notification;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NotificationMapper {


    @Insert("INSERT INTO notification (notifier, notifier_name, receiver, outer_id, outer_title, type, gmt_create, status) " +
            "VALUES (#{notifier}, #{notifierName}, #{receiver}, #{outerId}, #{outerTitle}, #{type}, #{gmtCreate}, #{status});")
    void insert(Notification notification);

    @Select("SELECT * FROM notification WHERE receiver = #{id} ORDER BY status ASC, gmt_create DESC LIMIT #{limit} OFFSET #{offset};")
    List<Notification> getListByReceiverIdOrderByStatus(@Param("id") Long id, @Param("offset") Integer offset, @Param("limit") Integer limit);

    @Select("SELECT COUNT(1) FROM notification WHERE receiver=#{id};")
    Long countByReceiverId(@Param("id") Long id);

    @Select("SELECT COUNT(1) FROM notification WHERE receiver=#{id} AND status=#{status};")
    Long countByReceiverIdAndStatus(@Param("id") Long id, @Param("status") Integer status);

    @Select("SELECT * FROM notification WHERE id = #{id} LIMIT 1;")
    Notification getById(@Param("id") Long id);

    @Update("UPDATE notification SET notifier=#{notifier}, receiver=#{receiver}, outer_id=#{outerId}, type=#{type}, " +
            "gmt_create=#{gmtCreate}, status=#{status}, notifier_name=#{notifierName}, outer_title=#{outerTitle} " +
            "WHERE id=#{id};")
    void update(Notification notification);

}
