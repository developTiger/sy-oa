package com.sunesoft.lemon.deanery.projectWorkFlow.application;

import com.sunesoft.lemon.deanery.car.application.SpecialtyTypeService;
import com.sunesoft.lemon.deanery.project.domain.ScientificResearchProject;
import com.sunesoft.lemon.deanery.projectPlanInput.applicatioin.factory.ProjectDeaneryUtil;
import com.sunesoft.lemon.deanery.projectWorkFlow.application.dto.FormOpenProjectFileDto;
import com.sunesoft.lemon.deanery.projectWorkFlow.application.dto.FormProjectApplyDto;
import com.sunesoft.lemon.deanery.projectWorkFlow.application.dto.FormProjectApplyFileDto;
import com.sunesoft.lemon.deanery.projectWorkFlow.domain.FormOpenProjectFile;
import com.sunesoft.lemon.deanery.projectWorkFlow.domain.FormProjectApply;
import com.sunesoft.lemon.deanery.projectWorkFlow.domain.FormProjectApplyFile;
import com.sunesoft.lemon.deanery.projectWorkFlow.domain.FormProjectApplyRepository;
import com.sunesoft.lemon.deanery.scientificRPKU.ScienticRPKUService;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.results.ResultFactory;
import com.sunesoft.lemon.fr.utils.BeanUtils;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.syms.eHr.application.factory.DtoFactory;
import com.sunesoft.lemon.syms.workflow.application.FormBase2;
import com.sunesoft.lemon.syms.workflow.application.FormHeaderService;
import com.sunesoft.lemon.syms.workflow.application.criteria.FormHeaderCriteria;
import com.sunesoft.lemon.syms.workflow.application.dtos.ApproveFormDto;
import com.sunesoft.lemon.syms.workflow.application.dtos.FormHeaderDto;
import com.sunesoft.lemon.syms.workflow.domain.FormHeaderRepository;
import com.sunesoft.lemon.syms.workflow.domain.enums.ApproveStatus;
import com.sunesoft.lemon.syms.workflow.domain.enums.FormStatus;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhouz on 2016/8/19.
 */
@Service("formProjectApplyService")
public class FormProjectApplyServiceImpl extends FormBase2<FormProjectApply,FormProjectApplyDto> implements FormProjectApplyService {

    @Autowired
    FormProjectApplyRepository formProjectApplyRepository;

    @Autowired
    FormHeaderRepository headerRepository;

    @Autowired
    SpecialtyTypeService specialtyTypeService;

    @Autowired
    ScienticRPKUService scienticRPKUService;

    @Override
    protected CommonResult save(FormProjectApply formProjectApply) {
        formProjectApplyRepository.save(formProjectApply);
        return ResultFactory.commonSuccess();
    }

    @Override
    protected CommonResult update(FormProjectApply formProjectApply) {

        FormProjectApply apply = this.getByFormNo(formProjectApply.getFormNo());

        apply.setProjectPlanInfoTxt(formProjectApply.getProjectPlanInfoTxt());
        apply.setProjectName(formProjectApply.getProjectName());
        apply.setProjectNo(formProjectApply.getProjectNo());

        formProjectApplyRepository.save(apply);

        return ResultFactory.commonSuccess();
    }

    @Override
    protected FormProjectApply ConvetDto(FormProjectApplyDto dto) {
        FormProjectApply formProjectApply=DtoFactory.convert(dto,new FormProjectApply());;
        List<FormProjectApplyFile> l=new ArrayList<>();
        for(FormProjectApplyFileDto d : dto.getFileList()){
            FormProjectApplyFile file =   DtoFactory.convert(d, new FormProjectApplyFile());
            l.add(file);
        }
        formProjectApply.setFiles(l);
        return formProjectApply;
    }

