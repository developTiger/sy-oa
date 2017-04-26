package com.sunesoft.lemon.deanery.car.application.dtos;

import com.sunesoft.lemon.deanery.car.domain.Car;
import com.sunesoft.lemon.deanery.car.domain.Driver;

import java.util.Date;

/**
 * Created by xubo on 2016/6/20 0020.
 */
public class CommDriverDto {

    //主键
    private Long id;

    //车的信息
    private Car car;

    //司机的信息
    private Driver driver;

    private String carNo;

    private String driverName;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }
}
