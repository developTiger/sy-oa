package com.sunesoft.lemon.syms.eHr.domain.attend;


import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.syms.eHr.application.criteria.AttendCriteria;

import java.util.Date;

/**
 * Created by xzl on 2016/6/29.
 */
public interface AttendRepository {

    /**
     * @param attend
     *
     * @return
     */
    Long save(Attend attend);

    /**
     * @param empId
     */
    void delete(Long empId);

    /**
     * @param id
     * @return
     */
    Attend get(Long id);

    /**
     * 改天该员工的考勤
     * @param empId
     * @param time
     * @return
     */
    Attend get(Long empId,Date time);

    /**
     * 分页查询
     * @param criteria
     * @return
     */
    PagedResult<Attend> page(AttendCriteria criteria);

}
