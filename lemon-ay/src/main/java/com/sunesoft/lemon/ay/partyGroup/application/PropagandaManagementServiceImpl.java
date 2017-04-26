package com.sunesoft.lemon.ay.partyGroup.application;

import com.sunesoft.lemon.ay.equipment.application.factory.DtoFactory;
import com.sunesoft.lemon.ay.partyGroup.application.criteria.PropagandaManagementCriteria;
import com.sunesoft.lemon.ay.partyGroup.application.dtos.PropagandaManagementDto;
import com.sunesoft.lemon.ay.partyGroup.application.dtos.WorkAchievementsDto;
import com.sunesoft.lemon.ay.partyGroup.domain.PropagandaManagement;
import com.sunesoft.lemon.ay.partyGroup.domain.PropagandaManagementRepository;
import com.sunesoft.lemon.ay.partyGroup.domain.WorkAchievements;
import com.sunesoft.lemon.ay.partyGroupForms.application.dtos.FormPropagandaManagementDto;
import com.sunesoft.lemon.ay.partyGroupForms.domain.FormPropagandaManagement;
import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateFinder;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.results.ResultFactory;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.syms.workflow.domain.enums.FormStatus;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2016/9/5.
 */
@Service("propagandaManagementService")
public class PropagandaManagementServiceImpl extends GenericHibernateFinder implements PropagandaManagementService {

    @Autowired
    PropagandaManagementRepository propagandaManagementRepository;

    @Override
    public CommonResult addOrUpdate(PropagandaManagementDto propagandaManagementDto) {
        PropagandaManagement propagandaManagement = DtoFactory.convert(propagandaManagementDto,new PropagandaManagement());
        propagandaManagementRepository.save(propagandaManagement);
        return ResultFactory.commonSuccess();
    }

    @Override
    public PropagandaManagementDto getById(Long id) {
        PropagandaManagement propagandaManagement=null;
        if (id != null){
            propagandaManagement = propagandaManagementRepository.getById(id);
        }
        PropagandaManagementDto propagandaManagementDto = DtoFactory.convert(propagandaManagement,new PropagandaManagementDto());
        return propagandaManagementDto;
    }

    @Override
    public List<PropagandaManagementDto> getAll() {
        List<PropagandaManagement> propagandaManagements=propagandaManagementRepository.getAll();
        List<PropagandaManagementDto> propagandaManagementDtos = new ArrayList<>();
        for (PropagandaManagement pm:propagandaManagements){
            propagandaManagementDtos.add(DtoFactory.convert(pm,new PropagandaManagementDto()));
        }
        return propagandaManagementDtos;
    }

    @Override
    public PagedResult<FormPropagandaManagementDto> getPagesByPropaManageDto(PropagandaManagementCriteria propagandaManagementCriteria) {
        Criteria criteria = getSession().createCriteria(FormPropagandaManagement.class);
        criteria.add(Restrictions.eq("isActive", true));

        if (propagandaManagementCriteria.getFormStatus() != null)
            criteria.add(Restrictions.eq("formStatus",propagandaManagementCriteria.getFormStatus()));
        if (!StringUtils.isNullOrWhiteSpace(propagandaManagementCriteria.getUnit()))
            criteria.add(Restrictions.eq("unit",propagandaManagementCriteria.getUnit()));
        if (!StringUtils.isNullOrWhiteSpace(propagandaManagementCriteria.getNewsType()))
            criteria.add(Restrictions.eq("newsType",propagandaManagementCriteria.getNewsType()));
        if (!StringUtils.isNullOrWhiteSpace(propagandaManagementCriteria.getTitle()))
            criteria.add(Restrictions.like("title","%"+propagandaManagementCriteria.getTitle()+"%"));
        if (propagandaManagementCriteria.getBeginTime() != null)
            criteria.add(Restrictions.ge("time",propagandaManagementCriteria.getBeginTime()));
        if (propagandaManagementCriteria.getEndTime() != null)
            criteria.add(Restrictions.le("time",propagandaManagementCriteria.getEndTime()));
        if (!StringUtils.isNullOrWhiteSpace(propagandaManagementCriteria.getAuthor()))
            criteria.add(Restrictions.like("author","%"+propagandaManagementCriteria.getAuthor()+"%"));

        int totalCount = ((Long)criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
        criteria.setProjection(null);
        criteria.setFirstResult((propagandaManagementCriteria.getPageNumber() - 1) * propagandaManagementCriteria.getPageSize()).setMaxResults(propagandaManagementCriteria.getPageSize());
        List<FormPropagandaManagement> beans = criteria.list();
        List<FormPropagandaManagementDto> list = new ArrayList<>();
        for (FormPropagandaManagement wp:beans){
            list.add(DtoFactory.convert(wp, new FormPropagandaManagementDto()));
        }
        return new PagedResult<FormPropagandaManagementDto>(list, propagandaManagementCriteria.getPageNumber(), propagandaManagementCriteria.getPageSize(), totalCount);
    }


}
