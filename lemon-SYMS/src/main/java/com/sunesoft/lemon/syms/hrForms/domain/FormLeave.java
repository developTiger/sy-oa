package com.sunesoft.lemon.syms.hrForms.domain;

import com.sunesoft.lemon.syms.eHr.domain.attendance.enums.AttendanceKind;
import com.sunesoft.lemon.syms.hrForms.domain.enums.LeaveType;
import com.sunesoft.lemon.syms.workflow.domain.BaseFormEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by zhouz on 2016/6/29.
 */
@Entity
@Table(name = "syy_oa_form_Leave")
public class FormLeave extends BaseFormEntity {

    /**
     * 备注
     */
    @Column(name = "content")
    private String content;

    /**
     * 实际返岗时间
     */
    @Column(name = "actual_time")
    private Date actualTime;

    /**
     * 请假类型
     */
    @Enumerated(EnumType.STRING)
    private LeaveType leaveType;
    /**
     * 提表时间
     */
    @Column(name = "from_time")
    private Date fromTime;
    /**
     * 原因
     */
    private String reason;
    /**
     * 目的
     */
    private String target;
    /**
     * 截止时间
     */
    @Column(name = "to_time")
    private Date toTime;
    /**
     * 历时多久
     */
    @Column(name = "count_time")
    private Float countTime;//小时  如 1.5 小时



    public FormLeave(){
        this.setIsActive(true);
        this.setCreateDateTime(new Date());
        this.setLastUpdateTime(new Date());
        this.setApplyDate(new Date());
        this.setViewUrl("forms");
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getActualTime() {
        return actualTime;
    }

    public void setActualTime(Date actualTime) {
        this.actualTime = actualTime;
    }

    public LeaveType getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(LeaveType leaveType) {
        this.leaveType = leaveType;
    }

    public Date getFromTime() {
        return fromTime;
    }

    public void setFromTime(Date fromTime) {
        this.fromTime = fromTime;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public Date getToTime() {
        return toTime;
    }

    public void setToTime(Date toTime) {
        this.toTime = toTime;
    }

    public Float getCountTime() {
        return countTime;
    }

    public void setCountTime(Float countTime) {
        this.countTime = countTime;
    }
}
