package com.sunesoft.lemon.deanery.project.domain;

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
@Table(name = "syy_oa_scientific_project")
public class ScientificResearchProject extends BaseFormEntity {


    //年度
    private Date niandu;
   //分管领导 审核人id
    private String leadSignId;
   //分管
    private String leadSignName;

    public String getLeadSignId() {
        return leadSignId;
    }

    public void setLeadSignId(String leadSignId) {
        this.leadSignId = leadSignId;
    }

//    @Column(name = "process_id",columnDefinition = "VARCHAR2(50) DEFAULT NULL ")
//    private String processId;
//    @Column(name = "order_id",columnDefinition = "VARCHAR2(50) DEFAULT NULL ")
//    private String orderId;
    //申报标题
   @Column(name = "baoTitle",columnDefinition = "VARCHAR2(100) DEFAULT NULL ")
    private String baoTitle;
    //申报时间
    @Column(name = "baoBeginDate",columnDefinition = "VARCHAR2(50) DEFAULT NULL ")
    private String baoBeginDate;

    @Column(name = "baoEndDate",columnDefinition = "VARCHAR2(50) DEFAULT NULL ")
    private String baoEndDate;
    //申报类容
    @Column(name = "baoContent",columnDefinition = "VARCHAR2(2000) DEFAULT NULL ")
    private String baoContent;


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
     * 流程中的状态
     * 插入数据库为数字
     * 1：代表运行
     * 2：代表撤销
     * 3：代表结束
     */
    @Column(name = "project_status",columnDefinition = "VARCHAR2(10) DEFAULT NULL ")
    private String projectStatus;

    /**
     * 所属部门
     */
    @Column(name = "department",columnDefinition = "NUMBER(19) DEFAULT NULL ")
    private Long dept;


    public List<ScientficProjectFile> getScientficProjectFileList() {
        return scientficProjectFileList;
    }

    public void setScientficProjectFileList(List<ScientficProjectFile> scientficProjectFileList) {
        this.scientficProjectFileList = scientficProjectFileList;
    }

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name ="scientificproject_id")
    private List<ScientficProjectFile> scientficProjectFileList;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name ="scientificApprove_id")
    private List<ScientficApproveFile> scientficApproveFileList;

   /* *//**
     * 公关目标
     *//*
    @Column(name = "relation_aim",columnDefinition = "VARCHAR2(200) DEFAULT NULL ")
    private String relationAim;

    *//**
     * 项目预期效果
     *//*
    @Column(name = "desired_effect",columnDefinition = "VARCHAR2(200) DEFAULT NULL ")
    private String desiredEffect;

    *//**
     * 经济指标
     *//*
    @Column(name = "economic_indicator",columnDefinition = "VARCHAR2(200) DEFAULT NULL ")
    private String economicIndicator;

*/
      //专家
     @Column(name = "proficient_opinion",columnDefinition = "VARCHAR2(2000) DEFAULT NULL ")
    private String proficientOpinion;
     //批示
    @Column(name = "instructions" ,columnDefinition = "VARCHAR2(200) DEFAULT NULL ")
    private String instructions;

    public ScientificResearchProject() {
        this.setIsActive(true);
        this.setCreateDateTime(new Date());
        this.setLastUpdateTime(new Date());
        this.setViewUrl("projectInfo");
        this.scientficProjectFileList=new ArrayList<>();
        this.scientficApproveFileList=new ArrayList<>();
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public Date getNiandu() {
        return niandu;
    }

    public void setNiandu(Date niandu) {
        this.niandu = niandu;
    }

    public String getLeadSignName() {
        return leadSignName;
    }

    public void setLeadSignName(String leadSignName) {
        this.leadSignName = leadSignName;
    }

    public String getBaoTitle() {
        return baoTitle;
    }

    public void setBaoTitle(String baoTitle) {
        this.baoTitle = baoTitle;
    }

    public String getBaoBeginDate() {
        return baoBeginDate;
    }

    public void setBaoBeginDate(String baoBeginDate) {
        this.baoBeginDate = baoBeginDate;
    }

    public String getBaoEndDate() {
        return baoEndDate;
    }

    public void setBaoEndDate(String baoEndDate) {
        this.baoEndDate = baoEndDate;
    }

    public String getBaoContent() {
        return baoContent;
    }

    public void setBaoContent(String baoContent) {
        this.baoContent = baoContent;
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

    public List<ScientficApproveFile> getScientficApproveFileList() {
        return scientficApproveFileList;
    }

    public void setScientficApproveFileList(List<ScientficApproveFile> scientficApproveFileList) {
        this.scientficApproveFileList = scientficApproveFileList;
    }

    public String getProficientOpinion() {
        return proficientOpinion;
    }

    public void setProficientOpinion(String proficientOpinion) {
        this.proficientOpinion = proficientOpinion;
    }
}
