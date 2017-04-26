package com.sunesoft.lemon.deanery.deliverables.application.dto;

import com.sunesoft.lemon.deanery.deliverables.domain.hibernate.FormDeliverApplyFile;
import com.sunesoft.lemon.deanery.project.domain.ScientficApproveFile;
import com.sunesoft.lemon.deanery.projectWorkFlow.application.dto.FormOpenProjectFileDto;
import com.sunesoft.lemon.syms.hrForms.application.Dtos.DeptAppraiseDetailDto;
import com.sunesoft.lemon.syms.workflow.application.dtos.BaseFormDto;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by swb on 2016/8/30.
 */
public class FormDeliverApplyDto extends BaseFormDto{

    /**
     *
     *项目编号
     */
    private String projectNo;
    /**
     * 项目名称
     */
    private String projectName;
    /*
    *主要完成单位
    * */
    private String assumeCompany;
    /*
    * 项目长Id
    * */
    private Long leader;
    /*
    * 项目长名字
    * */
    private String leaderName;
    /*
    * 组员
    * */
    private String groupMembers;
    /**
     * 组员名称
     */
    private String groupMembersName;
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
    *主要协作单位
    * */
    private String joinComopany;


    /*
      * 开始时间
      * */
    private Date BeginDate;
    private String BeginTime;
    /*
        * 结束时间
        * */
    private Date EndDate;
    private String EndTime;
    /*
    *主题词
    * */
    private String ztc;
    /*
    *三新一性
    * */
    private String sxyx;

    /*
    *内容摘要
    * */
    private String nrzy;
    /*
    *专利类型及数量
    * */
    private String zllxjsl;
    /*
    * 获奖时间
    * */
    private Date awardDate;
    private String awardTime;

    /*
    * 年度
    * */
    private Date niandu;
    private String nianduTime;

    /*
    * 研究内容，技术经济指标及项目进度安排
    * */

    private String projectPlanInfo;
    /**
     * 项目类型
     * 1：项目，2：课题，3：专题
     */
    private String projectType;

    /**
     * 副项目长
     */
    private Long deputy;
    /**
     * 副项目长名
     */
    private String deputyName;

    /*
         *基层单位领导批示
         * */
    private String proficientOpinion;

    /*
    *专家审查意见
    * */
    private String instructions;




    /**
     * 级别 1.国家级、2省部级、3市级、4油田公司级、5院级
     */
    private Long rank;

    /**
     * 等级 1特等奖、2一等奖、3二等奖、4三等奖
     * @return
     */
    private Long grade;

    /**
     * 颁发单位
     * @return
     */
    private String issuing_unit;

    //上传文件
    private List<FormDeliverApplyFile> formDeliverApplyFiles;

    public List<FormDeliverApplyFile> getFormDeliverApplyFiles() {
        return formDeliverApplyFiles;
    }

    public void setFormDeliverApplyFiles(List<FormDeliverApplyFile> formDeliverApplyFiles) {
        this.formDeliverApplyFiles = formDeliverApplyFiles;
    }


    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getAssumeCompany() {
        return assumeCompany;
    }

    public void setAssumeCompany(String assumeCompany) {
        this.assumeCompany = assumeCompany;
    }

    public Long getLeader() {
        return leader;
    }

    public void setLeader(Long leader) {
        this.leader = leader;
    }

    public String getGroupMembers() {
        return groupMembers;
    }

    public void setGroupMembers(String groupMembers) {
        this.groupMembers = groupMembers;
    }

    public String getSpecialtyType() {
        return specialtyType;
    }

    public void setSpecialtyType(String specialtyType) {
        this.specialtyType = specialtyType;
    }

    public String getJoinComopany() {
        return joinComopany;
    }

    public void setJoinComopany(String joinComopany) {
        this.joinComopany = joinComopany;
    }

    public String getZtc() {
        return ztc;
    }

    public void setZtc(String ztc) {
        this.ztc = ztc;
    }

    public String getSxyx() {
        return sxyx;
    }

    public void setSxyx(String sxyx) {
        this.sxyx = sxyx;
    }

    public String getNrzy() {
        return nrzy;
    }

    public void setNrzy(String nrzy) {
        this.nrzy = nrzy;
    }

    public String getZllxjsl() {
        return zllxjsl;
    }

    public void setZllxjsl(String zllxjsl) {
        this.zllxjsl = zllxjsl;
    }

    public String getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo;
    }

    public Date getAwardDate() {
        return awardDate;
    }

    public void setAwardDate(Date awardDate) {
        this.awardDate = awardDate;
    }

    public String getAwardTime() {
        return awardTime;
    }

    public void setAwardTime(String awardTime) {
        this.awardTime = awardTime;
    }

    public Date getNiandu() {
        return niandu;
    }

    public void setNiandu(Date niandu) {
        this.niandu = niandu;
    }

    public String getNianduTime() {
        return nianduTime;
    }

    public void setNianduTime(String nianduTime) {
        this.nianduTime = nianduTime;
    }

    public String getLeaderName() {
        return leaderName;
    }

    public void setLeaderName(String leaderName) {
        this.leaderName = leaderName;
    }

    public String getBeginTime() {
        return BeginTime;
    }

    public void setBeginTime(String beginTime) {
        BeginTime = beginTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
    }

    public Date getBeginDate() {
        return BeginDate;
    }

    public void setBeginDate(Date beginDate) {
        BeginDate = beginDate;
    }

    public Date getEndDate() {
        return EndDate;
    }

    public void setEndDate(Date endDate) {
        EndDate = endDate;
    }

    public String getProjectPlanInfo() {
        return projectPlanInfo;
    }

    public void setProjectPlanInfo(String projectPlanInfo) {
        this.projectPlanInfo = projectPlanInfo;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public Long getDeputy() {
        return deputy;
    }

    public void setDeputy(Long deputy) {
        this.deputy = deputy;
    }

    public String getDeputyName() {
        return deputyName;
    }

    public void setDeputyName(String deputyName) {
        this.deputyName = deputyName;
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

    public String getGroupMembersName() {
        return groupMembersName;
    }

    public void setGroupMembersName(String groupMembersName) {
        this.groupMembersName = groupMembersName;
    }

    public Long getRank() {
        return rank;
    }

    public void setRank(Long rank) {
        this.rank = rank;
    }
    public Long getGrade() {
        return grade;
    }

    public void setGrade(Long grade) {
        this.grade = grade;
    }

    public String getIssuing_unit() {
        return issuing_unit;
    }

    public void setIssuing_unit(String issuing_unit) {
        this.issuing_unit = issuing_unit;
    }

}
