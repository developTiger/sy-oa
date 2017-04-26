package com.sunesoft.lemon.syms.eHr.application.criteria;

import com.sunesoft.lemon.fr.results.PagedCriteria;

import java.util.Date;

/**
 * Created by xzl on 2016/6/30.
 */
public class AttendCriteria extends PagedCriteria {

    /**
     * 员工名称
     */
    private String empName;
    /**
     *出勤时间
     */
    private Date attDate;//时间

    /**
     * 部门Id
     */
    private Long depId;//部门Id

    public Date getAttDate() {
        return attDate;
    }

    public void setAttDate(Date attDate) {
        this.attDate = attDate;
    }

    public Long getDepId() {
        return depId;
    }

    public void setDepId(Long depId) {
        this.depId = depId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }


}
