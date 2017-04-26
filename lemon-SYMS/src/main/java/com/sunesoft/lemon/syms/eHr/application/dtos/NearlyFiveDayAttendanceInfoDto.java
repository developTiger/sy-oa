package com.sunesoft.lemon.syms.eHr.application.dtos;

import java.util.Map;

/**
 * 获取最近n天部门是否确认，列表
 * Created by zhouz on 2016/7/5.
 */
public class NearlyFiveDayAttendanceInfoDto {

    /**
     * 部门编号
     */
    private Long depId; //部门编号
    /**
     * name of dept
     */
    private String deptName;

    /**
     * key :date  如2016-07-05
     * value: 是否已确认
     */
    private Map<String,Boolean> attInfoMap;

    public Long getDepId() {
        return depId;
    }

    public void setDepId(Long depId) {
        this.depId = depId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Map<String, Boolean> getAttInfoMap() {
        return attInfoMap;
    }

    public void setAttInfoMap(Map<String, Boolean> attInfoMap) {
        this.attInfoMap = attInfoMap;
    }
}
