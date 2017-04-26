package com.sunesoft.lemon.ay.partyGroupForms.application;

import com.sunesoft.lemon.ay.partyGroup.application.criteria.CompetitionTitleTypeCriteria;
import com.sunesoft.lemon.ay.partyGroupForms.domain.WorkProject_competitionTitleType;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;

import java.util.List;

/**
 * Created by admin on 2017/2/13.
 */
public interface CompetitionTitleTypeService {

    public CommonResult addCompetitionTitleType(WorkProject_competitionTitleType titleType);

    public CommonResult updateCompetitionTitleType(WorkProject_competitionTitleType titleType);

    public CommonResult deleteTypes(List<Long> ids);

    public PagedResult<WorkProject_competitionTitleType> pages(CompetitionTitleTypeCriteria titleTypeCriteria);

    public WorkProject_competitionTitleType getById(Long id);




}
