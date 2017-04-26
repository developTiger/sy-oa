package com.sunesoft.lemon.deanery.car.application.criteria;

import com.sunesoft.lemon.fr.results.PagedCriteria;

/**
 * Created by xubo on 2016/6/20 0020.
 */
public class CommDriverCriteria extends PagedCriteria {

    private Long carId;

    private Long driverId;

    private Boolean isActive;

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

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
}
