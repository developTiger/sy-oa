package com.sunesoft.lemon.ay.partyGroup.application.dtos;

import com.sunesoft.lemon.ay.partyGroup.domain.enums.CompetitionType;
import com.sunesoft.lemon.ay.partyGroup.domain.enums.PrizeLeval;
import com.sunesoft.lemon.fr.ddd.BaseEntity;

import javax.persistence.Column;
import java.util.Date;

/**
 * Created by admin on 2016/9/2.
 */
public class WorkAchievementsDto extends BaseEntity {

    /**
     * 图片展示需要
     */
    private Long formNo;

    /**
     * 图片状态
     */
    private String imageStatus;

    /**
     * 统计 评审小组组员意见 第三步(展示)  （成果申报）
     */
    private String achiOnlyMembersAdvise;

    /**
     * 统计 评审小组组员评分 第三步(展示) （成果申报）
     */
    private String achiOnlyMembersScores;
//
//    /**
//     *图片数椐
//     */
//    private String imageData;

    //------------------------------------------------------------------

    /**
     * 专业评审小组成员(name) 后期添加
     */
    private String reviewGroupMembers;

    /**
     * 成果申报 保存项目的id
     * 项目申报 不需要保存
     */
    private Long formWork_id;

    /**
     * 奖项等级
     */
    private String prizeLeval;

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
    private String competitionType;

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

    //   ----------------------------------------------------------------------------------------
    //二期新增
    /**
     * 竞赛主题
     */
    private String competitionTitle;

    /**
     * 现状说明
     */
    private String situationExplain;

    /**
     * 立项依据
     */
    private String projectBasis;

    /**
     * 控制措施
     */
    private String controlAction;

    /**
     * 进度安排
     */
    private String scheduleArrange;

    /**
     * 预期效果
     */
    private String expectResult;

    /**
     * 评审意见(专业评审小组成员) (成果申报)
     */
    private String achievementJudgeSuggestions;

    //--------------------------------成果申报---------------------------------
    /**
     * 姓名/单位名称
     */
    private String nameOrdept;

    /**
     * 荣誉名称
     */
    private String prizeName;

    /**
     * 获得时间
     */
    private Date achiGetTime;

    /**
     * 颁发机构
     */
    private String awardAgency;

    /**
     * 文件 id
     */
    private String fileId;

    /**
     * 文件 名
     */
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

    public String getImageStatus() {
        return imageStatus;
    }

    public void setImageStatus(String imageStatus) {
        this.imageStatus = imageStatus;
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

    public String getReviewGroupMembers() {
        return reviewGroupMembers;
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


}
