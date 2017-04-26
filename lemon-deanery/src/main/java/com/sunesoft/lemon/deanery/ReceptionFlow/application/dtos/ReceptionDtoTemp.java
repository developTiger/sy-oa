package com.sunesoft.lemon.deanery.ReceptionFlow.application.dtos;

/**
 * Created by swb on 2016/12/15.
 */
public class ReceptionDtoTemp {

    private String formKindName;

    private String applyerName;

    /**
     * 外宾单位
     */
    private String foreignGuest;
    /**
     * 联系人
     */
    private String linkman;
    /**
     * 联系人电话
     */
    private String mobile;

    /**
     * 事由
     */
    private String reasons;

    private String deptName;

    /**
     * 附件name
     */
    private String fileName;

    /**
     * 附件name
     */
    private String fileName1;

    //院办分配接待任务
    private String receptionWork;

    //实际接待人，来访人员

    private String receptionPerson;

    //陪同人员

    //private String personComp;

    //实际接待费用

    //private Long actualCost;
    private String actualCost;

    //实际接待开始时间和结束时间

    private String actualBeginTime;

    private String actualEndTime;

    //接待过程

    private String receptionProcess;

/*
    private int taojian;
    private int danjian;
    private int biaozhunjian;
    private int qitajian;
    private String rence1;
    private String rence2;
    private String rence3;
    private String yanhuisj1;
    private String yanhuirs1;
    private String yanhuibz1;
    private String yanhuisj2;
    private String yanhuirs2;
    private String yanhuibz2;
    private String xiaoxie;
    private String daxie;
    private String feiyongyusuan1;
    private String feiyongyusuan2;
    private String feiyongyusuan3;
*/


    public String getFormKindName() {
        return formKindName;
    }

    public void setFormKindName(String formKindName) {
        this.formKindName = formKindName;
    }

    public String getApplyerName() {
        return applyerName;
    }

    public void setApplyerName(String applyerName) {
        this.applyerName = applyerName;
    }

    public String getForeignGuest() {
        return foreignGuest;
    }

    public void setForeignGuest(String foreignGuest) {
        this.foreignGuest = foreignGuest;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getReasons() {
        return reasons;
    }

    public void setReasons(String reasons) {
        this.reasons = reasons;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName1() {
        return fileName1;
    }

    public void setFileName1(String fileName1) {
        this.fileName1 = fileName1;
    }

    public String getReceptionWork() {
        return receptionWork;
    }

    public void setReceptionWork(String receptionWork) {
        this.receptionWork = receptionWork;
    }

    public String getReceptionPerson() {
        return receptionPerson;
    }

    public void setReceptionPerson(String receptionPerson) {
        this.receptionPerson = receptionPerson;
    }

    /*public String getPersonComp() {
        return personComp;
    }

    public void setPersonComp(String personComp) {
        this.personComp = personComp;
    }*/

    public String getActualCost() {
        return actualCost;
    }

    public void setActualCost(String actualCost) {
        this.actualCost = actualCost;
    }

    public String getActualBeginTime() {
        return actualBeginTime;
    }

    public void setActualBeginTime(String actualBeginTime) {
        this.actualBeginTime = actualBeginTime;
    }

    public String getActualEndTime() {
        return actualEndTime;
    }

    public void setActualEndTime(String actualEndTime) {
        this.actualEndTime = actualEndTime;
    }

    public String getReceptionProcess() {
        return receptionProcess;
    }

    public void setReceptionProcess(String receptionProcess) {
        this.receptionProcess = receptionProcess;
    }
}
