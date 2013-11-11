package com.qsoft.pilotproject.model.dto;

import com.qsoft.pilotproject.model.ListFeed;

/**
 * User: binhtv
 * Date: 11/11/13
 * Time: 5:49 PM
 */
public class ResponseDTO
{
    private int code;
    private String status;
    private ListFeed data;

    public int getCode()
    {
        return code;
    }

    public void setCode(int code)
    {
        this.code = code;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public ListFeed getData()
    {
        return data;
    }

    public void setData(ListFeed data)
    {
        this.data = data;
    }
}
