package com.sunesoft.lemon.deanery.projectWorkFlow.application;


import com.sunesoft.lemon.deanery.car.application.factory.DeaneryUtil;
import com.sunesoft.lemon.deanery.project.application.dtos.ScientificResearchProjectDto;
import com.sunesoft.lemon.deanery.project.domain.ScientficApproveFile;
import com.sunesoft.lemon.deanery.project.domain.ScientificResearchProject;
import com.sunesoft.lemon.deanery.projectWorkFlow.application.criteria.FormProjectExecutoryCriteria;
import com.sunesoft.lemon.deanery.projectWorkFlow.application.dto.FormOpenProjectFileDto;
import com.sunesoft.lemon.deanery.projectWorkFlow.application.dto.FormProjectApplyDto;
import com.sunesoft.lemon.deanery.projectWorkFlow.application.dto.FormProjectExecutoryDto;
import com.sunesoft.lemon.deanery.projectWorkFlow.domain.*;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.results.ResultFactory;
import com.sunesoft.lemon.fr.utils.BeanUtils;
import com.sunesoft.lemon.syms.eHr.application.EmployeeService;
import com.sunesoft.lemon.syms.eHr.application.dtos.EmpDto;
import com.sunesoft.lemon.syms.eHr.application.factory.DtoFactory;
import com.sunesoft.lemon.syms.workflow.application.FormBase2;
import com.sunesoft.lemon.syms.workflow.application.FormHeaderService;
import com.sunesoft.lemon.syms.workflow.application.criteria.FormHeaderCriteria;
import com.sunesoft.lemon.syms.workflow.application.dtos.ApproveFormDto;
import com.sunesoft.lemon.syms.workflow.application.dtos.FormHeaderDto;
import com.sunesoft.lemon.syms.workflow.domain.FormHeader;
import com.sunesoft.lemon.syms.workflow.domain.FormHeaderRepository;
import com.sunesoft.lemon.syms.workflow.domain.enums.ApproveStatus;
import com.sunesoft.lemon.syms.workflow.domain.enums.FormStatus;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zy on 2016/8/19.
 */
@Service("formProjectExecutionService")
public class FormProjectExecutionServiceImpl extends FormBase2<FormProjectExecution,FormProjectExecutoryDto> implements FormProjectExecutionService {
    @Autowired
    FormHeaderService headerService;
    @Autowired
    FormProjectExecutionRepository formProjectExecutionRepository;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    FormHeaderRepository headerRepository;

    //保存
    @Override
    protected CommonResult save(FormProjectExecution formProjectExecution) {
        formProjectExecutionRepository.save(formProjectExecution);
        return ResultFactory.commonSuccess();

    }
    //更新
    @Override
    protected CommonResult update(FormProjectExecution formProjectExecution) {
        FormProjectExecution execution = this.getByFormNo(formProjectExecution.getFormNo());
        execution.setProjectNo(formProjectExecution.getProjectNo());
        execution.setLeaderId(formProjectExecution.getLeaderId());
        execution.setProjectName(formProjectExecution.getProjectName());
        // execution.setProjectPlanInfoTxt(formProjectExecution.getProjectPlanInfoTxt());
        //项目进展情况
        execution.setProjectActualComplete(formProjectExecution.getProjectActualComplete());
        //取得阶段性成果
        execution.setChieveResult(formProjectExecution.getChieveResult());
        //项目经济效益分析
        execution.setEconomicAnalysis(formProjectExecution.getEconomicAnalysis());
        //课题进展程度
        execution.setProjectEvaluate(formProjectExecution.getProjectEvaluate());
        //项目存在问题及一下安排
        execution.setProjectProblem(formProjectExecution.getProjectProblem());
        formProjectExecutionRepository.save(execution);

        return ResultFactory.commonSuccess();
    }
    //实体转dto
    @Override
    protected FormProjectExecution ConvetDto(FormProjectExecutoryDto Dto) {
        return DtoFactory.convert(Dto,new FormProjectExecution());
    }

    @Override
    protected String getSummery(FormProjectExecution formProjectExecution) {
        return null;
    }

    @Override
    public CommonResult doAafterApprovedStep(Long formNo, Long step) {
        return null;
    }

    @Override
    public CommonResult doAafterAllApproved(Long formNo) {
        return null;
    }

