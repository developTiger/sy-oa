package com.sunesoft.lemon.syms.eHr.application.dtos;

/**
 * Created by xiazl on 2017/3/6.
 */
public class AttendSumDto {

    private Long empId;

    /**
     * 员工姓名
     */
    private String empName;//员工姓名
    /**
     * 出勤类型
     */
    private String cord;//出勤类型
    /**
     * 数量
     */
    private Integer count;

    public AttendSumDto(Long empId, String empName, String cord, Integer count) {
        this.empId = empId;
        this.empName = empName;
        this.cord = cord;
        this.count = count;
    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

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

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
