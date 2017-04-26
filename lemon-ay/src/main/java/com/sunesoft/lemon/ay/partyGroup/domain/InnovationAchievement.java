package com.sunesoft.lemon.ay.partyGroup.domain;

import com.sunesoft.lemon.ay.partyGroup.domain.enums.*;
import com.sunesoft.lemon.fr.ddd.BaseEntity;
import com.sunesoft.lemon.syms.eHr.domain.empInfo.Education;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 五小创新成果
 * Created by admin on 2016/9/3.
 */
@Entity
@Table(name = "syy_oa_dq_innova_achieve")
public class InnovationAchievement extends BaseEntity {

    public InnovationAchievement(){
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
     * 统计 评审小组组员意见 第三步
     */
    @Column(name = "inno_only_mem_advise",columnDefinition = "CLOB DEFAULT NULL")
    private String innoOnlyMembersAdvise;

    /**
     * 统计 评审小组组员评分 第三步
     */
    @Column(name = "inno_only_members_scores")
    private String innoOnlyMembersScores;
//
//    /**
//     *图片数椐
//     */
//    @Column(name = "image_data",columnDefinition = "CLOB DEFAULT NULL")
//    private String imageData;

    //------------------------------------------------------------------------------

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
     * 申报单位
     */
    @Column(name = "apply_unit")
    private String applyUnit;

    /**
     * 项目名称
     */
    @Column(name = "project_name")
    private String projectName;

    /**
     * 创造者姓名(已改为参与者)
     */
    @Column(name = "create_name")
    private String creatorName;

    /**
     * 创造者姓名id
     */
    @Column(name = "emp_id")
    private String empId;

    /**
     * 性别（枚举）
     */
    @Column(name = "sex")
    private Sex sex;

    /**
     * 年龄
     */
    @Column(name = "age")
    private Integer age;

    /**
     * 文化程度
     */
    @Column(name = "education_degree")
    private String educationDegree;

    /**
     * 岗位
     */
    @Column(name = "position")
    private String position;


    /**
     * 已经改为 专业技术职务
     */
    @Column(name = "title")
    private String title;

    /**
     * 成果创造时间
     */
    @Column(name = "achi_createTime")
    private Date achiCreateTime;

    /**
     * 成果是否转换(枚举)
     */
    @Column(name = "achi_transfor")
    private AchiTransformation achiTransformation;

    /**
     * 成果有无专利（枚举）
     */
    @Column(name = "achi_patent")
    private AchiPatent achiPatent;


    /**
     * 成果简介
     */
    @Column(name = "achi_introduce",columnDefinition = "CLOB DEFAULT NULL")
    private String achiIntroduce;

    /**
     * 附件id
     */
    @Column(name = "file_id")
    private String fileId;

    /**
     * 附件name
     */
    @Column(name = "file_name")
    private String fileName;

    public Long getFormNo() {
        return formNo;
    }

    public void setFormNo(Long formNo) {
        this.formNo = formNo;
    }

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

    public void setEducationDegree(String educationDegree) {
        this.educationDegree = educationDegree;
    }

    public String getPosition() {
        return position;
    }

    public String getImageStatus() {
        return imageStatus;
    }

    public void setImageStatus(String imageStatus) {
        this.imageStatus = imageStatus;
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
