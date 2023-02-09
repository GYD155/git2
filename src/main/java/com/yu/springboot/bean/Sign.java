package com.yu.springboot.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Sign
{
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private Integer examId;
    private String userName;
    private String state;
    private String name;

    public Sign(Integer id, Integer examId, String userName, String state, String name)
    {
        this.id = id;
        this.examId = examId;
        this.userName = userName;
        this.state = state;
        this.name = name;
    }

    public Sign()
    {
    }
}
