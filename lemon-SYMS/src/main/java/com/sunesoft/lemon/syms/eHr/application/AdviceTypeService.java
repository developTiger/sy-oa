package com.sunesoft.lemon.syms.eHr.application;

import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.syms.eHr.application.criteria.AdviceTypeCriteria;
import com.sunesoft.lemon.syms.eHr.application.dtos.RoleTypeDto;

import java.util.List;


/**
 * Created by xiazl on 2017/1/18.
 */
public interface AdviceTypeService {


    /**
     * 新增通知类型
     * @param dto 发布信息Dto
     * @return
     */
    CommonResult addAdviceType(RoleTypeDto dto);

    /**
     * 修改通知类型
     * @param dto 发布信息
     * @return
     */
    CommonResult updateAdvicetype(RoleTypeDto dto);

    /**
     * 删除
     * @param id
     * @return
     */
    CommonResult delete(Long id);
    /**
     * 设置状态
     * @param id
     * @param b
     * @return
     */
    CommonResult setStation(Long id,boolean b);


    /**
     * 删除
     * @param ids
     * @return
     */
    CommonResult delete(List<Long> ids);
    /**
     * 查询分页信息
     * @param criteria
     * @return
     */
    PagedResult<RoleTypeDto> paged(AdviceTypeCriteria criteria);

    /**
     * 根据ID 获取
     * @param id
     * @return
     */
    RoleTypeDto getById(Long id);

    /**
     * 根据名称查询查询数据(主要用于查询是否重名，这里设计不让它重名)     *
     * @return
     */
    List<RoleTypeDto> getByName(String name);

    /**
     *
     * @return
     */
    List<RoleTypeDto> getCanUse();

}
