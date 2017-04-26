package com.sunesoft.lemon.syms.eHr.application.dtos;

/**
 * Created by xiazl on 2017/4/7.
 */
public class RestSpanAndYear {
    private String name;

    private String no;

    private String deptName;

    private Integer days;

    public RestSpanAndYear(String name, String no, String deptName, Float days) {
        this.name = name;
        this.no = no;
        this.deptName = deptName;
        this.days = days == null ? null : days.intValue();
    }

    public RestSpanAndYear(String name, String no, String deptName) {
        this.name = name;
        this.no = no;
        this.deptName = deptName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }
}
