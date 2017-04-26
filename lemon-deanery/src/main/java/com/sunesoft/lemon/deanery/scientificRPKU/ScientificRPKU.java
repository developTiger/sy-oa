package com.sunesoft.lemon.deanery.scientificRPKU;

import com.sunesoft.lemon.fr.ddd.BaseEntity;
import com.sunesoft.lemon.syms.workflow.domain.BaseFormEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 立项流程的表
 * 科研项目信息表
 * Created by wangwj on 2016/6/27 0027.
 */
@Entity
@Table(name = "syy_oa_scientific_ku")
public class ScientificRPKU extends BaseEntity {

    private String niandu_str;

    public String getNiandu_str() {
        return niandu_str;
    }

    public void setNiandu_str(String niandu_str) {
        this.niandu_str = niandu_str;
    }

    //年度
    private Date niandu;

    /**
     * 项目编号
     */
    @Column(name = "project_no",columnDefinition = "VARCHAR2(50) DEFAULT NULL ")
    private String projectNo;
    /**
     * 项目名称
     */
    @Column(name = "project_name",columnDefinition = "VARCHAR2(200) DEFAULT NULL ")
    private String projectName;
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
    @Column(name = "specialty_type",columnDefinition = "VARCHAR2(50) DEFAULT NULL ")
    private String specialtyType;
    /**
     * 研究内容，技术经济指标及项目进度安排
     */
    @Column(name = "project_plan_info",columnDefinition = "BLOB DEFAULT NULL ")
    private byte[] projectPlanInfoTxt;
    /**
     * 承担单位
     */
    @Column(name = "assume_company",columnDefinition = "VARCHAR2(500) DEFAULT NULL ")
    private String assumeCompany;
    /**
     * 参加单位
     */
    @Column(name = "join_company",columnDefinition = "VARCHAR2(500) DEFAULT NULL ")
    private String joinComopany;
    /**
     * 开始时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "begin_time")
    private Date beginTime;
    /**
     * 截止时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_time")
    private Date endTime;
    /**
     * 备注
     */
    @Column(name = "remark",columnDefinition = "VARCHAR2(500) DEFAULT NULL ")
    private String remark;
    /**
     * 项目类型
     * 1：项目，2：课题，3：专题
     */
    @Column(name = "project_type",columnDefinition = "VARCHAR2(50) DEFAULT NULL ")
    private String projectType;
    /**
     * 项目长
     */
    @Column(name = "leader",columnDefinition = "NUMBER(19) DEFAULT NULL ")
    private Long leader;
    /**
     * 副项目长
     */
    @Column(name = "deputy",columnDefinition = "NUMBER(19) DEFAULT NULL ")
    private Long deputy;

    /**
     * 项目长名
     */
    @Column(name = "leaderName",columnDefinition = "VARCHAR2(50) DEFAULT NULL")
    private String leaderName;
    /**
     * 副项目长名
     */
    @Column(name = "deputyName",columnDefinition = "VARCHAR2(50) DEFAULT NULL")
    private String deputyName;

    /**
     * 组员（多个人员，以英文字符的逗号进行分割，对应人员表中的ID）
     */
    @Column(name = "group_members",columnDefinition = "VARCHAR2(200) DEFAULT NULL ")
    private String groupMembers;

    /**
     * 所属部门
     */
    @Column(name = "department",columnDefinition = "NUMBER(19) DEFAULT NULL ")
    private Long dept;

    @Column(name = "deptName",columnDefinition = "VARCHAR2(20) DEFAULT NULL ")
    private String deptName;


    /**
     *
     * 插入数据库为数字
     * 0：未运行
     * 1：运行中
     * 2:验收完成
     */
    @Column(name = "project_YXStatus",columnDefinition = "VARCHAR2(10) DEFAULT NULL ")
    private String projectYXStatus;

    /**
     *
     * 插入数据库为数字
     * 0：立项未开始
     * 1：立项中
     * 2:完成
     */
    @Column(name = "project_status",columnDefinition = "VARCHAR2(10) DEFAULT NULL ")
    private String projectStatus;

