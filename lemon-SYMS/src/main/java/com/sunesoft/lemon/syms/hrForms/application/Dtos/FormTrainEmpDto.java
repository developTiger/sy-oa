package com.sunesoft.lemon.syms.hrForms.application.Dtos;

/**
 * Created by jiangkefan on 2016/7/22.
 */
public class FormTrainEmpDto {

    /**
     * 所在部门及单位
     */
    private String deptName;
    /**
     * 员工编号
     */
    private String userNo;

    /**
     * 真实姓名
     */
    private String name; // 真实姓名

    //培训开始时间
    private String trainBeginTime;
    //培训结束时间
    private String trainEndTime;

    //培训名称
    private String trainName;

    //培训内容
    private String trainContent;

    //培训天数
    private String day;

    /**
     *学时
     */
    private String studyTime;

    /**
     * 主办单位
     */
    private String mainCompany;

    /**
     * 承办单位
     */
    private String didCompany;

    /**
     * 培训分类
     */
    private String trainCategory;

    //培训地点
    private String  trainPlace;

    /**
     * 计划内或外
     */
    private String plan;

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTrainBeginTime() {
        return trainBeginTime;
    }

    public void setTrainBeginTime(String trainBeginTime) {
        this.trainBeginTime = trainBeginTime;
    }

    public String getTrainEndTime() {
        return trainEndTime;
    }

    public void setTrainEndTime(String trainEndTime) {
        this.trainEndTime = trainEndTime;
    }

    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    public String getTrainContent() {
        return trainContent;
    }

    public void setTrainContent(String trainContent) {
        this.trainContent = trainContent;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getStudyTime() {
        return studyTime;
    }

    public void setStudyTime(String studyTime) {
        this.studyTime = studyTime;
    }

    public String getMainCompany() {
        return mainCompany;
    }

    public void setMainCompany(String mainCompany) {
        this.mainCompany = mainCompany;
    }

    public String getDidCompany() {
        return didCompany;
    }

    public void setDidCompany(String didCompany) {
        this.didCompany = didCompany;
    }

    public String getTrainCategory() {
        return trainCategory;
    }

    public void setTrainCategory(String trainCategory) {
        this.trainCategory = trainCategory;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public String getTrainPlace() {
        return trainPlace;
    }

    public void setTrainPlace(String trainPlace) {
        this.trainPlace = trainPlace;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
}
