package com.sunesoft.lemon.syms.eHr.application.dtos;

import java.util.Date;

/**
 * 某个员工某日的出勤信息
 * Created by zhouz on 2016/7/4.
 */
public class EmpAttendanceDto {
    /**
     * id of emp
     */
    private Long empId;
    /**
     * name of emp
     */
    private String empName;
    /**
     * id of dept
     */
    private Long deptId;
    /**
     * name of dept
     */
    private String deptName;
    /**
     * 出勤时间
     */
    private Date attDate;
    /**
     *出勤类型
     */
    private String attendanceKind;
    /**
     * 单个员工是否确认
     */
    private Boolean isSure;
//    /**
//     * 出勤状态
//     */
//    private Integer status;

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

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Date getAttDate() {
        return attDate;
    }

    public void setAttDate(Date attDate) {
        this.attDate = attDate;
    }


    public String getAttendanceKind() {
        return attendanceKind;
    }

    public void setAttendanceKind(String attendanceKind) {
        this.attendanceKind = attendanceKind;
    }

    public Boolean getIsSure() {
        return isSure;
    }

    public void setIsSure(Boolean isSure) {
        this.isSure = isSure;
    }
}
