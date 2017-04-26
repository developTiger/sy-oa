package com.sunesoft.lemon.ay.equipment.domain;

import com.sunesoft.lemon.fr.ddd.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by admin on 2016/10/21.
 * 设备保养记录
 */
@Entity
@Table(name = "syy_oa_ay_equipHealth_record")
public class EquipmentHealthRecord extends BaseEntity {

    public EquipmentHealthRecord(){
        this.setCreateDateTime(new Date());
        this.setLastUpdateTime(new Date());
        this.setIsActive(true);
    }

    /**
     * 关联 设备id
     */
    @Column(name = "equipment_id")
    private String equipmentId;

    /**
     * 时间
     */
    @Column(name = "time")
    private Date time;

    /**
     * 保养人员 id
     */
    @Column(name = "person_id")
    private Long personId;

    /**
     * 保养人员 name
     */
    @Column(name = "person_name")
    private String personName;

    /**
     * 保养部位
     */
    @Column(name = "position")
    private String position;

    /**
     * 更换情况
     */
    @Column(name = "change_situation")
    private String changeSituation;

    /**
     * 保养效果
     */
    @Column(name = "result")
    private String result;

    /**
     * 遗留问题
     */
    @Column(name = "question",columnDefinition = "CLOB DEFAULT NULL")
    private String question;

    /**
     * 保养停机小时
     */
    @Column(name = "stop_time")
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
