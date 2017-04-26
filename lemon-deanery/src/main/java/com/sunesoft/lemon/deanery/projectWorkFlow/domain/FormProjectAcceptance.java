package com.sunesoft.lemon.deanery.projectWorkFlow.domain;

import com.sunesoft.lemon.syms.workflow.domain.BaseFormEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zy on 2016/8/25.
 * 验收
 */

@Entity
@Table(name="syy_oa_form_project_acceptance")
public class  FormProjectAcceptance extends BaseFormEntity {
    /**
     * 专业类别
     */
    private String majorType;

    public String getMajorType() {
        return majorType;
    }

    public void setMajorType(String majorType) {
        this.majorType = majorType;
    }

    /**
     * 专家审批
     */
    private String expertSuggestion;

    public String getExpertSuggestion() {
        return expertSuggestion;
    }

    public void setExpertSuggestion(String expertSuggestion) {
        this.expertSuggestion = expertSuggestion;
    }

    /**
     * 单位领导审批意见字段
     */
    private String leaderWord;

    public String getLeaderWord() {
        return leaderWord;
    }

    public void setLeaderWord(String leaderWord) {
        this.leaderWord = leaderWord;
    }

    /**
     *
     *项目编号
     */
    private String projectNo;
    /**
     *
     *项目名称
     */
    private String projectName;

    /*
    *项目长
    * */
    private Long leader;
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
    private String specialtyType;

    /*
    * 组员
    * */
    private String groupMembers;

    /*
    * 开始时间
    * */
    private String beginTime;
    /*
    * 结束时间
    * */

    private String endTime;

    /*
    * 须知
    * */
    private String notice;

    /*
    *主要完成单位
    * */
    private String assumeCompany;
    /*
    *主要协作单位
    * */
    private String joinComopany;



    /**
     *
     *验收专家名单
     */
    private String professorName;

    /**
     *
     *单位自评
     */
   private String selfEvaluate;

    /**
     *
     *科管科评价
     */
    private String KeGuanEvaluate;

    /**
     *
     *zhuguan评价
     */
    private String zhuguanEvaluate;

    /**
     *
     *yuan评价
     */
    private String yuanEvaluate;

    private String projectPlanInfoTxt;
    /**
     *
     *验收意见
     */
    private String projectAcceptanceAdvice;
    /**
     * 申请验收时间
     */
    private String acceptanceTime;
    /**
     * 联系人
     */
    private String acceptancerName;
    /**
     * 联系人电话
     */
    private String acceptanceIphone;
    /**
     * 是验收清单
     */
    private String acceptanceList;
    /**
     * 承担单位科技管理部门意见
     */
    private String assumeComValue;
    /**
     * 专业管理部门意见
     */
    private String specialtyDeptValue;
    /**
     *  科技信息处意见
     */
    private String sciencerValue;



    /**
     *上传
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinColumn(name = "form_proj_id")
    private List<FormAcceptanceProjectFile> formAcceptanceProjectFile;

    public FormProjectAcceptance(){
        this.setIsActive(true);
        this.setCreateDateTime(new Date());
        this.setLastUpdateTime(new Date());
        this.formAcceptanceProjectFile= new ArrayList<>();
    }

    public String getAcceptanceTime() {
        return acceptanceTime;
    }

    public void setAcceptanceTime(String acceptanceTime) {
        this.acceptanceTime = acceptanceTime;
    }

    public String getAcceptancerName() {
        return acceptancerName;
    }

    public void setAcceptancerName(String acceptancerName) {
        this.acceptancerName = acceptancerName;
    }

    public String getAcceptanceIphone() {
        return acceptanceIphone;
    }

    public void setAcceptanceIphone(String acceptanceIphone) {
        this.acceptanceIphone = acceptanceIphone;
    }

    public String getAcceptanceList() {
        return acceptanceList;
    }

    public void setAcceptanceList(String acceptanceList) {
        this.acceptanceList = acceptanceList;
    }

    public String getAssumeComValue() {
        return assumeComValue;
    }

    public void setAssumeComValue(String assumeComValue) {
        this.assumeComValue = assumeComValue;
    }

    public String getSpecialtyDeptValue() {
        return specialtyDeptValue;
    }

    public void setSpecialtyDeptValue(String specialtyDeptValue) {
        this.specialtyDeptValue = specialtyDeptValue;
    }

    public String getSciencerValue() {
        return sciencerValue;
    }

    public void setSciencerValue(String sciencerValue) {
        this.sciencerValue = sciencerValue;
    }

    public String getProjectPlanInfoTxt() {
        return projectPlanInfoTxt;
    }

    public List<FormAcceptanceProjectFile> getFormAcceptanceProjectFile() {
        return formAcceptanceProjectFile;
    }

    public void setFormAcceptanceProjectFile(List<FormAcceptanceProjectFile> formAcceptanceProjectFile) {
        this.formAcceptanceProjectFile = formAcceptanceProjectFile;
    }

    public void setProjectPlanInfoTxt(String projectPlanInfoTxt) {
        this.projectPlanInfoTxt = projectPlanInfoTxt;
    }

    public String getProfessorName() {
        return professorName;
    }

    public void setProfessorName(String professorName) {
        this.professorName = professorName;
    }

    public String getSelfEvaluate() {
        return selfEvaluate;
    }

    public void setSelfEvaluate(String selfEvaluate) {
        this.selfEvaluate = selfEvaluate;
    }

    public String getKeGuanEvaluate() {
        return KeGuanEvaluate;
    }

    public void setKeGuanEvaluate(String keGuanEvaluate) {
        KeGuanEvaluate = keGuanEvaluate;
    }

    public String getZhuguanEvaluate() {
        return zhuguanEvaluate;
    }

    public void setZhuguanEvaluate(String zhuguanEvaluate) {
        this.zhuguanEvaluate = zhuguanEvaluate;
    }

    public String getYuanEvaluate() {
        return yuanEvaluate;
    }

    public void setYuanEvaluate(String yuanEvaluate) {
        this.yuanEvaluate = yuanEvaluate;
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

    public String getProjectAcceptanceAdvice() {
        return projectAcceptanceAdvice;
    }

    public void setProjectAcceptanceAdvice(String projectAcceptanceAdvice) {
        this.projectAcceptanceAdvice = projectAcceptanceAdvice;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public Long getLeader() {
        return leader;
    }

    public void setLeader(Long leader) {
        this.leader = leader;
    }

    public String getSpecialtyType() {
        return specialtyType;
    }

    public void setSpecialtyType(String specialtyType) {
        this.specialtyType = specialtyType;
    }

    public String getGroupMembers() {
        return groupMembers;
    }

    public void setGroupMembers(String groupMembers) {
        this.groupMembers = groupMembers;
    }
}
