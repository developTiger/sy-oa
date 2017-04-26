package com.sunesoft.lemon.ay.partyGroup.application;

import com.sunesoft.lemon.ay.partyGroup.application.criteria.PropagandaManagementCriteria;
import com.sunesoft.lemon.ay.partyGroup.application.dtos.PropagandaManagementDto;
import com.sunesoft.lemon.ay.partyGroupForms.application.dtos.FormPropagandaManagementDto;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;

import java.util.List;

/**
 * Created by admin on 2016/9/5.
 */
public interface PropagandaManagementService {

    public CommonResult addOrUpdate(PropagandaManagementDto propagandaManagementDto);

    public PropagandaManagementDto getById(Long id);

    public List<PropagandaManagementDto> getAll();

    public PagedResult<FormPropagandaManagementDto> getPagesByPropaManageDto(PropagandaManagementCriteria propagandaManagementCriteria);



}
