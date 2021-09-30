package com.yuanvv.mawen.mapper;

import com.yuanvv.mawen.model.Ad;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AdMapper {

    @Select("SELECT * FROM ad WHERE status=1 AND pos=#{pos} AND gmt_start <= #{now} AND gmt_end > #{now};")
    List<Ad> listAvailable(@Param("pos") String pos, @Param("now") Long timestamp);
}
