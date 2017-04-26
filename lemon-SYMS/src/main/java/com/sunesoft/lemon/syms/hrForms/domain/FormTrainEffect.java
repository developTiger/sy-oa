package com.sunesoft.lemon.syms.hrForms.domain;

import com.sunesoft.lemon.syms.workflow.domain.BaseFormEntity;

import javax.persistence.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by jiangkefan on 2016/7/27.
 */
@Entity
@Table(name="syy_oa_form_train_effect")
public class FormTrainEffect extends BaseFormEntity {

    /**
     * 培训名称
     */
    @Column(name="train_name")
    private String trainName;
    /**
     * 培训内容
     */
    @Column(name = "train_content")
    private String trainContent;
    /**
     * 培训开始时间
     */
    @Column(name="begin_date")
    private Date trainBeginTime;
    /**
     * 培训结束时间
     */
    @Column(name="end_date")
    private Date trainEndTime;
    /**
     * 培训地点
     */
    @Column(name="train_place")
    private String trainPlace;

    /**
     * 问题选项
     */
    @Column(name="practical")
    private Options practical;
    @Column(name="satisfied")
    private Options satisfied;
    @Column(name="help")
    private Options help;

    @Column(name="suggest")
    private String suggest;

    /**
     * 文件
     */
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "fileId")
    private List<TrainFile> files;
    public FormTrainEffect(){
        this.setIsActive(true);
        this.setCreateDateTime(new Date());
        this.setLastUpdateTime(new Date());
        this.setApplyDate(new Date());
        this.files = new ArrayList<>();
        this.setViewUrl("forms");
    }
    public String getTrainContent() {
        return trainContent;
    }

    public void setTrainContent(String trainContent) {
        this.trainContent = trainContent;
    }

    public Date getTrainBeginTime() {
        return trainBeginTime;
    }

    public void setTrainBeginTime(Date trainBeginTime) {
        this.trainBeginTime = trainBeginTime;
    }

    public Date getTrainEndTime() {
        return trainEndTime;
    }

    public void setTrainEndTime(Date trainEndTime) {
        this.trainEndTime = trainEndTime;
    }

    public String getTrainPlace() {
        return trainPlace;
    }

    public void setTrainPlace(String trainPlace) {
        this.trainPlace = trainPlace;
    }

    public Options getPractical() {
        return practical;
    }

    public void setPractical(Options practical) {
        this.practical = practical;
    }

    public Options getSatisfied() {
        return satisfied;
    }

    public void setSatisfied(Options satisfied) {
        this.satisfied = satisfied;
    }

    public Options getHelp() {
        return help;
    }

    public void setHelp(Options help) {
        this.help = help;
    }

    public String getSuggest() {
        return suggest;
    }

    public void setSuggest(String suggest) {
        this.suggest = suggest;
    }

    public List<TrainFile> getFiles() {
        return files;
    }

    public void setFiles(List<TrainFile> files) {
        this.files = files;
    }

    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }


}
