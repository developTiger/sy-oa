package com.sunesoft.lemon.syms.hrForms.application.criteria;

import com.sunesoft.lemon.fr.results.PagedCriteria;

import java.util.Date;

/**
 * Created by kkk on 2017/2/17.
 */
public class TrainEmpCriteria extends PagedCriteria {


    //申请人姓名
    private String name;

    //出差目的
    private Date beginDate;
    //申请时间
    private Date endDate;

    //出差结束时间
    private String trainName;

    private Long deptId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }
}
