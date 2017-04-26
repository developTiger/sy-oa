package com.sunesoft.lemon.syms.eHr.application.dtos;

import java.util.List;

/**
 * Created by zhouz on 2016/6/24.
 */
public class EmpSessionDto {



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


    private Long deptId;

    private String deptNo;


    private String deptName;

    /**
     * 职位
     */
    private String position;


    private List<Long> roleIds;


    private Integer fileManagerAccess; //0 不可操作 1 可以操作

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

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public List<Long> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Long> roleIds) {
        this.roleIds = roleIds;
    }

    public Integer getFileManagerAccess() {
        return fileManagerAccess;
    }

    public void setFileManagerAccess(Integer fileManagerAccess) {
        this.fileManagerAccess = fileManagerAccess;
    }

    public String getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(String deptNo) {
        this.deptNo = deptNo;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
