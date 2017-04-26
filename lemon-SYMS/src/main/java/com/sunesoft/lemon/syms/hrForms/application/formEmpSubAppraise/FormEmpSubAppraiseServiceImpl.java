package com.sunesoft.lemon.syms.hrForms.application.formEmpSubAppraise;

import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.ResultFactory;
import com.sunesoft.lemon.syms.eHr.application.factory.DtoFactory;
import com.sunesoft.lemon.syms.eHr.domain.empInfo.Employee;
import com.sunesoft.lemon.syms.hrForms.application.Dtos.EmpAppraiseDetailDto;
import com.sunesoft.lemon.syms.hrForms.application.Dtos.EmpSubAppraiseDetailDto;
import com.sunesoft.lemon.syms.hrForms.application.Dtos.FormEmpSubAppraiseDto;
import com.sunesoft.lemon.syms.hrForms.domain.*;
import com.sunesoft.lemon.syms.workflow.application.FormBase;
import com.sunesoft.lemon.syms.workflow.application.FormBase2;
import com.sunesoft.lemon.syms.workflow.application.dtos.ApproveFormDto;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("formEmpSubAppraiseService")
public class FormEmpSubAppraiseServiceImpl extends FormBase2<EmpSubAppraise, FormEmpSubAppraiseDto> implements FormEmpSubAppraiseService {

    @Autowired
    EmpSubAppraiseRepository empSubAppraiseRepository;

    @Autowired
    EmpAppraiseRepository empAppraiseRepository;

    @Autowired
    EmpSubAppraiseDetailsRepository empSubAppraiseDetailsRepository;


    @Override
    protected CommonResult save(EmpSubAppraise empSubAppraise) {

//        if(empSubAppraise.getId()==null) {
//            Criteria criterion = getSession().createCriteria(Employee.class);
//            criterion.add(Restrictions.eq("isActive", true));
//            criterion.createAlias("dept","dept");
//            criterion.add(Restrictions.eq("dept.id",empSubAppraise.getBlongDeptId()));
//            List<Employee>  employees = criterion.list();
//            List<EmpSubAppraiseDetail> details = new ArrayList<>();
//            for (Employee e : employees) {
//                EmpSubAppraiseDetail detail = new EmpSubAppraiseDetail();
//                detail.setDeptId(e.getDept().getId());
//                detail.setIsDone(false);
//                detail.setEmployee(e);
//                details.add(detail);
//            }
//            empSubAppraise.setDetails(details);
//        }
        empSubAppraiseRepository.save(empSubAppraise);
        return ResultFactory.commonSuccess();
    }

    @Override
    protected CommonResult update(EmpSubAppraise empSubAppraise) {
        empSubAppraiseRepository.save(empSubAppraise);
        return ResultFactory.commonSuccess();
    }

    @Override
    protected EmpSubAppraise ConvetDto(FormEmpSubAppraiseDto Dto) {

        EmpSubAppraise empSubAppraise = new EmpSubAppraise();
        empSubAppraise = DtoFactory.convert(Dto, empSubAppraise);
        return empSubAppraise;
    }

    @Override
    protected String getSummery(EmpSubAppraise empSubAppraise) {
        return null;
    }

    @Override
    public CommonResult doAafterApprovedStep(Long formNo, Long step) {
        return null;
    }

    @Override
    public CommonResult doAafterAllApproved(Long formNo) {
//        EmpSubAppraise empSubAppraise=getByFormNo(formNo);
//        EmpAppraise empAppraise=empAppraiseRepository.getByFormNo(empSubAppraise.getParentFormNo());
//        Criteria criterion=getSession().createCriteria(EmpAppraiseDetail.class);
//        criterion.add(Restrictions.eq("empAppraise",empAppraise.getId()));
//        List<EmpAppraiseDetail> listEmpSubDetail=criterion.list();
//        for (EmpAppraiseDetail detail:listEmpSubDetail){
//            detail.setAppStatus(2);
//        }
//        empAppraise.setDetails(listEmpSubDetail);
//        empAppraiseRepository.save(empAppraise);
        return ResultFactory.commonSuccess();
    }

    @Override
    protected EmpSubAppraise getByFormNo(Long formNo) {
        Criteria criterion = getSession().createCriteria(EmpSubAppraise.class);
        criterion.add(Restrictions.eq("isActive",true));
        criterion.add(Restrictions.eq("formNo", formNo));
        return (EmpSubAppraise) criterion.uniqueResult();
    }


    @Override
    public FormEmpSubAppraiseDto getFormByFormNo(Long formNo) {
        EmpSubAppraise empSubAppraise = this.getByFormNo(formNo);
        FormEmpSubAppraiseDto subAppraiseDto = new FormEmpSubAppraiseDto();
        subAppraiseDto = DtoFactory.convert(empSubAppraise, subAppraiseDto);

        subAppraiseDto.setEmpSubAppraiseDetailDtos(getEmpAppraiseDetailDtos(empSubAppraise.getParentFormNo(),empSubAppraise.getBlongDeptId()));

        return subAppraiseDto;
    }


