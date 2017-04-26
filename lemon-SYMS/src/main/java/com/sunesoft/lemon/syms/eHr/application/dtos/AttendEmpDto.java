package com.sunesoft.lemon.syms.eHr.application.dtos;

/**
 * Created by kkk on 2017/3/6.
 */
public class AttendEmpDto {

    private Long id;
    private String name;
    private String deptName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
}
