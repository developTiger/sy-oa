package com.sunesoft.lemon.syms.eHr.application.dtos;

import java.util.Map;

/**
 *
 * Created by xiazl on 2016/7/6.
 */
public class NewNearSevenDay {
    private Long deptId;
    /**
     * 部门名称
     */
    private String deptName;
    /**
     * 第 i天
     */
    private Map<String,Integer> dateToInt;

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

    public Map<String, Integer> getDateToInt() {
        return dateToInt;
    }

    public void setDateToInt(Map<String, Integer> dateToInt) {
        this.dateToInt = dateToInt;
    }
}
