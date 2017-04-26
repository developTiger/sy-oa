package com.sunesoft.lemon.deanery.car.application.dtos;

import com.sunesoft.lemon.deanery.car.application.static_common.static_common;
import com.sunesoft.lemon.fr.ddd.BaseEntity;

import java.util.Date;

/**
 * Created by xubo 2016/6/17 0017.
 */
public class DriverDto {

    //司机ID
    private Long id;

    //司机姓名
    private String driverName;
   //健康状态
    private String healthStatus;

    //所属公司ID
    private Long companyId;

    //所属公司名称
    private String companyName;

    //联系电话
    private String phone;

    //司机状态
    private int status;

    //证件类型
    private String docType;

    //聘用时间
    private Date hrieTime;

    private boolean isActive;

    //出生时间
    private Date bornTime;

    //取证时间
    private Date obEvidtime;

    //性别
    private int gender;

    //创建时间
    private Date createDateTime;

    //驾驶证
    private String[] docTypes;
    //以下展示数据
    private  String hrieTime_Str;
    private  String bornTime_Str;
    private  String obEvidtime_Str;
    private  String createDateTime_Str;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
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

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public Date getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(Date createDateTime) {
        this.createDateTime = createDateTime;
    }

    public String[] getDocTypes() {
        return docTypes;
    }

    public void setDocTypes(String[] docTypes) {
        this.docTypes = docTypes;
    }

    public String getHrieTime_Str() {
        return hrieTime_Str;
    }

    public void setHrieTime_Str(String hrieTime_Str) {
        this.hrieTime_Str = hrieTime_Str;
    }

    public String getBornTime_Str() {
        return bornTime_Str;
    }

    public void setBornTime_Str(String bornTime_Str) {
        this.bornTime_Str = bornTime_Str;
    }

    public String getObEvidtime_Str() {
        return obEvidtime_Str;
    }

    public void setObEvidtime_Str(String obEvidtime_Str) {
        this.obEvidtime_Str = obEvidtime_Str;
    }

    public String getCreateDateTime_Str() {
        return createDateTime_Str;
    }

    public void setCreateDateTime_Str(String createDateTime_Str) {
        this.createDateTime_Str = createDateTime_Str;
    }

    public String getHealthStatus() {
        return healthStatus;
    }

    public void setHealthStatus(String healthStatus) {
        this.healthStatus = healthStatus;
    }
}
