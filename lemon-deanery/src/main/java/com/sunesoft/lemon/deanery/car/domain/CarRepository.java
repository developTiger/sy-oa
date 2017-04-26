package com.sunesoft.lemon.deanery.car.domain;

import com.sunesoft.lemon.deanery.car.application.criteria.CarReportCriteria;

import java.util.List;
import java.util.Map;

import com.sunesoft.lemon.deanery.carWorkFlow.domain.CarApply;
import com.sunesoft.lemon.fr.results.PagedCriteria;

/**
 * Created by wangwj on 2016/6/16 0016.
 */
public interface CarRepository {
    Long save(Car car);

    void delete(Long carId);

    Car get(Long carId);

    List<Map<String,Object>> carReport(CarReportCriteria carReportCriteria);

    List<Map<String,Object>> carTypeList();

    List<Car> getAllcar();

    Car get(Long carId,Boolean isActive);


}
