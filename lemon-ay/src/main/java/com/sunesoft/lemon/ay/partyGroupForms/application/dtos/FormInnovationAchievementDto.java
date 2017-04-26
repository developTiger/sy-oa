package com.sunesoft.lemon.ay.partyGroupForms.application.dtos;

import com.sunesoft.lemon.ay.partyGroup.domain.enums.*;
import com.sunesoft.lemon.syms.eHr.application.dtos.MultiSelectUserWithDeptDto;
import com.sunesoft.lemon.syms.workflow.application.dtos.BaseFormDto;

import javax.persistence.Column;
import java.util.Date;
import java.util.List;

/**
 * 五小创新成果
 * Created by admin on 2016/9/3.
 */
public class FormInnovationAchievementDto extends BaseFormDto {

    private String[] mainPeople;//生成图片 主要完成人字段

    private String image_awardLevel;//图片 获奖等级


    /**
     * 图片状态
     */
    private String imageStatus;
//
//    /**
//     *图片数椐
//     */
//    private String imageData;

    //--------------------------------------------------------------------------

    private String[] innoOnlyAdvise;//意见展示 （成果申报）

    private String[] innoOnlyScores;//评分展示 （成果申报）

    /**
     * 统计 评审小组组员意见 第三步(展示)
     */
    private String innoOnlyMembersAdvise;

    /**
     * 统计 评审小组组员评分 第三步(展示)
     */
    private String innoOnlyMembersScores;


    /**
     * 颁发单位 （最后一步） id
     */
    private String awardDeptmentId;

    /**
     * 颁发单位 （最后一步）
     */
    private String awardDeptment;

    /**
     * 获奖时间 （最后一步）
     */
    private Date awardTime;

    /**
     * 评审意见
     */
    private String achiSuggestion;

    /**
     * 专业评审小组成员(name) (项目申报,成果申报) 展示字段
     */
    private List<MultiSelectUserWithDeptDto> project_empDtos;

    /**
     * 评审小组组长(id)
     */
    private String groupLeaderId;

    /**
     * 评审小组组长(name)
     */
    private String groupLeaderName;

    /**
     * 评审小组组员(id)
     */
    private String groupMemberIds;

    /**
     * 评审小组组员(name)
     */
    private String groupMemberNames;



    /**
     * 奖项等级
     */
    private PrizeLeval prizeLeval;

    /**
     * 审核评分
     */
    private String approveScore;

    /**
     * 申报单位
     */
    private String applyUnit;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 创造者姓名
     */
    private String creatorName;

    /**
     * 创造者姓名id
     */
    private String empId;

    /**
     * 性别（枚举）
     */
    private Sex sex;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 文化程度
     */
    private String educationDegree;

    /**
     * 岗位
     */
    private String position;


    /**
     * 职称
     */
    private String title;

    /**
     * 成果创造时间
     */
    private Date achiCreateTime;

    /**
     * 成果是否转换(枚举)
     */
    private AchiTransformation achiTransformation;

    /**
     * 成果有无专利（枚举）
     */
    private AchiPatent achiPatent;


    /**
     * 成果简介
     */
    private String achiIntroduce;

    /**
     * 附件id
     */
    private String fileId;

    /**
     * 附件name
     */
    private String fileName;

    public String getImageStatus() {
        return imageStatus;
    }

    public void setImageStatus(String imageStatus) {
        this.imageStatus = imageStatus;
    }

    public String getAwardDeptment() {
        return awardDeptment;
    }

    public void setAwardDeptment(String awardDeptment) {
        this.awardDeptment = awardDeptment;
    }

    public Date getAwardTime() {
        return awardTime;
    }

    public void setAwardTime(Date awardTime) {
        this.awardTime = awardTime;
    }

    public String getAchiSuggestion() {
        return achiSuggestion;
    }

    public void setAchiSuggestion(String achiSuggestion) {
        this.achiSuggestion = achiSuggestion;
    }

    public List<MultiSelectUserWithDeptDto> getProject_empDtos() {
        return project_empDtos;
    }

    public void setProject_empDtos(List<MultiSelectUserWithDeptDto> project_empDtos) {
        this.project_empDtos = project_empDtos;
    }

    public String getImage_awardLevel() {
        return image_awardLevel;
    }

    public void setImage_awardLevel(String image_awardLevel) {
        this.image_awardLevel = image_awardLevel;
    }

    public String getGroupLeaderId() {
        return groupLeaderId;
    }

    public void setGroupLeaderId(String groupLeaderId) {
        this.groupLeaderId = groupLeaderId;
    }

    public String getGroupLeaderName() {
        return groupLeaderName;
    }

    public void setGroupLeaderName(String groupLeaderName) {
        this.groupLeaderName = groupLeaderName;
    }

    public String getGroupMemberIds() {
        return groupMemberIds;
    }

    public String[] getMainPeople() {
        return mainPeople;
    }

    public void setMainPeople(String[] mainPeople) {
        this.mainPeople = mainPeople;
    }

    public void setGroupMemberIds(String groupMemberIds) {
        this.groupMemberIds = groupMemberIds;
    }

    public String getGroupMemberNames() {
        return groupMemberNames;
    }

    public void setGroupMemberNames(String groupMemberNames) {
        this.groupMemberNames = groupMemberNames;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String[] getInnoOnlyAdvise() {
        return innoOnlyAdvise;
    }

    public void setInnoOnlyAdvise(String[] innoOnlyAdvise) {
        this.innoOnlyAdvise = innoOnlyAdvise;
    }

    public String[] getInnoOnlyScores() {
        return innoOnlyScores;
    }

    public void setInnoOnlyScores(String[] innoOnlyScores) {
        this.innoOnlyScores = innoOnlyScores;
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

    public String getApplyUnit() {
        return applyUnit;
    }

    public String getAwardDeptmentId() {
        return awardDeptmentId;
    }

    public void setAwardDeptmentId(String awardDeptmentId) {
        this.awardDeptmentId = awardDeptmentId;
    }

    public void setApplyUnit(String applyUnit) {
        this.applyUnit = applyUnit;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getEducationDegree() {
        return educationDegree;
    }

    public void setEducationDegree(String educationDegree) {
        this.educationDegree = educationDegree;
    }

    public String getPosition() {
        return position;
    }

    public String getInnoOnlyMembersAdvise() {
        return innoOnlyMembersAdvise;
    }

    public void setInnoOnlyMembersAdvise(String innoOnlyMembersAdvise) {
        this.innoOnlyMembersAdvise = innoOnlyMembersAdvise;
    }

    public String getInnoOnlyMembersScores() {
        return innoOnlyMembersScores;
    }

    public void setInnoOnlyMembersScores(String innoOnlyMembersScores) {
        this.innoOnlyMembersScores = innoOnlyMembersScores;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getAchiCreateTime() {
        return achiCreateTime;
    }

    public void setAchiCreateTime(Date achiCreateTime) {
        this.achiCreateTime = achiCreateTime;
    }

    public AchiTransformation getAchiTransformation() {
        return achiTransformation;
    }

    public void setAchiTransformation(AchiTransformation achiTransformation) {
        this.achiTransformation = achiTransformation;
    }

    public AchiPatent getAchiPatent() {
        return achiPatent;
    }

    public void setAchiPatent(AchiPatent achiPatent) {
        this.achiPatent = achiPatent;
    }

    public String getAchiIntroduce() {
        return achiIntroduce;
    }

    public void setAchiIntroduce(String achiIntroduce) {
        this.achiIntroduce = achiIntroduce;
    }


}
