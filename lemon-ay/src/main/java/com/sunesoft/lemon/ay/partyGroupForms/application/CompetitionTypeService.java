package com.sunesoft.lemon.ay.partyGroupForms.application;

import com.sunesoft.lemon.ay.partyGroup.application.criteria.CompetitionTypeCriteria;

import com.sunesoft.lemon.ay.partyGroupForms.domain.CompetitionType;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;

/**
 * Created by admin on 2017/2/14.
 */
public interface CompetitionTypeService {

    public CommonResult add(CompetitionType type);

    public CommonResult update(CompetitionType type);

    public CommonResult delete(Long id);

    public CompetitionType getById(Long id);

    public PagedResult<CompetitionType> pages(CompetitionTypeCriteria typeCriteria);

}
