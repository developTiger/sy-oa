package com.sunesoft.lemon.deanery.deliverables.application;

import com.sun.org.apache.bcel.internal.generic.RETURN;
import com.sunesoft.lemon.deanery.car.application.factory.DeaneryUtil;
import com.sunesoft.lemon.deanery.deliverables.application.criteria.DeliverResearchCriteria;
import com.sunesoft.lemon.deanery.deliverables.application.dto.FormDeliverAplyExportDto;
import com.sunesoft.lemon.deanery.deliverables.application.dto.FormDeliverApplyDto;
import com.sunesoft.lemon.deanery.deliverables.domain.FormDeliverApply;
import com.sunesoft.lemon.deanery.deliverables.domain.FormDeliverApplyRepository;
import com.sunesoft.lemon.deanery.deliverables.domain.hibernate.FormDeliverApplyFile;
import com.sunesoft.lemon.deanery.project.domain.ScientficApproveFile;
import com.sunesoft.lemon.deanery.project.domain.ScientificResearchProject;
import com.sunesoft.lemon.deanery.projectAchievement.domain.ProjectAchievement;
import com.sunesoft.lemon.deanery.projectAchievement.domain.ProjectAchievementRepository;
import com.sunesoft.lemon.deanery.projectWorkFlow.domain.FormOpenProjectFile;
import com.sunesoft.lemon.deanery.scientificRPKU.ScientificRPKU;
import com.sunesoft.lemon.deanery.scientificRPKU.ScientificRPKUDto;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.results.ResultFactory;
import com.sunesoft.lemon.fr.utils.BeanUtils;
import com.sunesoft.lemon.fr.utils.DateHelper;
import com.sunesoft.lemon.syms.eHr.application.EmployeeService;
import com.sunesoft.lemon.syms.eHr.application.factory.DtoFactory;
import com.sunesoft.lemon.syms.eHr.domain.empInfo.Employee;
import com.sunesoft.lemon.syms.workflow.application.FormBase2;
import com.sunesoft.lemon.syms.workflow.application.FormHeaderService;
import com.sunesoft.lemon.syms.workflow.application.criteria.FormHeaderCriteria;
import com.sunesoft.lemon.syms.workflow.application.dtos.ApproveFormDto;
import com.sunesoft.lemon.syms.workflow.application.dtos.FormHeaderDto;
import com.sunesoft.lemon.syms.workflow.domain.FormApprover;
import com.sunesoft.lemon.syms.workflow.domain.FormHeader;
import com.sunesoft.lemon.syms.workflow.domain.enums.ApproveStatus;
import com.sunesoft.lemon.syms.workflow.domain.enums.FormStatus;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by swb on 2016/8/30.
 */
@Service("formDeliverApplyService")
public class FormDeliverApplyServiceImpl extends FormBase2<FormDeliverApply,FormDeliverApplyDto> implements FormDeliverApplyService{
    @Autowired
    FormHeaderService headerService;
    @Autowired
    FormDeliverApplyRepository formDeliverApplyRepository;

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private ProjectAchievementRepository projectAchievementRepository;

    @Override
    protected CommonResult save(FormDeliverApply formDeliverApply) {
        formDeliverApplyRepository.save(formDeliverApply);
        return ResultFactory.commonSuccess();
    }

    @Override
    protected CommonResult update(FormDeliverApply formDeliverApply) {
        return null;
    }

    @Override
    protected FormDeliverApply ConvetDto(FormDeliverApplyDto dto) {
        FormDeliverApply formDeliverApply=new FormDeliverApply();
        BeanUtils.copyProperties(dto,formDeliverApply);
        if(dto.getProjectPlanInfo()!=null) {
            formDeliverApply.setProjectPlanInfoTxt(dto.getProjectPlanInfo().getBytes(Charset.forName("UTF-8")));
        }
        return formDeliverApply;
//        return DtoFactory.convert(dto, new FormDeliverApply());
    }

