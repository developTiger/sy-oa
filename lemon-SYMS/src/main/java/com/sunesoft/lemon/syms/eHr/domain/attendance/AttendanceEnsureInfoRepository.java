package com.sunesoft.lemon.syms.eHr.domain.attendance;

/**
 * Created by xiazl on 2016/7/5.
 */
public interface AttendanceEnsureInfoRepository {
    /**
     * 增加或者保存
     * @param attendance
     * @return
     */
    Long save(AttendanceEnsureInfo attendance);

    /**
     * 删除
     * @param empId
     */
    void delete(Long empId);

    /**
     * 获取
     * @param attId
     * @return
     */
    AttendanceEnsureInfo get(Long attId);
}
