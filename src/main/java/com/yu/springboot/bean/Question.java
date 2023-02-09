package com.yu.springboot.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

public class Question
{
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private String name;
    private Integer type;
    private String a;
    private String b;
    private String c;
    private String d;
    private Integer score;
    private String answer;
    private Integer userId;
    private String time;
    private Integer courseId;

    public Question()
    {
    }

    public Question(Integer id, String name, Integer type, String a, String b, String c, String d, Integer score, String answer, Integer userId, String time, Integer courseId)
    {
        this.id = id;
        this.name = name;
        this.type = type;
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.score = score;
        this.answer = answer;
        this.userId = userId;
        this.time = time;
        this.courseId = courseId;
    }

    public Integer getCourseId()
    {
        return courseId;
    }

    public void setCourseId(Integer courseId)
    {
        this.courseId = courseId;
    }

    @Override
    public String toString()
    {
        return "Question{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", a='" + a + '\'' +
                ", b='" + b + '\'' +
                ", c='" + c + '\'' +
                ", d='" + d + '\'' +
                ", score=" + score +
                ", answer='" + answer + '\'' +
                ", userId=" + userId +
                ", time='" + time + '\'' +
                ", courseId=" + courseId +
                '}';
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

    public Integer getType()
    {
        return type;
    }

    public void setType(Integer type)
    {
        this.type = type;
    }

    public String getA()
    {
        return a;
    }

    public void setA(String a)
    {
        this.a = a;
    }

    public String getB()
    {
        return b;
    }

    public void setB(String b)
    {
        this.b = b;
    }

    public String getC()
    {
        return c;
    }

    public void setC(String c)
    {
        this.c = c;
    }

    public String getD()
    {
        return d;
    }

    public void setD(String d)
    {
        this.d = d;
    }

    public Integer getScore()
    {
        return score;
    }

    public void setScore(Integer score)
    {
        this.score = score;
    }

    public String getAnswer()
    {
        return answer;
    }

    public void setAnswer(String answer)
    {
        this.answer = answer;
    }

    public Integer getUserId()
    {
        return userId;
    }

    public void setUserId(Integer userId)
    {
        this.userId = userId;
    }

    public String getTime()
    {
        return time;
    }

    public void setTime(String time)
    {
        this.time = time;
    }
}
