package com.sunesoft.lemon.deanery.car.application.criteria;

import com.sunesoft.lemon.fr.results.PagedCriteria;

/**
 * Created by wangwj on 2016/6/16 0016.
 */
public class CarCriteria extends PagedCriteria {
    private Long companyId;
    private Integer status;
    private String carType;
    private String carNo;
    private String companyName;


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

    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
