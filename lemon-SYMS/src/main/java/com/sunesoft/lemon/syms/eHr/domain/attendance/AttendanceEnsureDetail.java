package com.sunesoft.lemon.syms.eHr.domain.attendance;

import com.sunesoft.lemon.fr.ddd.BaseEntity;
import com.sunesoft.lemon.syms.eHr.domain.attendance.enums.AttendanceKind;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by zhouz on 2016/7/5.
 */
@Deprecated
@Entity
@Table(name="syy_oa_att_ensure_detail")
public class AttendanceEnsureDetail extends BaseEntity {

    /**
     * 部门编号
     */
    @Column(name="dep_id")
    private Long depId; //部门编号

    /**
     *部门名称
     */
    @Column(name="dep_name")
    private String depName;//部门名称

    /**
     *
     */
    @Column(name="detail_status")
    private Integer status;

    /**
     *员工Id
     */
    @Column(name="emp_id")
    private Long empId; //员工Id

    /**
     *员工姓名
     */
    @Column(name="emp_name")
    private String name;//员工姓名

    /**
     *考勤类型
     */
    @Column(name="attendance_kind")
    private AttendanceKind attendanceKind;//考勤类型

    /**
     *
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="att_date")
    private Date attDate;

    /**
     *开始时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="begin_date")
    private Date beginDate;//开始时间

    /**
     *结束时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="end_date")
    private Date endDate;//结束时间

    /**
     *时间段
     */
    @Column(name="time_range")
    private Float timeRange;//时间段
    /**
     * 个人考勤是否确认，true为是
     */
    @Column(name = "is_sure")
    private Boolean isSure=false;
    /**
     * 确认时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="sure_date")
    private Date sureDate;//结束时间

    public AttendanceEnsureDetail() {
        this.setCreateDateTime(new Date());
        this.setLastUpdateTime(new Date());
        this.setAttendanceKind(AttendanceKind.I);
        this.setIsActive(true);
        this.timeRange=0f;
    }

    public Long getDepId() {
        return depId;
    }

    public void setDepId(Long depId) {
        this.depId = depId;
    }

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

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

    public AttendanceKind getAttendanceKind() {
        return attendanceKind;
    }

    public void setAttendanceKind(AttendanceKind attendanceKind) {
        this.attendanceKind = attendanceKind;
    }

    public Date getAttDate() {
        return attDate;
    }

    public void setAttDate(Date attDate) {
        this.attDate = attDate;
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

    public Float getTimeRange() {
        return timeRange;
    }

    public void setTimeRange(Float timeRange) {
        this.timeRange = timeRange;
    }

    public Boolean getIsSure() {
        return isSure;
    }

    public void setIsSure(Boolean isSure) {
        this.isSure = isSure;
    }

    public Date getSureDate() {
        return sureDate;
    }

    public void setSureDate(Date sureDate) {
        this.sureDate = sureDate;
    }
}
