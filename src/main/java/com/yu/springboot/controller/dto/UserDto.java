package com.yu.springboot.controller.dto;

import com.yu.springboot.bean.Menu;
import lombok.Data;

import java.util.List;

//接收前端登录请求的参数
//@Data
public class UserDto
{
    private String name;
    private String pwd;
    private String role;
    private List<Menu> menus;

    public UserDto()
    {
    }

    public UserDto(String name, String pwd, String role, List<Menu> menus)
    {
        this.name = name;
        this.pwd = pwd;
        this.role = role;
        this.menus = menus;
    }

    public String getRole()
    {
        return role;
    }

    public void setRole(String role)
    {
        this.role = role;
    }

    public List<Menu> getMenus()
    {
        return menus;
    }

    public void setMenus(List<Menu> menus)
    {
        this.menus = menus;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPwd()
    {
        return pwd;
    }


    public void setPwd(String pwd)
    {
        this.pwd = pwd;
    }

    @Override
    public String toString()
    {
        return "UserDto{" +
                "name='" + name + '\'' +
                ", pwd='" + pwd + '\'' +
                ", role='" + role + '\'' +
                ", menus=" + menus +
                '}';
    }
}

