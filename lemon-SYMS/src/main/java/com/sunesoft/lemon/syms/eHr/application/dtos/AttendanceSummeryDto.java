package com.sunesoft.lemon.syms.eHr.application.dtos;

import com.sunesoft.lemon.fr.ddd.BaseEntity;

import java.util.Date;

/**
 * Created by MJ006 on 2016/6/29.
 */
public class AttendanceSummeryDto extends BaseEntity {

    private Integer aStatus;
    /**
     * 员工Id
     */
    private Long empId; //员工Id
    /**
     * 员工姓名
     */
    private String empName;//员工姓名
    /**
     * 部门Id
     */
    private Long depId;//部门Id
    /**
     * 部门名称
     */
    private String depName;//部门名称
    /**
     * 年
     */
    private Integer year; //年
    /**
     * 月
     */
    private Integer month; //月


    /**
     * 出勤
     */
    private Float attend;

    /**
     * 探亲
     */
    private Float visitFamily;

    /**
     * 疗养
     */
    private Float spa;

    /**
     * 事假
     */
    private Float aleave;

    /**
     * 休假
     */
    private Float vacation;

    /**
     * 护理
     */
    private Float nurse;

    /**
     * 病假
     */
    private Float sick;

    /**
     * 学历
     */
    private Float studyExperience;

    /**
     * 脱产
     */
    private Float offJob;

    /**
     * 野外
     */
    private Float wild;

    /**
     * 出差
     */
    private Float businessTravel;

    /**
     * 婚丧
     */
    private Float weddingFuneral;

    /**
     * 产假
     */
    private Float birthChild;

    /**
     * 工伤
     */
    private Float workHurt;

    /**
     * 休息
     */
    private Float rest;

    /**
     * 劳保
     */
    private Float labor;

    /**
     * 节日加班天数
     */
    private Float vacationWork;
    /**
     * 双休加班天数
     */
    private Float weekendWork;

    private Float absenteeism;//旷工

    private Float compensate;//补休
    /**
     * 应该享受休假天数
     */
    private Float shouldDays;

    /**
     * 公休加班
     */

    private Float gwork;
    /**
     * 节日加班
     */

    private Float jwork;

    /**
     * 脱产学习
     */

    private Float tstudy;
    /**
     * 产后休长假
     */

    private Float lbirth;
    /**
     *拘留
     */


    private Float custody;
    /**
     * 戒毒
     */

    private Float drug;

    private Float Ddrug;
    private Float c001;






    public AttendanceSummeryDto() {
        this.setIsActive(true);
        this.setCreateDateTime(new Date());
        this.setLastUpdateTime(new Date());
        this.setaStatus(1);
    }

    public Float getAttend() {
        return attend;
    }

    public void setAttend(Float attend) {
        this.attend = attend;
    }

    public Float getBirthChild() {
        return birthChild;
    }

    public void setBirthChild(Float birthChild) {
        this.birthChild = birthChild;
    }

    public Float getBusinessTravel() {
        return businessTravel;
    }

    public void setBusinessTravel(Float businessTravel) {
        this.businessTravel = businessTravel;
    }

    public Float getLabor() {
        return labor;
    }

    public void setLabor(Float labor) {
        this.labor = labor;
    }



    public Float getNurse() {
        return nurse;
    }

    public void setNurse(Float nurse) {
        this.nurse = nurse;
    }

    public Float getOffJob() {
        return offJob;
    }

    public void setOffJob(Float offJob) {
        this.offJob = offJob;
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

    public Float getRest() {
        return rest;
    }

    public void setRest(Float rest) {
        this.rest = rest;
    }

    public Float getSick() {
        return sick;
    }

    public void setSick(Float sick) {
        this.sick = sick;
    }

    public Float getSpa() {
        return spa;
    }

    public void setSpa(Float spa) {
        this.spa = spa;
    }

    public Integer getaStatus() {
        return aStatus;
    }

    public void setaStatus(Integer aStatus) {
        this.aStatus = aStatus;
    }

    public Float getAleave() {
        return aleave;
    }

    public void setAleave(Float aleave) {
        this.aleave = aleave;
    }

    public Float getStudyExperience() {
        return studyExperience;
    }

    public void setStudyExperience(Float studyExperience) {
        this.studyExperience = studyExperience;
    }

    public Float getVacation() {
        return vacation;
    }

    public void setVacation(Float vacation) {
        this.vacation = vacation;
    }

    public Float getVisitFamily() {
        return visitFamily;
    }

    public void setVisitFamily(Float visitFamily) {
        this.visitFamily = visitFamily;
    }

    public Float getWeddingFuneral() {
        return weddingFuneral;
    }

    public void setWeddingFuneral(Float weddingFuneral) {
        this.weddingFuneral = weddingFuneral;
    }

    public Float getWild() {
        return wild;
    }

    public void setWild(Float wild) {
        this.wild = wild;
    }

    public Float getWorkHurt() {
        return workHurt;
    }

    public void setWorkHurt(Float workHurt) {
        this.workHurt = workHurt;
    }

    public Float getAbsenteeism() {
        return absenteeism;
    }

    public void setAbsenteeism(Float absenteeism) {
        this.absenteeism = absenteeism;
    }

    public Float getCompensate() {
        return compensate;
    }

    public void setCompensate(Float compensate) {
        this.compensate = compensate;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
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

    public Float getShouldDays() {
        return shouldDays;
    }

    public void setShouldDays(Float shouldDays) {
        this.shouldDays = shouldDays;
    }

    public Float getCustody() {
        return custody;
    }

    public void setCustody(Float custody) {
        this.custody = custody;
    }

    public Float getDrug() {
        return drug;
    }

    public void setDrug(Float drug) {
        this.drug = drug;
    }

    public Float getGwork() {
        return gwork;
    }

    public void setGwork(Float gwork) {
        this.gwork = gwork;
    }

    public Float getJwork() {
        return jwork;
    }

    public void setJwork(Float jwork) {
        this.jwork = jwork;
    }

    public Float getLbirth() {
        return lbirth;
    }

    public void setLbirth(Float lbirth) {
        this.lbirth = lbirth;
    }

    public Float getTstudy() {
        return tstudy;
    }

    public void setTstudy(Float tstudy) {
        this.tstudy = tstudy;
    }

}
