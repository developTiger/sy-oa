package com.sunesoft.lemon.deanery.car.domain;

import com.sunesoft.lemon.deanery.car.application.criteria.CarReportCriteria;
import com.sunesoft.lemon.deanery.car.application.criteria.DriverReportCriteria;

import java.util.List;
import java.util.Map;

/**
 * Created by xubo on 2016/6/17 0017.
 */
public interface DriverRepository {

    Long save(Driver driver);

    void delete(Long driverId);

    Driver get(Long driverId);

    List<Map<String,Object>> driverReport(DriverReportCriteria driverReportCriteria);

    List<Driver> getAlldriver();

    Driver get(Long driverId,Boolean isActive);
}
