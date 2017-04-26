package com.sunesoft.lemon.syms.eHr.application.dtos;

import java.util.List;
import java.util.Map;

/**
 * Created by kkk on 2017/3/3.
 */
public class AttendSummeryResultDto {

    private String empName;
    private Map<String,Integer> maps;
    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }


    public Map<String, Integer> getMaps() {
        return maps;
    }

    public void setMaps(Map<String, Integer> maps) {
        this.maps = maps;
    }
}
