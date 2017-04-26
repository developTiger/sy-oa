package com.sunesoft.lemon.ay.partyGroup.application;

import com.sunesoft.lemon.ay.equipment.application.factory.DtoFactory;
import com.sunesoft.lemon.ay.partyGroup.application.criteria.WorkersProposalCriteria;
import com.sunesoft.lemon.ay.partyGroup.application.dtos.WorkAchievementsDto;
import com.sunesoft.lemon.ay.partyGroup.application.dtos.WorkersProposalDto;
import com.sunesoft.lemon.ay.partyGroup.domain.WorkAchievements;
import com.sunesoft.lemon.ay.partyGroup.domain.WorkersProposal;
import com.sunesoft.lemon.ay.partyGroup.domain.WorkersProposalRepository;
import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateFinder;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.results.ResultFactory;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2016/9/5.
 */
@Service("workersProposalService")
public class WorkersProposalServiceImpl extends GenericHibernateFinder implements WorkersProposalService {

    @Autowired
    WorkersProposalRepository workersProposalRepository;

    @Override
    public CommonResult addOrUpdate(WorkersProposalDto workersProposalDto) {
        WorkersProposal workersProposal = DtoFactory.convert(workersProposalDto,new WorkersProposal());
        workersProposalRepository.save(workersProposal);
        return ResultFactory.commonSuccess();
    }

    @Override
    public WorkersProposalDto getById(Long id) {
        WorkersProposal workersProposal = null;
        if (id != null){
            workersProposal=workersProposalRepository.getById(id);
        }
        WorkersProposalDto workersProposalDto= DtoFactory.convert(workersProposal,new WorkersProposalDto());
        return workersProposalDto;
    }

    @Override
    public List<WorkersProposalDto> getAll() {
        List<WorkersProposal> list=workersProposalRepository.getAll();
        List<WorkersProposalDto> workersProposalDtos = new ArrayList<>();
        for (WorkersProposal wp:list){
            workersProposalDtos.add(DtoFactory.convert(wp,new WorkersProposalDto()));
        }
        return workersProposalDtos;
    }

    @Override
    public PagedResult<WorkersProposalDto> getPagesByWorkerpropoDto(WorkersProposalCriteria criteria) {
        Criteria crit = getSession().createCriteria(WorkersProposal.class);
        crit.add(Restrictions.eq("isActive", true));
        int totalCount = ((Long)crit.setProjection(Projections.rowCount()).uniqueResult()).intValue();
        crit.setProjection(null);
        crit.setFirstResult((criteria.getPageNumber() - 1) * criteria.getPageSize()).setMaxResults(criteria.getPageSize());
        List<WorkersProposal> beans = crit.list();
        List<WorkersProposalDto> list = new ArrayList<>();
        for (WorkersProposal wp:beans){
            list.add(DtoFactory.convert(wp, new WorkersProposalDto()));
        }
        return new PagedResult<WorkersProposalDto>(list, criteria.getPageNumber(), criteria.getPageSize(), totalCount);
    }
}
