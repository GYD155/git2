package com.yu.springboot.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("exam_paper")
@Data
public class ExamPaper
{
//    @TableId("roleId")
    private Integer id;
    private Integer paperId;
    private Integer examId;

    public ExamPaper(Integer id, Integer paperId, Integer examId)
    {
        this.id = id;
        this.paperId = paperId;
        this.examId = examId;
    }

    public ExamPaper()
    {
    }
}
