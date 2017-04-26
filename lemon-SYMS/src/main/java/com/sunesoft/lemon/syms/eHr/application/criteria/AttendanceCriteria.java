package com.sunesoft.lemon.syms.eHr.application.criteria;

import com.sunesoft.lemon.fr.results.PagedCriteria;

import java.util.Date;

/**
 * Created by MJ006 on 2016/6/30.
 */
public class AttendanceCriteria extends PagedCriteria {
    /**
     * 员工Id
     */
    private Long empId; //员工Id
    /**
     * 员工名称
     */
    private String empName;
    /**
     * 时间
     */
    private Date beginDate;//时间

    private Date endDate;
    /**
     * 部门Id
     */
    private Long depId;//部门Id

    private String month;//按照月份查询考勤信息

    private String year;

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Long getDepId() {
        return depId;
    }

    public void setDepId(Long depId) {
        this.depId = depId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
