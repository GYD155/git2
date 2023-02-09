package com.yu.springboot.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yu.springboot.bean.Course;
import com.yu.springboot.bean.Question;
import com.yu.springboot.mapper.CourseMapper;
import com.yu.springboot.mapper.QuestionMapper;
import org.springframework.stereotype.Service;

@Service
public class CourseService extends ServiceImpl<CourseMapper, Course>
{
}
