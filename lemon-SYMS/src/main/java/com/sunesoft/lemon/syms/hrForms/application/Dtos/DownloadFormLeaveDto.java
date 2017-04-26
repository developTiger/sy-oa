package com.sunesoft.lemon.syms.hrForms.application.Dtos;

/**
 * Created by xiazl on 2017/4/18.
 */
public class DownloadFormLeaveDto {
    /**
     * 请假人
     */
    private String applyerName;
    /**
     * 请假类型
     */
    private String leaveTypeName;
    /**
     * 前往目的地
     */
    private String target;
    /**
     * 请假天数
     */
    private Integer days;//小时  如 1.5 小时
    /**
     * 起止时间
     */
    private String dateSlot;
    /**
     * 申请时间
     */
    private String screateDateTime; // 创建时间
    /**
     * 归属部门
     */
    private String deptName;

    public String getApplyerName() {
        return applyerName;
    }

    public void setApplyerName(String applyerName) {
        this.applyerName = applyerName;
    }

    public String getLeaveTypeName() {
        return leaveTypeName;
    }

    public void setLeaveTypeName(String leaveTypeName) {
        this.leaveTypeName = leaveTypeName;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public String getDateSlot() {
        return dateSlot;
    }

    public void setDateSlot(String dateSlot) {
        this.dateSlot = dateSlot;
    }

    public String getScreateDateTime() {
        return screateDateTime;
    }

    public void setScreateDateTime(String screateDateTime) {
        this.screateDateTime = screateDateTime;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
}
