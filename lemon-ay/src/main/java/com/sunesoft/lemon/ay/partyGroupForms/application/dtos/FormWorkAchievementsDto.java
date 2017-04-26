package com.sunesoft.lemon.ay.partyGroupForms.application.dtos;

import com.sunesoft.lemon.ay.partyGroup.domain.enums.CompetitionType;
import com.sunesoft.lemon.ay.partyGroup.domain.enums.PrizeLeval;
import com.sunesoft.lemon.syms.workflow.application.dtos.BaseFormDto;
import com.sunesoft.lemon.syms.workflow.domain.enums.FormStatus;

import javax.persistence.Column;
import java.util.Date;

/**
 * Created by admin on 2016/9/3.
 */
public class FormWorkAchievementsDto extends BaseFormDto {

    /**
     * 专业评审小组成员(name) 后期添加
     */
    private String reviewGroupMembers;

    /**
     * 审核状态
     */
    private FormStatus formStatus;

    /**
     * 奖项等级
     */
    private PrizeLeval prizeLeval;

    /**
     * 审核评分
     */
    private String approveScore;

    /**
     * 申报时间
     */
    private Date cpmpetitionTime;

    /**
     * 竞赛单位
     */
    private String competitionUnit;

    /**
     * 竞赛类别
     */
    private CompetitionType competitionType;

    /**
     * 竞赛负责人
     */
    private String leader;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 序号
     */
    private String number;

    /**
     * 参加人
     */
    private String joinPeople;

    /**
     * 目标及措施
     */
    private String  goalAndMeasure;


    /**
     * 预期效益分析
     */
    private String benefitAnalysis;

    public String getReviewGroupMembers() {
        return reviewGroupMembers;
    }

    public void setReviewGroupMembers(String reviewGroupMembers) {
        this.reviewGroupMembers = reviewGroupMembers;
    }

    public CompetitionType getCompetitionType() {
        return competitionType;
    }

    public void setCompetitionType(CompetitionType competitionType) {
        this.competitionType = competitionType;
    }

    public FormStatus getFormStatus() {
        return formStatus;
    }

    public void setFormStatus(FormStatus formStatus) {
        this.formStatus = formStatus;
    }

    public PrizeLeval getPrizeLeval() {
        return prizeLeval;
    }

    public void setPrizeLeval(PrizeLeval prizeLeval) {
        this.prizeLeval = prizeLeval;
    }

    public String getApproveScore() {
        return approveScore;
    }

    public void setApproveScore(String approveScore) {
        this.approveScore = approveScore;
    }

    public Date getCpmpetitionTime() {
        return cpmpetitionTime;
    }

    public void setCpmpetitionTime(Date cpmpetitionTime) {
        this.cpmpetitionTime = cpmpetitionTime;
    }

    public String getCompetitionUnit() {
        return competitionUnit;
    }

    public void setCompetitionUnit(String competitionUnit) {
        this.competitionUnit = competitionUnit;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getJoinPeople() {
        return joinPeople;
    }

    public void setJoinPeople(String joinPeople) {
        this.joinPeople = joinPeople;
    }

    public String getGoalAndMeasure() {
        return goalAndMeasure;
    }

    public void setGoalAndMeasure(String goalAndMeasure) {
        this.goalAndMeasure = goalAndMeasure;
    }

    public String getBenefitAnalysis() {
        return benefitAnalysis;
    }

    public void setBenefitAnalysis(String benefitAnalysis) {
        this.benefitAnalysis = benefitAnalysis;
    }
}
