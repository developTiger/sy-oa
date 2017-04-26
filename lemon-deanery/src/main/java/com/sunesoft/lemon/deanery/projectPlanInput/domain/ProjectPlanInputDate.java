package com.sunesoft.lemon.deanery.projectPlanInput.domain;

import com.sunesoft.lemon.fr.ddd.BaseEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by pxj on 2016/9/9.
 */
@Entity
@Table(name = "syy_oa_project_plan")
public class ProjectPlanInputDate extends BaseEntity{
    /**
     * 导入年份
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "projectplan_inputyear")
    private Date projectPlan_InputYear;

    /**
     * 项目开始时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "projectplan_starttime")
    private Date projectPlan_StartTime;

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
     * 项目终止时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "projectplan_endtime")
    private Date projectPlan_EndTime;
    /**
     * 序号
     */
    @Column(name = "projectplan_no",columnDefinition = "VARCHAR2(500) DEFAULT NULL ")
    private Long projectPlan_No;
    /**
     * 编号
     */
    @Column(name = "projectplan_number",columnDefinition = "VARCHAR2(500) DEFAULT NULL ")
    private String projectPlan_Number;

    public String getProjectPlan_Number() {
        return projectPlan_Number;
    }

    public void setProjectPlan_Number(String projectPlan_Number) {
        this.projectPlan_Number = projectPlan_Number;
    }

    /**
     * 名称
     */
    @Column(name = "projectplan_name",columnDefinition = "VARCHAR2(500) DEFAULT NULL ")
    private String projectPlan_Name;

    /**
     * 专业类别
     */
    @Column(name = "projectplan_type",columnDefinition = "VARCHAR2(500) DEFAULT NULL ")
    private String projectPlan_Type;

    /**
     *研究内容、技术经济指标及项目进度安排
     */
    @Column(name = "projectplan_content",columnDefinition = "CLOB DEFAULT NULL ")
    private String projectPlan_Content;

    /**
     * 承担单位
     */
    @Column(name = "projectplan_bearunit",columnDefinition = "VARCHAR2(500) DEFAULT NULL ")
    private String projectPlan_BearUnit;

    /**
     * 参加单位
     */
    @Column(name = "projectplan_participatingunit",columnDefinition = "VARCHAR2(500) DEFAULT NULL ")
    private String projectPlan_ParticipatingUnit;

    /**
     * 起止时间
     */
    @Column(name = "projectplan_startendtime",columnDefinition = "VARCHAR2(500) DEFAULT NULL ")
    private String projectPlan_StartEndTime;

    /**
     * 负责人
     */
    @Column(name = "projectplan_manager",columnDefinition = "VARCHAR2(500) DEFAULT NULL ")
    private String projectPlan_Manager;

    /**
     * 负责人邮箱 关联员工邮箱
     */
    @Column(name = "projectplan_email",columnDefinition = "VARCHAR2(500) DEFAULT NULL ")
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
    @Column(name = "projectplan_remark",columnDefinition = "VARCHAR2(500) DEFAULT NULL ")
    private String projectPlan_Remark;

    public String getProjectPlan_State() {
        return projectPlan_State;
    }

    public void setProjectPlan_State(String projectPlan_State) {
        this.projectPlan_State = projectPlan_State;
    }

    /**
     * 状态 0为新增 1为立项运行 2为立项结束
     * @return
     */
    @Column(name="projectplan_state",columnDefinition = "VARCHAR2(500) DEFAULT NULL")
    private String projectPlan_State;

    public Long getProjectPlan_No() {
        return projectPlan_No;
    }

    public void setProjectPlan_No(Long projectPlan_No) {
        this.projectPlan_No = projectPlan_No;
    }

    public Date getProjectPlan_InputYear() {
        return projectPlan_InputYear;
    }

    public void setProjectPlan_InputYear(Date projectPlan_InputYear) {
        this.projectPlan_InputYear = projectPlan_InputYear;
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
    public ProjectPlanInputDate(){
        this.setIsActive(true);
        this.setCreateDateTime(new Date());
        this.setLastUpdateTime(new Date());
    }
}