    @Override
    protected FormProjectExecution getByFormNo(Long formNo) {
        Criteria criterion = getSession().createCriteria(FormProjectExecution.class);
        criterion.add(Restrictions.eq("isActive", true));
        criterion.add(Restrictions.eq("formNo", formNo));
        return (FormProjectExecution)criterion.uniqueResult();
    }

    protected FormProjectExecution getByFormNo1(Long formNo) {
        Criteria criterion = getSession().createCriteria(FormProjectExecution.class);
        criterion.add(Restrictions.eq("formNo", formNo));
        return (FormProjectExecution)criterion.uniqueResult();
    }
    //追加字段
    @Override
    public CommonResult addNewApprove(ApproveFormDto dto, Double price) {
        FormProjectExecution al=  this.getByFormNo(dto.getFormNo());
        //al.setPrice();
        formProjectExecutionRepository.save(al);
        return doApprove(dto,null);
    }

    @Override
    public List<FormProjectApply> queryProject() {
        Criteria criteria = this.getSession().createCriteria(FormProjectApply.class);
        criteria.add(Restrictions.eq("formStatus", FormStatus.AP ));
        criteria.add(Restrictions.eq("isActive", true));
        List<FormProjectApply> list=criteria.list();
        return list;
    }
    //追加字段更新
    @Override
    public CommonResult updateProject(ApproveFormDto dto, FormProjectExecutoryDto formProjectExecutoryDto) {
        FormProjectExecution execution = this.getByFormNo(dto.getFormNo());
        formProjectExecutionRepository.save(execution);

        return super.doApprove(dto, null);
    }
    @Override
    public CommonResult nextProject(ApproveFormDto dto) {
        return super.doApprove(dto, null);
    }
    @Override
    public CommonResult nextProject1(ApproveFormDto dto,String empId,String clStep) {
        if(Long.parseLong(clStep)==4||Long.parseLong(clStep)==7) {
            FormHeader header = formHeaderRepository.get(dto.getFormNo());
            header.setContent(empId);
            formHeaderRepository.save(header);
        }
        return super.doApprove(dto, null);
    }

    @Override
    public CommonResult nextProject2(ApproveFormDto dto,String clStep) {
        if(Long.parseLong(clStep)==5||Long.parseLong(clStep)==8) {
            FormHeader header = formHeaderRepository.get(dto.getFormNo());
            String empId=header.getContent();
            if (empId != "") {
                List<Long> emplist = new ArrayList<>();
                if (empId.indexOf(",") >= 0) {
                    List listid = new ArrayList();
                    String[] empids = empId.split(",");
                    for (String id : empids) {
                        emplist.add(Long.parseLong(id));
                    }
                } else {
                    emplist.add(Long.parseLong(empId));
                }
                this.resetNextApprover(dto.getFormNo(), emplist);
            }
        }
        return super.doApprove(dto, null);
    }
    @Override
    public FormProjectExecutoryDto getFormProjectExecution(Long id) {
        FormProjectExecution formProjectExecution=formProjectExecutionRepository.get(id);
        FormProjectExecutoryDto formProjectExecutoryDto=new FormProjectExecutoryDto();
        BeanUtils.copyProperties(formProjectExecution,formProjectExecutoryDto);
        return formProjectExecutoryDto;
    }

    @Override
    public Long updateFormProjectExecutionById(Long id) {
        FormProjectExecution formProjectExecution=formProjectExecutionRepository.get(id);
        //formProjectExecution.setIsActive(false);
        formProjectExecutionRepository.save(formProjectExecution);
        return 0L;
    }
    @Override
    public Long updateProjectExecutionById(Long id,String instructions,String clstep) {
        Criteria criterion = getSession().createCriteria(FormProjectExecution.class);
        criterion.add(Restrictions.eq("formNo", id));
        List<FormProjectExecution> beans = criterion.list();
        for(FormProjectExecution formProjectExecution:beans) {
            if (clstep.equals("2")) {
                String ins = formProjectExecution.getInstructions();
                if (ins == null || ins == "") {
                    formProjectExecution.setInstructions(instructions);
                } else {
                    ins = ins + "  |  ";
                    formProjectExecution.setInstructions(ins + instructions);
                }
                formProjectExecutionRepository.save(formProjectExecution);
            }
            if (clstep.equals("6")) {
                String ins = formProjectExecution.getProficientOpinion();
                if (ins == null || ins == "") {
                    formProjectExecution.setProficientOpinion(instructions);
                } else {
                    ins = ins + "  |  ";
                    formProjectExecution.setProficientOpinion(ins + instructions);
                }
                formProjectExecutionRepository.save(formProjectExecution);
            }
        }
        return 1L;
    }
    @Override
    public void deleteById(Long id) {
        formProjectExecutionRepository.delete(id);
    }

