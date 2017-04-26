package com.sunesoft.lemon.deanery.project.application;
import com.sunesoft.lemon.deanery.StringCommHelper;
import com.sunesoft.lemon.deanery.car.application.factory.DeaneryUtil;
import com.sunesoft.lemon.deanery.project.application.dtos.ProjectMainApplyDto;
import com.sunesoft.lemon.deanery.project.domain.ProjectMainApply;
import com.sunesoft.lemon.deanery.project.domain.ScientificResearchPeojectRepository;
import com.sunesoft.lemon.deanery.project.domain.ScientificResearchProject;
import com.sunesoft.lemon.deanery.projectPlanInput.applicatioin.dto.ProjectPlanInput;
import com.sunesoft.lemon.deanery.projectPlanInput.applicatioin.factory.ProjectDeaneryUtil;
import com.sunesoft.lemon.deanery.projectPlanInput.domain.ProjectPlanInputDate;
import com.sunesoft.lemon.deanery.projectPlanInput.domain.ProjectPlanInputRepository;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.ResultFactory;
import com.sunesoft.lemon.fr.utils.BeanUtils;
import com.sunesoft.lemon.syms.eHr.domain.empInfo.Employee;
import com.sunesoft.lemon.syms.workflow.application.FormBase2;
import com.sunesoft.lemon.syms.workflow.application.dtos.ApproveFormDto;
import com.sunesoft.lemon.syms.workflow.domain.FormHeader;
import com.sunesoft.lemon.syms.workflow.domain.InnerFormApprovePoint;
import com.sunesoft.lemon.syms.workflow.domain.enums.AppValue;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/27.
 */
@Service(value = "ProjectMainApplyService")
public class ProjectMainApplyServiceImpl extends FormBase2<ProjectMainApply,ProjectMainApplyDto> implements ProjectMainApplyService {

    @Autowired
    ProjectMainApplyRepository projectMainApplyRepository;
    @Autowired
    ProjectPlanInputRepository projectPlanInputRepository;
    @Autowired
    ScientificResearchPeojectRepository scientificResearchPeojectRepository;
    @Override
    protected CommonResult save(ProjectMainApply projectMainApply) {
        Long id = projectMainApplyRepository.save(projectMainApply);
        return ResultFactory.commonSuccess(id);
    }

    @Override
    protected CommonResult update(ProjectMainApply projectMainApply) {
        ProjectMainApply projectMain=projectMainApplyRepository.get(projectMainApply.getId());
        if(projectMain==null){
            return ResultFactory.commonError("更新失败");
        }
        projectMainApplyRepository.save(projectMainApply);
        return ResultFactory.commonSuccess();
    }

    @Override
    protected ProjectMainApply ConvetDto(ProjectMainApplyDto Dto) {
        ProjectMainApply projectMainApply= DeaneryUtil.convertFromListProjectMainApplyDto(Dto);
        return projectMainApply;
    }

    @Override
    protected String getSummery(ProjectMainApply projectMainApply) {
        return null;
    }

    @Override
    public CommonResult doAafterApprovedStep(Long formNo, Long step) {
        return null;
    }

    @Override
    public CommonResult doAafterAllApproved(Long formNo){
        List<ScientificResearchProject> sics = projectMainApplyRepository.getSubAppraiseByParentFormNo(formNo);
        StringBuilder builder = new StringBuilder();

        for (ScientificResearchProject sub : sics) {
            builder.append(sub.getFormNo());
            builder.append(",");
        }
        String forms = builder.toString().substring(0, builder.length() - 1);
        //最后一级审核 直接更新状态
        String sql = "begin update SYY_OA_FM_HEADER t set t.form_status=3, t.end_date=SYSDATE,t.life_cycle='F',t.provious_approver='评审小组' where t.id in (" + forms + ");";

        sql += " update SYY_OA_FM_APPROVER a set a.approve_status=2,a.app_value=1,a.end_date=SYSDATE,a.app_actor_name='评审小组' where a.form_no in (" + forms + ") and a.approve_status=0; end;";


        Query query = getSession().createSQLQuery(sql);
        query.executeUpdate();

        return ResultFactory.commonSuccess();

    }

    @Override
    protected ProjectMainApply getByFormNo(Long formNo) {
        Criteria criterion = getSession().createCriteria(ProjectMainApply.class);
        criterion.add(Restrictions.eq("isActive", true));
        criterion.add(Restrictions.eq("formNo",formNo));

        return (ProjectMainApply)criterion.uniqueResult();
    }



