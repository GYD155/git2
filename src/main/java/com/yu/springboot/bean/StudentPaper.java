package com.yu.springboot.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("student_paper")
@Data
public class StudentPaper
{
//    @TableId("roleId")
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private String userName;
    private Integer examId;
    private Integer score;
    private String paper;
//    private String userRole;


    public StudentPaper()
    {
    }

    public StudentPaper(Integer id, String userName, Integer examId, Integer score, String paper)
    {
        this.id = id;
        this.userName = userName;
        this.examId = examId;
        this.score = score;
        this.paper = paper;
    }
}