    @Override
    protected String getSummery(FormDeliverApply formDeliverApply) {
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
    protected FormDeliverApply getByFormNo(Long formNo) {
        Criteria criteria=getSession().createCriteria(FormDeliverApply.class);
        criteria.add(Restrictions.eq("isActive", true));
        criteria.add(Restrictions.eq("formNo",formNo));
        FormDeliverApply formDeliverApply = (FormDeliverApply) criteria.uniqueResult();
        return formDeliverApply;
    }

    @Override
    public CommonResult addDeliverAndApprove(ApproveFormDto approveFormDto) {
        return null;
    }

    @Override
    public List<FormDeliverApply> queryDeliver() {
        Criteria criteria=this.getSession().createCriteria(FormDeliverApply.class);
        List<FormDeliverApply> list=criteria.list();
        return list;
    }

    //第一步审批
    @Override
    public CommonResult updateDeliverForm1(ApproveFormDto dto) {
        return super.doApprove(dto,null);
    }

    //第二步审批操作通过
    @Override
    public CommonResult updateDeliverForm2(ApproveFormDto dto,Long formNo,String proficientOpinion) {
        FormDeliverApply apply=this.getByFormNo(dto.getFormNo());
        apply.setProficientOpinion(proficientOpinion);
        formDeliverApplyRepository.save(apply);
        return super.doApprove(dto,null);
    }

    //第三步审批操作
    @Override
    public CommonResult updateDeliverForm3(ApproveFormDto dto) {
        return super.doApprove(dto,null);
    }

    @Override
    public CommonResult updateDeliverForm31(ApproveFormDto dto, String opinion31) {
        FormDeliverApply apply=this.getByFormNo(dto.getFormNo());
//        apply.setOpinion31(opinion31);
        formDeliverApplyRepository.save(apply);
        return super.doApprove(dto,null);
    }

    @Override
    public CommonResult updateDeliverForm4(ApproveFormDto dto,String opinion4) {
        FormDeliverApply apply=this.getByFormNo(dto.getFormNo());
//        apply.setOpinion4(opinion4);
        formDeliverApplyRepository.save(apply);
        return super.doApprove(dto,null);
    }

    @Override
    public CommonResult updateDeliverForm5(ApproveFormDto dto,String opinion5) {
        FormDeliverApply apply=this.getByFormNo(dto.getFormNo());
//        apply.setOpinion5(opinion5);
        formDeliverApplyRepository.save(apply);
        return super.doApprove(dto,null);
    }

    @Override
    public CommonResult nextDeliverForm(ApproveFormDto dto) {
        return null;
    }

    @Override
    public CommonResult uploadProjectFile(Long formNo, String fileId, String fileName) {
        FormDeliverApply apply = this.getByFormNo(formNo);
        FormOpenProjectFile file  = new FormOpenProjectFile();
        file.setFileId(fileId);
        file.setFileName(fileName);
//        apply.getFormOpenProjectFiles().add(file);
        formDeliverApplyRepository.save(apply);
        return  ResultFactory.commonSuccess();
    }

    @Override
    public FormDeliverApplyDto downloadByOrderId(String id) {
        Criteria criteria = getSession().createCriteria(FormDeliverApply.class);
        criteria.add(Restrictions.eq("formNo",Long.parseLong(id)));
        List<FormDeliverApply> list=criteria.list();
        if(list!=null && list.size()>0){
            FormDeliverApplyDto dto= DeaneryUtil.convertFromFormDeliverApplyDto(list.get(0));
            return dto;
        }else{
            return null;
        }
    }

    @Override
    public PagedResult<FormDeliverApplyDto> getDeliverApproves1(DeliverResearchCriteria deliverResearchCriteria) {
        Criteria criteria = getSession().createCriteria(FormDeliverApply.class);
        criteria.add(Restrictions.eq("isActive", true));
        criteria.add(Restrictions.eq("clStep", 3));
        int totalCount = ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
        criteria.setProjection(null);
        criteria.setFirstResult((deliverResearchCriteria.getPageNumber() - 1) * deliverResearchCriteria.getPageSize()).setMaxResults(deliverResearchCriteria.getPageSize());
        List<FormDeliverApply> beans = criteria.list();

        List<FormDeliverApplyDto> dto = new ArrayList<FormDeliverApplyDto>();
        for(FormDeliverApply deliverApply : beans){
            FormDeliverApplyDto deliverApplyDto = DeaneryUtil.convertFromFormDeliverApplyDto(deliverApply);
//            deliverApplyDto.setResearchOrder(deliverApply.getResearchOrder());
            dto.add(deliverApplyDto);
        }
        return new PagedResult<FormDeliverApplyDto>(dto, deliverResearchCriteria.getPageNumber(), deliverResearchCriteria.getPageSize(), totalCount);
    }

    @Override
    public PagedResult<FormDeliverApplyDto> getDeliverApproves2(DeliverResearchCriteria deliverResearchCriteria) {
        Criteria criteria = getSession().createCriteria(FormDeliverApply.class);
        criteria.add(Restrictions.eq("isActive", true));
        criteria.add(Restrictions.eq("clStep", 4));
        int totalCount = ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
        criteria.setProjection(null);
        criteria.setFirstResult((deliverResearchCriteria.getPageNumber() - 1) * deliverResearchCriteria.getPageSize()).setMaxResults(deliverResearchCriteria.getPageSize());
        List<FormDeliverApply> beans = criteria.list();

        List<FormDeliverApplyDto> dto = new ArrayList<FormDeliverApplyDto>();
        for(FormDeliverApply deliverApply : beans){
            FormDeliverApplyDto deliverApplyDto = DeaneryUtil.convertFromFormDeliverApplyDto(deliverApply);
//            deliverApplyDto.setResearchOrder(deliverApply.getResearchOrder());
            dto.add(deliverApplyDto);
        }
        return new PagedResult<FormDeliverApplyDto>(dto, deliverResearchCriteria.getPageNumber(), deliverResearchCriteria.getPageSize(), totalCount);
    }

    @Override
    public PagedResult<FormDeliverApplyDto> getDeliverApproves3(DeliverResearchCriteria deliverResearchCriteria) {
        Criteria criteria = getSession().createCriteria(FormDeliverApply.class);
        criteria.add(Restrictions.eq("isActive", true));
        criteria.add(Restrictions.eq("clStep", 5));
        int totalCount = ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
        criteria.setProjection(null);
        criteria.setFirstResult((deliverResearchCriteria.getPageNumber() - 1) * deliverResearchCriteria.getPageSize()).setMaxResults(deliverResearchCriteria.getPageSize());
        List<FormDeliverApply> beans = criteria.list();

        List<FormDeliverApplyDto> dto = new ArrayList<FormDeliverApplyDto>();
        for(FormDeliverApply deliverApply : beans){
            FormDeliverApplyDto deliverApplyDto = DeaneryUtil.convertFromFormDeliverApplyDto(deliverApply);
//            deliverApplyDto.setResearchOrder(deliverApply.getResearchOrder());
            dto.add(deliverApplyDto);
        }
        return new PagedResult<FormDeliverApplyDto>(dto, deliverResearchCriteria.getPageNumber(), deliverResearchCriteria.getPageSize(), totalCount);
    }

    @Override
    public CommonResult updateDeliver(ApproveFormDto dto) {
        FormDeliverApply formDeliverApply=this.getByFormNo(dto.getFormNo());
        formDeliverApplyRepository.save(formDeliverApply);
        return super.doApprove(dto,null);
    }

    @Override
    public FormDeliverApplyDto getFormByFormNo(Long formNo) {
        FormDeliverApply apply=this.getByFormNo(formNo);
        FormDeliverApplyDto dto=DtoFactory.convert(apply,new FormDeliverApplyDto());
        List<FormDeliverApplyFile> formDeliverApplyFiles=new ArrayList<>();
        for(FormDeliverApplyFile file:apply.getFormDeliverApplyFiles()){
            formDeliverApplyFiles.add(file);
        }
        dto.setFormDeliverApplyFiles(formDeliverApplyFiles);
//        DateFormat df=new SimpleDateFormat("yyyy-MM-dd ");
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf2=new SimpleDateFormat("yyyy-MM-dd ");


        if(apply.getBeginDate()!=null){
            dto.setBeginTime(sdf2.format(apply.getBeginDate()));
        }

        if(apply.getEndDate()!=null){
            dto.setEndTime(sdf2.format(apply.getEndDate()));
        }
        try {
            if(apply.getProjectPlanInfoTxt()!=null){
                dto.setProjectPlanInfo(new String(apply.getProjectPlanInfoTxt(),"UTF-8"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return dto;
    }
    @Override
    public List<FormDeliverApplyDto> getFormDeliverApplyByClstep(){
        Criteria criteria=getSession().createCriteria(FormDeliverApply.class);
        criteria.add(Restrictions.eq("isActive", true));
        criteria.add(Restrictions.eq("clStep", 3));
        criteria.add(Restrictions.eq("formStatus", FormStatus.UA));
        List<FormDeliverApply> list=criteria.list();
        List<FormDeliverApplyDto> list2=new ArrayList<>();
        for (FormDeliverApply formDeliverApply:list
                ) {
            FormDeliverApplyDto dto=new FormDeliverApplyDto();
            BeanUtils.copyProperties(formDeliverApply,dto);
            dto.setFormDeliverApplyFiles(null);
            list2.add(dto);
        }
        return list2;
    }

    @Override
    public CommonResult updateProject1(ApproveFormDto dto,String empid) {
        if (empid!=""){
           FormHeader header = formHeaderRepository.get(dto.getFormNo());
           header.setContent(empid);
           formHeaderRepository.save(header);
        }
        return super.doApprove(dto, null);
    }

    @Override
    public CommonResult updateProject2(ApproveFormDto dto) {
           FormHeader header = formHeaderRepository.get(dto.getFormNo());
            String empid = header.getContent();
            List<Long> emplist = new ArrayList<>();
            if(empid.indexOf(",") >= 0){
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
    public CommonResult updateProject2(ApproveFormDto dto, String s){
        return super.doApprove(dto, null);
    }

    @Override
    public ScientificRPKUDto getfdaByFormNo(String formNo) {
        Criteria criteria=getSession().createCriteria(ScientificRPKU.class);
        criteria.add(Restrictions.eq("projectNo", formNo));
        List<ScientificRPKU> list=criteria.list();
        if(list!=null){
            ScientificRPKUDto scientificRPKUDto=DeaneryUtil.convertFromListScientificResearchProjectDto(list.get(0));
            return scientificRPKUDto;
        }
        return null;
    }




    @Override
    public List<FormDeliverApplyDto> getFormDeliverApplyByClstep4() {
        Criteria criteria=getSession().createCriteria(FormDeliverApply.class);
        criteria.add(Restrictions.eq("isActive", true));
        criteria.add(Restrictions.eq("clStep", 4));
        criteria.add(Restrictions.eq("formStatus", FormStatus.UA));
        List<FormDeliverApply> list=criteria.list();
        List<FormDeliverApplyDto> list2=new ArrayList<>();
        for (FormDeliverApply formDeliverApply:list
                ) {
            FormDeliverApplyDto dto=new FormDeliverApplyDto();
            BeanUtils.copyProperties(formDeliverApply,dto);
            dto.setFormDeliverApplyFiles(null);
            list2.add(dto);
        }
        return list2;
    }



    @Override
    public List<FormDeliverApplyDto> getFormDeliverApplyByClstep5(Long approveId) {

        FormHeaderCriteria headerCriteria = new FormHeaderCriteria();
        headerCriteria.setApproverId(approveId);
        headerCriteria.setFormKind("SYY_KG_LC06");
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
        Criteria criteria=getSession().createCriteria(FormDeliverApply.class);
        criteria.add(Restrictions.eq("isActive", true));
        criteria.add(Restrictions.eq("clStep", 5));
        criteria.add(Restrictions.in("formNo",formNOs));
        List<FormDeliverApply> list=criteria.list();
        List<FormDeliverApplyDto> list2=new ArrayList<>();
        for (FormDeliverApply formDeliverApply:list
                ) {
            FormDeliverApplyDto dto=new FormDeliverApplyDto();
            BeanUtils.copyProperties(formDeliverApply,dto);
            dto.setFormDeliverApplyFiles(null);
            list2.add(dto);
        }
        return list2;
    }
    @Override
    public CommonResult nextProject1(ApproveFormDto dto, String instructions) {
        Criteria criterion = getSession().createCriteria(FormDeliverApply.class);
        criterion.add(Restrictions.eq("formNo", dto.getFormNo()));
        List<FormDeliverApply> beans = criterion.list();
        if(beans!=null){
            FormDeliverApply formDeliverApply=beans.get(0);
            String instr=formDeliverApply.getInstructions();
            if(instr!=null && !instr.equals("")){
                instructions=instr+" | "+instructions;
            }
            formDeliverApply.setInstructions(instructions);
            formDeliverApplyRepository.save(formDeliverApply);
            return super.doApprove(dto, null);
        }
        return null;
    }
    @Override
    public CommonResult nextProject2(ApproveFormDto dto, String clstep) {
        return super.doApprove(dto,null);
    }

    @Override
    public List<FormDeliverApplyDto> getFormDeliverApplyByClstep6(Long approveId) {
        Criteria criteria=getSession().createCriteria(FormDeliverApply.class);
        criteria.add(Restrictions.eq("isActive", true));
        criteria.add(Restrictions.eq("clStep", 6));
        criteria.add(Restrictions.eq("formStatus", FormStatus.UA));
        List<FormDeliverApply> list=criteria.list();
        List<FormDeliverApplyDto> list2=new ArrayList<>();
        List<ScientficApproveFile> listFile=new ArrayList<>();
        for (FormDeliverApply formDeliverApply:list
                ) {
            FormDeliverApplyDto dto=new FormDeliverApplyDto();
            BeanUtils.copyProperties(formDeliverApply,dto);
            dto.setFormDeliverApplyFiles(null);
            list2.add(dto);
        }
        return list2;
    }

    @Override
    public CommonResult nextProject3(ApproveFormDto dto, String fileId,String fileName,String awardTime) {
        Criteria criteria=getSession().createCriteria(FormDeliverApply.class);
        criteria.add(Restrictions.eq("formNo", dto.getFormNo()));
        FormDeliverApply formDeliverApply=(FormDeliverApply)criteria.uniqueResult();
        Date awardDate=null;
        try {
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            awardDate=sdf.parse(awardTime);
        }catch (Exception e){
            e.printStackTrace();
        }
        formDeliverApply.setAwardDate(awardDate);
        FormDeliverApplyFile file  = new FormDeliverApplyFile();
        file.setFileId(fileId);
        file.setFileName(fileName);
        //formDeliverApply.getScientficApproveFileList().add(file);
        List<FormDeliverApplyFile> listFile=new ArrayList<>();
        listFile.add(file);
        formDeliverApply.setFormDeliverApplyFiles(listFile);
        formDeliverApplyRepository.save(formDeliverApply);
        //流程结束后在成果库加入数据
        ProjectAchievement projectAchievement=getByDeliver(formDeliverApply);
        projectAchievementRepository.save(projectAchievement);
        return super.doApprove(dto,null);
    }

    public ProjectAchievement getByDeliver(FormDeliverApply formDeliverApply){
        ProjectAchievement projectAchievement=new ProjectAchievement();
        BeanUtils.copyProperties(formDeliverApply,projectAchievement);
        projectAchievement.setId(null);
        projectAchievement.setDeliverId(formDeliverApply.getId());
        return projectAchievement;
    }


    @Override
    public List<FormDeliverAplyExportDto> getFormDeliverApplyByFormNo(String formKind) {
        Criteria criteria=getSession().createCriteria(FormDeliverApply.class);
        criteria.add(Restrictions.eq("formNo", Long.parseLong(formKind)));
        List<FormDeliverApply>  formDeliverApply=criteria.list();
        List<FormDeliverAplyExportDto> list=new ArrayList<>();
        for(FormDeliverApply formDeliverApply1:formDeliverApply){
            FormDeliverAplyExportDto formDeliverApplyDto=new FormDeliverAplyExportDto();
            BeanUtils.copyProperties(formDeliverApply1,formDeliverApplyDto);
            // formDeliverApplyDto.setFormDeliverApplyFiles(null);
            list.add(formDeliverApplyDto);
        }

        return list ;
    }

    @Override
    public CommonResult saveDeliver(List<FormDeliverAplyExportDto> items, String formNo) {
        String number[]=formNo.split(",");
        Long no[]=new Long[number.length];
        for(int i=0;i<number.length;i++){
            no[i]=Long.parseLong(number[i]);
        }
        Criteria criteria=getSession().createCriteria(FormDeliverApply.class);
        criteria.add(Restrictions.in("formNo",no));
        List<FormDeliverApply>  formDeliverApply=criteria.list();
        for (int i = 0; i < formDeliverApply.size(); i++) {
            //FormDeliverApply formDeliverApply=new FormDeliverApply();
//            BeanUtils.copyProperties(list.get(i),scientificResearchProject);
            formDeliverApply.get(i).setLeader(items.get(i).getLeader());
            formDeliverApply.get(i).setGroupMembers(items.get(i).getGroupMembers());
            formDeliverApply.get(i).setProjectName(items.get(i).getProjectName());
            formDeliverApply.get(i).setProjectNo(items.get(i).getProjectNo());
            formDeliverApply.get(i).setAssumeCompany(items.get(i).getAssumeCompany());
            // scientificResearchProject.setProjectPlanInfoTxt(list.get(i).getProjectPlanInfo().getBytes(Charset.forName("UTF-8")));
            formDeliverApplyRepository.save(formDeliverApply.get(i));
        }
        return ResultFactory.commonSuccess();
    }

    @Override
    public CommonResult nextProject4(ApproveFormDto dto, String awardTime){
        Criteria criteria=getSession().createCriteria(FormDeliverApply.class);
        criteria.add(Restrictions.eq("formNo", dto.getFormNo()));
        FormDeliverApply formDeliverApply=(FormDeliverApply)criteria.uniqueResult();
        Date awardDate=null;
        try {
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            awardDate=sdf.parse(awardTime);
        }catch (Exception e){
            e.printStackTrace();
        }
        formDeliverApply.setAwardDate(awardDate);
        formDeliverApplyRepository.save(formDeliverApply);
        ProjectAchievement projectAchievement=getByDeliver(formDeliverApply);
        projectAchievementRepository.save(projectAchievement);
        return super.doApprove(dto,null);
    }
    @Override
    public CommonResult nextProject11(ApproveFormDto dto, String kong){
        return super.doApprove(dto, null);
    }


    @Override
    public CommonResult updateDeliverForm6(ApproveFormDto dto,String empid) {
        if (empid!=""){
//            List<Long> emplist = new ArrayList<>();
//            if(empid.indexOf(",") >= 0){
//                String[] empids = empid.split(",");
//                for (String id : empids) {
//                    emplist.add(Long.parseLong(id));
//                }
//            }else{
//                emplist.add(Long.parseLong(empid));
//            }
//            this.resetNextApprover(dto.getFormNo(), emplist);
            FormHeader header = formHeaderRepository.get(dto.getFormNo());
            header.setContent(empid);
            formHeaderRepository.save(header);
            return super.doApprove(dto,null);
        }else {
            return null;
        }
    }

    //第一步批量数据
    @Override
    public List<FormDeliverApplyDto> getFormDeliverApplyByClstep1(String specialtyType,List<Long> l){
        Criteria criteria=getSession().createCriteria(FormDeliverApply.class);
//        criteria.add(Restrictions.eq("appUserId", id));
        if(specialtyType!=null && !specialtyType.equals("")){
            criteria.add(Restrictions.like("specialtyType", "%"+specialtyType+"%"));
        }
//        criteria.add(Restrictions.eq("isActive", true));
        criteria.add(Restrictions.eq("clStep", 1));
//        criteria.add(Restrictions.eq("formStatus", FormStatus.UA));

        criteria.add(Restrictions.eq("formStatus", FormStatus.UA));
        if(l!=null && l.size()>0) {
            criteria.add(Restrictions.in("formNo", l));
        }
        List<FormDeliverApply> list=null;
        if(l!=null && l.size()>0){
            list=criteria.list();
        }
        List<FormDeliverApplyDto> list2=new ArrayList<>();
        if(list!=null){
            for (FormDeliverApply formDeliverApply:list
                    ) {
                FormDeliverApplyDto dto=new FormDeliverApplyDto();
                BeanUtils.copyProperties(formDeliverApply,dto);
                dto.setFormDeliverApplyFiles(null);
                list2.add(dto);
            }
        }

        return list2;
    }

    //第二步批量数据
    @Override
    public List<FormDeliverApplyDto> getFormDeliverApplyByClstep2(String specialtyType,List<Long> l){
        Criteria criteria=getSession().createCriteria(FormDeliverApply.class);
        if(specialtyType!=null && !specialtyType.equals("")){
            criteria.add(Restrictions.like("specialtyType", "%"+specialtyType+"%"));
        }
        criteria.add(Restrictions.eq("clStep", 2));
        if(l!=null && l.size()>0){
            criteria.add(Restrictions.in("formNo", l));
        }
        criteria.add(Restrictions.ne("formStatus", FormStatus.WD));
        List<FormDeliverApply> list=null;
        if(l!=null && l.size()>0) {
            list=criteria.list();
        }
        List<FormDeliverApplyDto> list2=new ArrayList<>();
        if(list!=null) {
            for (FormDeliverApply formDeliverApply : list
                    ) {
                FormDeliverApplyDto dto = new FormDeliverApplyDto();
                BeanUtils.copyProperties(formDeliverApply, dto);
                dto.setFormDeliverApplyFiles(null);
                list2.add(dto);
            }
        }
        return list2;
    }

    //第三步批量数据
    @Override
    public List<FormDeliverApplyDto> getFormDeliverApplyByClstep3(String specialtyType,List<Long> l){
        Criteria criteria=getSession().createCriteria(FormDeliverApply.class);
        if(specialtyType!=null && !specialtyType.equals("")){
            criteria.add(Restrictions.like("specialtyType", "%"+specialtyType+"%"));
        }
        criteria.add(Restrictions.eq("clStep", 3));
        if(l!=null && l.size()>0) {
            criteria.add(Restrictions.in("formNo", l));
        }
        criteria.add(Restrictions.ne("formStatus", FormStatus.RJ));
        List<FormDeliverApply> list=null;
        if(l!=null && l.size()>0) {
            list=criteria.list();
        }
        List<FormDeliverApplyDto> list2=new ArrayList<>();
        if(list!=null) {
            for (FormDeliverApply formDeliverApply : list
                    ) {
                FormDeliverApplyDto dto = new FormDeliverApplyDto();
                BeanUtils.copyProperties(formDeliverApply, dto);
                dto.setFormDeliverApplyFiles(null);
                list2.add(dto);
            }
        }
        return list2;
    }

    //第四步批量数据
    @Override
    public List<FormDeliverApplyDto> getFormDeliverApplyByClstep4(String specialtyType,List<Long> l){
        Criteria criteria=getSession().createCriteria(FormDeliverApply.class);
        if(specialtyType!=null && !specialtyType.equals("")){
            criteria.add(Restrictions.like("specialtyType", "%"+specialtyType+"%"));
        }
//        criteria.add(Restrictions.eq("isActive", true));
        criteria.add(Restrictions.eq("clStep", 4));
//        criteria.add(Restrictions.eq("formStatus", FormStatus.UA));
        if(l!=null && l.size()>0) {
            criteria.add(Restrictions.in("formNo", l));
        }
        criteria.add(Restrictions.ne("formStatus", FormStatus.RJ));
        List<FormDeliverApply> list=null;
        if(l!=null && l.size()>0) {
            list=criteria.list();
        }
        List<FormDeliverApplyDto> list2=new ArrayList<>();
        if(list!=null) {
            for (FormDeliverApply formDeliverApply : list
                    ) {
                FormDeliverApplyDto dto = new FormDeliverApplyDto();
                BeanUtils.copyProperties(formDeliverApply, dto);
                dto.setFormDeliverApplyFiles(null);
                list2.add(dto);
            }
        }
        return list2;
    }

    //第5步批量数据
    @Override
    public List<FormDeliverApplyDto> getFormDeliverApplyByClstep5(String specialtyType,List<Long> l){
        Criteria criteria=getSession().createCriteria(FormDeliverApply.class);
        if(specialtyType!=null && !specialtyType.equals("")){
            criteria.add(Restrictions.like("specialtyType", "%"+specialtyType+"%"));
        }
//        criteria.add(Restrictions.eq("isActive", true));
        criteria.add(Restrictions.eq("clStep", 5));
//        criteria.add(Restrictions.eq("formStatus", FormStatus.UA));
        if(l!=null && l.size()>0) {
            criteria.add(Restrictions.in("formNo", l));
        }
        criteria.add(Restrictions.ne("formStatus", FormStatus.RJ));
        List<FormDeliverApply> list=null;
        if(l!=null && l.size()>0) {
            list=criteria.list();
        }
        List<FormDeliverApplyDto> list2=new ArrayList<>();
        if(list!=null) {
            for (FormDeliverApply formDeliverApply : list
                    ) {
                FormDeliverApplyDto dto = new FormDeliverApplyDto();
                BeanUtils.copyProperties(formDeliverApply, dto);
                dto.setFormDeliverApplyFiles(null);
                list2.add(dto);
            }
        }
        return list2;
    }
    //第6步批量数据
    @Override
    public List<FormDeliverApplyDto> getFormDeliverApplyByClstep6(String specialtyType,Long userId,List<Long> l){
        Criteria criteria=getSession().createCriteria(FormDeliverApply.class);
        if(specialtyType!=null && !specialtyType.equals("")){
            criteria.add(Restrictions.like("specialtyType", "%"+specialtyType+"%"));
        }
//        criteria.add(Restrictions.eq("isActive", true));
        criteria.add(Restrictions.eq("clStep", 6));
//        criteria.add(Restrictions.eq("formStatus", FormStatus.UA));
        if(l!=null && l.size()>0) {
            criteria.add(Restrictions.in("formNo", l));
        }
        criteria.add(Restrictions.ne("formStatus", FormStatus.RJ));
        List<FormDeliverApply> list=null;
        if(l!=null && l.size()>0) {
            list=criteria.list();
        }
        List<FormDeliverApply> listAll=new ArrayList<>();
        if(list!=null) {
            for (FormDeliverApply formDeliverApply : list) {
                Criteria criteria2 = getSession().createCriteria(FormApprover.class);
                criteria2.add(Restrictions.eq("formNo", formDeliverApply.getFormNo()));
                criteria2.add(Restrictions.eq("clStep", 6));
                criteria2.add(Restrictions.eq("appActor", userId));
                List<FormApprover> list1 = criteria2.list();
                if (list1 == null || list1.size() == 0) {
                    listAll.add(formDeliverApply);
                }
            }
        }
        List<FormDeliverApplyDto> list2=new ArrayList<>();
        for (FormDeliverApply formDeliverApply:listAll
                ) {
            FormDeliverApplyDto dto=new FormDeliverApplyDto();
            BeanUtils.copyProperties(formDeliverApply,dto);
            dto.setFormDeliverApplyFiles(null);
            list2.add(dto);
        }
        return list2;
    }
    //第7步批量数据
    @Override
    public List<FormDeliverApplyDto> getFormDeliverApplyByClstep7(String specialtyType,List<Long> l){
        Criteria criteria=getSession().createCriteria(FormDeliverApply.class);
        if(specialtyType!=null && !specialtyType.equals("")){
            criteria.add(Restrictions.like("specialtyType", "%"+specialtyType+"%"));
        }
//        criteria.add(Restrictions.eq("isActive", true));
        criteria.add(Restrictions.eq("clStep", 7));
//        criteria.add(Restrictions.eq("formStatus", FormStatus.UA));
        if(l!=null && l.size()>0) {
            criteria.add(Restrictions.in("formNo", l));
        }
        criteria.add(Restrictions.ne("formStatus", FormStatus.RJ));
        List<FormDeliverApply> list=null;
        if(l!=null && l.size()>0){
            list=criteria.list();
        }
        List<FormDeliverApplyDto> list2=new ArrayList<>();
        if(list!=null){
            for (FormDeliverApply formDeliverApply:list
                    ) {
                FormDeliverApplyDto dto=new FormDeliverApplyDto();
                BeanUtils.copyProperties(formDeliverApply,dto);
                dto.setFormDeliverApplyFiles(null);
                list2.add(dto);
            }
        }
        return list2;
    }

    //第8步批量数据
    @Override
    public List<FormDeliverApplyDto> getFormDeliverApplyByClstep8(String specialtyType,List<Long> l){
        Criteria criteria=getSession().createCriteria(FormDeliverApply.class);
        if(specialtyType!=null && !specialtyType.equals("")){
            criteria.add(Restrictions.like("specialtyType", "%"+specialtyType+"%"));
        }
//        criteria.add(Restrictions.eq("isActive", true));
        criteria.add(Restrictions.eq("clStep", 8));
//        criteria.add(Restrictions.eq("formStatus", FormStatus.UA));
        if(l!=null && l.size()>0) {
            criteria.add(Restrictions.in("formNo", l));
        }
        criteria.add(Restrictions.ne("formStatus", FormStatus.RJ));
        List<FormDeliverApply> list=null;
        if(l!=null && l.size()>0){
            list=criteria.list();
        }
        List<FormDeliverApplyDto> list2=new ArrayList<>();
        if(list!=null){
            for (FormDeliverApply formDeliverApply:list
                    ) {
                FormDeliverApplyDto dto=new FormDeliverApplyDto();
                BeanUtils.copyProperties(formDeliverApply,dto);
                dto.setFormDeliverApplyFiles(null);
                list2.add(dto);
            }
        }
        return list2;
    }


    //第9步批量数据
    @Override
    public List<FormDeliverApplyDto> getFormDeliverApplyByClstep9(String specialtyType,List<Long> l){
        Criteria criteria=getSession().createCriteria(FormDeliverApply.class);
        if(specialtyType!=null && !specialtyType.equals("")){
            criteria.add(Restrictions.like("specialtyType", "%"+specialtyType+"%"));
        }
//        criteria.add(Restrictions.eq("isActive", true));
        criteria.add(Restrictions.eq("clStep", 9));
//        criteria.add(Restrictions.eq("formStatus", FormStatus.UA));
        if(l!=null && l.size()>0) {
            criteria.add(Restrictions.in("formNo", l));
        }
        criteria.add(Restrictions.ne("formStatus", FormStatus.RJ));
        List<FormDeliverApply> list=null;
        if(l!=null && l.size()>0){
            list=criteria.list();
        }
        List<FormDeliverApplyDto> list2=new ArrayList<>();
        if(list!=null){
            for (FormDeliverApply formDeliverApply:list
                    ) {
                FormDeliverApplyDto dto=new FormDeliverApplyDto();
                BeanUtils.copyProperties(formDeliverApply,dto);
                dto.setFormDeliverApplyFiles(null);
                list2.add(dto);
            }
        }
        return list2;
    }

    //第10步批量数据
    @Override
    public List<FormDeliverApplyDto> getFormDeliverApplyByClstep10(String specialtyType,List<Long> l){
        Criteria criteria=getSession().createCriteria(FormDeliverApply.class);
        if(specialtyType!=null && !specialtyType.equals("")){
            criteria.add(Restrictions.like("specialtyType", "%"+specialtyType+"%"));
        }
//        criteria.add(Restrictions.eq("isActive", true));
        criteria.add(Restrictions.eq("clStep", 10));
//        criteria.add(Restrictions.eq("formStatus", FormStatus.UA));
        if(l!=null && l.size()>0) {
            criteria.add(Restrictions.in("formNo", l));
        }
        criteria.add(Restrictions.ne("formStatus", FormStatus.RJ));
        List<FormDeliverApply> list=null;
        if(l!=null && l.size()>0){
            list=criteria.list();
        }
        List<FormDeliverApplyDto> list2=new ArrayList<>();
        if(list!=null){
            for (FormDeliverApply formDeliverApply:list
                    ) {
                FormDeliverApplyDto dto=new FormDeliverApplyDto();
                BeanUtils.copyProperties(formDeliverApply,dto);
                dto.setFormDeliverApplyFiles(null);
                list2.add(dto);
            }
        }
        return list2;
    }

    //第11步批量数据
    @Override
    public List<FormDeliverApplyDto> getFormDeliverApplyByClstep11(String specialtyType,List<Long> l){
        Criteria criteria=getSession().createCriteria(FormDeliverApply.class);
        if(specialtyType!=null && !specialtyType.equals("")){
            criteria.add(Restrictions.like("specialtyType", "%"+specialtyType+"%"));
        }
//        criteria.add(Restrictions.eq("isActive", true));
        criteria.add(Restrictions.eq("clStep", 11));
        criteria.add(Restrictions.eq("isComplete", false));
//        criteria.add(Restrictions.eq("formStatus", FormStatus.UA));
        if(l!=null && l.size()>0) {
            criteria.add(Restrictions.in("formNo", l));
        }
        criteria.add(Restrictions.ne("formStatus", FormStatus.RJ));
        List<FormDeliverApply> list=null;
        if(l!=null && l.size()>0){
            list=criteria.list();
        }
        List<FormDeliverApplyDto> list2=new ArrayList<>();
        if(list!=null){
            for (FormDeliverApply formDeliverApply:list
                    ) {
                FormDeliverApplyDto dto=new FormDeliverApplyDto();
                BeanUtils.copyProperties(formDeliverApply,dto);
                dto.setFormDeliverApplyFiles(null);
                list2.add(dto);
            }
        }
        return list2;
    }
    //第二步审批不通过
    public CommonResult updateDeliverForm2No(ApproveFormDto dto){
        return super.doApprove(dto,null);
    }
    //第四步审批不通过
    public CommonResult updateProject4(ApproveFormDto dto){
        return super.doApprove(dto,null);
    }
    //第五步审批不通过
    public CommonResult nextProject5(ApproveFormDto dto){
        return super.doApprove(dto,null);
    }
    //第六步审批不通过
    public CommonResult nextProject6(ApproveFormDto dto){
        return super.doApprove(dto,null);
    }
    //第九步审批不通过
    public CommonResult nextProject9(ApproveFormDto dto){
        return super.doApprove(dto,null);
    }
    public String getLeaderNameById(long l){
        Criteria criteria=getSession().createCriteria(Employee.class);
        criteria.add(Restrictions.eq("isActive", true));
        criteria.add(Restrictions.eq("id", l));
        List<Employee> list=criteria.list();
        if(list!=null && list.size()>0){
            return list.get(0).getName();
        }else{
            return null;
        }
    }

    @Override
    public void updateDeliver_Information(FormDeliverApplyDto formDeliverApplyDto) {
        Criteria criteria=getSession().createCriteria(FormDeliverApply.class);
//        criteria.add(Restrictions.eq("isActive", true));
        criteria.add(Restrictions.eq("formNo", formDeliverApplyDto.getFormNo()));
//        criteria.add(Restrictions.eq("formStatus", FormStatus.UA));
        FormDeliverApply formDeliverApply= (FormDeliverApply) criteria.uniqueResult();

        formDeliverApply.setRank(formDeliverApplyDto.getRank());
        formDeliverApply.setGrade(formDeliverApplyDto.getGrade());
        formDeliverApply.setIssuing_unit(formDeliverApplyDto.getIssuing_unit());
        Long result= formDeliverApplyRepository.save(formDeliverApply);

        //return list2;
    }

    @Override
    public List<Long> getFormApproverByUserId(Long id,int clstep){
        Criteria criteria=getSession().createCriteria(FormApprover.class);
        criteria.add(Restrictions.eq("formKind", "SYY_KG_LC06"));
        criteria.add(Restrictions.eq("appUserId", id));
        criteria.add(Restrictions.eq("clStep", clstep));
        List<FormApprover> list=criteria.list();
        List<Long> l=new ArrayList<>();


        Criteria criteria2=getSession().createCriteria(FormHeader.class);
        criteria2.add(Restrictions.eq("formKind", "SYY_KG_LC06"));
        criteria2.add(Restrictions.eq("clStep", clstep));
        criteria2.add(Restrictions.eq("isStepComplete", false));
        criteria2.add(Restrictions.ne("formStatus", FormStatus.WD));
        criteria2.add(Restrictions.ne("formStatus", FormStatus.RJ));
        List<FormHeader> list2=criteria2.list();
        if(list.size()>0&&list2.size()>0){
            for(int i=0;i<list.size();i++){
                for(int j=0;j<list2.size();j++){
                    if(list.get(i).getFormNo().longValue()==list2.get(j).getId().longValue()){
                        l.add(list.get(i).getFormNo());
                    }
                }
            }
        }
        return l;
    }

    @Override
    public CommonResult lc06weiyi(String projectName){
        Criteria criteria=getSession().createCriteria(FormDeliverApply.class);
        criteria.add(Restrictions.eq("projectName",projectName));
        List<FormDeliverApply> list=criteria.list();
        if(list==null || list.size()==0){
            return new CommonResult(true,"是唯一");
        }else{
            return new CommonResult(false,"不是唯一");
        }
    }

    @Override
    public CommonResult lc06weiyi2(String projectName2, Long id){
        Criteria criteria=getSession().createCriteria(FormDeliverApply.class);
        criteria.add(Restrictions.eq("projectName",projectName2));
        criteria.add(Restrictions.ne("id",id));
        List<FormDeliverApply> list=criteria.list();
        if(list==null || list.size()==0){
            return new CommonResult(true,"是唯一");
        }else{
            return new CommonResult(false,"不是唯一");
        }
    }
}