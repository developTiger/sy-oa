package com.sunesoft.lemon.deanery.car.application.criteria;

import com.sunesoft.lemon.fr.results.PagedCriteria;

/**
 * Created by wangwj on 2016/6/21 0021.
 */
public class DriverReportCriteria extends PagedCriteria {
    /**
     *所属公司ID
     */
    private Long companyId;
    /**
     * 司机状态
     */

    private Integer status;
    /**
     * 证件类型
     */
    private String driverDocType;

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

    public String getDriverDocType() {
        return driverDocType;
    }

    public void setDriverDocType(String driverDocType) {
        this.driverDocType = driverDocType;
    }
}
