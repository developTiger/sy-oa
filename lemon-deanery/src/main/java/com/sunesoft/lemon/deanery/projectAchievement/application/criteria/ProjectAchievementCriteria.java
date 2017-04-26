package com.sunesoft.lemon.deanery.projectAchievement.application.criteria;

import com.sunesoft.lemon.fr.results.PagedCriteria;

import java.util.Date;

/**
 * Created by MJ003 on 2016/10/19.
 */
public class ProjectAchievementCriteria extends PagedCriteria {
    //项目名称
    private String projectName;
    //年度
    private String nianduTime;
    private Date niandu;
    //项目编号
    private String projectNo;
    //主要完成单位
    private String assumeCompany;
    /**
     * 专业类别
     * 01-油气勘探
     * 02-油气开发
     * 03-炼油化工
     * 04-油气集输
     * 05-计 算 机
     * 06- 软 科 学
     * 07-安全环保
     *
     */
    private String specialtyType;
    //获得时间
    private String beginTime;
    private Date beginDate;

    private String endTime;
    private Date endDate;
    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo;
    }

    public String getAssumeCompany() {
        return assumeCompany;
    }

    public void setAssumeCompany(String assumeCompany) {
        this.assumeCompany = assumeCompany;
    }

    public String getSpecialtyType() {
        return specialtyType;
    }

    public void setSpecialtyType(String specialtyType) {
        this.specialtyType = specialtyType;
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

    public String getNianduTime() {
        return nianduTime;
    }

    public void setNianduTime(String nianduTime) {
        this.nianduTime = nianduTime;
    }

    public Date getNiandu() {
        return niandu;
    }

    public void setNiandu(Date niandu) {
        this.niandu = niandu;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
