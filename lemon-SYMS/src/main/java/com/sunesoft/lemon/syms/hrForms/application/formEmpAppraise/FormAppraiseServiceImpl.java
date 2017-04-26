package com.sunesoft.lemon.syms.hrForms.application.formEmpAppraise;

import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.results.ResultFactory;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.syms.eHr.application.criteria.EmpCriteria;
import com.sunesoft.lemon.syms.eHr.application.factory.DtoFactory;
import com.sunesoft.lemon.syms.eHr.domain.empInfo.Employee;
import com.sunesoft.lemon.syms.hrForms.application.Dtos.*;
import com.sunesoft.lemon.syms.hrForms.domain.*;
import com.sunesoft.lemon.syms.workflow.application.FormBase;
import com.sunesoft.lemon.syms.workflow.application.FormBase2;
import com.sunesoft.lemon.syms.workflow.application.dtos.ApproveFormDto;
import com.sunesoft.lemon.syms.workflow.domain.enums.AppValue;
import com.sunesoft.lemon.syms.workflow.domain.enums.FormStatus;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.Query;
import java.util.*;

/**
 * Created by jiangkefan on 2016/7/22.
 */
@Service("formAppraiseService")
public class FormAppraiseServiceImpl extends FormBase2<EmpAppraise,FormEmpAppraiseDto> implements  FormAppraiseService {
    @Autowired
    FormAppriseRepository formAppriseRepository;

    @Override
    protected CommonResult save(EmpAppraise empAppraise) {
        if(empAppraise.getId()==null) {
//            Criteria criterion = getSession().createCriteria(Employee.class);
//            criterion.add(Restrictions.eq("isActive", true));
//            List<Employee> employees = criterion.list();

            List<EmpAppraiseDetail> details = new ArrayList<>();

            Long deptId = empAppraise.getDeptId();
            Map<Long,String> emps= employeeRepository.getAllByDeptId(deptId);
            Iterator iter = emps.keySet().iterator();
            while (iter.hasNext()) {
                Long key = (Long) iter.next();
                String name = emps.get(key);
                EmpAppraiseDetail detail = new EmpAppraiseDetail();
                detail.setDeptId(deptId);
                detail.setAppStatus(0);
                Employee e = new Employee();
                e.setId(key);
                detail.setEmployee(e);
                detail.setEmpAppraise(empAppraise);
                details.add(detail);
            }

//            for (Employee e : employees) {
//              //  if (e.getLeader() == null) continue;
//                EmpAppraiseDetail detail = new EmpAppraiseDetail();
//                detail.setDeptId(e.getDept().getId());
//                detail.setAppStatus(0);
//                detail.setEmployee(e);
//                detail.setEmpAppraise(empAppraise);
//                details.add(detail);
//            }
            empAppraise.setDetails(details);
        }
        formAppriseRepository.save(empAppraise);
        return ResultFactory.commonSuccess();
    }

    @Override
    protected CommonResult update(EmpAppraise empAppraise) {
        EmpAppraise appraise = formAppriseRepository.get(empAppraise.getDeptId());
       /* formEmpAppraise.setTrainName(formTrain.getTrainName());
        formEmpAppraise.setTrainBeginDate(formTrain.getTrainBeginDate());
        formEmpAppraise.setTrainEndDate(formTrain.getTrainEndDate());
        formEmpAppraise.setTrainPlace(formTrain.getTrainPlace());
        formEmpAppraise.setTrainContent(formTrain.getTrainContent());
        formEmpAppraise.setEmps(formTrain.getEmps());
        formTrainRepository.save(train);
        return ResultFactory.commonSuccess();*/
        appraise.setFormKindName(empAppraise.getFormKindName());
        return ResultFactory.commonSuccess();
    }

    @Override
    protected EmpAppraise ConvetDto(FormEmpAppraiseDto Dto) {
        EmpAppraise empAppraise=new EmpAppraise();
        empAppraise= DtoFactory.convert(Dto,empAppraise);
        return empAppraise;
    }

    @Override
    protected String getSummery(EmpAppraise empAppraise) {
        return empAppraise.getAppraisTitle();
    }

    @Override
    public CommonResult doAafterApprovedStep(Long formNo, Long step) {
        return null;
    }

    @Override
    public CommonResult doAafterAllApproved(Long formNo) {

        EmpAppraise empAppraise=getByFormNo(formNo);
//        Criteria criterion=getSession().createCriteria(EmpAppraise.class);
//        criterion.add(Restrictions.eq("formNo",formNo));
//        List<EmpAppraiseDetail> listEmpDetail=criterion.list();
        for (EmpAppraiseDetail detail:empAppraise.getDetails()){
            detail.setAppStatus(2);
        }

        return ResultFactory.commonSuccess();

    }

    @Override
    protected EmpAppraise getByFormNo(Long formNo) {
        Criteria criterion=getSession().createCriteria(EmpAppraise.class);
        /*criterion.add(Restrictions.eq("isActive",true));*/
        criterion.add(Restrictions.eq("formNo",formNo));
        return (EmpAppraise)criterion.uniqueResult();
    }

