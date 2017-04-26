package com.sunesoft.lemon.ay.partyGroupForms.application;

import com.sunesoft.lemon.ay.partyGroup.application.criteria.CompetitionTypeCriteria;
import com.sunesoft.lemon.ay.partyGroupForms.domain.CompetitionType;
import com.sunesoft.lemon.ay.partyGroupForms.domain.CompetitionTypeRepository;
import com.sunesoft.lemon.ay.partyGroupForms.domain.WorkProject_competitionTitleType;
import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateFinder;

import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.results.ResultFactory;
import com.sunesoft.lemon.fr.utils.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by admin on 2017/2/14.
 */
@Service("competitionTypeService")
public class CompetitionTypeServiceImpl extends GenericHibernateFinder implements CompetitionTypeService {

    @Autowired
    CompetitionTypeRepository typeRepository;


    @Override
    public CommonResult add(CompetitionType type) {
        typeRepository.save(type);
        return ResultFactory.commonSuccess();
    }

    @Override
    public CommonResult update(CompetitionType type) {
        CompetitionType competitionType=typeRepository.getById(type.getId());
        competitionType.setName(type.getName());
        competitionType.setDescription(type.getDescription());
        competitionType.setLastUpdateTime(new Date());
        typeRepository.save(competitionType);
        return ResultFactory.commonSuccess();
    }

    @Override
    public CommonResult delete(Long id) {
        typeRepository.delete(id);
        return ResultFactory.commonSuccess();
    }

    @Override
    public CompetitionType getById(Long id) {
        CompetitionType type=typeRepository.getById(id);
        return type;
    }

    @Override
    public PagedResult<CompetitionType> pages(CompetitionTypeCriteria typeCriteria) {
        Criteria criteria = getSession().createCriteria(CompetitionType.class);
//        criteria.add(Restrictions.eq("isActive",true));

        if (!StringUtils.isNullOrWhiteSpace(typeCriteria.getName()))
            criteria.add(Restrictions.like("name", "%" + typeCriteria.getName() + "%"));

        int totalCount = ((Long)criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
        criteria.setProjection(null);
        criteria.setFirstResult((typeCriteria.getPageNumber() - 1) * typeCriteria.getPageSize()).setMaxResults(typeCriteria.getPageSize());
        List<CompetitionType> list = criteria.list();

        return new PagedResult<CompetitionType>(list, typeCriteria.getPageNumber(), typeCriteria.getPageSize(), totalCount);
    }
}
