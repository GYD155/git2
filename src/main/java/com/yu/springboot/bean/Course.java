package com.yu.springboot.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Course
{
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private String name;
    private String user_role;

    public Course(Integer id, String name, String user_role)
    {
        this.id = id;
        this.name = name;
        this.user_role = user_role;
    }

    public Course()
    {
    }
}
