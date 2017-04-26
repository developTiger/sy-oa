package com.sunesoft.lemon.ay.partyGroupForms.application.dtos;

import com.sunesoft.lemon.ay.partyGroup.domain.enums.CompetitionType;
import com.sunesoft.lemon.ay.partyGroup.domain.enums.PrizeLeval;
import com.sunesoft.lemon.syms.eHr.application.dtos.EmpDto;
import com.sunesoft.lemon.syms.eHr.application.dtos.MultiSelectUserWithDeptDto;
import com.sunesoft.lemon.syms.workflow.application.dtos.BaseFormDto;
import com.sunesoft.lemon.syms.workflow.domain.enums.FormStatus;

import javax.persistence.Column;
import java.util.Date;
import java.util.List;

/**
 * 劳动竞赛项目
 * Created by admin on 2016/9/2.
 */
public class FormWorkProjectDto extends BaseFormDto {

    private String[] mainPeople;//生成图片 主要完成人字段

    /**
     * 图片状态
     */
    private String imageStatus;
//
//    /**
//     *图片数椐
//     */
//    private String imageData;

    //---------------------------------------------------------------------

    private String[] achiOnlyAdvise;//意见展示 （成果申报）

    private String[] achiOnlyScores;//评分展示 （成果申报）


    /**
     * 统计 评审小组组员意见 第三步(展示)  （成果申报）
     */
    private String achiOnlyMembersAdvise;

    /**
     * 统计 评审小组组员评分 第三步(展示) （成果申报）
     */
    private String achiOnlyMembersScores;


    /**
     * 专业评审小组成员(id) 成果申报
     */
    private String achiGroupMembersIds;

    /**
     * 评审组长id (成果申报)
     */
    private String achi_leader_id;

    /**
     * 评审小组组长(成果申报)
     */
    private String achi_judgeMemberLeader;


    /**
     * 专业评审小组成员(id) （项目申报）
     */
    private String projectJudgeMembersIds;

    /**
     * 评审组长（id） （项目字段）
     */
    private String judgeMembersLeaderId;

    /**
     * 评审组长 一名(项目字段)
     */
    private String judgeMembersLeader;

    //上面是后期添加
    //------------------------------------------

    /**
     * 专业评审小组成员(name) 后期添加  成果字段
     */
    private String reviewGroupMembers;


    /**
     * 评审意见 (成果字段)
     */
    private String achievementJudgeSuggestions;

    /**
     * 专业评审小组成员(name) (项目申报,成果申报) 展示字段
     */
    private List<MultiSelectUserWithDeptDto> project_empDtos;



    /**
     * 专业评审小组成员(name) (项目申报) 保存字段
     */
    private String projectJudgeMembers;

    /**
     *评审意见 (项目申报)
     */
    private String judgeSuggestion;

    /**
     * 成果申报 保存项目的id
     * 项目申报 不需要保存
     */
    private Long formWork_id;

    /**
     * 奖项等级（成果申报）
     */
    private String prizeLeval;

    /**
     * 审核评分（成果申报）
     */
    private String approveScore;

    /**
     * 项目状态
     */
    private FormStatus formStatus;

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
     * 文件 id
     */
    private String fileId;

    /**
     * 文件 名
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
     * 取得净效益(成果申报)
     */
    private String netBenefit;

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

    public String[] getMainPeople() {
        return mainPeople;
    }

    public void setMainPeople(String[] mainPeople) {
        this.mainPeople = mainPeople;
    }



    public String getAchiGroupMembersIds() {
        return achiGroupMembersIds;
    }

    public void setAchiGroupMembersIds(String achiGroupMembersIds) {
        this.achiGroupMembersIds = achiGroupMembersIds;
    }

    public String getJudgeMembersLeader() {
        return judgeMembersLeader;
    }

    public void setJudgeMembersLeader(String judgeMembersLeader) {
        this.judgeMembersLeader = judgeMembersLeader;
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

    public String getNetBenefit() {
        return netBenefit;
    }

    public void setNetBenefit(String netBenefit) {
        this.netBenefit = netBenefit;
    }



    public String getAchievementJudgeSuggestions() {
        return achievementJudgeSuggestions;
    }

    public void setAchievementJudgeSuggestions(String achievementJudgeSuggestions) {
        this.achievementJudgeSuggestions = achievementJudgeSuggestions;
    }

    public String getImageStatus() {
        return imageStatus;
    }

    public void setImageStatus(String imageStatus) {
        this.imageStatus = imageStatus;
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

    public List<MultiSelectUserWithDeptDto> getProject_empDtos() {
        return project_empDtos;
    }

    public void setProject_empDtos(List<MultiSelectUserWithDeptDto> project_empDtos) {
        this.project_empDtos = project_empDtos;
    }

    public String getAchi_leader_id() {
        return achi_leader_id;
    }

    public void setAchi_leader_id(String achi_leader_id) {
        this.achi_leader_id = achi_leader_id;
    }

    public String getAchi_judgeMemberLeader() {
        return achi_judgeMemberLeader;
    }

    public void setAchi_judgeMemberLeader(String achi_judgeMemberLeader) {
        this.achi_judgeMemberLeader = achi_judgeMemberLeader;
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

    public FormStatus getFormStatus() {
        return formStatus;
    }

    public void setFormStatus(FormStatus formStatus) {
        this.formStatus = formStatus;
    }

    public String getCompetitionUnit() {
        return competitionUnit;
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

    public String getProjectJudgeMembersIds() {
        return projectJudgeMembersIds;
    }

    public void setProjectJudgeMembersIds(String projectJudgeMembersIds) {
        this.projectJudgeMembersIds = projectJudgeMembersIds;
    }

    public String getJudgeMembersLeaderId() {
        return judgeMembersLeaderId;
    }

    public void setJudgeMembersLeaderId(String judgeMembersLeaderId) {
        this.judgeMembersLeaderId = judgeMembersLeaderId;
    }


}
