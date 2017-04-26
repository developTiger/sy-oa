package com.sunesoft.lemon.deanery.projectWorkFlow.application.criteria;

import com.sunesoft.lemon.fr.results.PagedCriteria;
import com.sunesoft.lemon.syms.workflow.application.dtos.BaseFormDto;

/**
 * Created by zy on 2016/8/19.
 */
public class FormProjectExecutoryCriteria extends PagedCriteria {

    private Long approverId;
    private String executionNamesel;
    //专业类别
    private String specialtyType;

    public String getExecutionNamesel() {
        return executionNamesel;
    }

    public void setExecutionNamesel(String executionNamesel) {
        this.executionNamesel = executionNamesel;
    }

    /**
     * 项目编号
     */
    private String projectNo;
    /**
     * 项目名称
     */
    private String projectName;
    /**
     * 项目年度
     */
    private String projectYear;
    //承担单位
    private String assumeCompany;
    //参加单位
    private String joinComopany;

    //组长id
    private String leaderId;
    //组长名
    private String leaderName;
    //项目开始时间--结束时间
    private String beginTime;
    private String endTime;

/*--------------------------------------------------*/
    /**
     * 研究类容
     */
    private String projectPlanInfoTxt;

    /**
     * 项目进展情况
     */
    private String projectActualComplete;
    /**
     * 取得阶段性成果
     */
    private String chieveResult;

    /**
     * 项目经济效益分析
     */
    private String economicAnalysis;

    /**
     * 课题进展程度
     */
    private String projectEvaluate;

    /**
     * 项目存在问题及一下安排
     */
    private String projectProblem;

    //专家
    private String proficientOpinion;
    //批示
    private String instructions;

    public Long getApproverId() {
        return approverId;
    }

    public void setApproverId(Long approverId) {
        this.approverId = approverId;
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

    public String getProjectYear() {
        return projectYear;
    }

    public void setProjectYear(String projectYear) {
        this.projectYear = projectYear;
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

    public String getProjectPlanInfoTxt() {
        return projectPlanInfoTxt;
    }

    public void setProjectPlanInfoTxt(String projectPlanInfoTxt) {
        this.projectPlanInfoTxt = projectPlanInfoTxt;
    }

    public String getProjectActualComplete() {
        return projectActualComplete;
    }

    public void setProjectActualComplete(String projectActualComplete) {
        this.projectActualComplete = projectActualComplete;
    }

    public String getChieveResult() {
        return chieveResult;
    }

    public void setChieveResult(String chieveResult) {
        this.chieveResult = chieveResult;
    }

    public String getEconomicAnalysis() {
        return economicAnalysis;
    }

    public void setEconomicAnalysis(String economicAnalysis) {
        this.economicAnalysis = economicAnalysis;
    }

    public String getProjectEvaluate() {
        return projectEvaluate;
    }

    public void setProjectEvaluate(String projectEvaluate) {
        this.projectEvaluate = projectEvaluate;
    }

    public String getProjectProblem() {
        return projectProblem;
    }

    public void setProjectProblem(String projectProblem) {
        this.projectProblem = projectProblem;
    }

    public String getSpecialtyType() {
        return specialtyType;
    }

    public void setSpecialtyType(String specialtyType) {
        this.specialtyType = specialtyType;
    }
}
