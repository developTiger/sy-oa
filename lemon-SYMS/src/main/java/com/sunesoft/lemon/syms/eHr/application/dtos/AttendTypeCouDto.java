package com.sunesoft.lemon.syms.eHr.application.dtos;


import com.sunesoft.lemon.syms.eHr.domain.attend.AttendType;

import java.util.List;

/**
 * Created by MJ006 on 2016/6/29.
 */
public class AttendTypeCouDto {

    /**
     * 名称
     */
    private String name;

    /**
     * 标记符
     */
    private String cord;

    private String count;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCord() {
        return cord;
    }

    public void setCord(String cord) {
        this.cord = cord;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
