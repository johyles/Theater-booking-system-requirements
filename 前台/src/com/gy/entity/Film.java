package com.gy.entity;

import java.util.Date;

public class Film {
    private Integer id;
    private  String name;
    private String introduce;
    private  String  url;
    private  Date time;

    public void setId(int d)
    {
        this.id = d;
    }

    public Integer getId()
    {
        return id;
    }

    public void setName(String n)
    {
        name = n;
    }

    public String getName()
    {
        return name;
    }

    public void setIntroduce(String i)
    {
        introduce = i;
    }

    public String getIntroduce()
    {
        return introduce;
    }

    public void setUrl(String u)
    {
        url = u;
    }

    public String getUrl()
    {
        return url;
    }

    public void setDate(Date d)
    {
        time = d;
    }

    public Date getDate()
    {
        return time;
    }

}
