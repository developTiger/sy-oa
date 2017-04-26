package com.sunesoft.lemon.ay.partyGroupForms.application;

import com.sunesoft.lemon.ay.equipment.application.factory.DtoFactory;
import com.sunesoft.lemon.ay.partyGroup.application.criteria.CompetitionTitleTypeCriteria;
import com.sunesoft.lemon.ay.partyGroupForms.application.dtos.FormWorkProjectDto;
import com.sunesoft.lemon.ay.partyGroupForms.domain.CompetitionTitleTypeRepository;
import com.sunesoft.lemon.ay.partyGroupForms.domain.FormWorkProject;
import com.sunesoft.lemon.ay.partyGroupForms.domain.WorkProject_competitionTitleType;
import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateFinder;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.results.ResultFactory;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.syms.workflow.domain.enums.FormStatus;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by admin on 2017/2/13.
 */
@Service("competitionTitleTypeService")
public class CompetitionTitleTypeServiceImpl extends GenericHibernateFinder implements CompetitionTitleTypeService {

    @Autowired
    CompetitionTitleTypeRepository titleTypeRepository;

    @Override
    public CommonResult addCompetitionTitleType(WorkProject_competitionTitleType titleType) {
        titleTypeRepository.save(titleType);
        return ResultFactory.commonSuccess();
    }

    @Override
    public CommonResult updateCompetitionTitleType(WorkProject_competitionTitleType titleType) {
        WorkProject_competitionTitleType type=titleTypeRepository.getById(titleType.getId());
        type.setName(titleType.getName());
        type.setDescription(titleType.getDescription());
        type.setLastUpdateTime(new Date());
        titleTypeRepository.save(type);
        return ResultFactory.commonSuccess();
    }

    @Override
    public CommonResult deleteTypes(List<Long> ids) {
        for (int i = 0; i < ids.size(); i++) {
            titleTypeRepository.delete(ids.get(i));
        }
        return ResultFactory.commonSuccess();
    }

    @Override
    public PagedResult<WorkProject_competitionTitleType> pages(CompetitionTitleTypeCriteria titleTypeCriteria) {
        Criteria criteria = getSession().createCriteria(WorkProject_competitionTitleType.class);
//        criteria.add(Restrictions.eq("isActive",true));

        if (!StringUtils.isNullOrWhiteSpace(titleTypeCriteria.getName()))
            criteria.add(Restrictions.like("name","%"+titleTypeCriteria.getName()+"%"));

        int totalCount = ((Long)criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
        criteria.setProjection(null);
        criteria.setFirstResult((titleTypeCriteria.getPageNumber() - 1) * titleTypeCriteria.getPageSize()).setMaxResults(titleTypeCriteria.getPageSize());
        List<WorkProject_competitionTitleType> list = criteria.list();

        return new PagedResult<WorkProject_competitionTitleType>(list, titleTypeCriteria.getPageNumber(), titleTypeCriteria.getPageSize(), totalCount);
    }

    @Override
    public WorkProject_competitionTitleType getById(Long id) {
        WorkProject_competitionTitleType titleType= titleTypeRepository.getById(id);
        return titleType;
    }
}
