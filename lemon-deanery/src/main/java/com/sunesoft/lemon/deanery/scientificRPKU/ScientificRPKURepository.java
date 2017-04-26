package com.sunesoft.lemon.deanery.scientificRPKU;

import com.sunesoft.lemon.deanery.productionData.ProductionDate;

import java.util.List;

/**
 * Created by zy on 2016/6/17 0017.
 */
public interface ScientificRPKURepository {
    ScientificRPKU get(Long id);
    Long save(ScientificRPKU scientificRPKU);

    List<ScientificRPKU> queryProjectApprove(String number);
}
