package com.sunesoft.lemon.deanery.projectWorkFlow.application.dto;

import com.sunesoft.lemon.deanery.projectWorkFlow.domain.FormProjectExecutionFile;
import com.sunesoft.lemon.syms.workflow.application.dtos.BaseFormDto;

import javax.persistence.Column;
import java.util.Date;
import java.util.List;

/**
 * Created by zy on 2016/8/19.
 */
public class FormProjectExecutoryDto extends BaseFormDto {
    /**
     * 附件id
     */
    private String fileId1;

    /**
     * 附件name
     */
    private String fileName1;

    public String getFileId1() {
        return fileId1;
    }

    public void setFileId1(String fileId1) {
        this.fileId1 = fileId1;
    }

    public String getFileName1() {
        return fileName1;
    }

    public void setFileName1(String fileName1) {
        this.fileName1 = fileName1;
    }

    List<ExecutionFileDto> executionFileList;

    /**
     * 附件id
     */
    private String fileId;

    /**
     * 附件name
     */
    private String fileName;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    //专业类别
    private String specialtyType;
    private String executionNamesel;

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

    public List<ExecutionFileDto> getExecutionFileList() {
        return executionFileList;
    }

    public void setExecutionFileList(List<ExecutionFileDto> executionFileList) {
        this.executionFileList = executionFileList;
    }
}
