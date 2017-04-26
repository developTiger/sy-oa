package com.sunesoft.lemon.deanery.car.application.criteria;

import com.sunesoft.lemon.fr.results.PagedCriteria;

/**
 * Created by Administrator on 2016/6/17 0017.
 */
public class CarReportCriteria extends PagedCriteria {
    //所属公司
    private String companyName;



    //车牌号
    private String carNo;


    /**
     * 所属公司ID
     */
    private Long companyId;
    /**
     * 车辆状态
     */
    private Integer status;
    /**
     * 车辆类型
     */
    private String carType;

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


    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }
}



