package com.sunesoft.lemon.ay.partyGroup.application;

import com.sunesoft.lemon.ay.partyGroup.application.criteria.WorkProjectCriteria;
import com.sunesoft.lemon.ay.partyGroup.application.dtos.WorkAchievementsDto;
import com.sunesoft.lemon.ay.partyGroup.application.dtos.WorkProjectDto;
import com.sunesoft.lemon.ay.partyGroup.domain.WorkAchievements;
import com.sunesoft.lemon.ay.partyGroup.domain.WorkAchievementsRepository;
import com.sunesoft.lemon.ay.partyGroup.domain.WorkProject;
import com.sunesoft.lemon.ay.partyGroup.domain.WorkProjectRepository;
import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateFinder;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.results.ResultFactory;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.syms.eHr.application.factory.DtoFactory;
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
@Service("workProjectService")
public class WorkProjectServiceImpl extends GenericHibernateFinder implements WorkProjectService {

    @Autowired
    WorkProjectRepository workProjectRepository;

    @Autowired
    WorkAchievementsRepository workAchievementsRepository;

    @Override
    public CommonResult addOrUpdateWorkPro(WorkProjectDto workProjectDto) {
        WorkProject workProject = DtoFactory.convert(workProjectDto,new WorkProject());
        workProjectRepository.save(workProject);
        return ResultFactory.commonSuccess();
    }

    @Override
    public WorkProjectDto getById(Long id) {
        WorkProject workProject = null;
        if (id != null){
          workProject = workProjectRepository.getById(id);
        }
        WorkProjectDto workProjectDto = DtoFactory.convert(workProject,new WorkProjectDto());
        return workProjectDto;
    }

    @Override
    public List<WorkProjectDto> getAll() {
        List<WorkProject> workProjects=workProjectRepository.getAll();
        List<WorkProjectDto> workProjectDtos = new ArrayList<>();
        for (WorkProject w:workProjects){
            workProjectDtos.add(DtoFactory.convert(w,new WorkProjectDto()));
        }
        return workProjectDtos;
    }

    @Override
    public List<WorkProjectDto> getAllByWorkAchievement() {
        List<WorkProject> workProjects=workProjectRepository.getAll();

        List<WorkAchievements> workAchievementses=workAchievementsRepository.getAll();
        for (int i = 0; i < workAchievementses.size(); i++) {
            for (int j = 0; j <   workProjects.size(); j++) {
                    if (workAchievementses.get(i).getProjectName().equals(workProjects.get(j).getProjectName())) {
                        workProjects.remove(j);
                        j--;
                        continue;
                    }
                }

        }
        List<WorkProjectDto> workProjectDtos = new ArrayList<>(); //项目
        for (WorkProject w:workProjects){
            workProjectDtos.add(DtoFactory.convert(w,new WorkProjectDto()));
        }


        return workProjectDtos;
    }

    @Override
    public PagedResult<WorkProjectDto> getPagesWorkProjectDto(WorkProjectCriteria workProjectCriteria) {
        Criteria criteria = getSession().createCriteria(WorkProject.class);
        criteria.add(Restrictions.eq("isActive",true));

        if (!StringUtils.isNullOrWhiteSpace(workProjectCriteria.getProjectName()))
            criteria.add(Restrictions.like("projectName","%"+workProjectCriteria.getProjectName()+"%"));
        if (!StringUtils.isNullOrWhiteSpace(workProjectCriteria.getLeader()))
            criteria.add(Restrictions.like("leader","%"+workProjectCriteria.getLeader()+"%"));
        if (!StringUtils.isNullOrWhiteSpace(workProjectCriteria.getCompetitionType()))
            criteria.add(Restrictions.like("competitionType", "%" + workProjectCriteria.getCompetitionType() + "%"));
        if (!StringUtils.isNullOrWhiteSpace(workProjectCriteria.getDeptName()))
            criteria.add(Restrictions.eq("competitionUnit",  workProjectCriteria.getDeptName()));
        if (workProjectCriteria.getBeginTime() != null)
            criteria.add(Restrictions.ge("cpmpetitionTime",workProjectCriteria.getBeginTime()));
        if (workProjectCriteria.getEndTime() != null)
            criteria.add(Restrictions.le("cpmpetitionTime",workProjectCriteria.getEndTime()));

        int totalCount = ((Long)criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
        criteria.setProjection(null);
        criteria.setFirstResult((workProjectCriteria.getPageNumber() - 1) * workProjectCriteria.getPageSize()).setMaxResults(workProjectCriteria.getPageSize());
        List<WorkProject> beans = criteria.list();
        List<WorkProjectDto> list = new ArrayList<>();
        for (WorkProject wp:beans){
            list.add(DtoFactory.convert(wp,new WorkProjectDto()));
        }
        return new PagedResult<WorkProjectDto>(list, workProjectCriteria.getPageNumber(), workProjectCriteria.getPageSize(), totalCount);
    }

    @Override
    public CommonResult updateWorkProject(WorkProjectDto dto) {
        WorkProject workProject=workProjectRepository.getById(dto.getId());
        workProject.setJoinPeople(dto.getJoinPeople());
        workProject.setSituationExplain(dto.getSituationExplain());
        workProject.setProjectBasis(dto.getProjectBasis());
        workProject.setControlAction(dto.getControlAction());
        workProject.setScheduleArrange(dto.getScheduleArrange());
        workProject.setExpectResult(dto.getExpectResult());
        workProjectRepository.save(workProject);
        return ResultFactory.commonSuccess();
    }
}
