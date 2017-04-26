package com.sunesoft.lemon.ay.equipment.application.dtos;

import com.sunesoft.lemon.fr.ddd.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by admin on 2016/10/21.
 */
public class EquipmentHealthRecordDto  {



    private Long id;

    private Date time;

    /**
     * 关联 设备id
     */
    private String equipmentId;

    /**
     * 保养人员 id
     */
    private Long personId;

    /**
     * 保养人员 name
     */
    private String personName;

    /**
     * 保养部位
     */
    private String position;

    /**
     * 更换情况
     */
    private String changeSituation;

    /**
     * 保养效果
     */
    private String result;

    /**
     * 遗留问题
     */
    private String question;

    /**
     * 保养停机小时
     */
    private Float stopMachineTime;

    public String getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
    }

    public Float getStopMachineTime() {
        return stopMachineTime;
    }

    public void setStopMachineTime(Float stopMachineTime) {
        this.stopMachineTime = stopMachineTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getChangeSituation() {
        return changeSituation;
    }

    public void setChangeSituation(String changeSituation) {
        this.changeSituation = changeSituation;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
