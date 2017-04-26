package com.sunesoft.lemon.deanery.projectWorkFlow.application;


import com.sunesoft.lemon.deanery.car.application.factory.DeaneryUtil;
import com.sunesoft.lemon.deanery.projectWorkFlow.application.criteria.FormAcceptanceCriteria;
import com.sunesoft.lemon.deanery.projectWorkFlow.application.dto.*;
import com.sunesoft.lemon.deanery.projectWorkFlow.domain.*;
import com.sunesoft.lemon.deanery.scientificRPKU.ScientificRPKU;
import com.sunesoft.lemon.deanery.scientificRPKU.ScientificRPKUDto;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.results.ResultFactory;
import com.sunesoft.lemon.fr.utils.BeanUtils;
import com.sunesoft.lemon.syms.eHr.application.factory.DtoFactory;
import com.sunesoft.lemon.syms.eHr.domain.empInfo.Employee;
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
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zy on 2016/8/19.
 */
@Service("formProjectAcceptanceService")
public class FormProjectAcceptanceServiceImpl extends FormBase2<FormProjectAcceptance,FormProjectAcceptanceDto> implements FormProjectAcceptanceService {

    @Autowired
    FormProjectAcceptanceRepository formProjectAcceptanceRepository;

    @Autowired
    FormHeaderRepository headerRepository;

    @Autowired
    FormHeaderService headerService;

    @Override
    protected CommonResult save(FormProjectAcceptance formProjectAcceptance) {
        formProjectAcceptanceRepository.save(formProjectAcceptance);
        return ResultFactory.commonSuccess();

    }

    @Override
    protected CommonResult update(FormProjectAcceptance formProjectAcceptance) {
        FormProjectAcceptance execution = this.getByFormNo(formProjectAcceptance.getFormNo());
        execution.setProjectNo(formProjectAcceptance.getProjectNo());
        execution.setProjectName(formProjectAcceptance.getProjectName());
        execution.setProjectAcceptanceAdvice(formProjectAcceptance.getProjectAcceptanceAdvice());
        formProjectAcceptanceRepository.save(execution);

        return ResultFactory.commonSuccess();


    }

    @Override
    protected FormProjectAcceptance ConvetDto(FormProjectAcceptanceDto Dto) {
        return DtoFactory.convert(Dto,new FormProjectAcceptance());
    }

