package com.sunesoft.lemon.syms.eHr.domain.empInfo;

import com.sunesoft.lemon.fr.ddd.BaseEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by zhouz on 2016/6/14.
 */
@Entity
@Table(name="syy_oa_hr_emp_group")
public class EmpGroup extends BaseEntity {

    /**
     * 是否是当前员工组
     */
    @Column(name = "is_current")
    private Boolean isCurrent;

    /**
     * 员工组名
     */
    @Column(name = "group_name")
    private String name;

    /**
     * 0代表禁用，1代表未禁用
     */
    @Column(name = "group_status")
    private Integer status;

    /**
     * 简介
     */
    private String brief;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private EmpGroup parent;

//    @Column(name="parent_id")
//    private Long parentId;
//
//
//    @Column(name="parent_name")
//    private String parentName;
//    /**
//     * 员工id
//     */
//    @Column(name = "emp_id")
//    private Long empId;

    public EmpGroup() {

        this.setCreateDateTime(new Date());
        this.setLastUpdateTime(new Date());
        this.setIsActive(true);
        this.setStatus(1);
        this.setIsCurrent(true);
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Boolean getIsCurrent() {
        return isCurrent;
    }

    public void setIsCurrent(Boolean isCurrent) {
        this.isCurrent = isCurrent;
    }

    public EmpGroup getParent() {
        return parent;
    }

    public void setParent(EmpGroup parent) {
        this.parent = parent;
    }

    //    public Long getParentId() {
//        return parentId;
//    }
//
//    public void setParentId(Long parentId) {
//        this.parentId = parentId;
//    }
//
//    public void setParentName(String parentName) {
//        this.parentName = parentName;
//    }
//
//    public String getParentName() {
//        return parentName;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }


}
