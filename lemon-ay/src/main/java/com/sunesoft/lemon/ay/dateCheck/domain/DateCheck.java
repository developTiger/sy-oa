package com.sunesoft.lemon.ay.dateCheck.domain;

import com.sunesoft.lemon.fr.ddd.BaseEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * 设备期间核查记录
 * Created by xiazl on 2016/10/21.
 */
@Entity

@Table(name="syy_oa_ay_date_check")
public class DateCheck extends BaseEntity {

    public DateCheck() {
        setIsActive(true);
        setCreateDateTime(new Date());
        setLastUpdateTime(new Date());
    }

    /**
     * 设备关联
     */
    private Long equipId;
    /**
     * 设备名称
     */
    private String equipName;

    /**
     * 厂家名称
     */
    @Column(name = "factory_name")
    private String factoryName;

    /**
     * 出厂编号
     */
    @Column(name = "outFactory_num")
    private String outFactoryNum;

    /**
     * 规格型号
     */
    @Column(name = "e_standard")
    private String standard;

    /**
     * 启用日期
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date useTime;

    /**
     * 间隔
     */
    private Integer days;

    /**
     * 核查方法
     */
    private String checkWay;
    /**
     * 核查时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "check_time")
    private Date checkTime;

    /**
     * 核查
     */

    @Column(columnDefinition = "CLOB DEFAULT NULL")
    private String content;

    /**
     * 核查数据
     */

    @Column(name = "check_date", columnDefinition = "CLOB DEFAULT NULL")
    private String checkDate;
    /**
     * 数据处理
     */

    @Column(name = "result_date",columnDefinition = "CLOB DEFAULT NULL")
    private String date;

    /**
     * 核查人
     */
    @Column(name = "check_person")
    private String checkPerson;
    /**
     * 时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "person_time")
    private Date personTime;

    /**
     * 负责人填写内容(核查结论)
     */
    @Column(name = "head_content", columnDefinition = "CLOB DEFAULT NULL")
    private String headContent;

    /**
     * 负责人
     */
    @Column(name = "head_person")
    private String headPerson;
    /**
     * 时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "head_time")
    private Date headTime;

    public String getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(String checkDate) {
        this.checkDate = checkDate;
    }

    public String getCheckPerson() {
        return checkPerson;
    }

    public void setCheckPerson(String checkPerson) {
        this.checkPerson = checkPerson;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public String getCheckWay() {
        return checkWay;
    }

    public void setCheckWay(String checkWay) {
        this.checkWay = checkWay;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public Long getEquipId() {
        return equipId;
    }

    public void setEquipId(Long equipId) {
        this.equipId = equipId;
    }

    public String getEquipName() {
        return equipName;
    }

    public void setEquipName(String equipName) {
        this.equipName = equipName;
    }

    public String getFactoryName() {
        return factoryName;
    }

    public void setFactoryName(String factoryName) {
        this.factoryName = factoryName;
    }

    public String getHeadContent() {
        return headContent;
    }

    public void setHeadContent(String headContent) {
        this.headContent = headContent;
    }

    public String getHeadPerson() {
        return headPerson;
    }

    public void setHeadPerson(String headPerson) {
        this.headPerson = headPerson;
    }

    public Date getHeadTime() {
        return headTime;
    }

    public void setHeadTime(Date headTime) {
        this.headTime = headTime;
    }

    public String getOutFactoryNum() {
        return outFactoryNum;
    }

    public void setOutFactoryNum(String outFactoryNum) {
        this.outFactoryNum = outFactoryNum;
    }

    public Date getPersonTime() {
        return personTime;
    }

    public void setPersonTime(Date personTime) {
        this.personTime = personTime;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public Date getUseTime() {
        return useTime;
    }

    public void setUseTime(Date useTime) {
        this.useTime = useTime;
    }



}
