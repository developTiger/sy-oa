package com.sunesoft.lemon.ay.dateCheck.application;

import com.sunesoft.lemon.ay.dateCheck.application.Criteria.DateCheckCriteria;
import com.sunesoft.lemon.ay.dateCheck.application.dtos.DateCheckDto;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;

import java.util.List;

/**
 * 期间核查记录 接口
 * Created by xiazl on 2016/10/21.
 */
public interface DateCheckService {

    /**
     *增加
     * @param dto
     * @return
     */
    public CommonResult add(DateCheckDto dto);

    /**
     * 修改
     * @param dto
     * @return
     */
    public CommonResult update(DateCheckDto dto);

    /**
     * 获取指定的
     * @param id
     * @return
     */
    public DateCheckDto getById(Long id);

    /**
     * 多个删除，采取不可见的方式
     * @param delIds
     * @return
     */
    public CommonResult deletes(List<Long> delIds);

    /**
     * 单个删除
     * @param id
     * @return
     */
    public CommonResult delete(Long id);

    /**
     * 根据设备名称查询
     * @param equpname
     * @return
     */
    public List<DateCheckDto> getByName(String equpname);

    /**
     * 分页查询
     * @param checkCriteria
     * @return
     */
    public PagedResult<DateCheckDto> paged(DateCheckCriteria checkCriteria);



}
