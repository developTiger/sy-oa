package com.sunesoft.lemon.syms.parameter.application;

import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.syms.parameter.application.criteria.ParameterCriteria;
import com.sunesoft.lemon.syms.parameter.application.criteria.ParameterTypeCriteria;
import com.sunesoft.lemon.syms.parameter.application.dtos.ParameterTypeDto;
import com.sunesoft.lemon.syms.parameter.domain.Parameter;
import com.sunesoft.lemon.syms.parameter.domain.ParameterType;
import com.sunesoft.lemon.syms.uAuth.application.criteria.UserCriteria;
import com.sunesoft.lemon.syms.uAuth.application.dtos.UserDto;

import java.util.List;

/**
 * Created by zy on 2016/6/2.
 */
public interface ParameterTypeService  {

    /**
     * 增加数据
     * @param dto
     * @return
     */
    public Long addParameterType(ParameterTypeDto dto);

    /**
     * 根据id删除数据
     * @param parameterTypeId
     * @return
     */
    public boolean deleteParameterType(Long[] parameterTypeId);

    /**
     * 修改数据
     * @param dto
     * @return
     */
    public Long updateParameterType(ParameterTypeDto dto);

    /**
     * 获取所用数据
     * @return
     */
    public List<ParameterTypeDto> getAllparametertype();

    /**
     * 根据id获取数据
     * @param id
     * @return
     */
    public ParameterTypeDto getById(Long id);

    /**
     * 分页查询
     * @param criteria 查询条件
     * @return
     */
    public PagedResult<ParameterTypeDto> FindParam(ParameterTypeCriteria criteria);

    /**
     * 根据name'获取数据
     * @param parameterTypeName
     * @return
     */
    List<ParameterTypeDto> getAllParameterType(String parameterTypeName);

}