    public List<EmpAppraiseDetailDto> getEmpAppraiseDetailDtos(Long parentFormNo, Long dept) {

        String hql = "from EmpAppraiseDetail d where d.empAppraise.formNo="+parentFormNo +" and d.employee.isActive=true and d.employee.dept.id="+dept;

        Query query = getSession().createQuery(hql);

        List<EmpAppraiseDetail> details = query.list();
        List<EmpAppraiseDetailDto> dtos = new ArrayList<>();
        if (details != null && details.size() > 0) {
            for (EmpAppraiseDetail d : details) {
                EmpAppraiseDetailDto appraiseDetailDto = DtoFactory.convert(d, new EmpAppraiseDetailDto());
                appraiseDetailDto.setEmpId(d.getEmployee().getId());
                appraiseDetailDto.setLoginName(d.getEmployee().getName());
                appraiseDetailDto.setAppName(d.getEmpAppraise().getAppraisTitle());
                dtos.add(appraiseDetailDto);
            }
        }
        return dtos;
    }

    public EmpSubAppraiseDetailDto EntityToDto(EmpSubAppraiseDetail empAppraiseDetail) {

        EmpSubAppraiseDetailDto empAppraiseDetailDto = new EmpSubAppraiseDetailDto();

       /* empAppraiseDetailDto.setEmpSelfScores(empAppraiseDetail.getEmpSelfScores());
        if(empAppraiseDetail.getEmpSelfGrade()==null){
            empAppraiseDetailDto.setEmpSelfGrade("良好");
        }else{
            empAppraiseDetailDto.setEmpSelfGrade(empAppraiseDetail.getEmpSelfGrade());
        }

        if(empAppraiseDetail.getEmpSelfLevel()==null){
            empAppraiseDetailDto.setEmpSelfLevel("良好");
        }else{
            empAppraiseDetailDto.setEmpSelfLevel(empAppraiseDetail.getEmpSelfLevel());
        }

        if(empAppraiseDetail.getEmpSelfRemark()==null){
            empAppraiseDetailDto.setEmpSelfRemark("良好");
        }else{
            empAppraiseDetailDto.setEmpSelfRemark(empAppraiseDetail.getEmpSelfRemark());
        }*/

        if (empAppraiseDetail.getDeptLevel() == null) {
            empAppraiseDetailDto.setDeptLevel("良好");
        } else {
            empAppraiseDetailDto.setDeptLevel(empAppraiseDetail.getDeptLevel());
        }

        if (empAppraiseDetail.getDeptRemark() == null) {
            empAppraiseDetailDto.setDeptRemark("良好");
        } else {
            empAppraiseDetailDto.setDeptRemark(empAppraiseDetail.getDeptRemark());
        }

        if (empAppraiseDetail.getDeptGrade() == null) {
            empAppraiseDetailDto.setDeptGrade("良好");
        } else {
            empAppraiseDetailDto.setDeptGrade(empAppraiseDetail.getDeptGrade());
        }

        if (empAppraiseDetail.getDeptScores() == null) {
            empAppraiseDetailDto.setDeptScores(90f);
        } else {
            empAppraiseDetailDto.setDeptScores(empAppraiseDetail.getDeptScores());
        }
        //empAppraiseDetailDto.setBlongDeptId(empAppraiseDetail.getEmpAppraise().getBlongDeptId());
        empAppraiseDetailDto.setEmpId(empAppraiseDetail.getEmployee().getId());

//        if(empAppraiseDetail.getEmpAppraise()!=null) {
//            if(empAppraiseDetail.getEmpAppraise().getFormNo()!=null);
//            empAppraiseDetailDto.setFormNo(empAppraiseDetail.getEmpAppraise().getFormNo());
//        }
     //   empAppraiseDetailDto.setId(empAppraiseDetail.getId());
        return empAppraiseDetailDto;

    }

//    @Override
//    public CommonResult doApprove(ApproveFormDto dto, Map<String, Object> param) {
//
//
//        EmpSubAppraise empSubAppraise = this.getByFormNo(dto.getFormNo());
//
//       /* EmpSubAppraise empSubAppraise=this.getByFormNo(dto.getFormNo());
//        empSubAppraise.setDetails(empSubAppraiseDetailList);*/
//        if (empSubAppraise.getClStep() == 1) {
//            return new CommonResult(true, "该表单已经签核完成，无需再次签核。");
//        }
//        empSubAppraise.setClStep(1);
//
//        Long id = empSubAppraiseRepository.save(empSubAppraise);
//        if (id > 0) {
//            return new CommonResult(true, "");
//        } else {
//            return new CommonResult(false, "");
//        }
//    }
}
