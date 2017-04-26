package com.sunesoft.lemon.syms.eHr.application.dtos;

import java.util.Date;

/**
 * Created by xiazl on 2017/3/2.
 */
public class AttendDto {

    /**
     * 个人 是否确认
     */

    private Boolean isSure;
    /**
     * 部门是否确认
     */

    private Boolean deptSure;
    /**
     * 个人确认时间
     */

    private String soneSureTime;
    /**
     * 部门确认时间
     */
    private String sdeptSureTime;


    private Long id;

    private Boolean isActive;

    private String sdateTime;


    /**
     * 员工Id
     */

    private Long empId; //员工Id

    /**
     * 员工姓名
     */

    private String empName;//员工姓名

    /**
     * 部门Id
     */

    private Long depId;//部门Id

    /**
     * 部门名称
     */

    private String depName;//部门名称
    /**
     * 出勤类型
     */
    private Long typeId;
    /**
     * 出勤类型名称
     */
    private String typeName;

    private String cord;

    /**
     * 出勤说明
     */
    private String detail;

    private Date dateTime;

    public Boolean getIsSure() {
        return isSure;
    }

    public void setIsSure(Boolean isSure) {
        this.isSure = isSure;
    }

    private String screateDateTime;

    private String slastUpdateTime;

    public Long getDepId() {
        return depId;
    }

    public void setDepId(Long depId) {
        this.depId = depId;
    }

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getScreateDateTime() {
        return screateDateTime;
    }

    public void setScreateDateTime(String screateDateTime) {
        this.screateDateTime = screateDateTime;
    }

    public String getSdateTime() {
        return sdateTime;
    }

    public void setSdateTime(String sdateTime) {
        this.sdateTime = sdateTime;
    }

    public String getSlastUpdateTime() {
        return slastUpdateTime;
    }

    public void setSlastUpdateTime(String slastUpdateTime) {
        this.slastUpdateTime = slastUpdateTime;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }


    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public Boolean getDeptSure() {
        return deptSure;
    }

    public void setDeptSure(Boolean deptSure) {
        this.deptSure = deptSure;
    }

    public String getSoneSureTime() {
        return soneSureTime;
    }

    public void setSoneSureTime(String soneSureTime) {
        this.soneSureTime = soneSureTime;
    }

    public String getSdeptSureTime() {
        return sdeptSureTime;
    }

    public void setSdeptSureTime(String sdeptSureTime) {
        this.sdeptSureTime = sdeptSureTime;
    }

    public String getCord() {
        return cord;
    }

    public void setCord(String cord) {
        this.cord = cord;
    }
}
