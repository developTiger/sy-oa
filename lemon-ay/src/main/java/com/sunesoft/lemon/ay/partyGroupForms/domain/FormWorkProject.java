package com.sunesoft.lemon.ay.partyGroupForms.domain;

import com.sunesoft.lemon.ay.partyGroup.domain.enums.CompetitionType;
import com.sunesoft.lemon.ay.partyGroup.domain.enums.PrizeLeval;
import com.sunesoft.lemon.syms.workflow.domain.BaseFormEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 劳动竞赛项目申报 和 成果申报 用一个实体类，
 * 项目和成果的区别就是 成果有获奖等级和成果评分这两个字段,还有一个项目申报的id（关联项目），而项目没有
 * 那这样 就是 只用formworkProject和formworkProjectDto，formWorkProject表里就保存了两条数据（项目和成果）
 * 但是，入库还是原来的方式，项目入库workProjec，成果入库workAchievement
 * Created by liulin on 2016/9/2.
 */
@Entity
@Table(name = "syy_oa_dq_form_workPro")
public class FormWorkProject extends BaseFormEntity {

    public FormWorkProject(){
        this.setCreateDateTime(new Date());
        this.setLastUpdateTime(new Date());
        this.setIsActive(true);
        this.setViewUrl("partyGroup");
    }

    /**
     * 图片状态
     */
    @Column(name = "image_status")
    private String imageStatus;

//    /**
//     *图片数椐
//     */
//    @Column(name = "image_data",columnDefinition = "CLOB DEFAULT NULL")
//    private String imageData;

    //-----------------------------------------------------------------------



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

    /**
     * 评审组长id (成果申报)
     */
    @Column(name = "achi_leader_id")
    private String achi_leader_id;

    /**
     * 评审小组组长(成果申报)
     */
    @Column(name = "achi_judge_member_leader")
    private String achi_judgeMemberLeader;

    /**
     * 评审组长（id） （项目字段）
     */
    @Column(name = "judge_members_leader_id")
    private String judgeMembersLeaderId;

    /**
     * 评审组长 一名(项目字段)
     */
    @Column(name = "judge_members_leader")
    private String judgeMembersLeader;

    //上面是后期添加
    //------------------------------------------------------------------------

    /**
     * 专业评审小组成员(name) (项目申报)
     */
    @Column(name = "project_judge_members")
    private String projectJudgeMembers;

    /**
     * 专业评审小组成员(id) （项目申报）
     */
    @Column(name = "project_judge_members_ids")
    private String projectJudgeMembersIds;

    /**
     * 专业评审小组成员(name) 后期添加  （成果申报）
     */
    @Column(name = "review_group_menbers")
    private String reviewGroupMembers;

    /**
     * 专业评审小组成员(id) 成果申报
     */
    @Column(name = "achi_group_members_ids")
    private String achiGroupMembersIds;

    /**
     * 评审意见(评审组长) 最终意见 (成果申报)
     */
    @Column(name = "achivevment_judge_suggestions",columnDefinition = "CLOB DEFAULT NULL")
    private String achievementJudgeSuggestions;

    /**
     * 成果申报 保存项目的id
     * 项目申报 不需要保存
     */
    @Column(name = "form_work_id")
    private Long formWork_id;

    /**
     * 奖项等级（成果申报） 现已改为荣誉级别
     */
    @Column(name = "prize_level")
    private String prizeLeval;

    /**
     * 审核评分 评审组长 最终评分（成果申报）
     */
    @Column(name = "approve_score")
    private String approveScore;

    /**
     * 竞赛单位(专业主管部门)
     */
    @Column(name = "comp_unit")
    private String competitionUnit;

    /**
     * 申报时间(实施时间)
     */
    @Column(name = "cpmp_time")
    private Date cpmpetitionTime;

    /**
     * 竞赛类别(项目申报类别) 可编辑
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
     * 竞赛负责人(默认为申报人)
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
     * 主要参加人
     */
    @Column(name = "join_people")
    private String joinPeople;


//    --------------------------------------------------------------------------------
    //二期修改
    /**
     * 竞赛主题(可编辑)
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
     * 评审意见(专业评审小组) (项目申报)
     */
    @Column(name = "judge_suggestion",columnDefinition = "CLOB DEFAULT NULL")
    private String judgeSuggestion;

    /**
     * 取得净效益(成果申报)
     */
    @Column(name = "net_benefit",columnDefinition = "CLOB DEFAULT NULL")
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

    public String getJudgeMembersLeaderId() {
        return judgeMembersLeaderId;
    }

    public void setJudgeMembersLeaderId(String judgeMembersLeaderId) {
        this.judgeMembersLeaderId = judgeMembersLeaderId;
    }

    public String getImageStatus() {
        return imageStatus;
    }

    public void setImageStatus(String imageStatus) {
        this.imageStatus = imageStatus;
    }

    public String getProjectJudgeMembersIds() {
        return projectJudgeMembersIds;
    }

    public void setProjectJudgeMembersIds(String projectJudgeMembersIds) {
        this.projectJudgeMembersIds = projectJudgeMembersIds;
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


    public String getProjectJudgeMembers() {
        return projectJudgeMembers;
    }

    public void setProjectJudgeMembers(String projectJudgeMembers) {
        this.projectJudgeMembers = projectJudgeMembers;
    }

    public String getAchievementJudgeSuggestions() {
        return achievementJudgeSuggestions;
    }

    public void setAchievementJudgeSuggestions(String achievementJudgeSuggestions) {
        this.achievementJudgeSuggestions = achievementJudgeSuggestions;
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

    public String getAchiGroupMembersIds() {
        return achiGroupMembersIds;
    }

    public void setAchiGroupMembersIds(String achiGroupMembersIds) {
        this.achiGroupMembersIds = achiGroupMembersIds;
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

    public Long getFormWork_id() {
        return formWork_id;
    }

    public void setFormWork_id(Long formWork_id) {
        this.formWork_id = formWork_id;
    }

    public String getApproveScore() {
        return approveScore;
    }

    public void setApproveScore(String approveScore) {
        this.approveScore = approveScore;
    }

    public String getPrizeLeval() {
        return prizeLeval;
    }

    public void setPrizeLeval(String prizeLeval) {
        this.prizeLeval = prizeLeval;
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
