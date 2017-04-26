package com.sunesoft.lemon.syms.eHr.application;

import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.syms.eHr.application.criteria.DeptmentCriteria;
import com.sunesoft.lemon.syms.eHr.application.dtos.DeptmentDto;

import java.util.List;

/**
 * Created by xiazl on 2016/6/22.
 */
public interface DeptmentService {

    /**
     * 增加部门
     * @param dto
     * @return
     */
    public CommonResult addDept(DeptmentDto dto);

    /**
     * 删除部门
     * @param ids
     * @return
     */
    public CommonResult deleteDept(List<Long> ids);

    /**
     * 设置部门状态
     * @param ids
     * @param status
     * @return
     */
    public CommonResult setStatus(List<Long> ids,Integer status);
    /**
     * 获取 DeptmentDtos
     * @param ids
     * @return
     */
    public List<DeptmentDto> getByDeptsIds(List<Long> ids);

    /**
     * 获取 DeptmentDtos
     * @return
     */
    public List<DeptmentDto> getByDeptsIds();


    /**
     * 获取 DeptmentDtos
     * @param id
     * @return
     */
    public DeptmentDto getByDeptId(Long id);

    /**
     * 修改部门
     * @param dto
     * @return
     */
    public CommonResult updateDept(DeptmentDto dto);

    /**
     * 获取所有部门根据用户名，这里考虑到用户名不重复
     *
     * @return
     * @ param loginName
     */
    public List<DeptmentDto> getDeptsByName(String name);

    /**
     * 获取所有可查询的部门
     * @return
     */
    public List<DeptmentDto>getAllDept();

    /**
     * 获取所有部门的部分字段
     * @return
     */
    public List<DeptmentDto>getAllSimpleDept();

    /**
     * 查询实例
     *
     * @param criteria 查询条件
     * @return
     */
    public PagedResult<DeptmentDto> findDeptsPaged(DeptmentCriteria criteria);

}
