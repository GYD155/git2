package com.yu.springboot.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("paper_question")
@Data
public class PaperQuestion
{
//    @TableId("roleId")
    private Integer id;
    private Integer paperId;
    private Integer questionId;

    public PaperQuestion(Integer id, Integer paperId, Integer questionId)
    {
        this.id = id;
        this.paperId = paperId;
        this.questionId = questionId;
    }

    public PaperQuestion()
    {
    }
}
