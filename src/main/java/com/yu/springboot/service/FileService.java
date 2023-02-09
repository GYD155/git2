package com.yu.springboot.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yu.springboot.bean.Exam;
import com.yu.springboot.bean.Files;
import com.yu.springboot.mapper.ExamMapper;
import com.yu.springboot.mapper.FileMapper;
import org.springframework.stereotype.Service;

@Service
public class FileService extends ServiceImpl<FileMapper, Files>
{
}
