package com.sunesoft.lemon.ay.partyGroup.application;

import com.sunesoft.lemon.ay.partyGroup.application.criteria.WorkersProposalCriteria;
import com.sunesoft.lemon.ay.partyGroup.application.dtos.WorkersProposalDto;
import com.sunesoft.lemon.ay.partyGroup.domain.WorkersProposal;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;

import java.util.List;

/**
 * Created by admin on 2016/9/5.
 */
public interface WorkersProposalService {

    public CommonResult addOrUpdate(WorkersProposalDto workersProposalDto);

    public WorkersProposalDto getById(Long id);

    public List<WorkersProposalDto> getAll();

    public PagedResult<WorkersProposalDto> getPagesByWorkerpropoDto(WorkersProposalCriteria criteria);

}
