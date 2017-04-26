package com.sunesoft.lemon.syms.eHr.domain.attend;

import com.sunesoft.lemon.fr.ddd.BaseEntity;
import com.sunesoft.lemon.syms.eHr.domain.attend.enums.AttendanceStyle;

import javax.persistence.*;
import java.util.Date;

/**
 * 出勤（）记录每一天的数据
 * Created by xzl on 2016/6/25.
 */
@Entity
@Table(name = "syy_oa_hr_attd")
public class Attend extends BaseEntity {
    /**
     * 记录当天出勤情况(yyyy-MM-dd)
     */
    @Column(name = "date_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateTime;


    /**
     * 员工Id
     */
    @Column(name = "emp_id")
    private Long empId; //员工Id

    /**
     * 员工姓名
     */
    @Column(name = "emp_name")
    private String empName;//员工姓名

    /**
     * 部门Id
     */
    @Column(name = "dep_id")
    private Long depId;//部门Id

    /**
     * 部门名称
     */
    @Column(name = "dep_name")
    private String depName;//部门名称
    /**
     * 出勤类型
     */
    @ManyToOne
    @JoinColumn(name = "type_id")
    private AttendType type;

    /**
     * 出勤说明
     */
    private String detail;
    /**
     * 个人 是否确认
     */
    @Column(name = "is_sure")
    private Boolean isSure;
    /**
     * 部门是否确认
     */
    @Column(name = "dept_sure")
    private Boolean deptSure;
    /**
     * 确认时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "one_sure_time")
    private Date oneSureTime;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dept_sure_time")
    private Date deptSureTime;
    public Attend() {
        setIsSure(false);//默认未确认
        this.setIsActive(true);
        this.setCreateDateTime(new Date());
        this.setLastUpdateTime(new Date());
    }

    public Boolean getIsSure() {
        return isSure;
    }

    public void setIsSure(Boolean isSure) {
        this.isSure = isSure;
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

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
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

    public Boolean getDeptSure() {
        return deptSure;
    }

    public void setDeptSure(Boolean deptSure) {
        this.deptSure = deptSure;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public AttendType getType() {
        return type;
    }

    public void setType(AttendType type) {
        this.type = type;
    }

    public Date getOneSureTime() {
        return oneSureTime;
    }

    public void setOneSureTime(Date oneSureTime) {
        this.oneSureTime = oneSureTime;
    }

    public Date getDeptSureTime() {
        return deptSureTime;
    }

    public void setDeptSureTime(Date deptSureTime) {
        this.deptSureTime = deptSureTime;
    }
}
