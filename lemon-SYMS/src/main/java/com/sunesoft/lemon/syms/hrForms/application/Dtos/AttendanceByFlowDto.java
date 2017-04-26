package com.sunesoft.lemon.syms.hrForms.application.Dtos;

import com.sunesoft.lemon.syms.hrForms.domain.enums.LeaveType;

import java.util.Date;

/**
 * Created by admin on 2017/3/3.
 */
public class AttendanceByFlowDto {

    private Long applyer;

    private String applyName;

    private Long deptId;

    private String deptName;

    private LeaveType leaveType;

    private String businessFlow;

    private Date beginTime;

    private Date endTime;

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getBusinessFlow() {
        return businessFlow;
    }

    public void setBusinessFlow(String businessFlow) {
        this.businessFlow = businessFlow;
    }

    public Long getApplyer() {
        return applyer;
    }

    public void setApplyer(Long applyer) {
        this.applyer = applyer;
    }

    public String getApplyName() {
        return applyName;
    }

    public void setApplyName(String applyName) {
        this.applyName = applyName;
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

    public LeaveType getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(LeaveType leaveType) {
        this.leaveType = leaveType;
    }
}
