package com.sunesoft.lemon.syms.hrForms.application.Dtos;

import com.sunesoft.lemon.syms.hrForms.domain.Options;
import com.sunesoft.lemon.syms.hrForms.domain.TrainFile;
import com.sunesoft.lemon.syms.workflow.application.dtos.BaseFormDto;

import java.util.Date;
import java.util.List;

/**
 * Created by jiangkefan on 2016/7/27.
 */
public class FormTrainEffectDto extends BaseFormDto{

    public  String trainName ;

    public String trainContent;

    public Date trainBeginTime;

    public Date trainEndTime;

    public String trainPlace;

    private Options practical;
    private Options satisfied;
    private Options help;

    private String suggest;

    private List<TrainFileDto> filesDto;



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

    public List<TrainFileDto> getFilesDto() {
        return filesDto;
    }

    public void setFilesDto(List<TrainFileDto> filesDto) {
        this.filesDto = filesDto;
    }

    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }
}
