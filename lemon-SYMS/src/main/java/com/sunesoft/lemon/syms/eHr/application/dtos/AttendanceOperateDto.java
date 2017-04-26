package com.sunesoft.lemon.syms.eHr.application.dtos;

import com.sunesoft.lemon.syms.eHr.domain.attendance.AttendanceDetail;
import com.sunesoft.lemon.syms.eHr.domain.attendance.AttendanceSummery;
import com.sunesoft.lemon.syms.eHr.domain.attendance.enums.AttendanceKind;
import com.sunesoft.lemon.syms.eHr.domain.dept.Deptment;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.util.Date;

/**
 * Created by MJ006 on 2016/6/29.
 */
public class AttendanceOperateDto {
    /**
     * 员工Id
     */
    private Long empId; //员工Id
    /**
     * 员工姓名
     */
    private String name;//员工姓名
    /**
     * 部门编号
     */
    private Long depId; //部门编号
    /**
     * 部门名称
     */
    private String depName;//部门名称
    /**
     * 考勤类型
     */
    private AttendanceKind attendanceKind;//考勤类型
    /**
     * 开始时间
     */
    private Date beginDate;//开始时间
    /**
     * 结束时间
     */
    private Date endDate;//结束时间
    /**
     * 时间段
     */
    private Float timeRange;//时间段


    /**
     * 打卡时间
     * @return
     */
    private Date attDate;

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getDepId() {
        return depId;
    }

    public void setDepId(Long depId) {
        this.depId = depId;
    }

    public AttendanceKind getAttendanceKind() {
        return attendanceKind;
    }

    public void setAttendanceKind(AttendanceKind attendanceKind) {
        this.attendanceKind = attendanceKind;
    }

    public Float getTimeRange() {
        return timeRange;
    }

    public void setTimeRange(Float timeRange) {
        this.timeRange = timeRange;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }


    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }

    public Date getAttDate() {
        return attDate;
    }

    public void setAttDate(Date attDate) {
        this.attDate = attDate;
    }
}

