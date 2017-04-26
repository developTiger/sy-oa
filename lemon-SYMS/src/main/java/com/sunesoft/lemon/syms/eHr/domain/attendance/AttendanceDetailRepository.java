package com.sunesoft.lemon.syms.eHr.domain.attendance;

/**
 * Created by xiazl on 2016/6/28.
 */
public interface AttendanceDetailRepository {

    /**
     * 增加或者保存
     * @param dateInfo
     * @return
     */
    Long save(AttendanceDetail dateInfo);

    /**
     * 删除
     * @param deptId
     */
    void delete(Long deptId);

    /**
     * 获取
     * @param inventorId
     * @return
     */
    AttendanceDetail get(Long inventorId);

}
