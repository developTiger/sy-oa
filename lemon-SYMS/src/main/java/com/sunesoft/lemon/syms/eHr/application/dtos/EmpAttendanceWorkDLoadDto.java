package com.sunesoft.lemon.syms.eHr.application.dtos;

import java.util.Date;

/**
 * 某个员工某日的出勤信息
 * Created by zhouz on 2016/7/4.
 */
public class EmpAttendanceWorkDLoadDto {


    private  String empName;
    /**
     * 参加工作时间
     */
    private String joinTime;
    /**
     * 工龄
     */
    private  Integer workAge;
    /**
     * 应该享受休假天数
     */
    private Float shouldDays;
    /**
     * 当月休假数
     */
    private Float rest;
    /**
     * 累计休假数
     */
    private Float sumDays;
    /**
     * 未休假天数
     */
    private Float notDays;
    /**
     * 疗养
     */
    private Float spa;
    /**
     * 休疗累计天数
     */
    private Float spaAndRestDays;

    /**
     * 补休天数
     */
    private Float compensate;

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(String joinTime) {
        this.joinTime = joinTime;
    }

    public Integer getWorkAge() {
        return workAge;
    }

    public void setWorkAge(Integer workAge) {
        this.workAge = workAge;
    }

    public Float getShouldDays() {
        return shouldDays;
    }

    public void setShouldDays(Float shouldDays) {
        this.shouldDays = shouldDays;
    }

    public Float getRest() {
        return rest;
    }

    public void setRest(Float rest) {
        this.rest = rest;
    }

    public Float getSumDays() {
        return sumDays;
    }

    public void setSumDays(Float sumDays) {
        this.sumDays = sumDays;
    }

    public Float getNotDays() {
        return notDays;
    }

    public void setNotDays(Float notDays) {
        this.notDays = notDays;
    }

    public Float getSpa() {
        return spa;
    }

    public void setSpa(Float spa) {
        this.spa = spa;
    }

    public Float getSpaAndRestDays() {
        return spaAndRestDays;
    }

    public void setSpaAndRestDays(Float spaAndRestDays) {
        this.spaAndRestDays = spaAndRestDays;
    }

    public Float getCompensate() {
        return compensate;
    }

    public void setCompensate(Float compensate) {
        this.compensate = compensate;
    }
}
