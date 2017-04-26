package com.sunesoft.lemon.ay.dateCheck.domain;

import com.sunesoft.lemon.ay.dateCheck.application.Criteria.DateCheckCriteria;
import com.sunesoft.lemon.fr.results.PagedResult;

import java.util.List;

/**
 * 期间核查记录仓储
 * Created by xiazl on 2016/10/21.
 */
public interface DateCheckRepository {
    /**
     * 增加 修改
     * @param dateCheck
     * @return
     */
    public Long save(DateCheck dateCheck);

    /**
     * 删除，这里采用不可见的方法
     * @param id
     */
    public void delete(Long id);

    /**
     * 获取指定的
     * @param id
     * @return
     */
    public DateCheck get(Long id);

    /**
     * 根据设备名称查询
     * @param equipName
     * @return
     */
    public List<DateCheck> getByName(String equipName);

    /**
     * 分页查询
     * @param checkCriteria
     * @return
     */
    public PagedResult<DateCheck> page(DateCheckCriteria checkCriteria);
}