    @Override
    public FormEmpAppraiseDto getFormByFormNo(Long formNo) {
        EmpAppraise empAppraise=this.getByFormNo(formNo);
        FormEmpAppraiseDto formEmpAppraiseDto =DtoFactory.convert(empAppraise,new FormEmpAppraiseDto());
        List<EmpAppraiseDetailDto> dtos = new ArrayList<>();
        for(EmpAppraiseDetail detail:empAppraise.getDetails()){
            EmpAppraiseDetailDto appraiseDetailDto = DtoFactory.convert(detail, new EmpAppraiseDetailDto());
            appraiseDetailDto.setEmpId(detail.getEmployee().getId());
            appraiseDetailDto.setLoginName(detail.getEmployee().getName());
            dtos.add(appraiseDetailDto);
        }
        formEmpAppraiseDto.setDetailsDtos(dtos);

        return formEmpAppraiseDto;
    }

    @Override
    public List<FormEmpSubAppraiseDto> getAllEmpSubAppraises(Long parentFormNo) {

        Criteria criterion=getSession().createCriteria(EmpSubAppraise.class);
        criterion.add(Restrictions.eq("parentFormNo",parentFormNo));
        criterion.add(Restrictions.eq("clStep",1));
        List<EmpSubAppraise> empSubAppraiseList=criterion.list();
        List<FormEmpSubAppraiseDto> empSubAppraiseDtoList=new ArrayList<>();
//        List<EmpSubAppraiseDetailDto> nnn = new ArrayList<>();
//        for(EmpSubAppraise empSubAppraise:empSubAppraiseList){
//            FormEmpSubAppraiseDto subAppraiseDto = new FormEmpSubAppraiseDto();
//            subAppraiseDto=DtoFactory.convert(empSubAppraise,subAppraiseDto);
//            for(EmpSubAppraiseDetail detail:empSubAppraise.getDetails()){
//                EmpSubAppraiseDetailDto dto=new EmpSubAppraiseDetailDto();
//                dto = EntityToDto(detail);
//                dto.setLoginName(detail.getEmployee().getName());
//                nnn.add(dto);
//            }
//            subAppraiseDto.setEmpSubAppraiseDetailDtos(nnn);
//            empSubAppraiseDtoList.add(subAppraiseDto);
//        }
        return empSubAppraiseDtoList;
    }

    @Override
    public FormEmpAppraiseDto getEmpAppDetailDtoPages(Long formNo) {
        EmpAppraise empAppraise=this.getByFormNo(formNo);
        FormEmpAppraiseDto formEmpAppraiseDto =DtoFactory.convert(empAppraise,new FormEmpAppraiseDto());
        List<EmpAppraiseDetailDto> dtos = new ArrayList<>();
        for(EmpAppraiseDetail detail:empAppraise.getDetails()){
            EmpAppraiseDetailDto appraiseDetailDto = DtoFactory.convert(detail, new EmpAppraiseDetailDto());
            appraiseDetailDto.setEmpId(detail.getEmployee().getId());
            appraiseDetailDto.setLoginName(detail.getEmployee().getName());
            dtos.add(appraiseDetailDto);
        }
        formEmpAppraiseDto.setDetailsDtos(dtos);

        return formEmpAppraiseDto;
    }

