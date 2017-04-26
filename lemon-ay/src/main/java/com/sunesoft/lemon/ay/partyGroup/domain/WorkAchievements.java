package com.sunesoft.lemon.ay.partyGroup.domain;

import com.sunesoft.lemon.ay.partyGroup.domain.enums.CompetitionType;
import com.sunesoft.lemon.ay.partyGroup.domain.enums.PrizeLeval;
import com.sunesoft.lemon.fr.ddd.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 竞赛劳动成果
 * Created by admin on 2016/9/2.
 */
@Entity
@Table(name = "syy_oa_dq_work_achieve")
public class WorkAchievements extends BaseEntity {

    public WorkAchievements(){
        this.setCreateDateTime(new Date());
        this.setLastUpdateTime(new Date());
        this.setIsActive(true);
    }

    /**
     * 图片展示需要
     */
    @Column(name="form_no")
    private Long formNo;

    /**
     * 图片状态
     */
    @Column(name = "image_status")
    private String imageStatus;

    /**
     * 统计 评审小组组员意见 第三步(展示)  （成果申报）
     */
    @Column(name = "achi_only_mem_advise",columnDefinition = "CLOB DEFAULT NULL")
    private String achiOnlyMembersAdvise;

    /**
     * 统计 评审小组组员评分 第三步(展示) （成果申报）
     */
    @Column(name = "achi_only_members_scores")
    private String achiOnlyMembersScores;
//
//    /**
//     *图片数椐
//     */
//    @Column(name = "image_data",columnDefinition = "CLOB DEFAULT NULL")
//    private String imageData;


    /**
     * 专业评审小组成员(name) 后期添加
     */
    @Column(name = "review_group_menbers")
    private String reviewGroupMembers;


    /**
     * 成果申报 保存项目的id
     * 项目申报 不需要保存
     */
    @Column(name = "form_work_id")
    private Long formWork_id;

    /**
     * 奖项等级
     */
    @Column(name = "prize_level")
    private String prizeLeval;

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
    private String competitionType;

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
     * 评审意见(专业评审小组成员) (成果申报)
     */
    @Column(name = "achivevment_judge_suggestions",columnDefinition = "CLOB DEFAULT NULL")
    private String achievementJudgeSuggestions;

    //--------------------------------成果申报---------------------------------
    /**
     * 姓名/单位名称
     */
    @Column(name = "name_or_dept")
    private String nameOrdept;

    /**
     * 荣誉名称
     */
    @Column(name = "prize_name")
    private String prizeName;

    /**
     * 获得时间
     */
    @Column(name = "achi_get_time")
    private Date achiGetTime;

    /**
     * 颁发机构
     */
    @Column(name = "award_agency")
    private String awardAgency;

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

    public Long getFormNo() {
        return formNo;
    }

    public void setFormNo(Long formNo) {
        this.formNo = formNo;
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

    public String getNameOrdept() {
        return nameOrdept;
    }

    public void setNameOrdept(String nameOrdept) {
        this.nameOrdept = nameOrdept;
    }

    public String getPrizeName() {
        return prizeName;
    }

    public void setPrizeName(String prizeName) {
        this.prizeName = prizeName;
    }

    public Date getAchiGetTime() {
        return achiGetTime;
    }

    public void setAchiGetTime(Date achiGetTime) {
        this.achiGetTime = achiGetTime;
    }

    public String getAwardAgency() {
        return awardAgency;
    }

    public void setAwardAgency(String awardAgency) {
        this.awardAgency = awardAgency;
    }

    public String getAchievementJudgeSuggestions() {
        return achievementJudgeSuggestions;
    }

    public void setAchievementJudgeSuggestions(String achievementJudgeSuggestions) {
        this.achievementJudgeSuggestions = achievementJudgeSuggestions;
    }



    public String getReviewGroupMembers() {
        return reviewGroupMembers;
    }

    public void setReviewGroupMembers(String reviewGroupMembers) {
        this.reviewGroupMembers = reviewGroupMembers;
    }

    public Long getFormWork_id() {
        return formWork_id;
    }

    public void setFormWork_id(Long formWork_id) {
        this.formWork_id = formWork_id;
    }

    public String getCompetitionType() {
        return competitionType;
    }

    public void setCompetitionType(String competitionType) {
        this.competitionType = competitionType;
    }

    public String getPrizeLeval() {
        return prizeLeval;
    }

    public void setPrizeLeval(String prizeLeval) {
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

    public String getAchiOnlyMembersAdvise() {
        return achiOnlyMembersAdvise;
    }

    public void setAchiOnlyMembersAdvise(String achiOnlyMembersAdvise) {
        this.achiOnlyMembersAdvise = achiOnlyMembersAdvise;
    }

    public String getAchiOnlyMembersScores() {
        return achiOnlyMembersScores;
    }

    public void setAchiOnlyMembersScores(String achiOnlyMembersScores) {
        this.achiOnlyMembersScores = achiOnlyMembersScores;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getNumber() {
        return number;
    }

    public String getImageStatus() {
        return imageStatus;
    }

    public void setImageStatus(String imageStatus) {
        this.imageStatus = imageStatus;
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
