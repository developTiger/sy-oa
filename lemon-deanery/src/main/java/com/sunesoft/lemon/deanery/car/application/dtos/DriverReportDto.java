package com.sunesoft.lemon.deanery.car.application.dtos;

import com.sunesoft.lemon.deanery.car.application.static_common.static_common;
import com.sunesoft.lemon.deanery.car.domain.Company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangwj on 2016/6/21 0021.
 */
public class DriverReportDto {
    /**
     * 所属公司列表信息
     */
    private List<Company> companys;
    /**
     * 证件类型列表信息
     */
    private List<String> driverDocTypes;
    /**
     * 司机状态列表信息
     * 1：待岗，2：出车，3：离岗
     */
    private List<Map<String,String>> statusList;
    /**
     * 查询条件1、所属公司ID
     */
    private Long companyId;
    /**
     * 查询条件2、证件类型
     */
    private String driverDocType;
    /**
     * 查询条件3、司机状态
     */
    private Integer status;
    /**
     * 司机汇总信息
     */
    private List<Map<String,Object>> driverReportInfo;

    public DriverReportDto() {
        //初始化司机状态信息列表
        List<Map<String,String>> statusList = new ArrayList<Map<String,String>>();
        Map<String,String> m1 = new HashMap<String,String>();
        m1.put("key","1");
        m1.put("value","待岗");
        statusList.add(m1);
        Map<String,String> m2 = new HashMap<String,String>();
        m2.put("key","2");
        m2.put("value","出车");
        statusList.add(m2);
        Map<String,String> m3 = new HashMap<String,String>();
        m3.put("key","3");
        m3.put("value","离岗");
        statusList.add(m3);
        this.statusList = statusList;
        //初始化证件类型
        this.driverDocTypes = static_common.DRIVER_TYPE;
    }

    public List<Company> getCompanys() {
        return companys;
    }

    public void setCompanys(List<Company> companys) {
        this.companys = companys;
    }

    public List<String> getDriverDocTypes() {
        return driverDocTypes;
    }

    public void setDriverDocTypes(List<String> driverDocTypes) {
        this.driverDocTypes = driverDocTypes;
    }

    public List<Map<String, String>> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<Map<String, String>> statusList) {
        this.statusList = statusList;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getDriverDocType() {
        return driverDocType;
    }

    public void setDriverDocType(String driverDocType) {
        this.driverDocType = driverDocType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<Map<String, Object>> getDriverReportInfo() {
        return driverReportInfo;
    }

    public void setDriverReportInfo(List<Map<String, Object>> driverReportInfo) {
        this.driverReportInfo = driverReportInfo;
    }
}
