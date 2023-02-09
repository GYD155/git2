package com.yu.springboot.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yu.springboot.bean.Question;
import com.yu.springboot.bean.User;
import com.yu.springboot.mapper.QuestionMapper;
import com.yu.springboot.mapper.UserMapper;
import org.springframework.stereotype.Service;

@Service
public class QuestionService extends ServiceImpl<QuestionMapper, Question>
{
}
