package com.sunesoft.lemon.ay.partyGroupForms.application;

import com.sunesoft.lemon.ay.equipment.application.factory.DtoFactory;
import com.sunesoft.lemon.ay.partyGroup.application.criteria.FormWorkProjectCriteria;
import com.sunesoft.lemon.ay.partyGroup.application.criteria.WorkProjectCriteria;
import com.sunesoft.lemon.ay.partyGroup.application.dtos.WorkProjectDto;
import com.sunesoft.lemon.ay.partyGroup.domain.WorkProject;
import com.sunesoft.lemon.ay.partyGroup.domain.WorkProjectRepository;
import com.sunesoft.lemon.ay.partyGroupForms.application.dtos.ApproveProjFormDto;
import com.sunesoft.lemon.ay.partyGroupForms.application.dtos.FormWorkProjectDto;
import com.sunesoft.lemon.ay.partyGroupForms.domain.FormWorkProject;
import com.sunesoft.lemon.ay.partyGroupForms.domain.FormWorkProjectRepository;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.results.ResultFactory;
import com.sunesoft.lemon.fr.utils.DateHelper;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.syms.eHr.domain.empInfo.Employee;
import com.sunesoft.lemon.syms.hrForms.domain.FormLeave;
import com.sunesoft.lemon.syms.hrForms.domain.enums.LeaveType;
import com.sunesoft.lemon.syms.workflow.application.FormBase2;
import com.sunesoft.lemon.syms.workflow.application.dtos.ApproveFormDto;
import com.sunesoft.lemon.syms.workflow.domain.FormApprover;
import com.sunesoft.lemon.syms.workflow.domain.FormHeader;
import com.sunesoft.lemon.syms.workflow.domain.InnerFormApprovePoint;
import com.sunesoft.lemon.syms.workflow.domain.enums.AppValue;
import com.sunesoft.lemon.syms.workflow.domain.enums.ApproveStatus;
import com.sunesoft.lemon.syms.workflow.domain.enums.FormStatus;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Order;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2016/9/2.
 */
@Service("formWorkProjectService")
public class FormWorkProjectServiceImpl extends FormBase2<FormWorkProject,FormWorkProjectDto> implements FormWorkProjectService {

    @Autowired
    FormWorkProjectRepository formWorkProjectRepository;

    @Autowired
    WorkProjectRepository workProjectRepository;


    @Override
    protected CommonResult save(FormWorkProject formWorkProject) {
        List<FormWorkProject> list=formWorkProjectRepository.getAll();//判断入库的项目名称是否重复
        for (FormWorkProject project:list){
            if(formWorkProject.getId()!=null){
                if (project.getProjectName().equals(formWorkProject.getProjectName())&&!formWorkProject.getId().equals(project.getId()) && formWorkProject.getFormWork_id()==null)
                    return ResultFactory.commonError("该项目已存在，请重新填写");
            }
            //前面判断项目名称是否重复,将项目与成果区分开
            else if (project.getProjectName().equals(formWorkProject.getProjectName()) && formWorkProject.getFormWork_id()==null)
                return ResultFactory.commonError("该项目已存在，请重新填写");
        }

        formWorkProjectRepository.save(formWorkProject);
        return ResultFactory.commonSuccess();
    }

    @Override
    protected CommonResult update(FormWorkProject formWorkProject) {
        FormWorkProject formWorkProject1 = new FormWorkProject();
        formWorkProject1.setJoinPeople(formWorkProject.getJoinPeople());
        formWorkProject1.setLeader(formWorkProject.getLeader());
        formWorkProject1.setNumber(formWorkProject.getNumber());
        formWorkProject1.setProjectName(formWorkProject.getProjectName());
        formWorkProjectRepository.save(formWorkProject1);
        return ResultFactory.commonSuccess();
    }

    @Override
    protected FormWorkProject ConvetDto(FormWorkProjectDto Dto) {
        FormWorkProject formWorkProject = DtoFactory.convert(Dto,new FormWorkProject());
        return formWorkProject;
    }

