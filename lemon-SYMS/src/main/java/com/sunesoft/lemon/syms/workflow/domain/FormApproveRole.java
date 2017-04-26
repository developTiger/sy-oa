package com.sunesoft.lemon.syms.workflow.domain;

import com.sunesoft.lemon.fr.ddd.BaseEntity;
import com.sunesoft.lemon.syms.eHr.domain.empInfo.Employee;
import com.sunesoft.lemon.syms.workflow.domain.enums.AppType;
import com.sunesoft.lemon.syms.workflow.domain.enums.RoleType;

import javax.persistence.*;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 签核角色表
 * Created by zhouz on 2016/5/25.
 * edit BY PXJ
 */
@Entity
@Table(name = "syy_oa_fm_approve_role")
public class FormApproveRole extends BaseEntity {
    /**
     * 角色名称
     */
    @Column(name = "role_name", columnDefinition = "varchar(100) DEFAULT NULL")
    private String roleName;

    /**
     * 角色编码
     */
    @Column(name = "role_code", columnDefinition = "varchar(100) DEFAULT NULL")
    private String roleCode;

    /**
     * 角色描述
     */
    @Column(name = "role_desc", columnDefinition = "varchar(100) DEFAULT NULL")
    public String roleDesc;

    /**
     * 角色类型
     */
    @Column(name = "role_type")
    private RoleType roleType;


    @Column(name = "app_type")
    private AppType appType;


    @Column(name="by_dept")
    private Boolean byDept;

    @Column(name = "dept_id")
    private Long deptId;
    //    /**
//     * 角色用户
//     */
//    private String roleUser;
//
//    /**
//     * 用户Id
//     */
//    private Long userId;
    @ManyToMany
    @JoinTable(name = "syy_oa_fm_role_approver", inverseJoinColumns = @JoinColumn(name = "emp_id"), joinColumns = @JoinColumn(name = "app_role_id"))
    private List<Employee> approver;

    /**
     * 表单处理级别
     */
    @Column(name = "cl_step")
    private Integer clStep;//表单处理级别

    public FormApproveRole(){
        this.setIsActive(true);
        this.setCreateDateTime(new Date());
        this.setLastUpdateTime(new Date());
        this.roleType=RoleType.OR;
       // this.appType=AppType.A; 去除默认为允许退回/否决
        this.approver = Collections.emptyList();
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

    public AppType getAppType() {
        return appType;
    }

    public void setAppType(AppType appType) {
        this.appType = appType;
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

    public Integer getClStep() {
        return clStep;
    }

    public void setClStep(Integer clStep) {
        this.clStep = clStep;
    }

    public List<Employee> getApprover() {
        return approver;
    }

    public void setApprover(List<Employee> approver) {
        this.approver = approver;
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
}
