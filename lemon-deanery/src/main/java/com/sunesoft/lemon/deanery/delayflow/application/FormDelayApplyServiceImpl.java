package com.sunesoft.lemon.deanery.delayflow.application;

import com.sunesoft.lemon.deanery.delayflow.application.dto.FormDelayApplyDto;
import com.sunesoft.lemon.deanery.delayflow.criteria.FormDelayCriteria;
import com.sunesoft.lemon.deanery.delayflow.domain.FormDelayApply;
import com.sunesoft.lemon.deanery.delayflow.domain.FormDelayApplyRepository;
import com.sunesoft.lemon.deanery.deliverables.application.dto.FormDeliverApplyDto;
import com.sunesoft.lemon.deanery.deliverables.domain.FormDeliverApply;
import com.sunesoft.lemon.deanery.projectWorkFlow.domain.FormOpenProjectFile;
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
import com.sunesoft.lemon.syms.workflow.application.factory.WorkflowDtoFactory;
import com.sunesoft.lemon.syms.workflow.domain.FormApprover;
import com.sunesoft.lemon.syms.workflow.domain.FormHeader;
import com.sunesoft.lemon.syms.workflow.domain.enums.ApproveStatus;
import com.sunesoft.lemon.syms.workflow.domain.enums.FormStatus;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by swb on 2016/8/25.
 */

@Service("formDelayApplyService")
public class FormDelayApplyServiceImpl extends FormBase2<FormDelayApply, FormDelayApplyDto> implements FormDelayApplyService {

    @Autowired
    FormDelayApplyRepository formDelayApplyRepository;

    @Autowired
    FormHeaderService headerService;

    @Override
    protected CommonResult save(FormDelayApply formDelayApply) {
        formDelayApplyRepository.save(formDelayApply);
        return ResultFactory.commonSuccess();
    }

    @Override
    protected CommonResult update(FormDelayApply formDelayApply) {
        return null;
    }

    @Override
    protected FormDelayApply ConvetDto(FormDelayApplyDto dto) {
        return DtoFactory.convert(dto, new FormDelayApply());
    }

   /* @Override
    protected FormDelayApply ConvetDto(FormDelayApplyDto dto) {
        return
    }*/