    @Override
    protected String getSummery(FormWorkProject formWorkProject) {
        return null;
    }

    @Override
    public CommonResult doAafterApprovedStep(Long formNo, Long step) {
        return null;
    }

    @Override
    public CommonResult doAafterAllApproved(Long formNo) {
        FormWorkProject formWorkProject=this.getByFormNo(formNo);
        WorkProject workProject = DtoFactory.convert(formWorkProject,new WorkProject());
        workProject.setId(null);
        workProjectRepository.save(workProject);
        return ResultFactory.commonSuccess();
    }

    @Override
    protected FormWorkProject getByFormNo(Long formNo) {

        return formWorkProjectRepository.getByFormNo(formNo);
    }

    @Override
    public FormWorkProjectDto getFormByFormNo(Long formNo) {
        FormWorkProject formWorkProject = this.getByFormNo(formNo);
        FormWorkProjectDto formWorkProjectDto = DtoFactory.convert(formWorkProject,new FormWorkProjectDto());
        return formWorkProjectDto;
    }

    @Override
    public List<FormWorkProjectDto> getAllForm() {
        List<FormWorkProject> projects=formWorkProjectRepository.getAll();
        List<FormWorkProjectDto> projectDtos= new ArrayList<>();
        for (FormWorkProject workProject:projects){
            projectDtos.add(DtoFactory.convert(workProject,new FormWorkProjectDto()));
        }
        return projectDtos;
    }

