package com.sunesoft.lemon.deanery.car.application.dtos;

import com.sunesoft.lemon.deanery.car.domain.Company;
import com.sunesoft.lemon.fr.results.PagedResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/21 0021.
 */
public class CarReportDto {
    /**
     * 公司信息列表
     */
    private List<Company> companys;
    /**
     * 查询统计信息
     */
    private List<Map<String,Object>> reportInfo;

    private PagedResult<Object> carInfo;
    /**
     * 车辆状态 1：待命，2：出车，3：维修，4：停运
     */
    private List<Map<String,String>> statusList;
    /**
     * 车辆类型列表
     */
    private List<Map<String,Object>> carTypeList;
    /**
     * 查询条件1、所属公司
     */
    private Long companyId;
    /**
     * 查询条件2、车辆状态
     */
    private Integer status;
    /**
     * 查询条件3、车辆类型
     */
    private String carType;

    public CarReportDto() {
        List<Map<String,String>> statusList = new ArrayList<Map<String,String>>();
        Map<String,String> m1 = new HashMap<String,String>();
        m1.put("key","1");
        m1.put("value","待命");
        statusList.add(m1);
        Map<String,String> m2 = new HashMap<String,String>();
        m2.put("key","2");
        m2.put("value","出车");
        statusList.add(m2);
        Map<String,String> m3 = new HashMap<String,String>();
        m3.put("key","3");
        m3.put("value","维护保养");
        statusList.add(m3);
        Map<String,String> m4 = new HashMap<String,String>();
        m4.put("key","4");
        m4.put("value","停运");
        statusList.add(m4);
        this.statusList = statusList;
    }

    public List<Company> getCompanys() {
        return companys;
    }

    public void setCompanys(List<Company> companys) {
        this.companys = companys;
    }

    public List<Map<String, Object>> getReportInfo() {
        return reportInfo;
    }


    public List<Map<String, String>> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<Map<String, String>> statusList) {
        this.statusList = statusList;
    }

    public List<Map<String, Object>> getCarTypeList() {
        return carTypeList;
    }

    public void setCarTypeList(List<Map<String, Object>> carTypeList) {
        this.carTypeList = carTypeList;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public void setReportInfo(List<Map<String, Object>> reportInfo) {
        this.reportInfo = reportInfo;
    }

    public PagedResult<Object> getCarInfo() {
        return carInfo;
    }

    public void setCarInfo(PagedResult<Object> carInfo) {
        this.carInfo = carInfo;
    }
}
