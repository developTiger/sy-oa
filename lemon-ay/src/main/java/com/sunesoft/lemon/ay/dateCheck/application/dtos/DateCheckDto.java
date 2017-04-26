package com.sunesoft.lemon.ay.dateCheck.application.dtos;


/**
 * Created by xiazl on 2016/10/21.
 */
public class DateCheckDto {

    private Long id;

   private Boolean isActive;

    private String screateDateTime;

    private String slastUpdateTime;
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

    private String factoryName;

    /**
     * 出厂编号
     */

    private String outFactoryNum;

    /**
     * 规格型号
     */

    private String standard;

    /**
     * 启用日期
     */

    private String suseTime;

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
    private String scheckTime;

    /**
     * 核查记录
     */
    private String content;

    /**
     * 核查数据
     */
    private String checkDate;
    /**
     * 数据处理
     */
    private String date;

    /**
     * 核查人
     */
    private String checkPerson;
    /**
     * 时间
     */
    private String spersonTime;

    /**
     * 负责人填写内容
     */
    private String headContent;

    /**
     * 负责人
     */
    private String headPerson;
    /**
     * 时间
     */
    private String sheadTime;

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

    public String getOutFactoryNum() {
        return outFactoryNum;
    }

    public void setOutFactoryNum(String outFactoryNum) {
        this.outFactoryNum = outFactoryNum;
    }

    public String getScreateDateTime() {
        return screateDateTime;
    }

    public void setScreateDateTime(String screateDateTime) {
        this.screateDateTime = screateDateTime;
    }

    public String getSlastUpdateTime() {
        return slastUpdateTime;
    }

    public void setSlastUpdateTime(String slastUpdateTime) {
        this.slastUpdateTime = slastUpdateTime;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getScheckTime() {
        return scheckTime;
    }

    public void setScheckTime(String scheckTime) {
        this.scheckTime = scheckTime;
    }

    public String getSheadTime() {
        return sheadTime;
    }

    public void setSheadTime(String sheadTime) {
        this.sheadTime = sheadTime;
    }

    public String getSpersonTime() {
        return spersonTime;
    }

    public void setSpersonTime(String spersonTime) {
        this.spersonTime = spersonTime;
    }

    public String getSuseTime() {
        return suseTime;
    }

    public void setSuseTime(String suseTime) {
        this.suseTime = suseTime;
    }
}
