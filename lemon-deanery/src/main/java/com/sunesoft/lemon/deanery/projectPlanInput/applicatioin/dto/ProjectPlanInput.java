package com.sunesoft.lemon.deanery.projectPlanInput.applicatioin.dto;

import com.sunesoft.lemon.fr.results.PagedCriteria;

import java.util.Date;

/**
 * Created by pxj on 2016/9/9.
 */
public class ProjectPlanInput extends PagedCriteria{
    public String getProjectPlan_InputYear_Str() {
        return projectPlan_InputYear_Str;
    }

    public void setProjectPlan_InputYear_Str(String projectPlan_InputYear_Str) {
        this.projectPlan_InputYear_Str = projectPlan_InputYear_Str;
    }

    public Long getMenagerid() {
        return menagerid;
    }

    public void setMenagerid(Long menagerid) {
        this.menagerid = menagerid;
    }
  //zhuangtai
    private String projectPlan_State;

    public String getProjectPlan_State() {
        return projectPlan_State;
    }

    public void setProjectPlan_State(String projectPlan_State) {
        this.projectPlan_State = projectPlan_State;
    }
    /**
     * 项目负责人id
     */
    public Long menagerid;
    /**
     * 主表id
     * @return
     */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private  Long id;

    public Date getProjectPlan_StartTime() {
        return projectPlan_StartTime;
    }

    public void setProjectPlan_StartTime(Date projectPlan_StartTime) {
        this.projectPlan_StartTime = projectPlan_StartTime;
    }

    public Date getProjectPlan_EndTime() {
        return projectPlan_EndTime;
    }

    public void setProjectPlan_EndTime(Date projectPlan_EndTime) {
        this.projectPlan_EndTime = projectPlan_EndTime;
    }

    /**
     * 项目开始时间
     */
    private Date  projectPlan_StartTime;
    /**
     * 项目结束时间
     */
    private Date  projectPlan_EndTime;

    /**
     * 导入年份String
     */
   private String projectPlan_InputYear_Str;

    public Date getProjectPlan_InputYear() {
        return projectPlan_InputYear;
    }

    public void setProjectPlan_InputYear(Date projectPlan_InputYear) {
        this.projectPlan_InputYear = projectPlan_InputYear;
    }

    /**
     * 导入年份
     */
    private Date projectPlan_InputYear;

    /**
     * 序号
     */
    private Long projectPlan_No;

    /**
     * 编号
     */
    private String projectPlan_Number;

    public String getProjectPlan_Number() {
        return projectPlan_Number;
    }

    public void setProjectPlan_Number(String projectPlan_Number) {
        this.projectPlan_Number = projectPlan_Number;
    }

    /**
     * 名称
     *
     */

    private String projectPlan_Name;

    /**
     * 专业类别
     */

    private String projectPlan_Type;

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

    /**
     * 起止时间
     */

    private String projectPlan_StartEndTime;

    /**
     * 负责人
     */
    private String projectPlan_Manager;

    /**
     * 负责人邮箱
     */
    private String projectPlan_Email;

    public String getProjectPlan_Email() {
        return projectPlan_Email;
    }

    public void setProjectPlan_Email(String projectPlan_Email) {
        this.projectPlan_Email = projectPlan_Email;
    }

    /**
     * 备注
     */

    private String projectPlan_Remark;

    public String getEndTime_Str() {
        return endTime_Str;
    }

    public void setEndTime_Str(String endTime_Str) {
        this.endTime_Str = endTime_Str;
    }

    public String getStartTime_Str() {
        return startTime_Str;
    }

    public void setStartTime_Str(String startTime_Str) {
        this.startTime_Str = startTime_Str;
    }

    /**
     * 计划导入查询开始时间
     */
    private String startTime_Str;

    private Date startTime;

    private Date endTime;

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * 计划导入查询结束时间
     */
    private String endTime_Str;

    public Long getProjectPlan_No() {
        return projectPlan_No;
    }

    public void setProjectPlan_No(Long projectPlan_No) {
        this.projectPlan_No = projectPlan_No;
    }


    public String getProjectPlan_Name() {
        return projectPlan_Name;
    }

    public void setProjectPlan_Name(String projectPlan_Name) {
        this.projectPlan_Name = projectPlan_Name;
    }

    public String getProjectPlan_Type() {
        return projectPlan_Type;
    }

    public void setProjectPlan_Type(String projectPlan_Type) {
        this.projectPlan_Type = projectPlan_Type;
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

    public String getProjectPlan_StartEndTime() {
        return projectPlan_StartEndTime;
    }

    public void setProjectPlan_StartEndTime(String projectPlan_StartEndTime) {
        this.projectPlan_StartEndTime = projectPlan_StartEndTime;
    }

    public String getProjectPlan_Manager() {
        return projectPlan_Manager;
    }

    public void setProjectPlan_Manager(String projectPlan_Manager) {
        this.projectPlan_Manager = projectPlan_Manager;
    }

    public String getProjectPlan_Remark() {
        return projectPlan_Remark;
    }

    public void setProjectPlan_Remark(String projectPlan_Remark) {
        this.projectPlan_Remark = projectPlan_Remark;
    }





}
