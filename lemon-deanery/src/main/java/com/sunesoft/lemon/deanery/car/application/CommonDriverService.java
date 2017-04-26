package com.sunesoft.lemon.deanery.car.application;


import com.sunesoft.lemon.deanery.car.application.criteria.CommDriverCriteria;
import com.sunesoft.lemon.deanery.car.application.dtos.CommDriverDto;
import com.sunesoft.lemon.deanery.car.domain.Car;
import com.sunesoft.lemon.deanery.car.domain.CommonDriver;
import com.sunesoft.lemon.deanery.car.domain.Driver;
import com.sunesoft.lemon.fr.results.PagedResult;

import java.util.List;

/**
 * Created by xubo on 2016/6/20 0020.
 */
public interface CommonDriverService {



    //查询车，以carId为准
    public PagedResult<CommDriverDto> getCommonDrivers(CommDriverCriteria cdc);

    //新增
    public Long addCommonDriver(CommDriverDto dto);

    //删除
    public boolean delCommonDriver(Long[] ids);

    //修改
    public boolean updCommonDriver(CommDriverDto dto);

    //新增和保存
    public Long addAndUpdate(CommDriverDto dto);

    //获取单条记录
    public CommDriverDto get(Long id);

    /**
     * 新增时判断是否存在的车辆，如果不存在，查出所有车辆
     * 不带参数是全部
     * @return
     */
    public List<Car> getCar();

    /**
     * 新增时判断是否存在的车辆，如果不存在，查出所有车辆
     * @param id 此参数是本身，包括自己
     * @return
     */
    public List<Car> getCar(Long id);


    /**
     * 新增时判断是否存在的司机，如果不存在，查出所有司机
     * 不带参数是全部
     * @return
     */
    public List<Driver> getDriver();

    /**
     * 新增时判断是否存在的司机，如果不存在，查出所有司机
     * @param id 此参数是本身，包括自己
     * @return
     */
    public List<Driver> getDriver(Long id);

    /**
     * 获取车辆所有对象
     * @return
     */
    public List<Car> getAllCar();

    /**
     * 获取司机所有对象
     * @return
     */
    public List<Driver> getAllDriver();


    /**
     * 提交表单时，拉出常用司机
     * @param carid
     * @return
     */
    public CommonDriver getCommonDriverByCarId(Long carid);

}
