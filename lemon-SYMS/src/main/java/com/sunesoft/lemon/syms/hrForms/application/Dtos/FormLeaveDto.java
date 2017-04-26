package com.sunesoft.lemon.syms.hrForms.application.Dtos;

import com.sunesoft.lemon.syms.eHr.domain.attendance.enums.AttendanceKind;
import com.sunesoft.lemon.syms.hrForms.domain.enums.LeaveType;
import com.sunesoft.lemon.syms.workflow.application.dtos.BaseFormDto;

import java.util.Date;

/**
 * Created by zhouz on 2016/7/22.
 */
public class FormLeaveDto extends BaseFormDto {

    /**
     * 备注
     */
    private String content;

    /**
     * 实际返岗时间
     */
    private Date actualTime;

    /**
     * 请假类型
     */
    private LeaveType leaveType;

    private String leaveTypeName;
    /**
     * 提表时间
     */
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
    private Date toTime;
    /**
     * 历时多久
     */
    private Float countTime;//小时  如 1.5 小时
    private String screateDateTime; // 创建时间

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

    public String getScreateDateTime() {
        return screateDateTime;
    }

    public void setScreateDateTime(String screateDateTime) {
        this.screateDateTime = screateDateTime;
    }

    public String getLeaveTypeName() {
        return leaveTypeName;
    }

    public void setLeaveTypeName(String leaveTypeName) {
        this.leaveTypeName = leaveTypeName;
    }
}
