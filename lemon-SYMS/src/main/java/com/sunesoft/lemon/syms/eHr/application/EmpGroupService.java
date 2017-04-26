package com.sunesoft.lemon.syms.eHr.application;

import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.syms.eHr.application.criteria.GroupCriteria;
import com.sunesoft.lemon.syms.eHr.application.dtos.EmpGroupDto;

import java.util.List;

/**
 * Created by xiazl on 2016/6/22.
 */
public interface EmpGroupService {

    /**
     * 增加员工组
     * @param dto
     * @return
     */
    public CommonResult addGroup(EmpGroupDto dto);

    /**
     * 删除员工组
     * @param ids
     * @return
     */
    public CommonResult deleteGroup(List<Long> ids);


    /**
     * 设置员工组状态
     * @param ids
     * @param status
     * @return
     */
    public CommonResult setStatus(List<Long> ids,Integer status);

    /**
     * 获取 EmpGroupDto
     * @param ids
     * @return
     */
    public List<EmpGroupDto> getByGroupsIds(List<Long> ids);

    /**
     * 获取 EmpGroupDto
     * @return
     */
    public List<EmpGroupDto> getByGroupsIds();

    /**
     * 修改员工组
     * @param dto
     * @return
     */
    public CommonResult updateGroup(EmpGroupDto dto);

    /**
     * 获取所有员工组根据用户名，这里考虑到用户名不重复
     *
     * @return
     * @ param loginName
     */
    public List<EmpGroupDto> getGroupsByName(String name);


    /**
     * 查询实例
     *
     * @param criteria 查询条件
     * @return
     */
    public PagedResult<EmpGroupDto> findGroupsPaged(GroupCriteria criteria);

    public List<EmpGroupDto> getAll();





}
