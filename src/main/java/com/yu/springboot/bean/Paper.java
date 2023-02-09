package com.yu.springboot.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Paper
{
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private String name;
    private Integer score;
    private Integer duration;
    private Integer courseId;

    public Paper(Integer id, String name, Integer score, Integer duration, Integer courseId)
    {
        this.id = id;
        this.name = name;
        this.score = score;
        this.duration = duration;
        this.courseId = courseId;
    }

    public Paper()
    {
    }
}
