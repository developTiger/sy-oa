package com.sunesoft.lemon.ay.partyGroup.application.dtos;

import com.sunesoft.lemon.ay.partyGroup.domain.enums.CompetitionType;
import com.sunesoft.lemon.ay.partyGroup.domain.enums.PrizeLeval;
import com.sunesoft.lemon.fr.ddd.BaseEntity;
import com.sunesoft.lemon.syms.eHr.application.dtos.MultiSelectUserWithDeptDto;
import com.sunesoft.lemon.syms.workflow.application.dtos.BaseFormDto;

import javax.persistence.Column;
import java.util.Date;
import java.util.List;

/**
 * 单个详情展示 ，将成果的信息合并到项目中去，展示项目dto
 * Created by admin on 2016/9/2.
 */
public class WorkProjectDto extends BaseFormDto {

    private List<MultiSelectUserWithDeptDto> mutiUserDtos;//主要参加人多个 成果申报

    private Long achi_formNo;//成果formNo

    private String achi_imageStatus;//成果 图片状态

    private String[] achiOnlyAdvise;//意见展示 （成果申报）

    private String[] achiOnlyScores;//评分展示 （成果申报）

    //------------------------------------------------------------------

    /**
     * 专业评审小组成员(name) 后期添加  用于数据展示(成果申报)
     */
    private String reviewGroupMembers;

    /**
     * 评审意见(专业评审小组成员) (成果申报)
     */
    private String achievementJudgeSuggestions;

    /**
     * 项目和成果 统计 workAchievement_id
     */
    private String workAhic_id;

    /**
     * 奖项等级（成果申报）
     */
    private String prizeLeval;

    /**
     * 审核评分（成果申报）
     */
    private String approveScore;

    /**
     * 竞赛单位
     */
    private String competitionUnit;

    /**
     * 申报时间
     */
    private Date cpmpetitionTime;

    /**
     * 竞赛类别
     */
    private String competitionType;

    /**
     * 文件 id    (项目)
     */
    private String fileId;

    /**
     * 文件 名     (项目)
     */
    private String fileName;

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
     * 专业评审小组成员(name) (项目申报)
     */
    private String projectJudgeMembers;

    /**
     * 评审意见(专业评审小组) (项目申报)
     */
    private String judgeSuggestion;

    //展示 成果数据 一共10个字段--------------------------------------------------------------
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
     * 文件 id (成果)
     */
    private String achi_fileId;

    /**
     * 文件 name (成果)
     */
    private String achi_fileName;

    public Long getAchi_formNo() {
        return achi_formNo;
    }

    public void setAchi_formNo(Long achi_formNo) {
        this.achi_formNo = achi_formNo;
    }

    public String getAchi_imageStatus() {
        return achi_imageStatus;
    }

    public void setAchi_imageStatus(String achi_imageStatus) {
        this.achi_imageStatus = achi_imageStatus;
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

    public String getAchi_fileId() {
        return achi_fileId;
    }

    public void setAchi_fileId(String achi_fileId) {
        this.achi_fileId = achi_fileId;
    }

    public String getAchi_fileName() {
        return achi_fileName;
    }

    public void setAchi_fileName(String achi_fileName) {
        this.achi_fileName = achi_fileName;
    }

    public String getAchievementJudgeSuggestions() {
        return achievementJudgeSuggestions;
    }

    public void setAchievementJudgeSuggestions(String achievementJudgeSuggestions) {
        this.achievementJudgeSuggestions = achievementJudgeSuggestions;
    }

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

    public String getReviewGroupMembers() {
        return reviewGroupMembers;
    }

    public void setReviewGroupMembers(String reviewGroupMembers) {
        this.reviewGroupMembers = reviewGroupMembers;
    }

    public String getWorkAhic_id() {
        return workAhic_id;
    }

    public void setWorkAhic_id(String workAhic_id) {
        this.workAhic_id = workAhic_id;
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

    public String[] getAchiOnlyAdvise() {
        return achiOnlyAdvise;
    }

    public void setAchiOnlyAdvise(String[] achiOnlyAdvise) {
        this.achiOnlyAdvise = achiOnlyAdvise;
    }

    public String[] getAchiOnlyScores() {
        return achiOnlyScores;
    }

    public void setAchiOnlyScores(String[] achiOnlyScores) {
        this.achiOnlyScores = achiOnlyScores;
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

    public List<MultiSelectUserWithDeptDto> getMutiUserDtos() {
        return mutiUserDtos;
    }

    public void setMutiUserDtos(List<MultiSelectUserWithDeptDto> mutiUserDtos) {
        this.mutiUserDtos = mutiUserDtos;
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
