package com.sunesoft.lemon.syms.eHr.application.criteria;

import com.sunesoft.lemon.fr.results.PagedCriteria;

import java.util.Date;

/**
 * Created by zhouz on 2016/7/4.
 */
public class EmpAttendancesCriteria extends PagedCriteria {
    /**
     * 员工编号
     */
    private Long empId;
    /**
     * 部门编号
     */
    private Long deptId;
    /**
     * 查询时间
     */
    private Date attDate;

    /**
     * 用户名
     * @return
     */
    private String name;

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Date getAttDate() {
        return attDate;
    }

    public void setAttDate(Date attDate) {
        this.attDate = attDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