    @Override
    public FormProjectExecutoryDto getFormByFormNo(Long formNo) {
        FormProjectExecution execution = this.getByFormNo(formNo);
        Hibernate.initialize(execution.getExecutionFileList());
        FormProjectExecutoryDto dto = DtoFactory.convert(execution,new FormProjectExecutoryDto() );
        return  dto;
    }

    public List<Map<String,String>> getfileList(Long formNo){
        String sb ="SELECT t.file_Id fileId,t.file_Name fileName FROM SYY_OA_FORM_EXECUTION_FILE t where t.EXECUTION_ID = "+formNo;
        Query query = this.getSession().createSQLQuery(sb.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return query.list();
    }
    @Override
    public FormProjectExecution getFormByFormNo1(Long formNo) {
        FormProjectExecution execution = this.getByFormNo1(formNo);
        List<Map<String,String>> getfileList=getfileList(formNo);
        List<FormProjectExecutionFile> sss=new ArrayList<>();
        FormProjectExecutionFile aa=new FormProjectExecutionFile();
        for (Map<String,String> map :getfileList){
            for (Map.Entry<String, String> m : map.entrySet()) {
                System.out.print(m.getKey());
                System.out.println(m.getValue());
                aa.setFileId1(m.getKey());
                aa.setFileName1(m.getValue());
            }
           }
        sss.add(aa);
        execution.setExecutionFileList(sss);
        return  execution;
    }

   /* List<FormProjectExecutionFile> getfileList(Long formNo){
        Criteria criterion = getSession().createCriteria(FormProjectExecutionFile.class);
        criterion.add(Restrictions.eq("formNo", formNo));
        List<FormProjectExecutionFile> list = criterion.list();
        return list;
    }*/
    @Override
    public List<FormProjectExecutoryDto> getProjectApproves3(FormProjectExecutoryCriteria formProjectExecutoryCriteria, Integer clstep,Long userId) {
        FormHeaderCriteria headerCriteria = new FormHeaderCriteria();
        headerCriteria.setApproverId(formProjectExecutoryCriteria.getApproverId());
        headerCriteria.setFormKind("SYY_KG_LC03");
        List<FormStatus> formStatuses = new ArrayList<>();
        formStatuses.add(FormStatus.UA);
        headerCriteria.setPageSize(1000);
        headerCriteria.setArrFormStatus(formStatuses);
        headerCriteria.setApproveStatus(ApproveStatus.U);
        headerCriteria.setApproverId(userId);

        PagedResult<FormHeaderDto> formHeaderDtos= headerService.findFormPaged(headerCriteria);

        List<Long> formNOs = new ArrayList<>();
        if(formHeaderDtos.getItems()!=null &&formHeaderDtos.getItems().size()>0){
            for(FormHeaderDto headerDto : formHeaderDtos.getItems()){
                formNOs.add(headerDto.getId());
            }
        }
        else{
            return  new ArrayList<>();
        }

        Criteria criteria = getSession().createCriteria(FormProjectExecution.class);
        criteria.add(Restrictions.eq("isActive", true));
        criteria.add(Restrictions.eq("clStep", clstep));
        criteria.add(Restrictions.eq("formStatus", FormStatus.UA));
        criteria.add(Restrictions.in("formNo",formNOs));

        if(formProjectExecutoryCriteria.getSpecialtyType()!=null && !("").equals(formProjectExecutoryCriteria.getSpecialtyType())){
            criteria.add(Restrictions.like("specialtyType", "%" + formProjectExecutoryCriteria.getSpecialtyType() + "%"));
        }

        List<FormProjectExecution> beans = criteria.list();

        List<FormProjectExecutoryDto> dto = new ArrayList<FormProjectExecutoryDto>();
        for(FormProjectExecution formProjectExecution : beans){
            Hibernate.initialize(formProjectExecution.getExecutionFileList());
            FormProjectExecutoryDto formProjectExecutoryDto = DtoFactory.convert(formProjectExecution, new FormProjectExecutoryDto());
          /*  scientificResearchProjectDto.setDeptName(deptmentService.getByDeptId(scientificResearchProject.getDept()).getDeptName());*/
            formProjectExecutoryDto.setDeptName(formProjectExecution.getDeptName());
            if(formProjectExecutoryDto.getLeaderId()!=null && formProjectExecutoryDto.getLeaderId().length()!=0){
                EmpDto empDto = employeeService.getLeader(Long.parseLong(formProjectExecutoryDto.getLeaderId()));
                formProjectExecutoryDto.setLeaderName(empDto.getName());
            }
            dto.add(formProjectExecutoryDto);
        }
        return dto;
    }

    @Override
    public CommonResult updateProject(ApproveFormDto dto,String zjid,String zgid,String clStep) {
        if(Long.parseLong(clStep)==4) {
            if (zjid != "") {
                FormHeader header = formHeaderRepository.get(dto.getFormNo());
                header.setContent(zjid);
                formHeaderRepository.save(header);
            }
        }
        if(Long.parseLong(clStep)==5) {
            FormHeader header = formHeaderRepository.get(dto.getFormNo());
              String zjid1 = header.getContent();
                List<Long> emplist = new ArrayList<>();
                if (zjid1.indexOf(",") >= 0) {
                    List listid = new ArrayList();
                    String[] empids = zjid1.split(",");
                    for (String id : empids) {
                        emplist.add(Long.parseLong(id));
                    }
                } else {
                    emplist.add(Long.parseLong(zjid1));
                }
                this.resetNextApprover(dto.getFormNo(), emplist);
        }

        if(Long.parseLong(clStep)==7) {
            if (zgid != "") {
                FormHeader header = formHeaderRepository.get(dto.getFormNo());
                header.setContent(zgid);
                formHeaderRepository.save(header);
            }
        }

        if(Long.parseLong(clStep)==8) {
            FormHeader header = formHeaderRepository.get(dto.getFormNo());
            String zgid1 = header.getContent();
                List<Long> emplist = new ArrayList<>();
                if (zgid1.indexOf(",") >= 0) {
                    List listid = new ArrayList();
                    String[] empids = zgid1.split(",");
                    for (String id : empids) {
                        emplist.add(Long.parseLong(id));
                    }
                } else {
                    emplist.add(Long.parseLong(zgid1));
                }
                this.resetNextApprover(dto.getFormNo(), emplist);
            }
        return super.doApprove(dto, null);
    }
    //群打回
    @Override
    public CommonResult reProject(ApproveFormDto dto) {
        return super.doApprove(dto, null);
    }

    @Override
    public CommonResult uploadProjectFile(Long formNo, String fileId, String fileName) {
        FormProjectExecution formProjectExecution=this.getByFormNo(formNo);
        FormOpenProjectFile file=new FormOpenProjectFile();
        file.setFileId(fileId);
        file.setFileName(fileName);
      //  formProjectExecution.getFormOpenProjectFiles().add(file);
        formProjectExecution.setFileId(fileId);
        formProjectExecution.setFileName(fileName);
        formProjectExecutionRepository.save(formProjectExecution);
        return ResultFactory.commonSuccess();
        /*FormDelayApply formDelayApply=this.getByFormNo(formNo);
        FormOpenProjectFile file=new FormOpenProjectFile();
        file.setFileId(fileId);
        file.setFileName(fileName);
        formDelayApply.getFormOpenProjectFiles().add(file);
        formDelayApply.setFileId(fileId);
        formDelayApply.setFileName(fileName);
        formDelayApplyRepository.save(formDelayApply);
        return ResultFactory.commonSuccess();*/
    }


    //上传2
    @Override
    public CommonResult uploadProjectFile2(Long formNo, String fileId1, String fileName1) {
        FormProjectExecution formProjectExecution=this.getByFormNo(formNo);
        FormProjectExecutionFile file=new FormProjectExecutionFile();
        file.setFileId1(fileId1);
        file.setFileName1(fileName1);
        formProjectExecution.getExecutionFileList().add(file);
        formProjectExecutionRepository.save(formProjectExecution);
        return ResultFactory.commonSuccess();
    }

}
