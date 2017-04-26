package com.sunesoft.lemon.ay.partyGroupForms.domain;

import com.sunesoft.lemon.ay.partyGroup.domain.enums.CompetitionType;
import com.sunesoft.lemon.ay.partyGroup.domain.enums.PrizeLeval;
import com.sunesoft.lemon.syms.workflow.domain.BaseFormEntity;
import com.sunesoft.lemon.syms.workflow.domain.enums.FormStatus;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 劳动竞赛项目成果
 * Created by admin on 2016/9/3.
 */
@Entity
@Table(name = "syy_oa_dq_form_workAch")
public class FormWorkAchievements extends BaseFormEntity {

    public FormWorkAchievements(){
        this.setCreateDateTime(new Date());
        this.setLastUpdateTime(new Date());
        this.setIsActive(true);
        this.setViewUrl("partyGroup");
    }


    /**
     * 专业评审小组成员(name) 后期添加
     */
    @Column(name = "review_group_menbers")
    private String reviewGroupMembers;

    /**
     * 奖项等级
     */
    @Column(name = "prize_level")
    private PrizeLeval prizeLeval;

    /**
     * 审核评分
     */
    @Column(name = "approve_score")
    private String approveScore;

    /**
     * 申报时间
     */
    @Column(name = "cpmp_time")
    private Date cpmpetitionTime;

    /**
     * 竞赛单位
     */
    @Column(name = "comp_unit")
    private String competitionUnit;

    /**
     * 竞赛类别
     */
    @Column(name = "compe_type")
    private CompetitionType competitionType;

    /**
     * 竞赛负责人
     */
    @Column(name = "leader")
    private String leader;

    /**
     * 项目名称
     */
    @Column(name = "project_name")
    private String projectName;

    /**
     * 序号
     */
    @Column(name = "num")
    private String number;

    /**
     * 参加人
     */
    @Column(name = "join_people")
    private String joinPeople;

    /**
     * 目标及措施
     */
    @Column(name = "goal_and_mea",columnDefinition = "CLOB DEFAULT NULL")
    private String  goalAndMeasure;


    /**
     * 预期效益分析
     */
    @Column(name = "benefit_analysis",columnDefinition = "CLOB DEFAULT NULL")
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
