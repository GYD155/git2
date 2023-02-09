package com.yu.springboot.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yu.springboot.bean.Ask;
import com.yu.springboot.bean.Exam;
import com.yu.springboot.mapper.AskMapper;
import com.yu.springboot.mapper.ExamMapper;
import org.springframework.stereotype.Service;

@Service
public class AskService extends ServiceImpl<AskMapper, Ask>
{
}
