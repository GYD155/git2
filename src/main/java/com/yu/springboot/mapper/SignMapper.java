package com.yu.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yu.springboot.bean.ExamPaper;
import com.yu.springboot.bean.Sign;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SignMapper extends BaseMapper<Sign>
{
//    Page<Sign> findPage(Page<Sign> page, @Param("id") Integer id);
//    @Delete("delete from rolemenu where role_id = #{roleId}")
//    int deleteByRoleId(@Param("roleId") Integer roleId);
//
//    @Select("select menu_id from rolemenu where role_id = #{roleId}")
//    List<Integer> selectByRoleId(@Param("roleId")Integer roleId);
}
