package com.sunesoft.lemon.syms.uAuth.domain;

import com.sunesoft.lemon.fr.ddd.BaseEntity;
import com.sunesoft.lemon.syms.eHr.domain.empInfo.Employee;

import javax.persistence.*;

/**
 * Created by zhouz on 2016/7/14.
 */
@Entity
@Table(name="syy_oa_sys_emp_role")
public class SysEmpAndRole extends BaseEntity {

    @ManyToOne
    @JoinColumn(name="emp_id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name="role_id")
    private SysRole role;


    public SysRole getRole() {
        return role;
    }

    public void setRole(SysRole role) {
        this.role = role;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
