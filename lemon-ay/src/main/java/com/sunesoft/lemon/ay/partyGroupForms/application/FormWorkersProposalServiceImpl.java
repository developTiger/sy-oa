package com.sunesoft.lemon.ay.partyGroupForms.application;

import com.sunesoft.lemon.ay.equipment.application.factory.DtoFactory;
import com.sunesoft.lemon.ay.partyGroup.application.criteria.WorkersProposalCriteria;
import com.sunesoft.lemon.ay.partyGroup.domain.WorkersProposal;
import com.sunesoft.lemon.ay.partyGroup.domain.WorkersProposalRepository;
import com.sunesoft.lemon.ay.partyGroupForms.application.dtos.FormWorkAchievementsDto;
import com.sunesoft.lemon.ay.partyGroupForms.application.dtos.FormWorkersProposalDto;
import com.sunesoft.lemon.ay.partyGroupForms.domain.FormWorkAchievements;
import com.sunesoft.lemon.ay.partyGroupForms.domain.FormWorkProject;
import com.sunesoft.lemon.ay.partyGroupForms.domain.FormWorkersProposal;
import com.sunesoft.lemon.ay.partyGroupForms.domain.FormWorkersProposalRepository;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.results.ResultFactory;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.syms.workflow.application.FormBase2;
import com.sunesoft.lemon.syms.workflow.application.dtos.ApproveFormDto;
import com.sunesoft.lemon.syms.workflow.domain.FormHeader;
import com.sunesoft.lemon.syms.workflow.domain.enums.AppValue;
import com.sunesoft.lemon.syms.workflow.domain.enums.FormStatus;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2016/9/5.
 */
@Service("formWorkersProposalService")
public class FormWorkersProposalServiceImpl extends FormBase2<FormWorkersProposal,FormWorkersProposalDto> implements FormWorkersProposalService {

    @Autowired
    FormWorkersProposalRepository formWorkersProposalRepository;

    @Autowired
    WorkersProposalRepository workersProposalRepository;


    @Override
    protected CommonResult save(FormWorkersProposal formWorkersProposal) {
        formWorkersProposalRepository.save(formWorkersProposal);
        return ResultFactory.commonSuccess();
    }

    @Override
    protected CommonResult update(FormWorkersProposal formWorkersProposal) {
        return null;
    }

    @Override
    protected FormWorkersProposal ConvetDto(FormWorkersProposalDto Dto) {
        FormWorkersProposal formWorkersProposal = DtoFactory.convert(Dto,new FormWorkersProposal());
        return formWorkersProposal;
    }

    @Override
    protected String getSummery(FormWorkersProposal formWorkersProposal) {
        return null;
    }

    @Override
    public CommonResult doAafterApprovedStep(Long formNo, Long step) {
        return null;
    }

    @Override
    public CommonResult doAafterAllApproved(Long formNo) {
        FormWorkersProposal formWorkersProposal = this.getByFormNo(formNo);
        WorkersProposal workersProposal = DtoFactory.convert(formWorkersProposal,new WorkersProposal());
        workersProposal.setId(null);
        workersProposalRepository.save(workersProposal);
        return ResultFactory.commonSuccess();
    }

    @Override
    protected FormWorkersProposal getByFormNo(Long formNo) {
        Criteria criteria = getSession().createCriteria(FormWorkersProposal.class);
        criteria.add(Restrictions.eq("isActive",true));
        criteria.add(Restrictions.eq("formNo",formNo));
        return (FormWorkersProposal)criteria.uniqueResult();
    }

    @Override
    public FormWorkersProposalDto getFormByFormNo(Long formNo) {
        FormWorkersProposal formWorkersProposal = this.getByFormNo(formNo);
        FormWorkersProposalDto workersProposalDto = DtoFactory.convert(formWorkersProposal,new FormWorkersProposalDto());
        return workersProposalDto;
    }

