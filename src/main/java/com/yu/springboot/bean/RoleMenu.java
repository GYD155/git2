package com.yu.springboot.bean;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("rolemenu")
@Data
public class RoleMenu
{
//    @TableId("roleId")
    private Integer roleId;
    private Integer menuId;

}
