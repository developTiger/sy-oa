package com.sunesoft.lemon.ay.partyGroup.domain;

import com.sunesoft.lemon.ay.partyGroup.domain.enums.CompetitionType;
import com.sunesoft.lemon.fr.ddd.BaseEntity;
import oracle.sql.CLOB;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by admin on 2016/9/2.
 */
@Entity
@Table(name = "syy_oa_dq_work_project")
public class WorkProject extends BaseEntity {

    public WorkProject() {
        this.setCreateDateTime(new Date());
        this.setLastUpdateTime(new Date());
        this.setIsActive(true);
    }


    /**
     * 竞赛单位
     */
    @Column(name = "comp_unit")
    private String competitionUnit;

    /**
     * 申报时间
     */
    @Column(name = "cpmp_time")
    private Date cpmpetitionTime;

    /**
     * 竞赛类别
     */
    @Column(name = "compe_type")
    private String competitionType;

    /**
     * 文件 id
     */
    @Column(name = "file_id")
    private String fileId;

    /**
     * 文件 名
     */
    @Column(name = "file_name")
    private String fileName;

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
    @Column(name = "num ")
    private String number;

    /**
     * 参加人
     */
    @Column(name = "join_people")
    private String joinPeople;

//    --------------------------------------------------------------------------------
    //二期修改
    /**
     * 竞赛主题
     */
    @Column(name = "competition_title")
    private String competitionTitle;

    /**
     * 现状说明
     */
    @Column(name = "situation_explain",columnDefinition = "CLOB DEFAULT NULL")
    private String situationExplain;

    /**
     * 立项依据
     */
    @Column(name = "project_basis",columnDefinition = "CLOB DEFAULT NULL")
    private String projectBasis;

    /**
     * 控制措施
     */
    @Column(name = "control_action",columnDefinition = "CLOB DEFAULT NULL")
    private String controlAction;

    /**
     * 进度安排
     */
    @Column(name = "schedule_arrange",columnDefinition = "CLOB DEFAULT NULL")
    private String scheduleArrange;

    /**
     * 预期效果
     */
    @Column(name = "expect_result",columnDefinition = "CLOB DEFAULT NULL")
    private String expectResult;

    /**
     * 专业评审小组成员(name) (项目申报)
     */
    @Column(name = "project_judge_members")
    private String projectJudgeMembers;

    /**
     * 评审意见(专业评审小组) (项目申报)
     */
    @Column(name = "judge_suggestion",columnDefinition = "CLOB DEFAULT NULL")
    private String judgeSuggestion;

    public String getProjectJudgeMembers() {
        return projectJudgeMembers;
    }

    public void setProjectJudgeMembers(String projectJudgeMembers) {
        this.projectJudgeMembers = projectJudgeMembers;
    }

    public String getJudgeSuggestion() {
        return judgeSuggestion;
    }

    public void setJudgeSuggestion(String judgeSuggestion) {
        this.judgeSuggestion = judgeSuggestion;
    }

    public String getCompetitionUnit() {
        return competitionUnit;
    }

    public void setCompetitionUnit(String competitionUnit) {
        this.competitionUnit = competitionUnit;
    }

    public Date getCpmpetitionTime() {
        return cpmpetitionTime;
    }

    public void setCpmpetitionTime(Date cpmpetitionTime) {
        this.cpmpetitionTime = cpmpetitionTime;
    }

    public String getCompetitionType() {
        return competitionType;
    }

    public void setCompetitionType(String competitionType) {
        this.competitionType = competitionType;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
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

    public String getCompetitionTitle() {
        return competitionTitle;
    }

    public void setCompetitionTitle(String competitionTitle) {
        this.competitionTitle = competitionTitle;
    }

    public String getSituationExplain() {
        return situationExplain;
    }

    public void setSituationExplain(String situationExplain) {
        this.situationExplain = situationExplain;
    }

    public String getProjectBasis() {
        return projectBasis;
    }

    public void setProjectBasis(String projectBasis) {
        this.projectBasis = projectBasis;
    }

    public String getControlAction() {
        return controlAction;
    }

    public void setControlAction(String controlAction) {
        this.controlAction = controlAction;
    }

    public String getScheduleArrange() {
        return scheduleArrange;
    }

    public void setScheduleArrange(String scheduleArrange) {
        this.scheduleArrange = scheduleArrange;
    }

    public String getExpectResult() {
        return expectResult;
    }

    public void setExpectResult(String expectResult) {
        this.expectResult = expectResult;
    }
}
