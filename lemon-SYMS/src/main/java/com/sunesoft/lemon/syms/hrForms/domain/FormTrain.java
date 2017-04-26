package com.sunesoft.lemon.syms.hrForms.domain;

import com.sunesoft.lemon.syms.eHr.domain.empInfo.Employee;
import com.sunesoft.lemon.syms.workflow.domain.BaseFormEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by jiangkefan on 2016/7/21.
 */


@Entity
@Table(name="syy_oa_form_train")
public class FormTrain extends BaseFormEntity {


    /**
     * 发布培训新闻通知的id
     */
    @Column(name = "publish_train_news_name")
    private String publishTrainNewsName;

    /**
     * 文件名称
     */
    @Column(name = "file_name")
    private String fileName;

    /**
     * 文件id
     */
    @Column(name = "file_id")
    private String fileId;

    /**
     * 岗位
     */
    @Column(name="currenttech_Position")
    private String currenttechPosition;

    /**
     * 培训名称
     */
    @Column(name="train_name")
    private String trainName ;

    /**
     * 培训开始时间
     */
    @Column(name="begin_date")
    private Date trainBeginDate;

    /**
     * 培训结束时间
     */
    @Column(name="end_date")
    private Date trainEndDate;


    /**
     * 培训地点
     */
    @Column(name="train_place")
    private String trainPlace;


    /**
     * 培训内容
     */
    @Column(name="train_content")
    private String trainContent;


    /**
     * 人员信息
     */
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="syy_oa_form_trainemp",inverseJoinColumns = @JoinColumn(name="emp_id"),joinColumns = @JoinColumn(name="train_id"))
    private List<Employee> emps;


    /**
     * 部门标识符
     */
    @Column(name="train_dept")
    private String trainDepts;

    @Column(name="train_dept_name")
    private String formDeptName;

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
    /**
     * 计划内或外
     */
    private String plan;

    public FormTrain(){
        this.setIsActive(true);
        this.setCreateDateTime(new Date());
        this.setLastUpdateTime(new Date());
        this.setApplyDate(new Date());
        this.emps = new ArrayList<>();
        this.setViewUrl("forms");
    }

    public String getPublishTrainNewsName() {
        return publishTrainNewsName;
    }

    public void setPublishTrainNewsName(String publishTrainNewsName) {
        this.publishTrainNewsName = publishTrainNewsName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    public String getTrainPlace() {
        return trainPlace;
    }

    public void setTrainPlace(String trainPlace) {
        this.trainPlace = trainPlace;
    }

    public String getTrainContent() {
        return trainContent;
    }

    public void setTrainContent(String trainContent) {
        this.trainContent = trainContent;
    }

    public List<Employee> getEmps() {
        return emps;
    }

    public void setEmps(List<Employee> emps) {
        this.emps = emps;
    }

    public Date getTrainBeginDate() {
        return trainBeginDate;
    }

    public void setTrainBeginDate(Date trainBeginDate) {
        this.trainBeginDate = trainBeginDate;
    }

    public Date getTrainEndDate() {
        return trainEndDate;
    }

    public void setTrainEndDate(Date trainEndDate) {
        this.trainEndDate = trainEndDate;
    }

    public String getTrainDepts() {
        return trainDepts;
    }

    public void setTrainDepts(String trainDepts) {
        this.trainDepts = trainDepts;
    }

    public String getFormDeptName() {
        return formDeptName;
    }

    public void setFormDeptName(String formDeptName) {
        this.formDeptName = formDeptName;
    }

    public String getCurrenttechPosition() {
        return currenttechPosition;
    }

    public void setCurrenttechPosition(String currenttechPosition) {
        this.currenttechPosition = currenttechPosition;
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
}