    @Override
    protected String getSummery(FormProjectApply formProjectApply) {
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
    protected FormProjectApply getByFormNo(Long formNo) {
        Criteria criterion = getSession().createCriteria(FormProjectApply.class);
        criterion.add(Restrictions.eq("isActive", true));
        criterion.add(Restrictions.eq("formNo", formNo));
        return (FormProjectApply)criterion.uniqueResult();
    }

    @Override
    public FormProjectApplyDto getFormByFormNo(Long formNo) {
        FormProjectApply apply = this.getByFormNo(formNo);
        FormProjectApplyDto dto = DtoFactory.convert(apply,new FormProjectApplyDto() );
        List<FormOpenProjectFileDto>  dtos = new ArrayList<>();
        if(apply.getFormOpenProjectFiles()!=null&&apply.getFormOpenProjectFiles().size()>0){
            for(FormOpenProjectFile file :apply.getFormOpenProjectFiles()){
                dtos.add(DtoFactory.convert(file,new FormOpenProjectFileDto()));
            }
        }
        List<FormProjectApplyFileDto>  applyFilesDto = new ArrayList<>();
            for(FormProjectApplyFile file :apply.getFiles()){
                applyFilesDto.add(DtoFactory.convert(file,new FormProjectApplyFileDto()));
            }
        dto.setFileList(applyFilesDto);
        dto.setFormOpenProjectFileDtos(dtos);
        return  dto;
    }

    @Override
    public ScientificResearchProject getFormSRPByFormNo(Long formNo) {
      return null;
    }

    @Override
    public FormProjectApplyDto getFormProjectApply(Long id) {
        FormProjectApply formProjectApply=formProjectApplyRepository.get(id);
        FormProjectApplyDto formProjectApplyDto=new FormProjectApplyDto();
        BeanUtils.copyProperties(formProjectApply,formProjectApplyDto);
        return formProjectApplyDto;
    }

    @Override
    public CommonResult updateProjectApplyEmployee(ApproveFormDto dto,String employeeid) {
        FormProjectApply apply = this.getByFormNo(dto.getFormNo());
        apply.setEmployeeIds(employeeid);
        formProjectApplyRepository.save(apply);
        return ResultFactory.commonSuccess();
    }

    @Override
    public CommonResult updateProjectApplyByFormNo(Long formNo, List<Long> list,String type) {
        FormProjectApply apply = this.getByFormNo(formNo);
        String employeeid="";
        for (int i = 0; i < list.size(); i++) {
            employeeid += list.get(i).toString();
            if (i < list.size() - 1) {
                employeeid += ",";
            }
        }
        if(type.equals("spcialist")){
           apply.setSpecialistIds(employeeid);
       }else if(type.equals("leader")){
           apply.setLeaderIds(employeeid);

       }else{
            ResultFactory.commonError("false");
       }

        formProjectApplyRepository.save(apply);
        return ResultFactory.commonSuccess();
    }

    @Override
    public CommonResult saveAllApproveFile(FormProjectApplyDto formProjectApplyDto) {
        FormProjectApply apply=this.ConvetDto(formProjectApplyDto);
        this.save(apply);
        return ResultFactory.commonSuccess();
    }
    @Autowired
    FormHeaderService headerService;
    @Override
    public PagedResult<FormProjectApplyDto> selectLeaderApprove(FormProjectApplyDto formProjectApplyDto) throws UnsupportedEncodingException {
        FormHeaderCriteria headerCriteria = new FormHeaderCriteria();
       // headerCriteria.setApproverId(formProjectApplyDto.getApproverId());
        headerCriteria.setFormKind(formProjectApplyDto.getFormKind());
        List<FormStatus> formStatuses = new ArrayList<>();
        formStatuses.add(FormStatus.UA);
       // headerCriteria.setPageSize(1000);
        headerCriteria.setArrFormStatus(formStatuses);
        headerCriteria.setApproveStatus(ApproveStatus.U);
        headerCriteria.setApproverId(formProjectApplyDto.getApplyer());
        List<FormHeaderDto> formHeaderDtos= headerService.findFormHeader(headerCriteria);

        List<Long> formNOs = new ArrayList<>();
        if(formHeaderDtos!=null &&formHeaderDtos.size()>0){
            for(FormHeaderDto headerDto : formHeaderDtos){
                formNOs.add(headerDto.getId());
            }
        }
      /*  else{
            return  new PagedResult<FormProjectApplyDto>(List<FormProjectApplyFileDto> items, int pageNumber, int pageSize, int totalItemsCount);
        }
*/
        Criteria criterion = getSession().createCriteria(FormProjectApply.class);
        criterion.add(Restrictions.eq("isActive", true));
       // List<SpecialtyType> alltype=specialtyTypeService.getAllType();
        // String wrn = URLDecoder.decode(projectPlanInput.getProjectPlan_Name(), "UTF-8");
        //   String projectPlan_name =  URLDecoder.decode(projectPlanInput.getProjectPlan_Name(),"UTF-8");
        if (!StringUtils.isNullOrWhiteSpace(formProjectApplyDto.getSpecial_Type())){
            criterion.add(Restrictions.like("special_Type",URLDecoder.decode(formProjectApplyDto.getSpecial_Type(),"UTF-8"), MatchMode.ANYWHERE));
        }
        if (formProjectApplyDto.getClStep()!=null){
            criterion.add(Restrictions.eq("clStep",formProjectApplyDto.getClStep()));
        }
        criterion.add(Restrictions.eq("formStatus", FormStatus.UA));
        if (formNOs.size()!=0){
            criterion.add(Restrictions.in("formNo",formNOs));
        }else {
            criterion.add(Restrictions.eq("formNo",-1L));
        }

/*        if (!StringUtils.isNullOrWhiteSpace(startTime)){
            Date st= DateHelper.parse(startTime,"yyyy");
            criterion.add(Restrictions.ge("projectPlan_InputYear",st));
        }
        if (!StringUtils.isNullOrWhiteSpace(endTime)){
            Date et= DateHelper.parse(endTime,"yyyy");
            criterion.add(Restrictions.le("projectPlan_InputYear",et));
        }*/
      //  System.out.print(criterion.setProjection(Projections.rowCount()).uniqueResult());
        int totalCount = ((Long)criterion.setProjection(Projections.rowCount()).uniqueResult()).intValue();
        criterion.setProjection(null);
        criterion.setFirstResult((formProjectApplyDto.getPageNumber() - 1) * formProjectApplyDto.getPageSize()).setMaxResults(formProjectApplyDto.getPageSize());
        List<FormProjectApply> list = criterion.list();
        List<FormProjectApplyDto> formProjectApplyDtos = new ArrayList<FormProjectApplyDto>();
        for (FormProjectApply p : list){
            formProjectApplyDtos.add(ProjectDeaneryUtil.converFromListProjectApply(p));
        }
        return new PagedResult<FormProjectApplyDto>(formProjectApplyDtos,formProjectApplyDto.getPageNumber(),formProjectApplyDto.getPageSize(),totalCount);
    }

    @Override
    public String findOpenFlowEmployeeids(long l, String type) {
        FormProjectApply apply = this.getByFormNo(l);
        String  result="";
        if(type.equals("leader")){
            result=apply.getLeaderIds();
        }else if(type.equals("specialids")){
            result=apply.getSpecialistIds();
        }
        return result;
    }

    @Override
    public CommonResult addPriceAndApprove(ApproveFormDto dto, Double price) {


        FormProjectApply al=  this.getByFormNo(dto.getFormNo());
        //al.setPrice();

        formProjectApplyRepository.save(al);

        return doApprove(dto,null);
    }

//获取项目列表
    @Override
    public List<ScientificResearchProject> queryProject(){
        Criteria criteria = this.getSession().createCriteria(ScientificResearchProject.class);
        criteria.add(Restrictions.eq("formStatus", FormStatus.AP ));
        criteria.add(Restrictions.eq("isActive", true ));
//        criteria.add(Restrictions.eq("isComplete", true ));
        List<ScientificResearchProject> list=criteria.list();
        return list;
    }

    @Override
    public CommonResult updateProject(ApproveFormDto dto, String content, String projName, String projectNo, FormProjectApplyDto formProjectApplyDto) {
        CommonResult result=null;
        if(formProjectApplyDto.getFormNo_OpenFlow()==null){
            saveOpenFlow(dto.getFormNo(),formProjectApplyDto);
            result = super.doApprove(dto, null);
        }else {
            for (int i = 0; i < formProjectApplyDto.getFormNo_OpenFlow().size(); i++) {
/*                saveOpenFlow(Long.parseLong(formProjectApplyDto.getFormNo_OpenFlow().get(i)),f);
                if (apply.getClStep() == 2) {
                    apply.setInstruction(formProjectApplyDto.getInstruction());
                    formProjectApplyRepository.save(apply);
                }*/

                saveOpenFlow(Long.parseLong(formProjectApplyDto.getFormNo_OpenFlow().get(i)),formProjectApplyDto);
                dto.setFormNo(Long.parseLong(formProjectApplyDto.getFormNo_OpenFlow().get(i)));
                result = super.doApprove(dto, null);
            }
        }
        updateStates(dto,formProjectApplyDto);
      //  FormProjectApply apply = this.getByFormNo(dto.getFormNo());
     /*   apply.setProjectPlanInfoTxt(content);
        apply.setProjectName(projName);
        apply.setProjectNo(projectNo);*/

//        FormHeader header=headerRepository.get(dto.getFormNo());
//        header.getCurrentAppPointId();
//        header.getNextAppPointId();

//        //第4步到第二步
//
//        for(InnerFormApprovePoint point :header.getInnerFormApprovePoints()){
//            if(point.getAppSerial().equals(4-2)){
//                this.setPointAsNext(dto.getFormNo(),point.getId());
//            }
//        }

//        for(InnerFormApprovePoint point :header.getInnerFormApprovePoints()){
//            if(point.getId().equals(header.getNextAppPointId())){
//                this.setPointAsNext(dto.getFormNo(),point.getNextPointId());
//            }
//        }
        return result;
    }

    @Override
    public CommonResult nextProject(ApproveFormDto dto) {
        return super.doApprove(dto, null);
    }

    @Override
    public CommonResult uploadProjectFile(Long formNo, String fileId, String fileName) {
        FormProjectApply apply = this.getByFormNo(formNo);
        FormOpenProjectFile file  = new FormOpenProjectFile();
        file.setFileId(fileId);
        file.setFileName(fileName);
        apply.getFormOpenProjectFiles().add(file);
        formProjectApplyRepository.save(apply);
        return  ResultFactory.commonSuccess();
    }

    public void  saveOpenFlow(long formNo,FormProjectApplyDto formProjectApplyDto){
        FormProjectApply apply = this.getByFormNo(formNo);
        if (apply.getClStep() == 2) {
            apply.setInstruction(formProjectApplyDto.getInstruction());
            //formProjectApplyRepository.save(apply);
        }else if(apply.getClStep()== 6){
            String review_Comments=apply.getReview_Comments();
            if(!StringUtils.isNullOrWhiteSpace(review_Comments)){
                apply.setReview_Comments(review_Comments+","+formProjectApplyDto.getReview_Comments());
            }else{
                apply.setReview_Comments(formProjectApplyDto.getReview_Comments());
            }
        }
        formProjectApplyRepository.save(apply);
    }

    public void updateStates(ApproveFormDto dto,FormProjectApplyDto formProjectApplyDto){
        String yx = null;
        String kt = "0";
        String ys = null;
        String sb = null;
        String jc = null;
        //否决
        if (dto.getAppValue() == 2) {
            for (int i = 0; i < formProjectApplyDto.getList_projectNo().size(); i++) {
                scienticRPKUService.reset(formProjectApplyDto.getList_projectNo().get(i), yx, kt, ys, sb, jc);
            }
        } else if (dto.getAppValue() == 1 && formProjectApplyDto.getClStep() == 11) {
            //最后一步同意
            for (int i = 0; i < formProjectApplyDto.getList_projectNo().size(); i++) {
                scienticRPKUService.update(formProjectApplyDto.getList_projectNo().get(i), yx, kt, ys, sb, jc);
            }
        }//第一步同意
       /* else if (dto.getAppValue() == 1 && formProjectApplyDto.getClStep() == 1) {
            for (int i = 0; i < formProjectApplyDto.getList_projectNo().size(); i++) {
                scienticRPKUService.updateByProjectApply(formProjectApplyDto.getList_projectNo().get(i), yx, kt, ys, sb, jc);
            }

        }*///第一步退回
        else if (dto.getAppValue() == 3 && formProjectApplyDto.getClStep() == 1) {
            for (int i = 0; i < formProjectApplyDto.getList_projectNo().size(); i++) {
                scienticRPKUService.reset(formProjectApplyDto.getList_projectNo().get(i), yx, kt, ys, sb, jc);
            }
        }
    }
}
