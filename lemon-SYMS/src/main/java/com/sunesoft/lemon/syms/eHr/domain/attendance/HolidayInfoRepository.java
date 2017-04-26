package com.sunesoft.lemon.syms.eHr.domain.attendance;

/**
 * Created by xiazl on 2016/6/30.
 */
public interface HolidayInfoRepository {
    /**
     * add or save
     * @param info
     * @return
     */
    Long save(HolidayInfo info);

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
    HolidayInfo get(Long inventorId);

}
