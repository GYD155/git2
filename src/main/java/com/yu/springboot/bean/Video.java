package com.yu.springboot.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("video")
public class Video
{
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private String name;
    private String url;

    public Video(Integer id, String name, String url)
    {
        this.id = id;
        this.name = name;
        this.url = url;
    }

    public Video()
    {
    }
}
