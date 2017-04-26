package com.sunesoft.lemon.deanery.FormFlow.domain;

import com.sunesoft.lemon.fr.ddd.BaseEntity;
import com.sunesoft.lemon.syms.workflow.domain.enums.RoleType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by wangwj on 2016/8/5 0005.
 * 会签表
 */
@Entity
@Table(name = "syy_oa_form_countersign")
public class Countersign extends BaseEntity {
    /**
     * 表单编号
     */
    @Column(name = "form_no",columnDefinition = "number(19)")
    private Long formNo;

    /**
     * 表单种类
     */
    @Column(name = "form_kind",columnDefinition = "varchar2(100)")
    private String formKind;

    /**
     * 会签人ID
     */
    @Column(name = "counter_id",columnDefinition = "number(19)")
    private Long counterId;

    /**
     * 会签人姓名
     */
    @Column(name = "counter_name",columnDefinition = "varchar2(100)")
    private String counterName;

    /**
     * 审批意见
     */
    @Column(name = "count_remark",columnDefinition = "varchar2(500)")
    private String countRemark;

    /**
     * 审批结果
     * 0 同意
     * 1 不同意
     */
    @Column(name = "count_result",columnDefinition = "number(2)")
    private Integer countResult;

    /**
     * 角色类型
     */
    @Column(name="role_ype")
    private RoleType roleType;

    /**
     * 角色名称
     */
    @Column(name = "role_name",columnDefinition = "varchar2(100)")
    private String roleName;

    public Countersign() {
        this.setIsActive(true);
        this.setCreateDateTime(new Date());
        this.setLastUpdateTime(new Date());
    }

    public Long getFormNo() {
        return formNo;
    }

    public void setFormNo(Long formNo) {
        this.formNo = formNo;
    }

    public String getFormKind() {
        return formKind;
    }

    public void setFormKind(String formKind) {
        this.formKind = formKind;
    }

    public Long getCounterId() {
        return counterId;
    }

    public void setCounterId(Long counterId) {
        this.counterId = counterId;
    }

    public String getCounterName() {
        return counterName;
    }

    public void setCounterName(String counterName) {
        this.counterName = counterName;
    }

    public String getCountRemark() {
        return countRemark;
    }

    public void setCountRemark(String countRemark) {
        this.countRemark = countRemark;
    }

    public Integer getCountResult() {
        return countResult;
    }

    public void setCountResult(Integer countResult) {
        this.countResult = countResult;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
