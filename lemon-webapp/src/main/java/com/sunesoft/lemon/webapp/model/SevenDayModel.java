package com.sunesoft.lemon.webapp.model;

import com.sunesoft.lemon.syms.eHr.application.dtos.NearlyFiveDayAttendanceInfoDto;

import java.util.List;

/**
 * Created by temp on 2016/7/6.
 */
public class SevenDayModel {

    private List<NearlyFiveDayAttendanceInfoDto> list;

    private String d1;
    private String d2;
    private String d3;
    private String d4;
    private String d5;

    private Boolean addSuccess;

    public Boolean getAddSuccess() {
        return addSuccess;
    }

    public void setAddSuccess(Boolean addSuccess) {
        this.addSuccess = addSuccess;
    }

    public List<NearlyFiveDayAttendanceInfoDto> getList() {
        return list;
    }

    public void setList(List<NearlyFiveDayAttendanceInfoDto> list) {
        this.list = list;
    }

    public String getD1() {
        return d1;
    }

    public void setD1(String d1) {
        this.d1 = d1;
    }

    public String getD2() {
        return d2;
    }

    public void setD2(String d2) {
        this.d2 = d2;
    }

    public String getD3() {
        return d3;
    }

    public void setD3(String d3) {
        this.d3 = d3;
    }

    public String getD4() {
        return d4;
    }

    public void setD4(String d4) {
        this.d4 = d4;
    }

    public String getD5() {
        return d5;
    }

    public void setD5(String d5) {
        this.d5 = d5;
    }
}

