package com.sunesoft.lemon.ay.partyGroup.application;

import com.sunesoft.lemon.ay.equipment.application.factory.DtoFactory;
import com.sunesoft.lemon.ay.partyGroup.application.criteria.InnovationAchievementCriteria;
import com.sunesoft.lemon.ay.partyGroup.application.dtos.InnovationAchievementDto;
import com.sunesoft.lemon.ay.partyGroup.application.dtos.WorkAchievementsDto;
import com.sunesoft.lemon.ay.partyGroup.domain.InnovationAchievement;
import com.sunesoft.lemon.ay.partyGroup.domain.InnovationAchievementRepository;
import com.sunesoft.lemon.ay.partyGroup.domain.WorkAchievements;
import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateFinder;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.results.ResultFactory;
import com.sunesoft.lemon.fr.utils.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2016/9/3.
 */
@Service("innovationAchievementService")
public class InnovationAchievementServiceImpl extends GenericHibernateFinder implements InnovationAchievementService {

    @Autowired
    InnovationAchievementRepository innovationAchievementRepository;

    @Override
    public CommonResult addOrUpdate(InnovationAchievementDto innovationAchievementDto) {
        InnovationAchievement innovationAchievement = DtoFactory.convert(innovationAchievementDto,new InnovationAchievement());
        innovationAchievementRepository.save(innovationAchievement);
        return ResultFactory.commonSuccess();
    }

    @Override
    public InnovationAchievementDto getById(Long id) {
        InnovationAchievement innovationAchievement = null;
        if (id != null){
            innovationAchievement=innovationAchievementRepository.getById(id);
        }
        InnovationAchievementDto innovationAchievementDto = DtoFactory.convert(innovationAchievement,new InnovationAchievementDto());
        return innovationAchievementDto;
    }

    @Override
    public List<InnovationAchievementDto> getAll() {
        List<InnovationAchievement> list=innovationAchievementRepository.getAll();
        List<InnovationAchievementDto> innovationAchievementDtos = new ArrayList<>();
        for (InnovationAchievement achievement:list){
            innovationAchievementDtos.add(DtoFactory.convert(achievement,new InnovationAchievementDto()));
        }
        return innovationAchievementDtos;
    }

    @Override
    public PagedResult<InnovationAchievementDto> getPagesInnovationAchiDto(InnovationAchievementCriteria innovationAchievementCriteria) {
        Criteria criteria = getSession().createCriteria(InnovationAchievement.class);
        criteria.add(Restrictions.eq("isActive", true));

        if (!StringUtils.isNullOrWhiteSpace(innovationAchievementCriteria.getProjectName()))
            criteria.add(Restrictions.like("projectName","%"+innovationAchievementCriteria.getProjectName()+"%"));
        if (!StringUtils.isNullOrWhiteSpace(innovationAchievementCriteria.getCreatorName()))
            criteria.add(Restrictions.like("creatorName","%"+innovationAchievementCriteria.getCreatorName()+"%"));
        if (innovationAchievementCriteria.getPrizeLeval() != null)
            criteria.add(Restrictions.eq("prizeLeval", innovationAchievementCriteria.getPrizeLeval()));
        if (!StringUtils.isNullOrWhiteSpace(innovationAchievementCriteria.getDeptName()))
            criteria.add(Restrictions.eq("applyUnit", innovationAchievementCriteria.getDeptName()));
        if (innovationAchievementCriteria.getBeginTime() != null)
            criteria.add(Restrictions.ge("achiCreateTime",innovationAchievementCriteria.getBeginTime()));
        if (innovationAchievementCriteria.getEndTime() != null)
            criteria.add(Restrictions.le("achiCreateTime",innovationAchievementCriteria.getEndTime()));

        int totalCount = ((Long)criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
        criteria.setProjection(null);
        criteria.setFirstResult((innovationAchievementCriteria.getPageNumber() - 1) * innovationAchievementCriteria.getPageSize()).setMaxResults(innovationAchievementCriteria.getPageSize());
        criteria.addOrder(Order.desc("createDateTime"));
        List<InnovationAchievement> beans = criteria.list();
        List<InnovationAchievementDto> list = new ArrayList<>();
        for (InnovationAchievement wp:beans){
            list.add(DtoFactory.convert(wp, new InnovationAchievementDto()));
        }
        return new PagedResult<InnovationAchievementDto>(list, innovationAchievementCriteria.getPageNumber(), innovationAchievementCriteria.getPageSize(), totalCount);
    }
}
