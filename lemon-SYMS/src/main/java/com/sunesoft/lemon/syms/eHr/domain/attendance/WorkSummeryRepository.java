package com.sunesoft.lemon.syms.eHr.domain.attendance;

/**
 * Created by xiazl on 2016/6/28.
 */
public interface WorkSummeryRepository {
    /**
     * add or save
     * @param workSummery
     * @return
     */
    Long save(AttendanceSummery workSummery);

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
    AttendanceSummery get(Long inventorId);

}
