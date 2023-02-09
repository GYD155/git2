package com.yu.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yu.springboot.bean.PaperQuestion;
import com.yu.springboot.bean.Question;
import com.yu.springboot.bean.RoleMenu;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PaperQuestionMapper extends BaseMapper<PaperQuestion>
{
    @Select("select q.* from paper_question pq left join question q on pq.question_id=q.id where pq.paper_id=#{paperId}")
    List<Question> selectQuestions(Integer paperId);

//    @Delete("delete from rolemenu where role_id = #{roleId}")
//    int deleteByRoleId(@Param("roleId") Integer roleId);
//
//    @Select("select menu_id from rolemenu where role_id = #{roleId}")
//    List<Integer> selectByRoleId(@Param("roleId")Integer roleId);
}
