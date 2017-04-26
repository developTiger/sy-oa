package com.sunesoft.lemon.syms.eHr.application.dtos;

/**
 * 节假日加班
 * Created by xiazl on 2016/7/6.
 */
public class EmpAttendanceWorkDto {
    /**
     * 查询年
     */
    private  Integer year;
    /**
     * 查询月
     */
    private Integer month;
    /**
     * name of emp
     */
    private Long empId;

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
     * 节日加班天数
     */
    private Float vacationWork;
    /**
     * 双休加班天数
     */
    private Float weekendWork;
    /**
     * 节日加班天数+双休加班天数
     */
    private Float sum;
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

    public Float getCompensate() {
        return compensate;
    }

    public void setCompensate(Float compensate) {
        this.compensate = compensate;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Float getRest() {
        return rest;
    }

    public void setRest(Float rest) {
        this.rest = rest;
    }

    public Float getNotDays() {
        return notDays;
    }

    public void setNotDays(Float notDays) {
        this.notDays = notDays;
    }

    public Float getShouldDays() {
        return shouldDays;
    }

    public void setShouldDays(Float shouldDays) {
        this.shouldDays = shouldDays;
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

    public Float getSum() {
        return sum;
    }

    public void setSum(Float sum) {
        this.sum = sum;
    }

    public Float getSumDays() {
        return sumDays;
    }

    public void setSumDays(Float sumDays) {
        this.sumDays = sumDays;
    }

    public Integer getWorkAge() {
        return workAge;
    }

    public void setWorkAge(Integer workAge) {
        this.workAge = workAge;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public Float getVacationWork() {
        return vacationWork;
    }

    public void setVacationWork(Float vacationWork) {
        this.vacationWork = vacationWork;
    }

    public Float getWeekendWork() {
        return weekendWork;
    }

    public void setWeekendWork(Float weekendWork) {
        this.weekendWork = weekendWork;
    }
}
