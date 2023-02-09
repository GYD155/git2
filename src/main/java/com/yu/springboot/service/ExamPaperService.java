package com.yu.springboot.service;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yu.springboot.bean.ExamPaper;
import com.yu.springboot.bean.Paper;
import com.yu.springboot.mapper.ExamPaperMapper;
import com.yu.springboot.mapper.PaperMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ExamPaperService extends ServiceImpl<ExamPaperMapper, ExamPaper>
{
    @Transactional
    public void addPaper(ExamPaper examPaper)
    {
        remove(new UpdateWrapper<ExamPaper>().eq("exam_id",examPaper.getExamId()));
        save(examPaper);
    }
}