    /**
     * 查询项目计划
     * @return
     */
    @Override
    public List<ProjectPlanInput> queryProjectPlan() {
        List<ProjectPlanInput> listInput=new ArrayList<>();
        Criteria criteria = this.getSession().createCriteria(ProjectPlanInputDate.class);
        //criteria.add(Restrictions.eq("projectPlan_State","0"));
        List<ProjectPlanInputDate> list = criteria.list();
        for(ProjectPlanInputDate listDate:list){
            ProjectPlanInput listImput = ProjectDeaneryUtil.converFromListProjectPlanDto(listDate);
            String email=listImput.getProjectPlan_Email();
            listImput.setMenagerid(projectPlanInputRepository.getEmployeeId(email));
            listInput.add(listImput);
        }
        return listInput;
    }


   public  Long addProjectMain(ProjectPlanInput projectPlanInput){
       ProjectMainApply projectMainApply=new ProjectMainApply();
       BeanUtils.copyProperties(projectPlanInput, projectMainApply);
       Long l= projectMainApplyRepository.save(projectMainApply);
       return l;
   }

    @Override
    public ScientificResearchProject getByOrderId(String fromNo){
        Criteria criteria = getSession().createCriteria(ScientificResearchProject.class);
        criteria.add(Restrictions.eq("formNo",Long.parseLong(fromNo)));
        List<ScientificResearchProject> list=criteria.list();
        if(list!=null && list.size()>0){
            //ScientificResearchProjectDto dto=DeaneryUtil.convertFromListScientificResearchProjectDto(list.get(0));
            return list.get(0);
        }else{
            return null;
        }
    }

    @Override
    public CommonResult doApprove(ApproveFormDto dto, Map<String, Object> param) {
        Integer clStep = Integer.parseInt(param.get("clStep").toString());
        Boolean isComplete = Boolean.valueOf(param.get("isStepComplete").toString());

        if(clStep.equals(1)&&!isComplete&&dto.getAppValue().equals(AppValue.Y.ordinal())) {
            FormHeader header = formHeaderRepository.get(dto.getFormNo());
            Long nextId = header.getNextAppPointId();
            String hidId= StringCommHelper.nullToString(param.get("hidId"));
            String hidName= StringCommHelper.nullToString(param.get("hidName"));
            String[] ids=hidId.split(",");
            List<Long> emplist = new ArrayList<>();
              for (int i=0;i<ids.length;i++) {
                  emplist.add(Long.getLong(ids[i]));
              }
             this.resetNextApprover(dto.getFormNo(), emplist);
        }


        if(clStep.equals(1)&&!isComplete&&dto.getAppValue().equals(AppValue.Y.ordinal())) {
            Long id= StringCommHelper.nullToLong(param.get("id"));
            ProjectMainApply projectMainApply=projectMainApplyRepository.get(id);
            projectMainApply.setTitle(StringCommHelper.nullToString(param.get("title")));
            projectMainApply.setMainBeginDate(StringCommHelper.nullToString(param.get("mainBeginDate")));
            projectMainApply.setMainContent(StringCommHelper.nullToString(param.get("mainContent")));
            projectMainApplyRepository.save(projectMainApply);
        }

        if(clStep.equals(5)&&!isComplete&&dto.getAppValue().equals(AppValue.Y.ordinal())) {
            String sciId= StringCommHelper.nullToString(param.get("formNo"));
            doAafterAllApproved(Long.parseLong(sciId));
        }

        return super.doApprove(dto,param);
    }

    @Override
    public ProjectMainApplyDto getFormByFormNo(Long formNo) {
        return null;
    }

    public Long UpdateByDto(ProjectMainApplyDto dto){
        ProjectMainApply projectMainApply=DeaneryUtil.convertFromListProjectMainApplyDto(dto);
        Long l=projectMainApplyRepository.save(projectMainApply);
        return l;
    }


    @Override
    public CommonResult mainApprove(ApproveFormDto dto, Integer leaderType, List<Long> empIds) {
        FormHeader header = formHeaderRepository.get(dto.getFormNo());
        Long nextId = header.getNextAppPointId();
        List<Long> emplist = new ArrayList<>();
        for (InnerFormApprovePoint point : header.getInnerFormApprovePoints()) {
            if (nextId.equals(point.getId())) {
                for (Employee e : point.getAppRole().getApprover()) {
                    emplist.add(e.getId());
                }
            }
        }
        emplist.addAll(empIds);
        return this.resetNextApprover(dto.getFormNo(), emplist);
        //super.doApprove(dto,param)

    }
}

