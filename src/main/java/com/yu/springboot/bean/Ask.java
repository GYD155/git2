package com.yu.springboot.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Ask
{
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private String qus;
    private String reply;
    private String state;
    private String userName;
    private String userRole;

    public Ask(Integer id, String qus, String reply, String state, String userName, String userRole)
    {
        this.id = id;
        this.qus = qus;
        this.reply = reply;
        this.state = state;
        this.userName = userName;
        this.userRole = userRole;
    }

    public Ask()
    {
    }
}
