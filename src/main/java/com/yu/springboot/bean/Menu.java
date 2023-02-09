package com.yu.springboot.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.util.List;

public class Menu
{
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private String name;
    private String description;
    private String path;


    @TableField(exist = false)
    private List<Menu> children;
    private Integer pid;
    private String pagePath;

    public Menu()
    {
    }

    public Menu(Integer id, String name, String description, String path, String pagePath,Integer pid, List<Menu> children)
    {
        this.id = id;
        this.name = name;
        this.description = description;
        this.path = path;

        this.children = children;
        this.pid=pid;
        this.pagePath=pagePath;
    }

    public String getPagePath()
    {
        return pagePath;
    }

    public void setPagePath(String pagePath)
    {
        this.pagePath = pagePath;
    }

    public Integer getPid()
    {
        return pid;
    }

    public void setPid(Integer pid)
    {
        this.pid = pid;
    }

    public List<Menu> getChildren()
    {
        return children;
    }

    public void setChildren(List<Menu> children)
    {
        this.children = children;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getPath()
    {
        return path;
    }

    public void setPath(String path)
    {
        this.path = path;
    }



    @Override
    public String toString()
    {
        return "Menu{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", path='" + path + '\'' +

                ", children=" + children +
                ", pid=" + pid +
                ", pagePath='" + pagePath + '\'' +
                '}';
    }
}
