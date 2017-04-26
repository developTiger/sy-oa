package com.sunesoft.lemon.deanery.car.domain;

import com.sunesoft.lemon.fr.ddd.BaseEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by xubo on 2016/6/17 0017.
 */
@Entity
@Table(name = "syy_oa_driverinfo")
public class Driver extends BaseEntity {

    //所属公司ID
    @Column(name = "company_id",columnDefinition = "NUMBER(19)")
    private Long companyId;

    //司机姓名
    @Column(name = "driver_name",columnDefinition = "VARCHAR2(50)")
    private String driverName;
    //健康状态
    @Column(name = "health_status",columnDefinition = "VARCHAR2(50)")
    private String healthStatus;


    //司机性别 0:男性，1：是女性
    @Column(name = "driver_gender",columnDefinition = "NUMBER(1)")
    private Integer gender;

    //司机联系电话
    @Column(name = "driver_phone",columnDefinition = "VARCHAR2(15)")
    private String phone;

    //状态
    @Column(name = "status",columnDefinition = "NUMBER(1)")
    private Integer status; //1：待岗，2：出车，3：离岗

    //录入人
    @Column(name = "user_id",columnDefinition = "NUMBER(19)")
    private long userId;

    //证件类型
    @Column(name = "driver_doctype",columnDefinition = "VARCHAR2(20)")
    private String docType;

    //聘用时间
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "driver_hrietime")
    private Date hrieTime;

    //出生时间
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "driver_bornTime")
    private Date bornTime;

    //取证时间
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "driver_obEvidTime")
    private Date obEvidtime;

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
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

    public Date getBornTime() {
        return bornTime;
    }

    public void setBornTime(Date bornTime) {
        this.bornTime = bornTime;
    }

    public Date getObEvidtime() {
        return obEvidtime;
    }

    public void setObEvidtime(Date obEvidtime) {
        this.obEvidtime = obEvidtime;
    }

    public String getHealthStatus() {
        return healthStatus;
    }

    public void setHealthStatus(String healthStatus) {
        this.healthStatus = healthStatus;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
