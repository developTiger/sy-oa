package com.sunesoft.lemon.syms.hrForms.application.Dtos;


/**
 * Created by zhouz on 2016/7/29.
 */
public class DeptAppraiseDetailDto  {

    private Long id;
    /**
     * 部门信息
     */
    private String deptName;

    /**
     * 部门Id
     */
    private Long deptId;

    /**
     * 部门档次
     */
    private String deptLevel;

    /**
     * 部门分数
     */
    private Float   deptScores;

    /**
     * 部门等级
     */
    private String  deptGrade;


    /**
     * 部门备注——》审核意见
     */
    private String  deptRemark;

    /**
     * 专家组档次
     */
    private String groupLevel;

    /**
     * 专家组分数
     */
    private Float  groupScores;

    /**
     * 专家组等级
     */
    private String  groupGrade;

    /**
     * 专家组备注
     */
    private String groupRemark;


    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getDeptLevel() {
        return deptLevel;
    }

    public void setDeptLevel(String deptLevel) {
        this.deptLevel = deptLevel;
    }

    public Float getDeptScores() {
        return deptScores;
    }

    public void setDeptScores(Float deptScores) {
        this.deptScores = deptScores;
    }

    public String getDeptGrade() {
        return deptGrade;
    }

    public void setDeptGrade(String deptGrade) {
        this.deptGrade = deptGrade;
    }

    public String getDeptRemark() {
        return deptRemark;
    }

    public void setDeptRemark(String deptRemark) {
        this.deptRemark = deptRemark;
    }

    public String getGroupLevel() {
        return groupLevel;
    }

    public void setGroupLevel(String groupLevel) {
        this.groupLevel = groupLevel;
    }

    public Float getGroupScores() {
        return groupScores;
    }

    public void setGroupScores(Float groupScores) {
        this.groupScores = groupScores;
    }

    public String getGroupGrade() {
        return groupGrade;
    }

    public void setGroupGrade(String groupGrade) {
        this.groupGrade = groupGrade;
    }

    public String getGroupRemark() {
        return groupRemark;
    }

    public void setGroupRemark(String groupRemark) {
        this.groupRemark = groupRemark;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
