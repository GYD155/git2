package com.yu.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yu.springboot.bean.ExamPaper;
import com.yu.springboot.bean.PaperQuestion;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ExamPaperMapper extends BaseMapper<ExamPaper>
{
//    @Delete("delete from rolemenu where role_id = #{roleId}")
//    int deleteByRoleId(@Param("roleId") Integer roleId);
//
//    @Select("select menu_id from rolemenu where role_id = #{roleId}")
//    List<Integer> selectByRoleId(@Param("roleId")Integer roleId);
}
