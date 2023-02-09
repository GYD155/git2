package com.yu.springboot.controller.dto;

import lombok.Data;

public class UserPasswordDto
{
        private String name;
        private String password;
        private String newPassword;

    public UserPasswordDto()
    {
    }

    public UserPasswordDto(String name, String password, String newPassword)
    {
        this.name = name;
        this.password = password;
        this.newPassword = newPassword;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getNewPassword()
    {
        return newPassword;
    }

    public void setNewPassword(String newPassword)
    {
        this.newPassword = newPassword;
    }

    @Override
    public String toString()
    {
        return "UserPasswordDto{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", newPassword='" + newPassword + '\'' +
                '}';
    }
}