    @Override
    protected String getSummery(FormProjectAcceptance formProjectAcceptance) {
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
    protected FormProjectAcceptance getByFormNo(Long formNo) {
        Criteria criterion = getSession().createCriteria(FormProjectAcceptance.class);
        criterion.add(Restrictions.eq("isActive", true));
        criterion.add(Restrictions.eq("formNo", formNo));
        return (FormProjectAcceptance)criterion.uniqueResult();
    }

    protected FormProjectAcceptance getByFormNo1(Long formNo) {
        Criteria criterion = getSession().createCriteria(FormProjectAcceptance.class);
        criterion.add(Restrictions.eq("formNo", formNo));
        return (FormProjectAcceptance)criterion.uniqueResult();
    }
    @Override
    public FormProjectAcceptanceDto getDtoByFormNo(Long formNo){
        Criteria criteria=getSession().createCriteria(FormProjectAcceptance.class);
        criteria.add(Restrictions.eq("isActive", true));
        criteria.add(Restrictions.eq("formNo", formNo));
        FormProjectAcceptance formProjectAcceptance=(FormProjectAcceptance)criteria.uniqueResult();
        FormProjectAcceptanceDto dto=new FormProjectAcceptanceDto();
        BeanUtils.copyProperties(formProjectAcceptance,dto);
        return dto;
    }

    @Override
    public Long getDtoByFormNo1(Long formNo,String instructions){
        Criteria criteria=getSession().createCriteria(FormProjectAcceptance.class);
        criteria.add(Restrictions.eq("isActive", true));
        criteria.add(Restrictions.eq("formNo", formNo));
        List<FormProjectAcceptance> beans = criteria.list();
        for(FormProjectAcceptance formProjectAcceptance:beans) {
                String ins = formProjectAcceptance.getExpertSuggestion();
                if (ins == null || ins == "") {
                    formProjectAcceptance.setExpertSuggestion(instructions);
                } else {
                    ins = ins + "  |  ";
                    formProjectAcceptance.setExpertSuggestion(ins + instructions);
                }
                   formProjectAcceptanceRepository.save(formProjectAcceptance);

        }

        return  1l;
    }
    @Override
    public CommonResult addNewApprove(ApproveFormDto dto, Double price) {
        FormProjectAcceptance al=  this.getByFormNo(dto.getFormNo());
        //al.setPrice();
        formProjectAcceptanceRepository.save(al);
        return doApprove(dto,null);
    }

    @Override
    public List<FormProjectApply> queryProject() {
       Criteria criteria = this.getSession().createCriteria(FormProjectApply.class);
//        FormStatus ap = FormStatus.valueOf(FormStatus.class, "AP");
        criteria.add(Restrictions.eq("formStatus",FormStatus.AP ));
       List<FormProjectApply> list=criteria.list();

//        String  sql=  "select * from syy_oa_form_project_apply where FORM_STATUS=3";
//        Query query = this.getSession().createSQLQuery(sql.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
//        List<FormProjectApply> list=query.list();
        return list;
    }

    @Override
    public CommonResult updateProject(ApproveFormDto dto, FormProjectAcceptanceDto formProjectAcceptanceDto) {
        FormProjectAcceptance al=  this.getByFormNo(dto.getFormNo());
        al.setBeginTime(formProjectAcceptanceDto.getBeginTime());
        al.setEndTime(formProjectAcceptanceDto.getEndTime());
        al.setProjectPlanInfoTxt(formProjectAcceptanceDto.getProjectPlanInfoTxt());
        al.setJoinComopany(formProjectAcceptanceDto.getJoinComopany());
        al.setAssumeCompany(formProjectAcceptanceDto.getAssumeCompany());
        al.setSelfEvaluate(formProjectAcceptanceDto.getSelfEvaluate());
        al.setKeGuanEvaluate(formProjectAcceptanceDto.getKeGuanEvaluate());
        al.setZhuguanEvaluate(formProjectAcceptanceDto.getZhuguanEvaluate());
        al.setProfessorName(formProjectAcceptanceDto.getProfessorName());
        al.setYuanEvaluate(formProjectAcceptanceDto.getYuanEvaluate());
        al.setLeaderWord(formProjectAcceptanceDto.getLeaderWord());
        formProjectAcceptanceRepository.save(al);
        return doApprove(dto,null);
    }

    @Override
    public CommonResult updateProject1(ApproveFormDto dto, FormProjectAcceptanceDto formProjectAcceptanceDto) {
        FormProjectAcceptance al=  this.getByFormNo(dto.getFormNo());
        al.setBeginTime(formProjectAcceptanceDto.getBeginTime());
        al.setEndTime(formProjectAcceptanceDto.getEndTime());
        al.setProjectPlanInfoTxt(formProjectAcceptanceDto.getProjectPlanInfoTxt());
        al.setJoinComopany(formProjectAcceptanceDto.getJoinComopany());
        al.setAssumeCompany(formProjectAcceptanceDto.getAssumeCompany());
        al.setSelfEvaluate(formProjectAcceptanceDto.getSelfEvaluate());
        al.setKeGuanEvaluate(formProjectAcceptanceDto.getKeGuanEvaluate());
        al.setZhuguanEvaluate(formProjectAcceptanceDto.getZhuguanEvaluate());
        al.setProfessorName(formProjectAcceptanceDto.getProfessorName());
        al.setYuanEvaluate(formProjectAcceptanceDto.getYuanEvaluate());
      //  al.setExpertSuggestion(formProjectAcceptanceDto.getExpertSuggestion());
        formProjectAcceptanceRepository.save(al);
        return doApprove(dto,null);
    }

    @Override
    public CommonResult nextProject(ApproveFormDto dto) {
        return doApprove(dto,null);
    }

    @Override
    public FormProjectAcceptanceDto getFormByFormNo(Long formNo) {
        FormProjectAcceptance acceptance = this.getByFormNo(formNo);
        FormProjectAcceptanceDto dto = DtoFactory.convert(acceptance,new FormProjectAcceptanceDto() );
        List<FormAcceptanceProjectFileDto>  dtos = new ArrayList<>();
        if(acceptance.getFormAcceptanceProjectFile()!=null&&acceptance.getFormAcceptanceProjectFile().size()>0){
            for(FormAcceptanceProjectFile file :acceptance.getFormAcceptanceProjectFile()){
                dtos.add(DtoFactory.convert(file,new FormAcceptanceProjectFileDto()));
            }
        }
        dto.setFormAcceptanceProjectFileDto(dtos);
        return  dto;
    }



    @Override
    public CommonResult uploadProjectFile(Long formNo, String fileId, String fileName) {
        FormProjectAcceptance acceptance = this.getByFormNo(formNo);
        FormAcceptanceProjectFile file  = new FormAcceptanceProjectFile();
        file.setFileId(fileId);
        file.setFileName(fileName);
        acceptance.getFormAcceptanceProjectFile().add(file);
        formProjectAcceptanceRepository.save(acceptance);
        return  ResultFactory.commonSuccess();
    }

    @Override
    public PagedResult<ScientificRPKUDto> queryAcceptance(ScientificRPKUCriteria scientificRPKUCriteria){
        Criteria criteria = getSession().createCriteria(ScientificRPKU.class);
        criteria.add(Restrictions.eq("isActive", true));
//        criteria.add(Restrictions.eq("isComplete", true));
        criteria.add(Restrictions.eq("projectYSStatus","2" ));
        criteria.add(Restrictions.eq("projectSBStatus","0" ));
        List<ScientificRPKU> list=criteria.list();
        List<ScientificRPKUDto> dtos=new ArrayList<>();
        for(ScientificRPKU scientificRPKU : list){
            ScientificRPKUDto dto= DeaneryUtil.convertFromListScientificResearchProjectDto(scientificRPKU);
            dtos.add(dto);
        }
        criteria.setFirstResult((scientificRPKUCriteria.getPageNumber() - 1) * scientificRPKUCriteria.getPageSize()).setMaxResults(scientificRPKUCriteria.getPageSize());
        int totalCount = ((Long)criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
        return new PagedResult<ScientificRPKUDto>(dtos, scientificRPKUCriteria.getPageNumber(), scientificRPKUCriteria.getPageSize(),totalCount);
    }

    @Override
    public List<ScientificRPKUDto> ListAcceptance(Long leader){
        Criteria criteria = getSession().createCriteria(ScientificRPKU.class);
        criteria.add(Restrictions.eq("isActive", true));
//        criteria.add(Restrictions.eq("isComplete", true));
        criteria.add(Restrictions.eq("leader",leader));
        criteria.add(Restrictions.eq("projectYSStatus","2" ));
        criteria.add(Restrictions.eq("projectSBStatus","0" ));
        List<ScientificRPKU> list=criteria.list();
        List<ScientificRPKUDto> dtos=new ArrayList<>();
        for(ScientificRPKU scientificRPKU : list){
            ScientificRPKUDto dto= DeaneryUtil.convertFromListScientificResearchProjectDto(scientificRPKU);
            dtos.add(dto);
        }
        return dtos;
    }
    @Override
    public List<Employee> getAllEmployee(){
        Criteria criteria=getSession().createCriteria(Employee.class);
        criteria.add(Restrictions.eq("isActive",true));
        List<Employee> employees=criteria.list();
        return employees;
    }

    @Override
    public List<ScientificRPKUDto> getByIds(String acceptances){
        String[] ids=acceptances.split(",");
        Long[] l=new Long[ids.length];
        for(int i=0;i<ids.length;i++){
            l[i]=Long.parseLong(ids[i]);
        }
        Criteria criteria = getSession().createCriteria(ScientificRPKU.class);
        criteria.add(Restrictions.in("id",l));
        List<ScientificRPKU> list=criteria.list();
        List<ScientificRPKUDto> beans=new ArrayList<>();
        for(int i=0;i<list.size();i++){
            ScientificRPKUDto scientificRPKUDto=DeaneryUtil.convertFromListScientificResearchProjectDto(list.get(i));
            beans.add(scientificRPKUDto);
        }
        return beans;
    }

    @Override
    public Long updateFormProjectAcceptanceById(Long id) {
        FormProjectAcceptance formProjectAcceptance=formProjectAcceptanceRepository.get(id);
        //formProjectAcceptance.setIsActive(false);
        formProjectAcceptanceRepository.save(formProjectAcceptance);
        return 0L;
    }

    @Override
    public FormProjectAcceptanceDto getFormByFormNo1(Long formNo) {

        FormProjectAcceptance acceptance = this.getByFormNo1(formNo);
        FormProjectAcceptanceDto dto = DtoFactory.convert(acceptance,new FormProjectAcceptanceDto() );
        List<FormAcceptanceProjectFileDto>  dtos = new ArrayList<>();
        if(acceptance.getFormAcceptanceProjectFile()!=null&&acceptance.getFormAcceptanceProjectFile().size()>0){
            for(FormAcceptanceProjectFile file :acceptance.getFormAcceptanceProjectFile()){
                dtos.add(DtoFactory.convert(file,new FormAcceptanceProjectFileDto()));
            }
        }
        dto.setFormAcceptanceProjectFileDto(dtos);
        return  dto;
    }

    @Override
    public List<FormProjectAcceptanceDto> getAcceptanceApproves3(FormAcceptanceCriteria formAcceptanceCriteria,String majorType) {
        FormHeaderCriteria headerCriteria = new FormHeaderCriteria();
        headerCriteria.setApproverId(formAcceptanceCriteria.getApproverId());
        headerCriteria.setFormKind("SYY_KG_LC05");
        List<FormStatus> formStatuses = new ArrayList<>();
        formStatuses.add(FormStatus.UA);
        headerCriteria.setPageSize(1000);
        headerCriteria.setArrFormStatus(formStatuses);
        headerCriteria.setApproveStatus(ApproveStatus.U);

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

        Criteria criteria = getSession().createCriteria(FormProjectAcceptance.class);
        criteria.add(Restrictions.eq("isActive", true));
        //criteria.add(Restrictions.eq("clStep", clstep));
        if(!majorType.equals("")){
            criteria.add(Restrictions.like("majorType",majorType, MatchMode.ANYWHERE));
        }
        criteria.add(Restrictions.eq("formStatus", FormStatus.UA));
        criteria.add(Restrictions.in("formNo",formNOs));
        criteria.add(Restrictions.eq("clStep",3));

        /*if(scientificResearchProjectCriteria.getSpecialtyType()!=null && !("").equals(scientificResearchProjectCriteria.getSpecialtyType())){
            criteria.add(Restrictions.like("specialtyType", "%" + scientificResearchProjectCriteria.getSpecialtyType() + "%"));
        }*/

        List<FormProjectAcceptance> beans = criteria.list();

        List<FormProjectAcceptanceDto> dto = new ArrayList<FormProjectAcceptanceDto>();
        for(FormProjectAcceptance formProjectAcceptance : beans){
            FormProjectAcceptanceDto formProjectAcceptanceDto =convertDtoToFormAcceptance(formProjectAcceptance);
            formProjectAcceptanceDto.setDeptName(formProjectAcceptance.getDeptName());
            dto.add(formProjectAcceptanceDto);
        }
        return dto;
    }

    @Override
    public List<FormProjectAcceptanceDto> getAcceptanceApproves4(FormAcceptanceCriteria formAcceptanceCriteria,String majorType) {
        FormHeaderCriteria headerCriteria = new FormHeaderCriteria();
        headerCriteria.setApproverId(formAcceptanceCriteria.getApproverId());
        headerCriteria.setFormKind("SYY_KG_LC05");
        List<FormStatus> formStatuses = new ArrayList<>();
        formStatuses.add(FormStatus.UA);
        headerCriteria.setPageSize(1000);
        headerCriteria.setArrFormStatus(formStatuses);
        headerCriteria.setApproveStatus(ApproveStatus.U);

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

        Criteria criteria = getSession().createCriteria(FormProjectAcceptance.class);
        criteria.add(Restrictions.eq("isActive", true));
        //criteria.add(Restrictions.eq("clStep", clstep));
        criteria.add(Restrictions.eq("formStatus", FormStatus.UA));
        if(!majorType.equals("")){
            criteria.add(Restrictions.like("majorType",majorType,MatchMode.ANYWHERE));
        }
        criteria.add(Restrictions.in("formNo",formNOs));
        criteria.add(Restrictions.eq("clStep",4));

        /*if(scientificResearchProjectCriteria.getSpecialtyType()!=null && !("").equals(scientificResearchProjectCriteria.getSpecialtyType())){
            criteria.add(Restrictions.like("specialtyType", "%" + scientificResearchProjectCriteria.getSpecialtyType() + "%"));
        }*/

        List<FormProjectAcceptance> beans = criteria.list();

        List<FormProjectAcceptanceDto> dto = new ArrayList<FormProjectAcceptanceDto>();
        for(FormProjectAcceptance formProjectAcceptance : beans){
            FormProjectAcceptanceDto formProjectAcceptanceDto =convertDtoToFormAcceptance(formProjectAcceptance);
            formProjectAcceptanceDto.setDeptName(formProjectAcceptance.getDeptName());
            dto.add(formProjectAcceptanceDto);
        }
        return dto;
    }


    @Override
    public List<FormProjectAcceptanceDto> getAcceptanceApproves5kgk(FormAcceptanceCriteria formAcceptanceCriteria,String majorType) {
        FormHeaderCriteria headerCriteria = new FormHeaderCriteria();
        headerCriteria.setApproverId(formAcceptanceCriteria.getApproverId());
        headerCriteria.setFormKind("SYY_KG_LC05");
        List<FormStatus> formStatuses = new ArrayList<>();
        formStatuses.add(FormStatus.UA);
        headerCriteria.setPageSize(1000);
        headerCriteria.setArrFormStatus(formStatuses);
        headerCriteria.setApproveStatus(ApproveStatus.U);

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

        Criteria criteria = getSession().createCriteria(FormProjectAcceptance.class);
        criteria.add(Restrictions.eq("isActive", true));
        //criteria.add(Restrictions.eq("clStep", clstep));
        criteria.add(Restrictions.eq("formStatus", FormStatus.UA));
        if(!majorType.equals("")){
            criteria.add(Restrictions.like("majorType",majorType,MatchMode.ANYWHERE));
        }
        criteria.add(Restrictions.in("formNo",formNOs));
        criteria.add(Restrictions.eq("clStep",5));

        /*if(scientificResearchProjectCriteria.getSpecialtyType()!=null && !("").equals(scientificResearchProjectCriteria.getSpecialtyType())){
            criteria.add(Restrictions.like("specialtyType", "%" + scientificResearchProjectCriteria.getSpecialtyType() + "%"));
        }*/

        List<FormProjectAcceptance> beans = criteria.list();

        List<FormProjectAcceptanceDto> dto = new ArrayList<FormProjectAcceptanceDto>();
        for(FormProjectAcceptance formProjectAcceptance : beans){
            FormProjectAcceptanceDto formProjectAcceptanceDto =convertDtoToFormAcceptance(formProjectAcceptance);
            formProjectAcceptanceDto.setDeptName(formProjectAcceptance.getDeptName());
            dto.add(formProjectAcceptanceDto);
        }
        return dto;
    }

    @Override
    public List<FormProjectAcceptanceDto> getAcceptanceApproves8kgk(FormAcceptanceCriteria formAcceptanceCriteria,String majorType) {
        FormHeaderCriteria headerCriteria = new FormHeaderCriteria();
        headerCriteria.setApproverId(formAcceptanceCriteria.getApproverId());
        headerCriteria.setFormKind("SYY_KG_LC05");
        List<FormStatus> formStatuses = new ArrayList<>();
        formStatuses.add(FormStatus.UA);
        headerCriteria.setPageSize(1000);
        headerCriteria.setArrFormStatus(formStatuses);
        headerCriteria.setApproveStatus(ApproveStatus.U);

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

        Criteria criteria = getSession().createCriteria(FormProjectAcceptance.class);
        criteria.add(Restrictions.eq("isActive", true));
        //criteria.add(Restrictions.eq("clStep", clstep));
        criteria.add(Restrictions.eq("formStatus", FormStatus.UA));
        if(!majorType.equals("")){
            criteria.add(Restrictions.like("majorType",majorType,MatchMode.ANYWHERE));
        }
        criteria.add(Restrictions.in("formNo",formNOs));
        criteria.add(Restrictions.eq("clStep",8));

        /*if(scientificResearchProjectCriteria.getSpecialtyType()!=null && !("").equals(scientificResearchProjectCriteria.getSpecialtyType())){
            criteria.add(Restrictions.like("specialtyType", "%" + scientificResearchProjectCriteria.getSpecialtyType() + "%"));
        }*/

        List<FormProjectAcceptance> beans = criteria.list();

        List<FormProjectAcceptanceDto> dto = new ArrayList<FormProjectAcceptanceDto>();
        for(FormProjectAcceptance formProjectAcceptance : beans){
            FormProjectAcceptanceDto formProjectAcceptanceDto =convertDtoToFormAcceptance(formProjectAcceptance);
            formProjectAcceptanceDto.setDeptName(formProjectAcceptance.getDeptName());
            dto.add(formProjectAcceptanceDto);
        }
        return dto;
    }


    @Override
    public List<FormProjectAcceptanceDto> getAcceptanceApproves5(FormAcceptanceCriteria formAcceptanceCriteria,String majorType) {
        FormHeaderCriteria headerCriteria = new FormHeaderCriteria();
        headerCriteria.setApproverId(formAcceptanceCriteria.getApproverId());
        headerCriteria.setFormKind("SYY_KG_LC05");
        List<FormStatus> formStatuses = new ArrayList<>();
        formStatuses.add(FormStatus.UA);
        headerCriteria.setPageSize(1000);
        headerCriteria.setArrFormStatus(formStatuses);
        headerCriteria.setApproveStatus(ApproveStatus.U);

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

        Criteria criteria = getSession().createCriteria(FormProjectAcceptance.class);
        criteria.add(Restrictions.eq("isActive", true));
        //criteria.add(Restrictions.eq("clStep", clstep));
        if(!majorType.equals("")){
            criteria.add(Restrictions.like("majorType",majorType,MatchMode.ANYWHERE));
        }
        criteria.add(Restrictions.eq("formStatus", FormStatus.UA));
        criteria.add(Restrictions.in("formNo",formNOs));
        criteria.add(Restrictions.eq("clStep",7));

        /*if(scientificResearchProjectCriteria.getSpecialtyType()!=null && !("").equals(scientificResearchProjectCriteria.getSpecialtyType())){
            criteria.add(Restrictions.like("specialtyType", "%" + scientificResearchProjectCriteria.getSpecialtyType() + "%"));
        }*/

        List<FormProjectAcceptance> beans = criteria.list();

        List<FormProjectAcceptanceDto> dto = new ArrayList<FormProjectAcceptanceDto>();
        for(FormProjectAcceptance formProjectAcceptance : beans){
            FormProjectAcceptanceDto formProjectAcceptanceDto =convertDtoToFormAcceptance(formProjectAcceptance);
            formProjectAcceptanceDto.setDeptName(formProjectAcceptance.getDeptName());
            dto.add(formProjectAcceptanceDto);
        }
        return dto;
    }

    @Override
    public CommonResult updateProject0(ApproveFormDto dto) {
        return super.doApprove(dto, null);
    }

    @Override
    public CommonResult updateProject1(ApproveFormDto dto) {
        return super.doApprove(dto, null);
    }

    @Override
    public CommonResult updateAcceptProject(ApproveFormDto dto, String empid) {
        if (empid!=""){
            FormHeader header = formHeaderRepository.get(dto.getFormNo());
            header.setContent( empid);
            formHeaderRepository.save(header);
            /*List<Long> emplist = new ArrayList<>();
            if(empid.indexOf(",") >= 0){
                List listid = new ArrayList();
                String[] empids = empid.split(",");
                for (String id : empids) {
                    emplist.add(Long.parseLong(id));
                }
            }else{
                emplist.add(Long.parseLong(empid));
            }
            this.resetNextApprover(dto.getFormNo(), emplist);*/
        }
        return super.doApprove(dto, null);
    }

    @Override
    public CommonResult updateProject001(ApproveFormDto dto) {
        FormHeader header = formHeaderRepository.get(dto.getFormNo());
        List<Long> emplist = new ArrayList<>();
        String empid= header.getContent();
        if(empid.indexOf(",") >= 0){
            List listid = new ArrayList();
            String[] empids = empid.split(",");
            for (String id : empids) {
                emplist.add(Long.parseLong(id));
            }
        }else{
            emplist.add(Long.parseLong(empid));
        }
        this.resetNextApprover(dto.getFormNo(), emplist);
        return super.doApprove(dto, null);
    }

    @Override
    public CommonResult updateacceptProject1(ApproveFormDto dto, String empid) {
        if (empid!=""){
            FormHeader header = formHeaderRepository.get(dto.getFormNo());
            header.setContent( empid);
            formHeaderRepository.save(header);
            List<Long> emplist = new ArrayList<>();
            if(empid.indexOf(",") >= 0){
                List listid = new ArrayList();
                String[] empids = empid.split(",");
                for (String id : empids) {
                    emplist.add(Long.parseLong(id));
                }
            }else{
                emplist.add(Long.parseLong(empid));
            }
            this.resetNextApprover(dto.getFormNo(), emplist);
        }
        return super.doApprove(dto, null);
    }

    @Override
    public CommonResult updateAcceptProjectSu(ApproveFormDto dto, String empid) {
        if (empid!=""){
            FormHeader header = formHeaderRepository.get(dto.getFormNo());
            header.setContent( empid);
            formHeaderRepository.save(header);
            List<Long> emplist = new ArrayList<>();
            if(empid.indexOf(",") >= 0){
                List listid = new ArrayList();
                String[] empids = empid.split(",");
                for (String id : empids) {
                    emplist.add(Long.parseLong(id));
                }
            }else{
                emplist.add(Long.parseLong(empid));
            }
          //  this.resetNextApprover(dto.getFormNo(), emplist);
        }
        return super.doApprove(dto, null);
    }

    @Override
    public CommonResult updateAcceptProjectLeader(ApproveFormDto dto, String empid) {
        if (empid!=""){
            FormHeader header = formHeaderRepository.get(dto.getFormNo());
            header.setContent( empid);
            formHeaderRepository.save(header);
            List<Long> emplist = new ArrayList<>();
            if(empid.indexOf(",") >= 0){
                List listid = new ArrayList();
                String[] empids = empid.split(",");
                for (String id : empids) {
                    emplist.add(Long.parseLong(id));
                }
            }else{
                emplist.add(Long.parseLong(empid));
            }
            //this.resetNextApprover(dto.getFormNo(), emplist);
        }
        return super.doApprove(dto, null);
    }

    @Override
    public List<FormProjectAcceptanceDto> getAcceptanceApproveszj(FormAcceptanceCriteria formAcceptanceCriteria, String majorType,Long userId) {
        FormHeaderCriteria headerCriteria = new FormHeaderCriteria();
        headerCriteria.setApproverId(formAcceptanceCriteria.getApproverId());
        headerCriteria.setFormKind("SYY_KG_LC05");
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

        Criteria criteria = getSession().createCriteria(FormProjectAcceptance.class);
        criteria.add(Restrictions.eq("isActive", true));
        //criteria.add(Restrictions.eq("clStep", clstep));
        if(!majorType.equals("")){
            criteria.add(Restrictions.like("majorType",majorType,MatchMode.ANYWHERE));
        }
        criteria.add(Restrictions.eq("formStatus", FormStatus.UA));
        criteria.add(Restrictions.in("formNo",formNOs));
        criteria.add(Restrictions.eq("clStep",6));

        /*if(scientificResearchProjectCriteria.getSpecialtyType()!=null && !("").equals(scientificResearchProjectCriteria.getSpecialtyType())){
            criteria.add(Restrictions.like("specialtyType", "%" + scientificResearchProjectCriteria.getSpecialtyType() + "%"));
        }*/

        List<FormProjectAcceptance> beans = criteria.list();

        List<FormProjectAcceptanceDto> dto = new ArrayList<FormProjectAcceptanceDto>();
        for(FormProjectAcceptance formProjectAcceptance : beans){
            FormProjectAcceptanceDto formProjectAcceptanceDto =convertDtoToFormAcceptance(formProjectAcceptance);
            formProjectAcceptanceDto.setDeptName(formProjectAcceptance.getDeptName());
            dto.add(formProjectAcceptanceDto);
        }
        return dto;
    }

    @Override
    public FormProjectAcceptance getAcceptance(String projectNo,Long formNo) {
        Criteria criteria=this.getSession().createCriteria(FormProjectAcceptance.class);
        criteria.add(Restrictions.eq("projectNo",projectNo));
        criteria.add(Restrictions.eq("formNo",formNo));
        List<FormProjectAcceptance> acceptances=criteria.list();
        return acceptances.get(0);
    }

    public static FormProjectAcceptanceDto convertDtoToFormAcceptance(FormProjectAcceptance formProjectAcceptance){

        FormProjectAcceptanceDto dto = new FormProjectAcceptanceDto();
        BeanUtils.copyProperties(formProjectAcceptance,dto);
        return dto;
    }

}
