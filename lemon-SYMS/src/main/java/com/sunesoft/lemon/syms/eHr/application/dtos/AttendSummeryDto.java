package com.sunesoft.lemon.syms.eHr.application.dtos;

import java.util.List;
import java.util.Map;

/**
 * Created by kkk on 2017/3/3.
 */
public class AttendSummeryDto {

    private Long empId;

    private String empName;
    private String cord;
    private Integer countCord;

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getCord() {
        return cord;
    }

    public void setCord(String cord) {
        this.cord = cord;
    }

    public Integer getCountCord() {
        return countCord;
    }

    public void setCountCord(Integer countCord) {
        this.countCord = countCord;
    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }
}
