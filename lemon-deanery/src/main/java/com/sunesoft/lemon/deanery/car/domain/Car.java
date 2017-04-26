package com.sunesoft.lemon.deanery.car.domain;

import com.sunesoft.lemon.fr.ddd.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Blob;
import java.util.Date;

/**
 * Created by wangwj on 2016/6/15 0015.
 */
@Entity
@Table(name = "syy_oa_carinfo")
public class Car extends BaseEntity{
    /**
     * 公司Id
     */
    @Column(name = "company_id",columnDefinition = "NUMBER(19)")
    private Long companyId;

    /**
     * 车型
     */
    @Column(name = "car_type",
            columnDefinition = "VARCHAR2(50)")
    private String carType;

    /**
     * 车牌照
     */
    @Column(name = "car_no",
            columnDefinition = "VARCHAR2(50)")
    private String carNo;

    /**
     *车辆状态
     */
    @Column(name = "status",
            columnDefinition = "NUMBER(1) DEFAULT 1 ")
    private Integer status;//1：待命，2：出车，3：维修，4：停运

    /**
     *车辆维护记录
     */
    @Column(name ="repair_log",
            columnDefinition = "BLOB")
    private byte[]  repairLogTxt;

    /**
     *归属部门
     */
    @Column(name = "control_name",
            columnDefinition = "VARCHAR2(100)")
    private String controlName;

    /**
     *录入人
     */
    @Column(name = "create_id",
            columnDefinition = "NUMBER(19)")
    private Long userId;


    public Long getCompanyId() {
        return companyId;
    }

    public Car(){
        this.setIsActive(true);
        this.setCreateDateTime(new Date());
        this.setLastUpdateTime(new Date());
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
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


    public byte[] getRepairLogTxt() {
        return repairLogTxt;
    }

    public void setRepairLogTxt(byte[] repairLogTxt) {
        this.repairLogTxt = repairLogTxt;
    }

    public String getControlName() {
        return controlName;
    }

    public void setControlName(String controlName) {
        this.controlName = controlName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
