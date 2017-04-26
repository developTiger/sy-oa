package com.sunesoft.lemon.syms.eHr.domain.dept;

import java.util.HashMap;
import java.util.List;

/**
 * Created by xiazl on 2016/5/12.
 */
public interface DeptmentRepository {

    /**
     * add or save
     * @param dept
     * @return
     */
    Long save(Deptment dept);

    /**
     * delete
     * @param deptId
     */
    void delete(Long deptId);

    /**
     * get
     * @param inventorId
     * @return
     */
    Deptment get(Long inventorId);

    /**
     * get list
     * @param ids
     * @return
     */
    List<Deptment> getByIds(List<Long> ids);

    /**
     * 获取所有部门ID分管领导ID键值集合
     * @return
     */
    HashMap<Long,Long> getDeptChargeLeaderIds();

    List<Deptment> getAll();
}
