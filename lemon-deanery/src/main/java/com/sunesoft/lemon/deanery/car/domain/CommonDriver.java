package com.sunesoft.lemon.deanery.car.domain;

import com.sunesoft.lemon.fr.ddd.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by xubo on 2016/6/19 0019.
 */
@Entity
@Table(name = "syy_oa_common_driver")
public class CommonDriver extends BaseEntity{

    //车辆ID
    @Column(name = "carid",columnDefinition = "NUMBER(19)")
    private Long carId;

    //司机ID
    @Column(name = "driverid",columnDefinition = "NUMBER(19)")
    private Long driverId;

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public Long getDriverId() {
        return driverId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }

}
