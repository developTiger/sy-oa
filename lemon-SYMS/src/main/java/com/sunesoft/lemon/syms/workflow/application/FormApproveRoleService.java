package com.sunesoft.lemon.syms.workflow.application;
import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateFinder;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.syms.workflow.application.criteria.FormApproveRoleCriteria;
import com.sunesoft.lemon.syms.workflow.application.criteria.FormListCriteria;
import com.sunesoft.lemon.syms.workflow.application.dtos.FormAppRoleDto;

import java.util.List;

/**
 * Created by MJ006 on 2016/6/20.
 */
public interface FormApproveRoleService {


    public FormAppRoleDto getByKey(Long id);


    public CommonResult add(FormAppRoleDto dto);


    public CommonResult update(FormAppRoleDto dto);


    public CommonResult delete(Long id);


    public CommonResult addRoleEmp(Long roleId,Long empId);

    public CommonResult addRoleEmp(Long roleId,List<Long> empIds);


    public CommonResult removeRoleEmp(Long roleId,Long empId);


    public PagedResult<FormAppRoleDto> getFormListPaged(FormApproveRoleCriteria criteria);

    public List<FormAppRoleDto> getAll();


    public List<FormAppRoleDto> getTreatiseRoleID(String roleName);
}
