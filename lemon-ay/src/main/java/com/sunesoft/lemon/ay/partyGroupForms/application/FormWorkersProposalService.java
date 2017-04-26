package com.sunesoft.lemon.ay.partyGroupForms.application;

import com.sunesoft.lemon.ay.partyGroup.application.criteria.WorkersProposalCriteria;
import com.sunesoft.lemon.ay.partyGroupForms.application.dtos.FormWorkersProposalDto;
import com.sunesoft.lemon.ay.partyGroupForms.domain.FormWorkersProposal;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.syms.workflow.application.serviceSelector.FormService;

import java.util.List;

/**
 * Created by admin on 2016/9/5.
 */
public interface FormWorkersProposalService extends FormService<FormWorkersProposal,FormWorkersProposalDto> {


    /**
     * 查看全部未审批申请 步骤为2
     * @param proposalCriteria
     * @return
     */
    public PagedResult<FormWorkersProposalDto> getPagesByStep_2(WorkersProposalCriteria proposalCriteria);

    /**
     * 查询数据 所有 通过和不通过的
     * @param proposalCriteria
     * @return
     */
    public PagedResult<FormWorkersProposalDto> getPagesByFormWorkPro(WorkersProposalCriteria proposalCriteria);

}