    @Override
    protected String getSummery(FormDelayApply formDelayApply) {
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
    protected FormDelayApply getByFormNo(Long formNo) {
        Criteria criteria = getSession().createCriteria(FormDelayApply.class);
        criteria.add(Restrictions.eq("isActive", true));
        criteria.add(Restrictions.eq("formNo", formNo));
        FormDelayApply formDelayApply = (FormDelayApply) criteria.uniqueResult();
        return formDelayApply;
    }

    @Override
    public CommonResult addDelayAndApprove(ApproveFormDto dto) {
        return null;
    }

    @Override
    public List<FormDelayApply> queryDelay() {
        Criteria criteria = this.getSession().createCriteria(FormDelayApply.class);
        List<FormDelayApply> list = criteria.list();
        return list;
    }

    @Override
    public CommonResult updateDelayForm1(ApproveFormDto dto, String opnion) {
        FormDelayApply apply = this.getByFormNo(dto.getFormNo());
        apply.setOpinion(opnion);
        formDelayApplyRepository.save(apply);
        return super.doApprove(dto, null);
    }

    @Override
    public CommonResult updateDelayForm2(ApproveFormDto dto, String leaderWord) {
        FormDelayApply apply = this.getByFormNo(dto.getFormNo());
        apply.setLeaderWord(leaderWord);
        formDelayApplyRepository.save(apply);
        return super.doApprove(dto, null);
    }

    @Override
    public CommonResult updateDelayForm3(ApproveFormDto dto, String opinion2) {
        FormDelayApply apply = this.getByFormNo(dto.getFormNo());
        apply.setOpinion2(opinion2);
        formDelayApplyRepository.save(apply);
        return super.doApprove(dto, null);
    }

    @Override
    public CommonResult updateDelayForm05(ApproveFormDto dto, String opinion2) {
        FormDelayApply apply = this.getByFormNo(dto.getFormNo());
        //apply.setOpinion2(opinion2);
        formDelayApplyRepository.save(apply);
        return super.doApprove(dto, null);
    }

    @Override
    public CommonResult updateDelayForm06(ApproveFormDto dto, String opinion2) {
        FormDelayApply apply = this.getByFormNo(dto.getFormNo());
        //apply.setOpinion2(opinion2);
        formDelayApplyRepository.save(apply);
        return super.doApprove(dto, null);
    }

    @Override
    public CommonResult nextDelayForm(ApproveFormDto dto) {
        return null;
    }

    @Override
    public CommonResult updateDelayForm4(ApproveFormDto dto, String opinion2) {
        FormDelayApply apply = this.getByFormNo(dto.getFormNo());
        apply.setOpinion3(opinion2);
        formDelayApplyRepository.save(apply);
        return super.doApprove(dto, null);
    }

    @Override
    public CommonResult uploadProjectFile(Long formNo, String fileId, String fileName) {
        FormDelayApply formDelayApply=this.getByFormNo(formNo);
        FormOpenProjectFile file=new FormOpenProjectFile();
        file.setFileId(fileId);
        file.setFileName(fileName);
        formDelayApply.getFormOpenProjectFiles().add(file);
        formDelayApply.setFileId(fileId);
        formDelayApply.setFileName(fileName);
        formDelayApplyRepository.save(formDelayApply);
        return ResultFactory.commonSuccess();
    }

    @Override
    public PagedResult<FormDelayApplyDto> getFormDelayPaged(FormDelayCriteria criteria) {
        Criteria criterion=getSession().createCriteria(FormDelayApply.class);
        int totalCount = ((Long) criterion.setProjection(Projections.rowCount()).uniqueResult()).intValue();
        criterion.setProjection(null);
        criterion.setFirstResult((criteria.getPageNumber()-1)*criteria.getPageSize()).setMaxResults(criteria.getPageSize());
        List<FormDelayApply> beans=criterion.list();
        List<FormDelayApplyDto> list=new ArrayList<>();
        for(FormDelayApply user:beans){
            list.add(convertDtoToFormDelay(user));
        }
        return new PagedResult<FormDelayApplyDto>(list,criteria.getPageNumber(),criteria.getPageSize(),totalCount);
    }

    @Override
    public PagedResult<FormHeaderDto> findFormPaged(FormHeaderCriteria criteria) {
        String  hql = "select distinct  h from FormHeader h ";
        String countSql = "select count(distinct h.id) from FormHeader h ";
        String whereCondition = " where h.isActive=1 ";
        if (criteria.getApproveStatus() != null || criteria.getApproverId() != null) {
            hql+="join  h.formApprovers a ";
            countSql+="join  h.formApprovers a ";
            if (criteria.getApproveStatus() != null)
                whereCondition+= " and a.approveStatus="+criteria.getApproveStatus().ordinal();
            if (criteria.getApproverId() != null)
                whereCondition+= " and a.appUserId="+criteria.getApproverId();
        }
        if (StringUtils.isNullOrWhiteSpace(criteria.getFormKind())) {
            whereCondition+= " and h.formKind='SYY_KG_LC04' and h.clStep=3";
        }if (null != criteria.getBlongDept()) {
            whereCondition+= " and h.deptId="+criteria.getBlongDept();
        }
        if (criteria.getApplyer() != null && criteria.getApplyer() > 0) {
            whereCondition+= " and h.applyer="+criteria.getApplyer();
        }
        if (!StringUtils.isNullOrWhiteSpace(criteria.getApplyerName())) {
            whereCondition+= " and h.applyerName like '%"+criteria.getApplyerName()+"%'";
        }
        if (criteria.getArrFormStatus() != null && criteria.getArrFormStatus().size() > 0) {
            String s = "";
            for(FormStatus status: criteria.getArrFormStatus()){
                s+=status.ordinal()+",";
            }

            whereCondition+= " and h.formStatus in ("+s.substring(0,s.length()-1)+")";

        }
        if (criteria.getFormNo() != null && criteria.getFormNo() > 0) {
            whereCondition+= " and h.formNo="+criteria.getFormNo();
        }

        if (criteria.getBeginDate() != "" && criteria.getEndDate() != null) {

            whereCondition+= " and h.beginDate>:beginDate";

        }
        if (criteria.getEndDate() != "" && criteria.getEndDate() != null) {

            whereCondition+= " and h.beginDate<:endDate";
        }




        Query count= getSession().createQuery(countSql+whereCondition);
        Query query= getSession().createQuery(hql+whereCondition +" order by h.id desc");
        if (criteria.getBeginDate() != "" && criteria.getEndDate() != null) {
            String beginTime = criteria.getBeginDate();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date begining = sdf.parse(beginTime);
                count.setDate("beginDate", begining);
                query.setDate("beginDate", begining);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        if (criteria.getEndDate() != "" && criteria.getEndDate() != null) {
            String endTime = criteria.getEndDate();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date ending = sdf.parse(endTime);
                count.setDate("endDate", ending);
                query.setDate("endDate", ending);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        count.setResultTransformer(Criteria.ROOT_ENTITY);
        int totalCount =((Long) count.uniqueResult()).intValue();


        query.setResultTransformer(Criteria.ROOT_ENTITY);

        query.setFirstResult((criteria.getPageNumber() - 1) * criteria.getPageSize()).setMaxResults(criteria.getPageSize());
        List<FormHeader> beans = query.list();


        List<FormHeaderDto> list = new ArrayList<FormHeaderDto>();
        for (FormHeader header : beans) {
            header.getFormApprovers().size();
            list.add(WorkflowDtoFactory.convert(header, new FormHeaderDto()));
        }
        return new PagedResult<FormHeaderDto>(list, criteria.getPageNumber(), criteria.getPageSize(), totalCount);
    }

    @Override
    public PagedResult<FormHeaderDto> findFormPaged1(FormHeaderCriteria criteria) {
        String  hql = "select distinct  h from FormHeader h ";
        String countSql = "select count(distinct h.id) from FormHeader h ";
        String whereCondition = " where h.isActive=1 ";
        if (criteria.getApproveStatus() != null || criteria.getApproverId() != null) {
            hql+="join  h.formApprovers a ";
            countSql+="join  h.formApprovers a ";
            if (criteria.getApproveStatus() != null)
                whereCondition+= " and a.approveStatus="+criteria.getApproveStatus().ordinal();
            if (criteria.getApproverId() != null)
                whereCondition+= " and a.appUserId="+criteria.getApproverId();
        }
        if (StringUtils.isNullOrWhiteSpace(criteria.getFormKind())) {
            whereCondition+= " and h.formKind='SYY_KG_LC04' and h.clStep=4";
        }if (null != criteria.getBlongDept()) {
            whereCondition+= " and h.deptId="+criteria.getBlongDept();
        }
        if (criteria.getApplyer() != null && criteria.getApplyer() > 0) {
            whereCondition+= " and h.applyer="+criteria.getApplyer();
        }
        if (!StringUtils.isNullOrWhiteSpace(criteria.getApplyerName())) {
            whereCondition+= " and h.applyerName like '%"+criteria.getApplyerName()+"%'";
        }
        if (criteria.getArrFormStatus() != null && criteria.getArrFormStatus().size() > 0) {
            String s = "";
            for(FormStatus status: criteria.getArrFormStatus()){
                s+=status.ordinal()+",";
            }

            whereCondition+= " and h.formStatus in ("+s.substring(0,s.length()-1)+")";

        }
        if (criteria.getFormNo() != null && criteria.getFormNo() > 0) {
            whereCondition+= " and h.formNo="+criteria.getFormNo();
        }

        if (criteria.getBeginDate() != "" && criteria.getEndDate() != null) {

            whereCondition+= " and h.beginDate>:beginDate";

        }
        if (criteria.getEndDate() != "" && criteria.getEndDate() != null) {

            whereCondition+= " and h.beginDate<:endDate";
        }




        Query count= getSession().createQuery(countSql+whereCondition);
        Query query= getSession().createQuery(hql+whereCondition +" order by h.id desc");
        if (criteria.getBeginDate() != "" && criteria.getEndDate() != null) {
            String beginTime = criteria.getBeginDate();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date begining = sdf.parse(beginTime);
                count.setDate("beginDate", begining);
                query.setDate("beginDate", begining);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        if (criteria.getEndDate() != "" && criteria.getEndDate() != null) {
            String endTime = criteria.getEndDate();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date ending = sdf.parse(endTime);
                count.setDate("endDate", ending);
                query.setDate("endDate", ending);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        count.setResultTransformer(Criteria.ROOT_ENTITY);
        int totalCount =((Long) count.uniqueResult()).intValue();


        query.setResultTransformer(Criteria.ROOT_ENTITY);

        query.setFirstResult((criteria.getPageNumber() - 1) * criteria.getPageSize()).setMaxResults(criteria.getPageSize());
        List<FormHeader> beans = query.list();


        List<FormHeaderDto> list = new ArrayList<FormHeaderDto>();
        for (FormHeader header : beans) {
            header.getFormApprovers().size();
            list.add(WorkflowDtoFactory.convert(header, new FormHeaderDto()));
        }
        return new PagedResult<FormHeaderDto>(list, criteria.getPageNumber(), criteria.getPageSize(), totalCount);
    }

    @Override
    public CommonResult updateDelayFormAll1(ApproveFormDto dto) {
        FormDelayApply apply = this.getByFormNo(dto.getFormNo());
        formDelayApplyRepository.save(apply);
        return super.doApprove(dto, null);
    }

    @Override
    public CommonResult updateDelayFormAll2(ApproveFormDto dto) {
        FormDelayApply apply = this.getByFormNo(dto.getFormNo());
        formDelayApplyRepository.save(apply);
        return super.doApprove(dto, null);
    }

    @Override
    public List<FormDelayApplyDto> getDelayApproves3(FormDelayCriteria formDelayCriteria,String majorType) {
        FormHeaderCriteria headerCriteria = new FormHeaderCriteria();
        headerCriteria.setApproverId(formDelayCriteria.getApproverId());
        headerCriteria.setFormKind("SYY_KG_LC04");
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

        Criteria criteria = getSession().createCriteria(FormDelayApply.class);
        criteria.add(Restrictions.eq("isActive", true));
        //criteria.add(Restrictions.eq("clStep", clstep));
        criteria.add(Restrictions.eq("formStatus", FormStatus.UA));
        if(!majorType.equals("")){
            criteria.add(Restrictions.like("majorType",majorType));
        }
        criteria.add(Restrictions.in("formNo",formNOs));
        criteria.add(Restrictions.eq("clStep",3));

        /*if(scientificResearchProjectCriteria.getSpecialtyType()!=null && !("").equals(scientificResearchProjectCriteria.getSpecialtyType())){
            criteria.add(Restrictions.like("specialtyType", "%" + scientificResearchProjectCriteria.getSpecialtyType() + "%"));
        }*/

        List<FormDelayApply> beans = criteria.list();

        List<FormDelayApplyDto> dto = new ArrayList<FormDelayApplyDto>();
        for(FormDelayApply formDelayApply : beans){
            FormDelayApplyDto formDelayApplyDto = convertDtoToFormDelay(formDelayApply);
            formDelayApplyDto.setDeptName(formDelayApply.getDeptName());
            dto.add(formDelayApplyDto);
        }
        return dto;
    }

    @Override
    public List<FormDelayApplyDto> getDelayApproves4(FormDelayCriteria formDelayCriteria,String majorType) {
        FormHeaderCriteria headerCriteria = new FormHeaderCriteria();
        headerCriteria.setApproverId(formDelayCriteria.getApproverId());
        headerCriteria.setFormKind("SYY_KG_LC04");
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

        Criteria criteria = getSession().createCriteria(FormDelayApply.class);
        criteria.add(Restrictions.eq("isActive", true));
        //criteria.add(Restrictions.eq("clStep", clstep));
        criteria.add(Restrictions.eq("formStatus", FormStatus.UA));
        if(!majorType.equals("")){
            criteria.add(Restrictions.like("majorType",majorType));
        }
        criteria.add(Restrictions.in("formNo",formNOs));
        criteria.add(Restrictions.eq("clStep",4));

        /*if(scientificResearchProjectCriteria.getSpecialtyType()!=null && !("").equals(scientificResearchProjectCriteria.getSpecialtyType())){
            criteria.add(Restrictions.like("specialtyType", "%" + scientificResearchProjectCriteria.getSpecialtyType() + "%"));
        }*/

        List<FormDelayApply> beans = criteria.list();

        List<FormDelayApplyDto> dto = new ArrayList<FormDelayApplyDto>();
        for(FormDelayApply formDelayApply : beans){
            FormDelayApplyDto formDelayApplyDto = convertDtoToFormDelay(formDelayApply);
            formDelayApplyDto.setDeptName(formDelayApply.getDeptName());
            dto.add(formDelayApplyDto);
        }
        return dto;
    }

    @Override
    public CommonResult updateProject0(ApproveFormDto dto) {
        return super.doApprove(dto, null);
    }

    @Override
    public CommonResult updateDelayProject(ApproveFormDto dto, String empid) {
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
    public CommonResult updateDelay001(ApproveFormDto dto) {
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

    public static FormDelayApplyDto convertDtoToFormDelay(FormDelayApply formDelayApply){

        FormDelayApplyDto dto = new FormDelayApplyDto();
        BeanUtils.copyProperties(formDelayApply,dto);
        return dto;
    }

    @Override
    public FormDelayApplyDto getFormByFormNo(Long formNo) {
        FormDelayApply apply = this.getByFormNo(formNo);
        FormDelayApplyDto dto = DtoFactory.convert(apply, new FormDelayApplyDto());
        return dto;
    }

    //第一步批量数据
    @Override
    public List<Long> getFormApproverByUserId(Long id,int clstep){
        Criteria criteria=getSession().createCriteria(FormApprover.class);
        criteria.add(Restrictions.eq("formKind", "SYY_KG_LC04"));
        criteria.add(Restrictions.eq("appUserId", id));
        criteria.add(Restrictions.eq("clStep", clstep));
        List<FormApprover> list=criteria.list();
        List<Long> l=new ArrayList<>();


        Criteria criteria2=getSession().createCriteria(FormHeader.class);
        criteria2.add(Restrictions.eq("formKind", "SYY_KG_LC04"));
        criteria2.add(Restrictions.eq("clStep", clstep));
        criteria2.add(Restrictions.eq("isStepComplete", false));
        criteria2.add(Restrictions.eq("formStatus", FormStatus.UA));
//        criteria2.add(Restrictions.ne("formStatus", FormStatus.WD));
//        criteria2.add(Restrictions.ne("formStatus", FormStatus.RJ));
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

    //第一步批量数据
    @Override
    public List<FormDelayApplyDto> getFormDeliverApplyByClstep(String specialtyType, List<Long> l,Integer clstep){
        Criteria criteria=getSession().createCriteria(FormDelayApply.class);
        if(specialtyType!=null && !specialtyType.equals("")){
            criteria.add(Restrictions.like("majorType", "%"+specialtyType+"%"));
        }
        criteria.add(Restrictions.eq("clStep", clstep));
        if(l!=null && l.size()>0) {
            criteria.add(Restrictions.in("formNo", l));
        }
        criteria.add(Restrictions.eq("formStatus", FormStatus.UA));

        criteria.add(Restrictions.ne("isComplete", true));

        List<FormDelayApply> list=null;
        if(l!=null && l.size()>0) {
            list= criteria.list();
        }
        List<FormDelayApplyDto> list2=new ArrayList<>();
        if(list!=null) {
            for (FormDelayApply formDeliverApply : list
                    ) {
                FormDelayApplyDto dto = new FormDelayApplyDto();
                BeanUtils.copyProperties(formDeliverApply, dto);
                list2.add(dto);
            }
        }
        return list2;
    }

    @Override
    public CommonResult approve(ApproveFormDto dto){
        return super.doApprove(dto, null);
    }

    @Override
    public CommonResult updateBatch(ApproveFormDto dto) {
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
}
