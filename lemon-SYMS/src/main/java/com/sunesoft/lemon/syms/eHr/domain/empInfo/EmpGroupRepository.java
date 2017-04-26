package com.sunesoft.lemon.syms.eHr.domain.empInfo;

import java.util.List;

/**
 * Created by zhouz on 2016/5/12.
 */
public interface EmpGroupRepository {

    /**
     * add or save
     * @param group
     * @return
     */
    Long save(EmpGroup group);

    /**
     * delete
     * @param groupId
     */
    void delete(Long groupId);

    /**
     * get
     * @param inventorId
     * @return
     */
    EmpGroup get(Long inventorId);

    /**
     * get list by ids
     * @param ids
     * @return
     */
    List<EmpGroup> getByIds(List<Long> ids);
}
