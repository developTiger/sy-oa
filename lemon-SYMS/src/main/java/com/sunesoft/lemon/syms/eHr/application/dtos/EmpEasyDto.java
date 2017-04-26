package com.sunesoft.lemon.syms.eHr.application.dtos;

/**
 * Created by zhouz on 2016/7/22.
 */
public class EmpEasyDto {

    /**
     * 岗位
     */
    private String position;

    private Long id;

    /**
     * 员工编号
     */
    private String userNo;

    /**
     * 真实姓名
     */
    private String name; // 真实姓名

    /**
     * 登录用户帐号
     */
    private String loginName;
    /**
     * EMP部门ID
     */
    private  Long deptId;
    /**
     * EMP部门名称
     */
    private String trainDeptName;

    /**
     * 培训分类
     */
    private String trainClassify;

    /**
     * 计划内 或者外
     */
    private Boolean planFlag;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }


    public String getTrainDeptName() {
        return trainDeptName;
    }

    public void setTrainDeptName(String trainDeptName) {
        this.trainDeptName = trainDeptName;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
