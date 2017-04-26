package com.sunesoft.lemon.deanery.car.domain;

import com.sunesoft.lemon.deanery.car.application.dtos.CommDriverDto;

import java.util.List;

/**
 * Created by xubo on 2016/6/20 0020.
 */
public interface CommDriverRepository {

    //新增时汽车ID是否重复
    public boolean checkHasCar(Long carId);

    //保存时司机ID是否重复
    public boolean checkHasDriver(Long driverId);

    //保存
    public Long save(CommonDriver cd);

    //软删除
    public boolean delete(Long[] ids);

    //删除
    public void delete(Long commDriverId);

    //获取单个记录
    public CommonDriver get(Long commDriverId);

    //获取所有常用司机信息
    public List<CommonDriver> getAll();

    //根据有效的车和有效的司机查出常用司机
    public List<CommonDriver> getAllByIsActive(List<Car> cars , List<Driver> drivers);
}
