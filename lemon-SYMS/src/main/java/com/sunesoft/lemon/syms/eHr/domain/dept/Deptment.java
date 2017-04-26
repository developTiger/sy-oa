package com.sunesoft.lemon.syms.eHr.domain.dept;

import com.sunesoft.lemon.fr.ddd.BaseEntity;
import com.sunesoft.lemon.syms.eHr.domain.empInfo.Employee;

import javax.persistence.*;
import java.util.Collections;
import java.util.Date;
import java.util.List;


/**
 * Created by xiazl on 2016/6/17.
 */
@Entity
@Table(name="syy_oa_hr_dept")
public class Deptment extends BaseEntity {

    /**
     * 部门分管领导
     */

    @Column(name = "charge_leader_id")
    private Long chargeLeaderId;

    /**
     * 部门分管领导 name
     */
    @Column(name = "charge_leader_name")
    private String chargeLeaderName;

    /**
     * 部门名称
     */
    @Column(name = "dept_name", nullable = false)
    private String deptName;

//    /**
//     * 部门简称
//     */
//    @Column(name = "short_name", nullable = false)
//    private String shortName;


    /**
     * 部门编号
     */
    @Column(name = "dept_no")
    private String deptNo;

    /**
     * 部门等级
     * 0代表院级，1代表科级
     */
    @Column(name="dept_type")
    private Integer deptType;
    /**
     * 上级部门
     */
    @ManyToOne
    @JoinColumn(name = "parent_dept_id")
    private Deptment parentDept;

//    /**
//     * 直属上级部门的id
//     */
//    @Column(name = "parent_dept_id")
//    private Long parentDeptId;
//
//    /**
//     * 直属上级部门的名称
//     */
//    @Column(name = "parent_dept_name")
//    private String parentDeptName;


    /**
     * 0代表禁用，1代表未禁用
     */
    @Column(name ="dept_status")
    private Integer status;

    /**
     * 部门介绍
     */
    private String brief;

    public Deptment() {
        this.setIsActive(true);
        this.setCreateDateTime(new Date());
        this.setLastUpdateTime(new Date());
        this.setStatus(1);
        this.setDeptType(0);

    }

    public Long getChargeLeaderId() {
        return chargeLeaderId;
    }

    public void setChargeLeaderId(Long chargeLeaderId) {
        this.chargeLeaderId = chargeLeaderId;
    }

    public String getChargeLeaderName() {
        return chargeLeaderName;
    }

    public void setChargeLeaderName(String chargeLeaderName) {
        this.chargeLeaderName = chargeLeaderName;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getIdCard() {
        return deptNo;
    }

    public void setIdCard(String idCard) {
        this.deptNo = idCard;
    }


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(String deptNo) {
        this.deptNo = deptNo;
    }

    public Integer getDeptType() {
        return deptType;
    }

    public void setDeptType(Integer deptType) {
        this.deptType = deptType;
    }

    public Deptment getParentDept() {
        return parentDept;
    }

    public void setParentDept(Deptment parentDept) {
        this.parentDept = parentDept;
    }



    //    public Long getParentDeptId() {
//        return parentDeptId;
//    }
//
//    public void setParentDeptId(Long parentDeptId) {
//        this.parentDeptId = parentDeptId;
//    }
//
//    public String getParentDeptName() {
//        return parentDeptName;
//    }
//
//    public void setParentDeptName(String parentDeptName) {
//        this.parentDeptName = parentDeptName;
//    }
}
