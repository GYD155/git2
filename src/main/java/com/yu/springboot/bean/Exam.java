package com.yu.springboot.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Exam
{
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private String name;
    private String time;
    private String state;
    private String userRole;
    private Integer courseId;
//    private Boolean enable;


    public Exam(Integer id, String name, String time, String state, String userRole, Integer courseId)
    {
        this.id = id;
        this.name = name;
        this.time = time;
        this.state = state;
        this.userRole = userRole;
        this.courseId = courseId;
    }

    public Exam()
    {
    }
}
