package com.sunesoft.lemon.syms.eHr.domain.attendance;

/**
 * Created by xiazl on 2016/6/30.
 */
public interface RuleDateRepository {
    /**
     * add or save
     * @param ruleDate
     * @return
     */
    Long save(RuleDate ruleDate);

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
    RuleDate get(Long inventorId);
}
