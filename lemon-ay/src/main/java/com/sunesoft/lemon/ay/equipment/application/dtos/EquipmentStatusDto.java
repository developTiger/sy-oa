package com.sunesoft.lemon.ay.equipment.application.dtos;

import java.util.Date;

/**
 * Created by jiangkefan on 2016/8/5.
 */
public class EquipmentStatusDto {

    /**
     * 设备ID
     */
    private Long resId;

    /**
     * 运行状态
     */
    private String status;
    /**
     * 停止时间
     */
    private Date stopTime;
    /**
     * 运行时间
     */
    private Date runTime;
    /**
     * 操作人
     */
    private String person;

    /**
     * 工作内容
     */
    private String workContent;

    /**
     * 名称
     */
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getStopTime() {
        return stopTime;
    }

    public void setStopTime(Date stopTime) {
        this.stopTime = stopTime;
    }

    public Date getRunTime() {
        return runTime;
    }

    public void setRunTime(Date runTime) {
        this.runTime = runTime;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getWorkContent() {
        return workContent;
    }

    public void setWorkContent(String workContent) {
        this.workContent = workContent;
    }

    public Long getResId() {
        return resId;
    }

    public void setResId(Long resId) {
        this.resId = resId;
    }
}