    @Override
    public PagedResult<EmpAppraiseDetailDto> getPagesByAllDetails(Long formNo,EmpCriteria empCriteria) {
        Criteria criteria = getSession().createCriteria(EmpAppraiseDetail.class);
        criteria.add(Restrictions.eq("isActive",true));
        criteria.createAlias("empAppraise","empAppraise");
        criteria.add(Restrictions.eq("empAppraise.formNo",formNo));


        int totalCount = ((Long)criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
        criteria.setProjection(null);
        criteria.setResultTransformer(Criteria.ROOT_ENTITY);
        criteria.setFirstResult((empCriteria.getPageNumber() - 1) * empCriteria.getPageSize()).setMaxResults(empCriteria.getPageSize());

        List<EmpAppraiseDetail> empAppraise= criteria.list();
        List<EmpAppraiseDetailDto> dtos = new ArrayList<>();
        for(EmpAppraiseDetail detail:empAppraise){
            EmpAppraiseDetailDto appraiseDetailDto = DtoFactory.convert(detail, new EmpAppraiseDetailDto());
            appraiseDetailDto.setEmpId(detail.getEmployee().getId());
            appraiseDetailDto.setLoginName(detail.getEmployee().getName());
            dtos.add(appraiseDetailDto);
        }

        return new PagedResult<EmpAppraiseDetailDto>(dtos, empCriteria.getPageNumber(), empCriteria.getPageSize(), totalCount);
    }

    @Override
    public CommonResult updateByList(Long formNo, List<EmpAppraiseDetailDto> dtos) {
        EmpAppraise empAppraise=this.getByFormNo(formNo);

        List<EmpAppraiseDetail> empAppraiseDetail = new ArrayList<>();

        for (EmpAppraiseDetail detail:empAppraise.getDetails()){
            for (EmpAppraiseDetailDto appraiseDetailDto:dtos){
                if (appraiseDetailDto.getLoginName().equals(detail.getEmployee().getName())){

                    detail.setChargeLeaderScores(appraiseDetailDto.getChargeLeaderScores());
                    detail.setChargeLeaderLevel(appraiseDetailDto.getChargeLeaderLevel());
                }
            }
        }

        formAppriseRepository.save(empAppraise);
        return ResultFactory.commonSuccess();
    }

//
//    public EmpSubAppraiseDetailDto EntityToDto(EmpSubAppraiseDetail empAppraiseDetail) {
//
//        EmpSubAppraiseDetailDto empAppraiseDetailDto=new EmpSubAppraiseDetailDto();
//
//       /* empAppraiseDetailDto.setEmpSelfScores(empAppraiseDetail.getEmpSelfScores());
//        if(empAppraiseDetail.getEmpSelfGrade()==null){
//            empAppraiseDetailDto.setEmpSelfGrade("良好");
//        }else{
//            empAppraiseDetailDto.setEmpSelfGrade(empAppraiseDetail.getEmpSelfGrade());
//        }
//
//        if(empAppraiseDetail.getEmpSelfLevel()==null){
//            empAppraiseDetailDto.setEmpSelfLevel("良好");
//        }else{
//            empAppraiseDetailDto.setEmpSelfLevel(empAppraiseDetail.getEmpSelfLevel());
//        }
//
//        if(empAppraiseDetail.getEmpSelfRemark()==null){
//            empAppraiseDetailDto.setEmpSelfRemark("良好");
//        }else{
//            empAppraiseDetailDto.setEmpSelfRemark(empAppraiseDetail.getEmpSelfRemark());
//        }*/
//
//        if(empAppraiseDetail.getDeptLevel()==null){
//            empAppraiseDetailDto.setDeptLevel("良好");
//        }else{
//            empAppraiseDetailDto.setDeptLevel(empAppraiseDetail.getDeptLevel());
//        }
//
//        if(empAppraiseDetail.getDeptRemark()==null){
//            empAppraiseDetailDto.setDeptRemark("良好");
//        }else{
//            empAppraiseDetailDto.setDeptRemark(empAppraiseDetail.getDeptRemark());
//        }
//
//        if(empAppraiseDetail.getDeptGrade()==null){
//            empAppraiseDetailDto.setDeptGrade("良好");
//        }else{
//            empAppraiseDetailDto.setDeptGrade(empAppraiseDetail.getDeptGrade());
//        }
//
//        if(empAppraiseDetail.getDeptScores()==null){
//            empAppraiseDetailDto.setDeptScores(90f);
//        }else{
//            empAppraiseDetailDto.setDeptScores(empAppraiseDetail.getDeptScores());
//        }
//        empAppraiseDetailDto.setBlongDeptId(empAppraiseDetail.getEmpAppraise().getBlongDeptId());
//        empAppraiseDetailDto.setEmpId(empAppraiseDetail.getEmployee().getId());
//        empAppraiseDetailDto.setLoginName(empAppraiseDetail.getEmployee().getLoginName());
//
////        if(empAppraiseDetail.getEmpAppraise()!=null) {
////            if(empAppraiseDetail.getEmpAppraise().getFormNo()!=null);
////            empAppraiseDetailDto.setFormNo(empAppraiseDetail.getEmpAppraise().getFormNo());
////        }
//        empAppraiseDetailDto.setId(empAppraiseDetail.getId());
//        return  empAppraiseDetailDto;
//
//    }

    @Override
    public CommonResult doApprove(ApproveFormDto dto,Map<String,Object> param){

        if (param.get("clStep").toString().equals("2") && param.get("isStepComplete").toString().equals("false") && dto.getAppValue().equals(AppValue.Y.ordinal())) {
//            String formNo = param.get("formNo").toString();
//            EmpCriteria empCriteria = new EmpCriteria();
//            PagedResult<EmpAppraiseDetailDto> pagedResult=this.getPagesByAllDetails(Long.parseLong(formNo),empCriteria);
//            List<EmpAppraiseDetailDto> list = pagedResult.getItems();
//            for (EmpAppraiseDetailDto detailDto:list){
//                if (StringUtils.isNullOrWhiteSpace(detailDto.getChargeLeaderScores()) && StringUtils.isNullOrWhiteSpace(detailDto.getChargeLeaderLevel())){
//
//                }
//            }

            //判断上传结果为空
            Criteria criteria = getSession().createCriteria(EmpAppraiseDetail.class);
            criteria.add(Restrictions.eq("isActive",true));
            criteria.createAlias("empAppraise", "emp");
            criteria.add(Restrictions.eq("emp.formNo", Long.parseLong(param.get("formNo").toString())));
            criteria.add(Restrictions.or(Restrictions.isNull("chargeLeaderScores"),Restrictions.isNull("chargeLeaderLevel")));

            criteria.setResultTransformer(Criteria.ROOT_ENTITY);
            List<EmpAppraiseDetail> allList = criteria.list();

            if (allList != null &&allList.size()>0){
                return ResultFactory.commonError("请填写员工的评分或评级");
            }

        }


        return super.doApprove(dto, param);
    }


}
