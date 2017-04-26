package com.sunesoft.lemon.syms.hrForms.application.criteria;

import com.sunesoft.lemon.fr.results.PagedCriteria;

import java.util.Date;

/**
 * 请假信息的统计列表查询
 * Created by xiazl on 2017/4/18.
 */
public class LeaveFormCriteria extends PagedCriteria {

    /**
     * 开始时间
     */
    private Date begin;
    /**
     * 结束时间
     */
    private Date end;
    /**
     * 申请人
     */
    private String personName;
    /**
     * 部门
     */
    private Long deptId;

    public Date getBegin() {
        return begin;
    }

    public void setBegin(Date begin) {
        this.begin = begin;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }
}
