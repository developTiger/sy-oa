package com.sunesoft.lemon.deanery.car.application.criteria;

import com.sunesoft.lemon.fr.results.PagedCriteria;


import java.util.Date;

/**
 * Created by xubo on 2016/6/17 0017.
 */
public class DriverCriteria extends PagedCriteria {

    //司机姓名
    private String name;

    private String healthStatus;


    //司机性别 0:男性，1：是女性
    private Integer gender;

    //司机联系电话
    private String phone;

    /**
     * 所属公司ID
     */
    private Long companyId;
    private String companyName;
    /**
     * 司机状态
     */
    private Integer status;
    /**
     * 证件类型
     */
    private String docType;

    //聘用时间段
    private Date hrieTime;

    //查询
    private String beginTime;

    private String endTime;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public Date getHrieTime() {
        return hrieTime;
    }

    public void setHrieTime(Date hrieTime) {
        this.hrieTime = hrieTime;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getHealthStatus() {
        return healthStatus;
    }

    public void setHealthStatus(String healthStatus) {
        this.healthStatus = healthStatus;
    }
}
