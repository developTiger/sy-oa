package com.sunesoft.lemon.deanery.productionData;

import com.sunesoft.lemon.deanery.car.application.criteria.DriverReportCriteria;
import com.sunesoft.lemon.deanery.car.domain.Driver;

import java.util.List;
import java.util.Map;

/**
 * Created by zy on 2016/6/17 0017.
 */
public interface ProductionDateRepository {
    ProductionDate get(Long id);
}
