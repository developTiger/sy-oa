package com.sunesoft.lemon.ay.partyGroup.application.dtos;

import com.sunesoft.lemon.ay.partyGroup.domain.enums.*;
import com.sunesoft.lemon.syms.workflow.application.dtos.BaseFormDto;

import javax.persistence.Column;
import java.util.Date;

/**
 * Created by admin on 2016/9/3.
 */
public class InnovationAchievementDto extends BaseFormDto {


    /**
     * 图片状态
     */
    private String imageStatus;
//
//    /**
//     *图片数椐
//     */
//    private String imageData;

    //-----------------------------------------------------------------------

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



    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
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

    public String getImageStatus() {
        return imageStatus;
    }

    public void setImageStatus(String imageStatus) {
        this.imageStatus = imageStatus;
    }

    public void setEducationDegree(String educationDegree) {
        this.educationDegree = educationDegree;
    }

    public String getPosition() {
        return position;
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
