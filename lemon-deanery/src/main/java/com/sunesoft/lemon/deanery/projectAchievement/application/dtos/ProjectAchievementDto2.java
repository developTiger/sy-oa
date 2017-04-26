package com.sunesoft.lemon.deanery.projectAchievement.application.dtos;

import com.sunesoft.lemon.syms.workflow.application.dtos.BaseFormDto;

import java.util.Date;

/**
 * Created by MJ003 on 2016/10/19.
 */
public class ProjectAchievementDto2 {


    /*
     * 年度
     * */
    private String year;
    /**
     *
     *项目编号
     */
    private String projectNo;
    /**
     * 项目名称
     */
    private String projectName;
  /*
   * 申报开始时间
   *
   * */
    private String deliverBeginTime;
   /*
    * 申报结束时间
    * */
    private String deliverEndTime;
     /*
    * 获奖时间
    * */
    private String awardTime;

    /*
    * 项目长名字
    * */
    private String leaderName;

    /*
     * 组员
     * */
    private String groupMembers;
    /**
     * 专业类别
     */
    private String specialtyType;

    /*
     *主要完成单位
     * */
    private String assumeCompany;
    /*
     *主要协作单位
     * */
    private String joinComopany;
    /*
    *主题词
    * */
    private String ztc;
    /*
    *三新一性
    * */
    private String sxyx;

    /*
    *内容摘要
    * */
    private String nrzy;
    /*
    *专利类型及数量
    * */
    private String zllxjsl;

    /**
     * 级别 1.国家级、2省部级、3市级、4油田公司级、5院级
     */
    private String rank1;



    /**
     * 等级 1特等奖、2一等奖、3二等奖、4三等奖
     * @return
     */
    private String grade1;

    /**
     * 颁发单位
     * @return
     */
    private String issuing_unit;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getDeliverBeginTime() {
        return deliverBeginTime;
    }

    public void setDeliverBeginTime(String deliverBeginTime) {
        this.deliverBeginTime = deliverBeginTime;
    }

    public String getDeliverEndTime() {
        return deliverEndTime;
    }

    public void setDeliverEndTime(String deliverEndTime) {
        this.deliverEndTime = deliverEndTime;
    }

    public String getAwardTime() {
        return awardTime;
    }

    public void setAwardTime(String awardTime) {
        this.awardTime = awardTime;
    }

    public String getLeaderName() {
        return leaderName;
    }

    public void setLeaderName(String leaderName) {
        this.leaderName = leaderName;
    }

    public String getGroupMembers() {
        return groupMembers;
    }

    public void setGroupMembers(String groupMembers) {
        this.groupMembers = groupMembers;
    }

    public String getSpecialtyType() {
        return specialtyType;
    }

    public void setSpecialtyType(String specialtyType) {
        this.specialtyType = specialtyType;
    }

    public String getAssumeCompany() {
        return assumeCompany;
    }

    public void setAssumeCompany(String assumeCompany) {
        this.assumeCompany = assumeCompany;
    }

    public String getJoinComopany() {
        return joinComopany;
    }

    public void setJoinComopany(String joinComopany) {
        this.joinComopany = joinComopany;
    }

    public String getZtc() {
        return ztc;
    }

    public void setZtc(String ztc) {
        this.ztc = ztc;
    }

    public String getSxyx() {
        return sxyx;
    }

    public void setSxyx(String sxyx) {
        this.sxyx = sxyx;
    }

    public String getNrzy() {
        return nrzy;
    }

    public void setNrzy(String nrzy) {
        this.nrzy = nrzy;
    }

    public String getZllxjsl() {
        return zllxjsl;
    }

    public void setZllxjsl(String zllxjsl) {
        this.zllxjsl = zllxjsl;
    }

    public String getRank1() {
        return rank1;
    }

    public void setRank1(String rank1) {
        this.rank1 = rank1;
    }

    public String getGrade1() {
        return grade1;
    }

    public void setGrade1(String grade1) {
        this.grade1 = grade1;
    }

    public String getIssuing_unit() {
        return issuing_unit;
    }

    public void setIssuing_unit(String issuing_unit) {
        this.issuing_unit = issuing_unit;
    }
}
