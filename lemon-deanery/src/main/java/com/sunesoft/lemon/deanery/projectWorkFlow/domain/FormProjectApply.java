package com.sunesoft.lemon.deanery.projectWorkFlow.domain;

import com.sunesoft.lemon.deanery.resultCertificate.domain.ResultCertificateFile;
import com.sunesoft.lemon.syms.workflow.domain.BaseFormEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 开题
 */
@Entity
@Table(name="syy_oa_form_project_apply")
public class FormProjectApply extends BaseFormEntity {
    private String projectNo;
/*
* 项目名称
* */
    private String projectName;

    private String beginTime;
    private String endTime;

    private String assumeCompany;

    private String joinComopany;
    //组长id
    private String leaderId;
    //组长名
    private String leaderName;

    /**
     * 2、研究目标和主要研究内容
     */
    private String projectPlanInfoTxt;
    /**
     * 1国内外现状、技术发展趋势、生产需求和市场前景
     */
    private String prospect;
    /**
     * 2关键技术和技术研究路线（方案）比选
     */
   private String schemeOpt;
    /**
     * 3主要技术经济考核指标
     */
   private String  examineTarget;
    /**
     * 4、项目验收最终成果及成果形式
     */
   private String finalShape;

    /**
     * 5、项目研究进度计划
     */
    private String proResearchProgress;


    /**
     * 6、资源计划
     */
    private String resourcePlan ;

    /**
     * 7、前期研究，支撑条件分析
     */
    private String beforeStatusAnalyze  ;

    /**
     * 8、与本项目有关专利------
     */
    private String lawAnalyze  ;

    /**
     * 8、௄应用前景和经济效应------
     */
    private String vistaAndBenefit   ;

    /**
     * 9、௄项目主其他成员------
     */
    private String atherPeople   ;

    public String getEmployeeIds() {
        return employeeIds;
    }

    public void setEmployeeIds(String employeeIds) {
        this.employeeIds = employeeIds;
    }

    /**
     * 分管领导id
     */
    private String employeeIds;

    public String getSpecialistIds() {
        return specialistIds;
    }

    public void setSpecialistIds(String specialistIds) {
        this.specialistIds = specialistIds;
    }

    /**\
     * 专家的employeeid
     */
     private String specialistIds;

    public String getLeaderIds() {
        return leaderIds;
    }

    public void setLeaderIds(String leaderIds) {
        this.leaderIds = leaderIds;
    }

    /**
     * 领导的employeeid
     */
     private String leaderIds;

    /**
     * 批示
     */
    private String instruction;

    public String getReview_Comments() {
        return review_Comments;
    }

    public void setReview_Comments(String review_Comments) {
        this.review_Comments = review_Comments;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    /**
     * 审查意见
     */
    private String review_Comments;

    public String getSpecial_Type() {
        return special_Type;
    }

    public void setSpecial_Type(String special_Type) {
        this.special_Type = special_Type;
    }

    /**
     * 专业类别
     */
    private String special_Type;


    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinColumn(name = "form_proj_id")
    private List<FormOpenProjectFile> formOpenProjectFiles;

    public List<FormProjectApplyFile> getFiles() {
        return files;
    }

    public void setFiles(List<FormProjectApplyFile> files) {
        this.files = files;
    }

    /**
     附件
     */
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "projectFile_id")
    private List<FormProjectApplyFile> files;

    public  FormProjectApply(){
        this.setIsActive(true);
        this.setCreateDateTime(new Date());
        this.setLastUpdateTime(new Date());
        this.formOpenProjectFiles= new ArrayList<>();
        this.files=new ArrayList<>();
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

    public String getProjectPlanInfoTxt() {
        return projectPlanInfoTxt;
    }

    public void setProjectPlanInfoTxt(String projectPlanInfoTxt) {
        this.projectPlanInfoTxt = projectPlanInfoTxt;
    }

    public List<FormOpenProjectFile> getFormOpenProjectFiles() {
        return formOpenProjectFiles;
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

    public void setFormOpenProjectFiles(List<FormOpenProjectFile> formOpenProjectFiles) {
        this.formOpenProjectFiles = formOpenProjectFiles;
    }

    public String getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(String leaderId) {
        this.leaderId = leaderId;
    }

    public String getLeaderName() {
        return leaderName;
    }

    public void setLeaderName(String leaderName) {
        this.leaderName = leaderName;
    }

    public String getProspect() {
        return prospect;
    }

    public void setProspect(String prospect) {
        this.prospect = prospect;
    }

    public String getSchemeOpt() {
        return schemeOpt;
    }

    public void setSchemeOpt(String schemeOpt) {
        this.schemeOpt = schemeOpt;
    }

    public String getExamineTarget() {
        return examineTarget;
    }

    public void setExamineTarget(String examineTarget) {
        this.examineTarget = examineTarget;
    }

    public String getFinalShape() {
        return finalShape;
    }

    public void setFinalShape(String finalShape) {
        this.finalShape = finalShape;
    }

    public String getProResearchProgress() {
        return proResearchProgress;
    }

    public void setProResearchProgress(String proResearchProgress) {
        this.proResearchProgress = proResearchProgress;
    }

    public String getResourcePlan() {
        return resourcePlan;
    }

    public void setResourcePlan(String resourcePlan) {
        this.resourcePlan = resourcePlan;
    }

    public String getBeforeStatusAnalyze() {
        return beforeStatusAnalyze;
    }

    public void setBeforeStatusAnalyze(String beforeStatusAnalyze) {
        this.beforeStatusAnalyze = beforeStatusAnalyze;
    }

    public String getLawAnalyze() {
        return lawAnalyze;
    }

    public void setLawAnalyze(String lawAnalyze) {
        this.lawAnalyze = lawAnalyze;
    }

    public String getVistaAndBenefit() {
        return vistaAndBenefit;
    }

    public void setVistaAndBenefit(String vistaAndBenefit) {
        this.vistaAndBenefit = vistaAndBenefit;
    }

    public String getAtherPeople() {
        return atherPeople;
    }

    public void setAtherPeople(String atherPeople) {
        this.atherPeople = atherPeople;
    }

}
