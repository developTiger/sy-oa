package com.sunesoft.lemon.deanery.projectPlanInput.applicatioin.dto;

/**
 * Created by pxj on 2016/9/13.
 */
public class ProjectPlanOutput {
    /**
     * 项目年度
     */
    private String projectPlan_InputYear;


    /**
     * 项目编号
     */

    private String projectPlan_Number;

    /**
     * 项目名称
     */

    private String projectPlan_Name;



    /**
     *研究内容、技术经济指标及项目进度安排
     */

    private String projectPlan_Content;

    /**
     * 承担单位
     */

    private String projectPlan_BearUnit;

    /**
     * 参加单位
     */

    private String projectPlan_ParticipatingUnit;

  /*  *//**
     * 起止时间
     *//*

    private String projectPlan_StartEndTime;*/

    /**
     * 项目开始时间
     */
    private String startTime;

       /* *
     * 项目结束时间
     */

    private String endTime;

    /**
     * 负责人
     */
    private String projectPlan_Manager;



    public String getProjectPlan_InputYear() {
        return projectPlan_InputYear;
    }

    public void setProjectPlan_InputYear(String projectPlan_InputYear) {
        this.projectPlan_InputYear = projectPlan_InputYear;
    }

    public String getProjectPlan_Name() {
        return projectPlan_Name;
    }

    public void setProjectPlan_Name(String projectPlan_Name) {
        this.projectPlan_Name = projectPlan_Name;
    }

    public String getProjectPlan_Content() {
        return projectPlan_Content;
    }

    public void setProjectPlan_Content(String projectPlan_Content) {
        this.projectPlan_Content = projectPlan_Content;
    }

    public String getProjectPlan_BearUnit() {
        return projectPlan_BearUnit;
    }

    public void setProjectPlan_BearUnit(String projectPlan_BearUnit) {
        this.projectPlan_BearUnit = projectPlan_BearUnit;
    }

    public String getProjectPlan_ParticipatingUnit() {
        return projectPlan_ParticipatingUnit;
    }

    public void setProjectPlan_ParticipatingUnit(String projectPlan_ParticipatingUnit) {
        this.projectPlan_ParticipatingUnit = projectPlan_ParticipatingUnit;
    }

  /*  public String getProjectPlan_StartEndTime() {
        return projectPlan_StartEndTime;
    }

    public void setProjectPlan_StartEndTime(String projectPlan_StartEndTime) {
        this.projectPlan_StartEndTime = projectPlan_StartEndTime;
    }*/

    public String getProjectPlan_Manager() {
        return projectPlan_Manager;
    }

    public void setProjectPlan_Manager(String projectPlan_Manager) {
        this.projectPlan_Manager = projectPlan_Manager;
    }


    public String getProjectPlan_Number() {
        return projectPlan_Number;
    }

    public void setProjectPlan_Number(String projectPlan_Number) {
        this.projectPlan_Number = projectPlan_Number;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

}
