package com.cj.service;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface SlaveMapper {
    @Update("update user set username=#{username} where id = #{id}")
    public void updateUser(@Param("username") String username, @Param("id") Integer id);
}
