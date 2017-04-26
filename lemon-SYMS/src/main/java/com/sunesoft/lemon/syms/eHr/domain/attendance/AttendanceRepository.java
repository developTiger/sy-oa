package com.sunesoft.lemon.syms.eHr.domain.attendance;

import java.util.Date;

/**
 * Created by MJ006 on 2016/6/29.
 */
public interface AttendanceRepository {

    /**
     * @param attendance
     *
     * @return
     */
    Long save(Attendance attendance);

    /**
     * @param empId
     */
    void delete(Long empId);

    /**
     * @param attId
     * @return
     */
    Attendance get(Long attId);

}
