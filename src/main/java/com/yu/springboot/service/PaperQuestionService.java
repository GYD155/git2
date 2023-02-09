package com.yu.springboot.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yu.springboot.bean.Course;
import com.yu.springboot.bean.PaperQuestion;
import com.yu.springboot.bean.Question;
import com.yu.springboot.mapper.CourseMapper;
import com.yu.springboot.mapper.PaperQuestionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PaperQuestionService extends ServiceImpl<PaperQuestionMapper, PaperQuestion>
{
    @Autowired
    private  PaperQuestionMapper paperQuestionMapper;

    public List<Question> selectQuestions(Integer paperId)
    {
        return paperQuestionMapper.selectQuestions(paperId);
    }
}
