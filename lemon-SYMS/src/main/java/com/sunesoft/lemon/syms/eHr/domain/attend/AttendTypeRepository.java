package com.sunesoft.lemon.syms.eHr.domain.attend;


import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.syms.eHr.application.criteria.AdviceTypeCriteria;
import com.sunesoft.lemon.syms.eHr.application.criteria.AttendCriteria;
import com.sunesoft.lemon.syms.eHr.application.criteria.AttendTypeCriteria;

import java.util.List;

/**
 * Created by xzl on 2016/6/29.
 */
public interface AttendTypeRepository {

    /**
     * @param type
     *
     * @return
     */
    Long save(AttendType type);

    /**
     * @param id
     */
    void delete(Long id);

    /**
     * @param id
     * @return
     */
    AttendType get(Long id);

    AttendType getByCord(String cord);

    /**
     * 分页查询
     * @param name
     * @return
     */
    List<AttendType> getList(String name);

    /**
     * 获取所有
     * @return
     */
    PagedResult<AttendType> page(AttendTypeCriteria criteria);

}
