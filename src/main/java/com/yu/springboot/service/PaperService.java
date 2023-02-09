package com.yu.springboot.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yu.springboot.bean.Exam;
import com.yu.springboot.bean.Paper;
import com.yu.springboot.mapper.ExamMapper;
import com.yu.springboot.mapper.PaperMapper;
import org.springframework.stereotype.Service;

@Service
public class PaperService extends ServiceImpl<PaperMapper, Paper>
{
}
