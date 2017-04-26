package com.sunesoft.lemon.syms.workflow.application;

import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.syms.workflow.application.criteria.FormListCriteria;
import com.sunesoft.lemon.syms.workflow.application.dtos.FormListDto;

/**
 * Created by zhouz on 2016/5/30.
 */
public interface FormListService {
    /**
     * 根据Id 获取表单信息
     * @param id
     * @return
     */
    public FormListDto getByKey(Long id);

    /**
     * 新增表单信息
     * @param dto
     * @return
     */
    public Long add(FormListDto dto);

    /**
     * 根据Kind 获取表单信息
     * @param formKind
     * @return
     */
    public FormListDto getFormListInfo(String formKind);

    /**
     * 更新表单信息
     * @param dto
     * @return
     */
    public CommonResult update(FormListDto dto);

    /**
     * 删除表单信息
     * @param id
     * @return
     */
    public CommonResult delete(Long id);

    /**
     * 分页查询
     * @param criteria
     * @return
     */
    public PagedResult<FormListDto> getFormListPaged(FormListCriteria criteria);

}
