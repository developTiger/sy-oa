package com.sunesoft.lemon.syms.eHr.domain.attendance;

/**
 * Created by xiazl on 2016/6/30.
 */
public interface AttendanceRuleSetRepository {
    /**
     * add or save
     * @param ruleSet
     * @return
     */
    Long save(AttendanceRuleSet ruleSet);

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
    AttendanceRuleSet get(Long inventorId);
}
