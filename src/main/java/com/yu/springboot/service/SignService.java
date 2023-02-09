package com.yu.springboot.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yu.springboot.bean.Exam;
import com.yu.springboot.bean.Sign;
import com.yu.springboot.mapper.ExamMapper;
import com.yu.springboot.mapper.SignMapper;
import com.yu.springboot.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SignService extends ServiceImpl<SignMapper, Sign>
{
//    @Autowired
//    private SignMapper signMapper;
//
//    public Page<Sign> findPage(Page<Sign> page, Integer id) {
//        return signMapper.findPage(page, id);
//    }
}