    @Override
    public CommonResult doApprove(ApproveFormDto dto, Map<String, Object> param) {


        FormWorkersProposal formWorkersProposal = this.getByFormNo(dto.getFormNo());

        if (param.get("clStep").toString().equals("2") && param.get("isStepComplete").toString().equals("false") && dto.getAppValue().equals(AppValue.Y.ordinal())) {
            String undertakeDept_id = param.get("undertakeDept_id").toString();
            String hiddenDeptName = param.get("hiddenDeptName").toString();

            //设置归口部门
            if (!StringUtils.isNullOrWhiteSpace(undertakeDept_id))
                formWorkersProposal.setBlongDeptId(Long.parseLong(undertakeDept_id));
            if (!StringUtils.isNullOrWhiteSpace(hiddenDeptName)) {
                formWorkersProposal.setBlongDeptName(hiddenDeptName);
                formWorkersProposal.setUndertakeDept(hiddenDeptName);
            }
            FormHeader header= formHeaderRepository.get(dto.getFormNo());
            header.setBlongDept(Long.parseLong(undertakeDept_id));//设置归口部门，
            formHeaderRepository.save(header);
        }

        if (param.get("clStep").toString().equals("3") && param.get("isStepComplete").toString().equals("false") && dto.getAppValue().equals(AppValue.Y.ordinal())) {
//            String score = param.get("approveScore").toString();//T3 打分
            String answerAdvise = param.get("answerAdvise").toString();//T3 答复意见


//            String suggestion = param.get("content").toString();//原来的意见 现已废弃

            /*if (StringUtils.isNullOrWhiteSpace(score)) {
                return ResultFactory.commonError("请先评分！");
            }

            for (int i = 0; i < score.length(); i++){
                if (!Character.isDigit(score.charAt(i))){
                    return ResultFactory.commonError("请填写正确格式的评分！");
                }
            }

            Integer appScore = Integer.parseInt(score);
            if (appScore<0 || appScore>100){
                return ResultFactory.commonError("请填写0——100之间的评分！");
            }

            formWorkersProposal.setScore(Integer.parseInt(score));*/
//            formWorkersProposal.setSuggestion(suggestion);

            if (!StringUtils.isNullOrWhiteSpace(answerAdvise))
                formWorkersProposal.setAnswerAdvise(answerAdvise.trim());

        }
        formWorkersProposalRepository.save(formWorkersProposal);

//        return doApproveOk(dto.getFormNo(), dto.getFormKind(), dto.getApproverId(), dto.getApproverName(), dto.getContent(), dto.getAgentId());
        return super.doApprove(dto, param);
    }



    @Override
    public PagedResult<FormWorkersProposalDto> getPagesByStep_2(WorkersProposalCriteria proposalCriteria) {
        Criteria criteria = getSession().createCriteria(FormWorkersProposal.class);
        criteria.add(Restrictions.eq("isActive", true));

        if (!StringUtils.isNullOrWhiteSpace(proposalCriteria.getTitle()))
            criteria.add(Restrictions.like("title", "%" + proposalCriteria.getTitle() + "%"));
        if (!StringUtils.isNullOrWhiteSpace(proposalCriteria.getPerson()))
            criteria.add(Restrictions.like("applyerName", "%" + proposalCriteria.getPerson() + "%"));
        if (proposalCriteria.getDeptName() != null)
            criteria.add(Restrictions.eq("deptName", proposalCriteria.getDeptName()));

        //添加formStatus字段查询，查询出待签核的表单
        criteria.add(Restrictions.eq("formStatus", FormStatus.UA));
        criteria.add(Restrictions.eq("clStep", proposalCriteria.getStep()));

        int totalCount = ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
        criteria.setProjection(null);
        criteria.setFirstResult((proposalCriteria.getPageNumber() - 1) * proposalCriteria.getPageSize()).setMaxResults(proposalCriteria.getPageSize());
        List<FormWorkersProposal> beans = criteria.list();
        List<FormWorkersProposalDto> list = new ArrayList<>();
        for (FormWorkersProposal wp : beans) {
            list.add(DtoFactory.convert(wp, new FormWorkersProposalDto()));
        }
        return new PagedResult<FormWorkersProposalDto>(list, proposalCriteria.getPageNumber(), proposalCriteria.getPageSize(), totalCount);
    }

    @Override
    public PagedResult<FormWorkersProposalDto> getPagesByFormWorkPro(WorkersProposalCriteria proposalCriteria) {
        Criteria criteria = getSession().createCriteria(FormWorkersProposal.class);
        criteria.add(Restrictions.eq("isActive", true));

        if (!StringUtils.isNullOrWhiteSpace(proposalCriteria.getTitle()))
            criteria.add(Restrictions.like("title", "%" + proposalCriteria.getTitle() + "%"));
        if (!StringUtils.isNullOrWhiteSpace(proposalCriteria.getPerson()))
            criteria.add(Restrictions.like("applyerName", "%" + proposalCriteria.getPerson() + "%"));
        if (proposalCriteria.getDeptName() != null)
            criteria.add(Restrictions.eq("deptName", proposalCriteria.getDeptName()));
        if (!StringUtils.isNullOrWhiteSpace(proposalCriteria.getType()))
            criteria.add(Restrictions.eq("type",proposalCriteria.getType()));
        if (!StringUtils.isNullOrWhiteSpace(proposalCriteria.getUndertakeDept()))
            criteria.add(Restrictions.eq("undertakeDept",proposalCriteria.getUndertakeDept()));


        int totalCount = ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
        criteria.setProjection(null);
        criteria.setFirstResult((proposalCriteria.getPageNumber() - 1) * proposalCriteria.getPageSize()).setMaxResults(proposalCriteria.getPageSize());
        List<FormWorkersProposal> beans = criteria.list();
        List<FormWorkersProposalDto> list = new ArrayList<>();
        for (FormWorkersProposal wp : beans) {
            list.add(DtoFactory.convert(wp, new FormWorkersProposalDto()));
        }
        return new PagedResult<FormWorkersProposalDto>(list, proposalCriteria.getPageNumber(), proposalCriteria.getPageSize(), totalCount);
    }
}
