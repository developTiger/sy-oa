package com.sunesoft.lemon.syms.hrForms.domain;

import com.sunesoft.lemon.fr.results.PagedCriteria;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.syms.hrForms.application.criteria.LeaveFormCriteria;

/**
 * Created by zhouz on 2016/7/1.
 */
public interface FormLeaveRepository {
    /**
     * add or save
     * @param formLeave
     * @return
     */
    Long save(FormLeave formLeave);

    /**
     * delete
     * @param FormLeaveId
     */
    void delete(Long FormLeaveId);

    /**
     * get
     * @param FormLeaveId
     * @return
     */
    FormLeave get(Long FormLeaveId);

    /**
     * 请假统计分页查询
     * @param criteria
     * @return
     */
    public PagedResult<FormLeave> page(LeaveFormCriteria criteria);

}
