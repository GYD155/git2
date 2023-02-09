package com.yu.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yu.springboot.bean.Course;
import com.yu.springboot.bean.Question;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CourseMapper extends BaseMapper<Course>
{
}
