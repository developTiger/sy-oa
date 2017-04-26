package com.sunesoft.lemon.syms.eHr.domain.notice;

import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.syms.eHr.application.criteria.AdviceTypeCriteria;

import java.util.List;

/**
 * Created by xiazl on 2017/1/18.
 */
public interface AdviceTypeRepository {

    /**
     * add or save
     * @param advice
     * @return
     */
    Long save(AdviceType advice);

    /**
     * delete
     * @param id
     */
    void delete(Long id);

    /**
     * delete
     * @param ids
     */
    void delete(List<Long> ids);

    /**
     * get
     * @param id
     * @return
     */
    AdviceType get(Long id);

    /**
     * 用于判断是否已经存在，不同名
     * @param name
     * @return
     */
    List<AdviceType> getByName(String name);

    /**
     * 分页查询
     * @return
     */
    PagedResult<AdviceType> page(AdviceTypeCriteria criteria);


}
