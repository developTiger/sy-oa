package com.sunesoft.lemon.deanery.productionData;

import com.sunesoft.lemon.fr.ddd.BaseEntity;
import com.sunesoft.lemon.fr.results.PagedResult;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by zy on 2016/10/7.
 */
public interface ProductionDateService  {

    PagedResult<ProductionDate> getByProductionDate(ProductionDateCria productionDateCria);
    List<ProductionDateDto1> downByOrderId(Boolean is);
    ProductionDate  getDateById(Long id);

}
