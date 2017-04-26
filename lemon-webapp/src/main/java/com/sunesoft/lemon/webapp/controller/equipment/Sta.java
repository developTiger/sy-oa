package com.sunesoft.lemon.webapp.controller.equipment;

import javax.persistence.Column;
import java.util.Date;

/**
 * Created by admin on 2016/8/15.
 */
public class Sta {
    /**
     * 运行状态
     */
    @Column(name = "status")
    private String status;
    /**
     * 停止时间
     */
    @Column(name = "stop_time")
    private Date stopTime;
    /**
     * 运行时间
     */
    @Column(name ="run_time")
    private Date runTime;
    /**
     * 操作人
     */
    @Column(name ="person")
    private String person;

    /**
     * 工作内容
     */
    @Column(name ="work_content")
    private String workContent;


    /**
     * 记录ID
     */

    @Column(name = "res_id")
    private Long resId;

    /**
     * 设备名称
     */
    @Column(name = "name")
    private String name;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