    @Override
    public PagedResult<FormWorkProjectDto> getPagesFormWorkProjectDto(FormWorkProjectCriteria workProjectCriteria) {
        Criteria criteria = getSession().createCriteria(FormWorkProject.class);
        criteria.add(Restrictions.eq("isActive",true));

        if (!StringUtils.isNullOrWhiteSpace(workProjectCriteria.getProjectName()))
            criteria.add(Restrictions.like("projectName","%"+workProjectCriteria.getProjectName()+"%"));
        if (!StringUtils.isNullOrWhiteSpace(workProjectCriteria.getLeader()))
            criteria.add(Restrictions.like("leader","%"+workProjectCriteria.getLeader()+"%"));
        if (!StringUtils.isNullOrWhiteSpace(workProjectCriteria.getCompetitionType()))
            criteria.add(Restrictions.like("competitionType", "%"+workProjectCriteria.getCompetitionType()+"%"));
        if (!StringUtils.isNullOrWhiteSpace(workProjectCriteria.getDeptName()))
            criteria.add(Restrictions.eq("deptName",  workProjectCriteria.getDeptName()));
        //添加formStatus字段查询，查询出待签核的表单
        criteria.add(Restrictions.eq("formStatus", FormStatus.UA));
        criteria.add(Restrictions.eq("clStep",workProjectCriteria.getStep()));

        int totalCount = ((Long)criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
        criteria.setProjection(null);
        criteria.setFirstResult((workProjectCriteria.getPageNumber() - 1) * workProjectCriteria.getPageSize()).setMaxResults(workProjectCriteria.getPageSize());
        List<FormWorkProject> beans = criteria.list();
        List<FormWorkProjectDto> list = new ArrayList<>();
        for (FormWorkProject wp:beans){
            FormWorkProjectDto projectDto = DtoFactory.convert(wp,new FormWorkProjectDto());
            String scores = projectDto.getAchiOnlyMembersScores();
            if (!StringUtils.isNullOrWhiteSpace(scores)){
                String[] arr_scores = scores.split("//");
                projectDto.setAchiOnlyScores(arr_scores);
            }
            String suggestions = projectDto.getAchiOnlyMembersAdvise();
            if (!StringUtils.isNullOrWhiteSpace(suggestions)){
                String[] arr_suggestions = suggestions.split("//");
                projectDto.setAchiOnlyAdvise(arr_suggestions);
            }
            list.add(projectDto);
        }
        return new PagedResult<FormWorkProjectDto>(list, workProjectCriteria.getPageNumber(), workProjectCriteria.getPageSize(), totalCount);
    }

    @Override
    public PagedResult<FormWorkProjectDto> getAllFormsPages(FormWorkProjectCriteria workProjectCriteria) {
        Criteria criteria = getSession().createCriteria(FormWorkProject.class);
        criteria.add(Restrictions.eq("isActive",true));

        if (!StringUtils.isNullOrWhiteSpace(workProjectCriteria.getProjectName()))
            criteria.add(Restrictions.like("projectName","%"+workProjectCriteria.getProjectName()+"%"));
        if (!StringUtils.isNullOrWhiteSpace(workProjectCriteria.getLeader()))
            criteria.add(Restrictions.like("leader","%"+workProjectCriteria.getLeader()+"%"));
        if (workProjectCriteria.getCompetitionType() != null)
            criteria.add(Restrictions.eq("competitionType", workProjectCriteria.getCompetitionType()));
        if (!StringUtils.isNullOrWhiteSpace(workProjectCriteria.getDeptName()))
            criteria.add(Restrictions.eq("deptName",  workProjectCriteria.getDeptName()));
        if (workProjectCriteria.getBeginTime() != null)
            criteria.add(Restrictions.ge("cpmpetitionTime",workProjectCriteria.getBeginTime()));
        if (workProjectCriteria.getEndTime() != null)
            criteria.add(Restrictions.le("cpmpetitionTime",workProjectCriteria.getEndTime()));


        int totalCount = ((Long)criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
        criteria.setProjection(null);
        criteria.setFirstResult((workProjectCriteria.getPageNumber() - 1) * workProjectCriteria.getPageSize()).setMaxResults(workProjectCriteria.getPageSize());
        List<FormWorkProject> beans = criteria.list();
        List<FormWorkProjectDto> list = new ArrayList<>();
        for (FormWorkProject wp:beans){
            list.add(DtoFactory.convert(wp, new FormWorkProjectDto()));
        }
        return new PagedResult<FormWorkProjectDto>(list, workProjectCriteria.getPageNumber(), workProjectCriteria.getPageSize(), totalCount);
    }

    @Override
    public List<FormWorkProjectDto> getAllPassFormWorkProjectDto() {
        Criteria criteria = getSession().createCriteria(FormWorkProject.class);
        criteria.add(Restrictions.eq("isActive",true));
        criteria.add(Restrictions.eq("formStatus",FormStatus.AP));
        List<FormWorkProject> formWorkProjects = criteria.list();
        List<FormWorkProjectDto> formWorkProjectDtos = new ArrayList<>();
        for (FormWorkProject project:formWorkProjects){
            formWorkProjectDtos.add(DtoFactory.convert(project,new FormWorkProjectDto()));
        }


        return formWorkProjectDtos;
    }

    @Override
    public FormWorkProjectDto getById(Long id) {
        FormWorkProject formWorkProject=formWorkProjectRepository.getById(id);
        FormWorkProjectDto formWorkProjectDto = DtoFactory.convert(formWorkProject,new FormWorkProjectDto());
        return formWorkProjectDto;
    }

    @Override
    public CommonResult doApprove(ApproveFormDto dto, Map<String, Object> param) {

        FormWorkProject formWorkProject = this.getByFormNo(dto.getFormNo());

        if (param.get("clStep").toString().equals("2") && param.get("isStepComplete").toString().equals("false")
                && dto.getAppValue().equals(AppValue.Y.ordinal())) {
            String project_membersName = param.get("project_membersName").toString();//评审小组
//            String project_judgeSuggestions = param.get("project_judgeSuggestions").toString();
            String members_leader = param.get("members_leader").toString();//评审小组组长

            String project_membersNameIds = param.get("project_membersNameIds").toString();
            String hiddenLeaderId = param.get("hiddenLeaderId").toString();

            if (!StringUtils.isNullOrWhiteSpace(project_membersName)){
                formWorkProject.setProjectJudgeMembers(project_membersName.trim());
            }else{
                return ResultFactory.commonError("请选择评审小组成员！");
            }

            if (!StringUtils.isNullOrWhiteSpace(project_membersNameIds)){
                formWorkProject.setProjectJudgeMembersIds(project_membersNameIds);
            }

//            if (!StringUtils.isNullOrWhiteSpace(project_judgeSuggestions)){
//                formWorkProject.setJudgeSuggestion(project_judgeSuggestions.trim());
//            }

            if (!StringUtils.isNullOrWhiteSpace(members_leader)){
                formWorkProject.setJudgeMembersLeader(members_leader.trim());
            }else{
                return ResultFactory.commonError("请选择评审小组组长！");
            }

            if (!StringUtils.isNullOrWhiteSpace(hiddenLeaderId)){
                formWorkProject.setJudgeMembersLeaderId(hiddenLeaderId);
            }

//            subFormOperate(dto.getFormNo(), false, "审核通过", 4);
            //将T3节点的审核人设置为评审小组成员和评审小组组长
            List<Long> empIds = new ArrayList<>();
            String[] membersId = project_membersNameIds.split(",");
            for (String id:membersId){
                empIds.add(Long.parseLong(id));
            }
            String[] leaderId = hiddenLeaderId.split(",");
            for (String id:leaderId){
                empIds.add(Long.parseLong(id));
            }

            super.resetNextApprover(dto.getFormNo(), empIds);

        }

        if (param.get("clStep").toString().equals("3") && param.get("isStepComplete").toString().equals("false")
                && dto.getAppValue().equals(AppValue.Y.ordinal())) {
//            super.su
//            subFormOperate(dto.getFormNo(),false,"系统自动审核",3);
//            String userId = param.get("hiddenUserId").toString();
            //组长签核
            if (dto.getApproverId()==Long.parseLong(formWorkProject.getJudgeMembersLeaderId())){
                formWorkProjectRepository.save(formWorkProject);
                return super.doApprove(dto, param);
            }

        }

        formWorkProjectRepository.save(formWorkProject);
//        return doApproveOk(dto.getFormNo(), dto.getFormKind(), dto.getApproverId(), dto.getApproverName(), dto.getContent(), dto.getAgentId());
        return super.doApprove(dto, param);
    }


    @Override
    public CommonResult doApproveOk(Long formNo, String formKind, Long approverId, String approver, String content, List<Long> agentId) throws Exception {
        FormHeader header = formHeaderRepository.get(formNo);
        CommonResult result = header.approve2(approverId, approver, content, agentId);

        // step
        //如果审核人是组长

        // 更新当前节点未审核人FormApprover的状态   P

        //设置 header IsCurrentPiontComplete=true;
        if (header.getClStep()==3){
            for (FormApprover formApprover:header.getFormApprovers()){
                //待签核
                if (formApprover.getApproveStatus().equals(ApproveStatus.W)){
                    formApprover.setApproveStatus(ApproveStatus.P);
                }
            }
            header.setIsCurrentPiontComplete(true);
        }

        if (header.getIsCurrentPiontComplete())
            header = this.activeNextApprover(header);

        if (result.getIsSuccess()) {
            if (header.getFormStatus() == FormStatus.AP) {


                result = doAafterAllApproved(formNo);

            } else
                result = doAafterApprovedStep(formNo, result.getId());
        }
        if (result != null && !result.getIsSuccess()) {
            throw new Exception(result.getMsg());
        }
        if (formHeaderRepository.save(header) > 0) {
            result = updateFormStstus(header.getId(), header.getClStep(), header.getIsStepComplete(), header.getFormStatus(), header.getCurrentApproveAction(), header.getCurrentViewAction());
            //subFormOperate(header.getId(),true,"ceshi",0);
            return result;
        } else
            return ResultFactory.commonError("签核失败");
    }

}
