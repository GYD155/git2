package com.yu.springboot.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yu.springboot.bean.Course;
import com.yu.springboot.bean.Exam;
import com.yu.springboot.mapper.CourseMapper;
import com.yu.springboot.mapper.ExamMapper;
import org.springframework.stereotype.Service;

@Service
public class ExamService extends ServiceImpl<ExamMapper, Exam>
{
}
