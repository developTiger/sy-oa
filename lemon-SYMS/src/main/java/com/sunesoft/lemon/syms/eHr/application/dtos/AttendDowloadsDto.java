package com.sunesoft.lemon.syms.eHr.application.dtos;


import com.sunesoft.lemon.syms.eHr.domain.attend.AttendType;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by MJ006 on 2016/6/29.
 */
public class AttendDowloadsDto {

    private String beginTime;

    private String endTime;

    private String deptName;
    /**
     * 员工姓名
     */
    private String empName;//员工姓名

    private  List<AttendDayDto> days;
    /**
     * 制度工作日
     */
    private String SystemWorking;

    /**
     * 出勤天数 I
     */
    private String AttendD;

    /**
     * 驾驶天数
     */
    private String driverD;
    /**
     * 误餐天数
     */
    private String wuCaiD;
    /**
     * 疾病救济天数
     */
    private String diseaseD;

    /**
     * 公休加班 J1
     */
    private String gwork;

    /**
     * 节日加班 J2
     */
    private String jwork;

    /**
     * 其他夜班
     */
    private String otherNightWork;

    /**
     * 小夜班
     */
    private String smallNightWork;

    /**
     * 大夜班
     */
    private String bigNightWork;

    /**
     * 野外 Y1
     */
    private String wildO;

    /**
     * 野外 Y2
     */
    private String wildW;

    /**
     * 野外 Y3
     */
    private String wildT;

    /**
     * 出差 Z
     */
    private String businessTravel;

    /**
     * 学历 X1
     */
    private String studyExperience;
    /**
     * 脱产学习 X2
     */
    private String tstudy;
    /**
     * 带薪休假 S1
     */
    private String vacation;
    /**
     * 疗养 L
     */
    private String spa;

    /**
     * 探亲 T
     */
    private String visitFamily;
    /**
     * 婚丧 H
     */
    private String weddingFuneral;
    /**
     * 工伤 G
     */
    private String workHurt;
    /**
     * 产假 C
     */
    private String birthChild;
    /**
     * 产后休长假 C1
     */

    private String lbirth;
    /**
     * (护理)假 S2
     */
    private String nurseH;
    /**
     * (陪护)假 S2
     */
    private String nurseP;
    /**
     * 计划生育 S2
     */
    private String nurseJ;
    /**
     * 病假 B
     */
    private String sick;
    /**
     * 事假 S3
     */
    private String aleave;
    /**
     * 旷工 K1
     */
    private String absenteeism;//

    /**
     * 拘留 K2
     */
    private String custody;
    /**
     * 戒毒 K3
     */
    private String drug;
    /**
     * 劳保 W
     */
    private String labor;
    /**
     * 保健天数
      */
    private String HealthCare;

    /**
     * 备注
     */
    private String remark;


    public String getNurseH() {
        return nurseH;
    }

    public void setNurseH(String nurseH) {
        this.nurseH = nurseH;
    }

    public String getNurseP() {
        return nurseP;
    }

    public void setNurseP(String nurseP) {
        this.nurseP = nurseP;
    }

    public String getNurseJ() {
        return nurseJ;
    }

    public void setNurseJ(String nurseJ) {
        this.nurseJ = nurseJ;
    }

    public String getHealthCare() {
        return HealthCare;
    }

    public void setHealthCare(String healthCare) {
        HealthCare = healthCare;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getSystemWorking() {
        return SystemWorking;
    }

    public void setSystemWorking(String systemWorking) {
        SystemWorking = systemWorking;
    }

    public String getDriverD() {
        return driverD;
    }

    public void setDriverD(String driverD) {
        this.driverD = driverD;
    }

    public String getWuCaiD() {
        return wuCaiD;
    }

    public void setWuCaiD(String wuCaiD) {
        this.wuCaiD = wuCaiD;
    }

    public String getDiseaseD() {
        return diseaseD;
    }

    public void setDiseaseD(String diseaseD) {
        this.diseaseD = diseaseD;
    }

    public String getGwork() {
        return gwork;
    }

    public void setGwork(String gwork) {
        this.gwork = gwork;
    }

    public String getJwork() {
        return jwork;
    }

    public void setJwork(String jwork) {
        this.jwork = jwork;
    }

    public String getBusinessTravel() {
        return businessTravel;
    }

    public void setBusinessTravel(String businessTravel) {
        this.businessTravel = businessTravel;
    }

    public String getStudyExperience() {
        return studyExperience;
    }

    public void setStudyExperience(String studyExperience) {
        this.studyExperience = studyExperience;
    }

    public String getTstudy() {
        return tstudy;
    }

    public void setTstudy(String tstudy) {
        this.tstudy = tstudy;
    }

    public String getVacation() {
        return vacation;
    }

    public void setVacation(String vacation) {
        this.vacation = vacation;
    }

    public String getSpa() {
        return spa;
    }

    public void setSpa(String spa) {
        this.spa = spa;
    }

    public String getVisitFamily() {
        return visitFamily;
    }

    public void setVisitFamily(String visitFamily) {
        this.visitFamily = visitFamily;
    }

    public String getWeddingFuneral() {
        return weddingFuneral;
    }

    public void setWeddingFuneral(String weddingFuneral) {
        this.weddingFuneral = weddingFuneral;
    }

    public String getWorkHurt() {
        return workHurt;
    }

    public void setWorkHurt(String workHurt) {
        this.workHurt = workHurt;
    }

    public String getBirthChild() {
        return birthChild;
    }

    public void setBirthChild(String birthChild) {
        this.birthChild = birthChild;
    }

    public String getLbirth() {
        return lbirth;
    }

    public void setLbirth(String lbirth) {
        this.lbirth = lbirth;
    }


    public String getSick() {
        return sick;
    }

    public void setSick(String sick) {
        this.sick = sick;
    }

    public String getAleave() {
        return aleave;
    }

    public void setAleave(String aleave) {
        this.aleave = aleave;
    }

    public String getAbsenteeism() {
        return absenteeism;
    }

    public void setAbsenteeism(String absenteeism) {
        this.absenteeism = absenteeism;
    }

    public String getCustody() {
        return custody;
    }

    public void setCustody(String custody) {
        this.custody = custody;
    }

    public String getDrug() {
        return drug;
    }

    public void setDrug(String drug) {
        this.drug = drug;
    }

    public String getLabor() {
        return labor;
    }

    public void setLabor(String labor) {
        this.labor = labor;
    }

    public List<AttendDayDto> getDays() {
        return days;
    }

    public void setDays(List<AttendDayDto> days) {
        this.days = days;
    }

    public String getAttendD() {
        return AttendD;
    }

    public void setAttendD(String attendD) {
        AttendD = attendD;
    }

    public String getOtherNightWork() {
        return otherNightWork;
    }

    public void setOtherNightWork(String otherNightWork) {
        this.otherNightWork = otherNightWork;
    }

    public String getSmallNightWork() {
        return smallNightWork;
    }

    public void setSmallNightWork(String smallNightWork) {
        this.smallNightWork = smallNightWork;
    }

    public String getBigNightWork() {
        return bigNightWork;
    }

    public void setBigNightWork(String bigNightWork) {
        this.bigNightWork = bigNightWork;
    }

    public String getWildO() {
        return wildO;
    }

    public void setWildO(String wildO) {
        this.wildO = wildO;
    }

    public String getWildW() {
        return wildW;
    }

    public void setWildW(String wildW) {
        this.wildW = wildW;
    }

    public String getWildT() {
        return wildT;
    }

    public void setWildT(String wildT) {
        this.wildT = wildT;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
