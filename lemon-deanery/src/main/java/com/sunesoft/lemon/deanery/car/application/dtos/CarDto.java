package com.sunesoft.lemon.deanery.car.application.dtos;

import com.sunesoft.lemon.deanery.car.domain.Company;
import com.sunesoft.lemon.fr.ddd.BaseEntity;
import com.sunesoft.lemon.fr.results.PagedResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangwj on 2016/6/16 0016.
 */
public class CarDto {

    //序号：车辆主键
    private Long id;

    //所属公司
    private String companyName;

    //车型
    private String carType;

    //车牌号
    private String carNo;

    //车辆状态
    private Integer status;

    //维护记录
    private String repairLog;

    //车辆维护历史记录
    private String repairLogOld;

    //创建时间
    private String createTime;

    //录入人
    private String user;

    //车辆状态中文信息

    private String statusName;



    private PagedResult<Object> reportInfo;

    private List<Company> companys;

    //归属部门
    private String controlName;

    private Long companyId;

    /**
     * 车辆状态 1：待命，2：出车，3：维修，4：停运
     */
    private List<Map<String,String>> statusList;
    /**
     * 车辆类型列表
     */
    private List<Map<String,Object>> carTypeList;

    private List<Company> companyList;

    private List<String> repairLogs;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }


    public String getRepairLog() {
        return repairLog;
    }

    public void setRepairLog(String repairLog) {
        this.repairLog = repairLog;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getControlName() {
        return controlName;
    }

    public void setControlName(String controlName) {
        this.controlName = controlName;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }



    public List<Company> getCompanyList() {
        return companyList;
    }

    public void setCompanyList(List<Company> companyList) {
        this.companyList = companyList;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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



    public List<Company> getCompanys() {
        return companys;
    }

    public void setCompanys(List<Company> companys) {
        this.companys = companys;
    }

    public PagedResult<Object> getReportInfo() {
        return reportInfo;
    }

    public void setReportInfo(PagedResult<Object> reportInfo) {
        this.reportInfo = reportInfo;
    }


    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public List<String> getRepairLogs() {
        return repairLogs;
    }

    public void setRepairLogs(List<String> repairLogs) {
        this.repairLogs = repairLogs;
    }

    public String getRepairLogOld() {
        return repairLogOld;
    }

    public void setRepairLogOld(String repairLogOld) {
        this.repairLogOld = repairLogOld;
    }
}
