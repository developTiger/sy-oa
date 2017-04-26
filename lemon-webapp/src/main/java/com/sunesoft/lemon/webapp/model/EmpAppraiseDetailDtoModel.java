package com.sunesoft.lemon.webapp.model;

/**
 * Created by admin on 2016/10/15.
 */
public class EmpAppraiseDetailDtoModel {

    private String loginName;

    private String deptName;

    private String empSelfGrade;

    private String deptScores;

    private String deptGrade;

    private String groupScores;

    private String groupGrade;

    private String chargeLeaderScores;

    private String chargeLeaderLevel;



    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getChargeLeaderScores() {
        return chargeLeaderScores;
    }

    public void setChargeLeaderScores(String chargeLeaderScores) {
        this.chargeLeaderScores = chargeLeaderScores;
    }

    public String getChargeLeaderLevel() {
        return chargeLeaderLevel;
    }

    public void setChargeLeaderLevel(String chargeLeaderLevel) {
        this.chargeLeaderLevel = chargeLeaderLevel;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getEmpSelfGrade() {
        return empSelfGrade;
    }

    public void setEmpSelfGrade(String empSelfGrade) {
        this.empSelfGrade = empSelfGrade;
    }

    public String getDeptScores() {
        return deptScores;
    }

    public void setDeptScores(String deptScores) {
        this.deptScores = deptScores;
    }

    public String getDeptGrade() {
        return deptGrade;
    }

    public void setDeptGrade(String deptGrade) {
        this.deptGrade = deptGrade;
    }

    public String getGroupScores() {
        return groupScores;
    }

    public void setGroupScores(String groupScores) {
        this.groupScores = groupScores;
    }

    public String getGroupGrade() {
        return groupGrade;
    }

    public void setGroupGrade(String groupGrade) {
        this.groupGrade = groupGrade;
    }
}
