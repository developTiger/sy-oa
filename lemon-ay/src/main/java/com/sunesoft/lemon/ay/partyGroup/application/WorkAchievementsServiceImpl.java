package com.sunesoft.lemon.ay.partyGroup.application;

import com.sunesoft.lemon.ay.equipment.application.factory.DtoFactory;
import com.sunesoft.lemon.ay.partyGroup.application.criteria.WorkAchievementsCriteria;
import com.sunesoft.lemon.ay.partyGroup.application.dtos.WorkAchievementsDto;
import com.sunesoft.lemon.ay.partyGroup.application.dtos.WorkProjectDto;
import com.sunesoft.lemon.ay.partyGroup.domain.WorkAchievements;
import com.sunesoft.lemon.ay.partyGroup.domain.WorkAchievementsRepository;
import com.sunesoft.lemon.ay.partyGroup.domain.WorkProject;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2016/9/2.
 */
@Service("workAchievementsService")
public class WorkAchievementsServiceImpl extends GenericHibernateFinder implements WorkAchievementsService {

    @Autowired
    WorkAchievementsRepository workAchievementsRepository;


    @Override
    public CommonResult addOrUpdateWorkAchievement(WorkAchievementsDto workAchievementsDto) {
        WorkAchievements workAchievements = DtoFactory.convert(workAchievementsDto,new WorkAchievements());
        workAchievementsRepository.save(workAchievements);
        return ResultFactory.commonSuccess();
    }

    @Override
    public WorkAchievementsDto getById(Long id) {
        WorkAchievements workAchievements=null;
        if (id != null){
            workAchievements = workAchievementsRepository.getById(id);
        }
        WorkAchievementsDto workAchievementsDto = DtoFactory.convert(workAchievements,new WorkAchievementsDto());
        return workAchievementsDto;
    }

    @Override
    public List<WorkAchievementsDto> getAll() {
        List<WorkAchievementsDto> workAchievementsDtos = new ArrayList<>();
        List<WorkAchievements> workAchievementses = workAchievementsRepository.getAll();
        for (WorkAchievements work:workAchievementses){
            workAchievementsDtos.add(DtoFactory.convert(work,new WorkAchievementsDto()));
        }
        return workAchievementsDtos;
    }

    @Override
    public PagedResult<WorkAchievementsDto> getPagesWorkAchievementsDto(WorkAchievementsCriteria workAchievementsCriteria) {
        Criteria criteria = getSession().createCriteria(WorkAchievements.class);
        criteria.add(Restrictions.eq("isActive",true));
        int totalCount = ((Long)criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
        criteria.setProjection(null);
        criteria.setFirstResult((workAchievementsCriteria.getPageNumber() - 1) * workAchievementsCriteria.getPageSize()).setMaxResults(workAchievementsCriteria.getPageSize());
        List<WorkAchievements> beans = criteria.list();
        List<WorkAchievementsDto> list = new ArrayList<>();
        for (WorkAchievements wp:beans){
            list.add(DtoFactory.convert(wp, new WorkAchievementsDto()));
        }
        return new PagedResult<WorkAchievementsDto>(list, workAchievementsCriteria.getPageNumber(), workAchievementsCriteria.getPageSize(), totalCount);
    }

    @Override
    public CommonResult updateWorkAchievement(WorkAchievementsDto dto) {
        WorkAchievements workAchievements=workAchievementsRepository.getById(dto.getId());
        workAchievements.setJoinPeople(dto.getJoinPeople());
        workAchievements.setSituationExplain(dto.getSituationExplain());
        workAchievements.setProjectBasis(dto.getProjectBasis());
        workAchievements.setControlAction(dto.getControlAction());
        workAchievements.setScheduleArrange(dto.getScheduleArrange());
        workAchievements.setExpectResult(dto.getExpectResult());
        workAchievementsRepository.save(workAchievements);
        return ResultFactory.commonSuccess();
    }
}
