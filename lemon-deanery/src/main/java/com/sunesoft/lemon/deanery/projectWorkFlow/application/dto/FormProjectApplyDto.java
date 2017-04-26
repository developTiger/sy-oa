package com.sunesoft.lemon.deanery.projectWorkFlow.application.dto;

import com.sunesoft.lemon.syms.workflow.application.dtos.BaseFormDto;

import java.util.List;

/**
 * Created by zhouz on 2016/8/19.
 */
public class FormProjectApplyDto  extends BaseFormDto{

    private String projectNo;

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
    private String atherPeople;

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


    public List<Long> getChoose_LeaderId() {
        return choose_LeaderId;
    }

    public void setChoose_LeaderId(List<Long> choose_LeaderId) {
        this.choose_LeaderId = choose_LeaderId;
    }

    /**
     * 院领导id
     * @return
     */
    private List<Long> choose_LeaderId;

    public List<String> getFormNo_OpenFlow() {
        return formNo_OpenFlow;
    }

    public void setFormNo_OpenFlow(List<String> formNo_OpenFlow) {
        this.formNo_OpenFlow = formNo_OpenFlow;
    }

    /**
     * 汇总审批formNo
     * @return
     */
    private List<String> formNo_OpenFlow;

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

    public List<Long> getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(List<Long> employeeId) {
        this.employeeId = employeeId;
    }

    /**
     * 原分管领导id 现改为专家id
     */

    private List<Long> employeeId;

    public List<String> getList_projectNo() {
        return list_projectNo;
    }

    public void setList_projectNo(List<String> list_projectNo) {
        this.list_projectNo = list_projectNo;
    }

    /**
     * 项目库中项目编号
     */

    private List<String> list_projectNo;


    private int pageNumber = 1; // 去取第几页的数据

    public String getCurrentViewAction() {
        return currentViewAction;
    }

    public void setCurrentViewAction(String currentViewAction) {
        this.currentViewAction = currentViewAction;
    }

    /**
     * 审核页面
     * @return
     */
    private String currentViewAction;

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    private int pageSize = 10; //  每页多少条数据

    /**
     * 已上传文件id
     * @return
     */
    private List<String> already_FileId;

    public List<String> getAlready_FileId() {
        return already_FileId;
    }

    public void setAlready_FileId(List<String> already_FileId) {
        this.already_FileId = already_FileId;
    }

    public List<String> getAlready_FileName() {
        return already_FileName;
    }

    public void setAlready_FileName(List<String> already_FileName) {
        this.already_FileName = already_FileName;
    }

    /**
     * 已上传文件Name
     * @return
     */
    private List<String> already_FileName;

    public List<FormProjectApplyFileDto> getFileList() {
        return fileList;
    }

    public void setFileList(List<FormProjectApplyFileDto> fileList) {
        this.fileList = fileList;
    }

    private List<FormProjectApplyFileDto> fileList;

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

    public List<FormOpenProjectFileDto> formOpenProjectFileDtos;

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

    public List<FormOpenProjectFileDto> getFormOpenProjectFileDtos() {
        return formOpenProjectFileDtos;
    }

    public void setFormOpenProjectFileDtos(List<FormOpenProjectFileDto> formOpenProjectFileDtos) {
        this.formOpenProjectFileDtos = formOpenProjectFileDtos;
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
