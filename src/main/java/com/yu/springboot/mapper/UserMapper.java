package com.yu.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yu.springboot.bean.User;
import com.yu.springboot.controller.dto.UserPasswordDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User>//DAO层
{
    @Update("update user set pwd = #{newPassword} where name = #{name} and pwd = #{password}")
    int updatePassword(UserPasswordDto userPasswordDTO);
//    @Select("select * from user")
//    List<User> findAll();
//
//    @Insert("insert into user(name,pwd,email,phone) values(#{name},#{pwd},#{email},#{phone})")
//    int insert(User user);
//
//    int update(User user);
//
//    @Delete("delete from user where id=#{id}")
//    Integer delete(@Param("id") Integer id);
//
//    @Select("select * from user where name like #{name} limit #{pageNum},#{pageSize}")
//    List<User> select(@Param("pageNum") Integer pageNum,@Param("pageSize") Integer pageSize,@Param("name") String name);
//
//    @Select("select count(*) from user where name like #{name}")
//    Integer selectNum(@Param("name") String name);
}
