package com.sunesoft.lemon.syms.eHr.application.dtos;

/**
 * 节假日加班
 * Created by xiazl on 2016/7/6.
 */
public class EmpAttendWorkDto {

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
     * 累计休假数
     */
    private Integer sumDays;
    /**
     * 未休假天数
     */
    private Integer notDays;
    /**
     * 疗养
     */
    private Integer spa;
    /**
     * 休疗累计天数
     */
    private Integer spaAndRestDays;

    /**
     * 补休天数
     */
    private Integer compensate;

    /**
     * 双休加班天数
     */
    private Integer weekendWork;
    /**
     * 节日加班天数
     */
    private Integer vacationWork;



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



    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public Integer getSumDays() {
        return sumDays;
    }

    public void setSumDays(Integer sumDays) {
        this.sumDays = sumDays;
    }

    public Integer getNotDays() {
        return notDays;
    }

    public void setNotDays(Integer notDays) {
        this.notDays = notDays;
    }

    public Integer getSpa() {
        return spa;
    }

    public void setSpa(Integer spa) {
        this.spa = spa;
    }

    public Integer getSpaAndRestDays() {
        return spaAndRestDays;
    }

    public void setSpaAndRestDays(Integer spaAndRestDays) {
        this.spaAndRestDays = spaAndRestDays;
    }

    public Integer getVacationWork() {
        return vacationWork;
    }

    public void setVacationWork(Integer vacationWork) {
        this.vacationWork = vacationWork;
    }

    public Integer getWeekendWork() {
        return weekendWork;
    }

    public void setWeekendWork(Integer weekendWork) {
        this.weekendWork = weekendWork;
    }

    public Integer getCompensate() {
        return compensate;
    }

    public void setCompensate(Integer compensate) {
        this.compensate = compensate;
    }
}
