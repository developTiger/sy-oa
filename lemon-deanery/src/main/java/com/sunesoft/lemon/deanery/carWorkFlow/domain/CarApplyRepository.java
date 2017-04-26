package com.sunesoft.lemon.deanery.carWorkFlow.domain;

import org.hibernate.event.spi.SaveOrUpdateEvent;

/**
 * Created by wangwj on 2016/7/26 0026.
 */
public interface CarApplyRepository {
    Long save(CarApply carApply);

    void delete(Long id);

    CarApply get(Long id);

    CarApply get(Long formNo,Boolean isActive);

    void update(CarApply carApply);

    /**
     * 根据taskid和isactive查询carapply属性
     * @param orderid
     * @param isActive
     * @return
     */
    CarApply get(String orderid,Boolean isActive);

}
