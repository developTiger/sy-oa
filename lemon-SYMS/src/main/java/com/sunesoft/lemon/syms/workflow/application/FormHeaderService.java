package com.sunesoft.lemon.syms.workflow.application;

import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.syms.workflow.application.criteria.FormHeaderCriteria;
import com.sunesoft.lemon.syms.workflow.application.dtos.FormHeaderDto;
import com.sunesoft.lemon.syms.workflow.domain.FormHeader;

import java.util.List;

/**
 * Created by zhouz on 2016/6/25.
 */
public interface FormHeaderService {

    public FormHeaderDto getHeaderByFormNo(Long formNo);

    public PagedResult<FormHeaderDto> findFormPaged(FormHeaderCriteria criteria);

    PagedResult<FormHeaderDto> findFormPagedWithDept(FormHeaderCriteria criteria);

    public CommonResult AddFormHeader(FormHeaderDto formHeaderDto);

    public CommonResult UpdateFormHeader(FormHeaderDto formHeaderDto);


    public List<FormHeader>  getHeaderByParentFormNo(Long parentFormNo);

    public FormHeader getHeaderById(Long headerId);

    public List<FormHeaderDto> findFormHeader(FormHeaderCriteria criteria);
}
