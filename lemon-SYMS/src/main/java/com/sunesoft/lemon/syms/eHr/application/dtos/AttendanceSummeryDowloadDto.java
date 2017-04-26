package com.sunesoft.lemon.syms.eHr.application.dtos;

import com.sunesoft.lemon.fr.ddd.BaseEntity;

import java.util.Date;

/**
 * Created by MJ006 on 2016/6/29.
 */
public class AttendanceSummeryDowloadDto extends BaseEntity {

    /**
     * 员工姓名
     */
    private String empName;//员工姓名

    /**
     * 出勤
     */
    private Float attend;

    /**
     * 公休加班
     */
    private Float gwork;

    /**
     * 节日加班
     */
    private Float jwork;

    /**
     * 野外
     */
    private Float wild;

    /**
     * 出差
     */
    private Float businessTravel;

    /**
     * 学历
     */
    private Float studyExperience;
    /**
     * 脱产学习
     */
    private Float tstudy;
    /**
     * 休假
     */
    private Float vacation;
    /**
     * 疗养
     */
    private Float spa;

    /**
     * 探亲
     */
    private Float visitFamily;
    /**
     * 婚丧
     */
    private Float weddingFuneral;
    /**
     * 工伤
     */
    private Float workHurt;
    /**
     * 产假
     */
    private Float birthChild;
    /**
     * 产后休长假
     */

    private Float lbirth;
    /**
     * 护理
     */
    private Float nurse;
    /**
     * 病假
     */
    private Float sick;
    /**
     * 事假
     */
    private Float aleave;

    private Float absenteeism;//旷工

    /**
     * 拘留
     */
    private Float custody;
    /**
     * 戒毒
     */
    private Float drug;
    /**
     * 劳保
     */
    private Float labor;


    private Float compensate;//补休

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

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
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

    public Float getWild() {
        return wild;
    }

    public void setWild(Float wild) {
        this.wild = wild;
    }

    public Float getStudyExperience() {
        return studyExperience;
    }

    public void setStudyExperience(Float studyExperience) {
        this.studyExperience = studyExperience;
    }

    public Float getTstudy() {
        return tstudy;
    }

    public void setTstudy(Float tstudy) {
        this.tstudy = tstudy;
    }

    public Float getVacation() {
        return vacation;
    }

    public void setVacation(Float vacation) {
        this.vacation = vacation;
    }

    public Float getSpa() {
        return spa;
    }

    public void setSpa(Float spa) {
        this.spa = spa;
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

    public Float getWorkHurt() {
        return workHurt;
    }

    public void setWorkHurt(Float workHurt) {
        this.workHurt = workHurt;
    }

    public Float getLbirth() {
        return lbirth;
    }

    public void setLbirth(Float lbirth) {
        this.lbirth = lbirth;
    }

    public Float getSick() {
        return sick;
    }

    public void setSick(Float sick) {
        this.sick = sick;
    }

    public Float getAleave() {
        return aleave;
    }

    public void setAleave(Float aleave) {
        this.aleave = aleave;
    }

    public Float getAbsenteeism() {
        return absenteeism;
    }

    public void setAbsenteeism(Float absenteeism) {
        this.absenteeism = absenteeism;
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

    public Float getCompensate() {
        return compensate;
    }

    public void setCompensate(Float compensate) {
        this.compensate = compensate;
    }
}