    /**
     * 0未验收
     * 1验收中
     * 2验收完成
     */
    @Column(name = "project_YSStatus",columnDefinition = "VARCHAR2(5) DEFAULT NULL ")
    private String projectYSStatus;
    /**
     * 流程3
     * 0未检测
     * 1检测运行中
     * 2检测运行完成
     */
    @Column(name = "project_JCStatus",columnDefinition = "VARCHAR2(5) DEFAULT NULL ")
    private String projectJCStatus;
    /**
     * 流程2
     * 0未开题
     * 1开题进行中
     * 2已开题
     */
    @Column(name = "project_KTStatus",columnDefinition = "VARCHAR2(5) DEFAULT NULL ")
    private String projectKTStatus;
    /**
     * 0未申报
     * 1申报进行中
     * 2已申报
     */
    @Column(name = "project_SBStatus",columnDefinition = "VARCHAR2(5) DEFAULT NULL ")
    private String projectSBStatus;

    @Column(name = "projectID",columnDefinition = "NUMBER(10) DEFAULT NULL ")
    private Long projectID;

    @Column(name = "proficient_opinion",columnDefinition = "VARCHAR2(2000) DEFAULT NULL ")
    private String proficientOpinion;
    @Column(name = "instructions" ,columnDefinition = "VARCHAR2(200) DEFAULT NULL ")
    private String instructions;


    public ScientificRPKU() {
        this.setIsActive(true);
        this.setCreateDateTime(new Date());
        this.setLastUpdateTime(new Date());

    }

    public String getProficientOpinion() {
        return proficientOpinion;
    }

    public void setProficientOpinion(String proficientOpinion) {
        this.proficientOpinion = proficientOpinion;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getProjectYXStatus() {
        return projectYXStatus;
    }

    public void setProjectYXStatus(String projectYXStatus) {
        this.projectYXStatus = projectYXStatus;
    }

    public String getProjectJCStatus() {
        return projectJCStatus;
    }

    public void setProjectJCStatus(String projectJCStatus) {
        this.projectJCStatus = projectJCStatus;
    }

    public String getLeaderName() {
        return leaderName;
    }

    public void setLeaderName(String leaderName) {
        this.leaderName = leaderName;
    }

    public String getDeputyName() {
        return deputyName;
    }

    public void setDeputyName(String deputyName) {
        this.deputyName = deputyName;
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

    public String getSpecialtyType() {
        return specialtyType;
    }

    public void setSpecialtyType(String specialtyType) {
        this.specialtyType = specialtyType;
    }

    public byte[] getProjectPlanInfoTxt() {
        return projectPlanInfoTxt;
    }

    public void setProjectPlanInfoTxt(byte[] projectPlanInfoTxt) {
        this.projectPlanInfoTxt = projectPlanInfoTxt;
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

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getGroupMembers() {
        return groupMembers;
    }

    public void setGroupMembers(String groupMembers) {
        this.groupMembers = groupMembers;
    }

    public String getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(String projectStatus) {
        this.projectStatus = projectStatus;
    }

    public Long getDept() {
        return dept;
    }

    public void setDept(Long dept) {
        this.dept = dept;
    }

    public Long getLeader() {
        return leader;
    }

    public void setLeader(Long leader) {
        this.leader = leader;
    }

    public Long getDeputy() {
        return deputy;
    }

    public void setDeputy(Long deputy) {
        this.deputy = deputy;
    }

    public Date getNiandu() {
        return niandu;
    }

    public void setNiandu(Date niandu) {
        this.niandu = niandu;
    }

    public String getProjectYSStatus() {
        return projectYSStatus;
    }

    public void setProjectYSStatus(String projectYSStatus) {
        this.projectYSStatus = projectYSStatus;
    }

    public String getProjectKTStatus() {
        return projectKTStatus;
    }

    public void setProjectKTStatus(String projectKTStatus) {
        this.projectKTStatus = projectKTStatus;
    }

    public String getProjectSBStatus() {
        return projectSBStatus;
    }

    public void setProjectSBStatus(String projectSBStatus) {
        this.projectSBStatus = projectSBStatus;
    }

    public Long getProjectID() {
        return projectID;
    }

    public void setProjectID(Long projectID) {
        this.projectID = projectID;
    }
}
