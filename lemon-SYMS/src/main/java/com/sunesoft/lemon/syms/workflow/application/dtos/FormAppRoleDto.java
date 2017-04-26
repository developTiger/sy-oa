package com.sunesoft.lemon.syms.workflow.application.dtos;

import com.sunesoft.lemon.syms.eHr.application.dtos.EmpSimpleDto;
import com.sunesoft.lemon.syms.workflow.domain.enums.AppType;
import com.sunesoft.lemon.syms.workflow.domain.enums.RoleType;

import java.util.List;

/**
 * Created by zhouz on 2016/6/17.
 * edit by pxj appType增加判断否决-退回
 */
public class FormAppRoleDto {

    private Long id;
    /**
     * 角色名称
     */
    private String roleName;


    /**
     * 角色编码
     */
    private String roleCode;

    /**
     * 角色描述
     */
    public String roleDesc;

    /**
     * 角色类型
     */
    private RoleType roleType;

     /**
     * 签核人的Id 和名字
     */
    private List<EmpSimpleDto> empList;

    private Boolean byDept;

    private Long deptId;

    public AppType getAppType() {
        return appType;
    }

    public void setAppType(AppType appType) {
        this.appType = appType;
    }

    private AppType appType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public List<EmpSimpleDto> getEmpList() {
        return empList;
    }

    public void setEmpList(List<EmpSimpleDto> empList) {
        this.empList = empList;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }

    public Boolean getByDept() {
        return byDept;
    }

    public void setByDept(Boolean byDept) {
        this.byDept = byDept;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }
}
