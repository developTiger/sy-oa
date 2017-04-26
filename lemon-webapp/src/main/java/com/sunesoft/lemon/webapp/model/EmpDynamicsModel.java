package com.sunesoft.lemon.webapp.model;

import com.sunesoft.lemon.syms.eHr.application.dtos.AttendanceDto;
import com.sunesoft.lemon.syms.eHr.application.dtos.DayModel;
import com.sunesoft.lemon.syms.eHr.application.dtos.EmpAttendsDto;

import java.util.List;
import java.util.Map;

/**
 * Created by temp on 2016/8/9.
 */
public class EmpDynamicsModel {

    private List<EmpAttendsDto> resultList;
    private Map<String, Object> map;
    private List<AttendanceDto> items;
    private String dMonth;
    private Integer day;
    private Map<Integer,Boolean> dayMap;
    private Integer dayBetween;
    private List<DayModel> dayModels;
    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    public List<AttendanceDto> getItems() {
        return items;
    }

    public void setItems(List<AttendanceDto> items) {
        this.items = items;
    }

    public String getdMonth() {
        return dMonth;
    }

    public void setdMonth(String dMonth) {
        this.dMonth = dMonth;
    }

    public Map<Integer, Boolean> getDayMap() {
        return dayMap;
    }

    public void setDayMap(Map<Integer, Boolean> dayMap) {
        this.dayMap = dayMap;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public List<EmpAttendsDto> getResultList() {
        return resultList;
    }

    public void setResultList(List<EmpAttendsDto> resultList) {
        this.resultList = resultList;
    }

    public Integer getDayBetween() {
        return dayBetween;
    }

    public void setDayBetween(Integer dayBetween) {
        this.dayBetween = dayBetween;
    }

    public List<DayModel> getDayModels() {
        return dayModels;
    }

    public void setDayModels(List<DayModel> dayModels) {
        this.dayModels = dayModels;
    }
}
