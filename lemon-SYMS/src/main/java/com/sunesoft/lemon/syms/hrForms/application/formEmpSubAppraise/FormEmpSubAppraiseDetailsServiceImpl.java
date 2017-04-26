package com.sunesoft.lemon.syms.hrForms.application.formEmpSubAppraise;

import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateFinder;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.ResultFactory;
import com.sunesoft.lemon.syms.eHr.application.factory.DtoFactory;
import com.sunesoft.lemon.syms.eHr.domain.empInfo.Employee;
import com.sunesoft.lemon.syms.eHr.domain.empInfo.EmployeeRepository;
import com.sunesoft.lemon.syms.hrForms.application.Dtos.EmpSubAppraiseDetailDto;
import com.sunesoft.lemon.syms.hrForms.domain.EmpSubAppraise;
import com.sunesoft.lemon.syms.hrForms.domain.EmpSubAppraiseDetail;
import com.sunesoft.lemon.syms.hrForms.domain.EmpSubAppraiseDetailsRepository;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by temp on 2016/8/2.
 */
@Service("formEmpSubAppraiseDetailsService")
public class FormEmpSubAppraiseDetailsServiceImpl extends GenericHibernateFinder implements FormEmpSubAppraiseDetailsService{

    @Autowired
    EmpSubAppraiseDetailsRepository empSubAppraiseDetailRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public CommonResult addOrUpdateFormAppraiseDetails(EmpSubAppraiseDetailDto empAppraiseDetailDto) {

        /*
         * 员工自评分数，根据日期自评保存
         */
        EmpSubAppraiseDetail empAppraiseDetail=null;
//        if(empAppraiseDetailDto.getEmpSelfDate()!=null && empAppraiseDetailDto.getId()==null){
//
//            String sql = " select t2.id from syy_oa_emp_appraise_detail t1,(select id from SYY_OA_EMP_APPRAISE where to_date("+empAppraiseDetailDto.getEmpSelfDate()+",'YYYY-MM-DD') " +
//                         " between to_date('start_date','YYYY-MM-DD') and to_date('end_date','YYYY-MM-DD')) t2 "+
//                         " where t1.emp_appraise_id=t2.id and t1.appraise_emp_id="+empAppraiseDetailDto.getEmpId();
//            Query temp=createQuery(sql);
//            Long detail_id=(Long)temp.uniqueResult();
//            empAppraiseDetailDto.setId(detail_id);
//        }
//        empAppraiseDetail= empSubAppraiseDetailRepository.get(empAppraiseDetailDto.getId());

        /*
        if(empAppraiseDetailDto.getEmpSelfLevel()!=null){
            empAppraiseDetail.setEmpSelfLevel(empAppraiseDetailDto.getEmpSelfLevel());
        }
        if(empAppraiseDetailDto.getEmpSelfScores()!=null &&empAppraiseDetailDto.getEmpSelfScores()>0){
            empAppraiseDetail.setEmpSelfScores(empAppraiseDetailDto.getEmpSelfScores());
        }
        if(empAppraiseDetailDto.getEmpSelfGrade()!=null){
            empAppraiseDetail.setEmpSelfGrade(empAppraiseDetailDto.getEmpSelfGrade());
        }
        if(empAppraiseDetailDto.getEmpSelfRemark()!=null) {
            empAppraiseDetail.setEmpSelfRemark(empAppraiseDetailDto.getEmpSelfRemark());
        }
        if(empAppraiseDetailDto.getDeptLevel()!=null){
            empAppraiseDetail.setDeptLevel(empAppraiseDetailDto.getDeptLevel());
        }
        if(empAppraiseDetailDto.getDeptScores()!=null &&empAppraiseDetailDto.getDeptScores() > 0) {
            empAppraiseDetail.setDeptScores(empAppraiseDetailDto.getDeptScores());
        }
        if(empAppraiseDetailDto.getDeptGrade() != null) {
            empAppraiseDetail.setDeptGrade(empAppraiseDetailDto.getDeptGrade());
        }
        if(empAppraiseDetailDto.getDeptRemark()!=null){
            empAppraiseDetail.setDeptRemark(empAppraiseDetailDto.getDeptRemark());
        }
        if(empAppraiseDetailDto.getId()!=null){
            empAppraiseDetail.setId(empAppraiseDetailDto.getId());
        }*/
        empAppraiseDetail= DtoFactory.convert(empAppraiseDetailDto, empAppraiseDetail);
        empSubAppraiseDetailRepository.save(empAppraiseDetail);
        return ResultFactory.commonSuccess();
    }
/*
    @Override
    public EmpSubAppraiseDetailDto getEmpSubAppraiseDetails(Long formNo) {
        Criteria criterion=getSession().createCriteria(EmpSubAppraiseDetail.class);
        criterion.add(Restrictions.eq("empAppraise.id",formNo));
        EmpSubAppraiseDetail empAppraiseDetail=(EmpSubAppraiseDetail)criterion.uniqueResult();

        if(empAppraiseDetail==null){
            EmpSubAppraiseDetailDto empSubAppraiseDetailDto= new EmpSubAppraiseDetailDto();
            empSubAppraiseDetailDto.setEmpSelfLevel("良好");
            empSubAppraiseDetailDto.setEmpSelfScores(90f);
            empSubAppraiseDetailDto.setEmpSelfGrade("良好");
            empSubAppraiseDetailDto.setDeptLevel("良好");
            empSubAppraiseDetailDto.setDeptScores(90f);
            empSubAppraiseDetailDto.setDeptGrade("良好");
            empSubAppraiseDetailDto.setDeptRemark("");
            return empSubAppraiseDetailDto;
        }
        return EntityToDto(empAppraiseDetail);
    }

    @Override
    public EmpSubAppraiseDetailDto ByEmpIDgetEmpSubAppraiseDetails(Long id) {

        Criteria criterion=getSession().createCriteria(EmpSubAppraiseDetail.class);
        criterion.add(Restrictions.eq("employee.id",id));
        EmpSubAppraiseDetail empAppraiseDetail=(EmpSubAppraiseDetail)criterion.uniqueResult();
        if(empAppraiseDetail==null){
            EmpSubAppraiseDetailDto empSubAppraiseDetailDto= new EmpSubAppraiseDetailDto();

            *//*empSubAppraiseDetailDto.setEmpSelfLevel("良好");
            empSubAppraiseDetailDto.setEmpSelfScores(90f);
            empSubAppraiseDetailDto.setEmpSelfGrade("良好");*//*

            empSubAppraiseDetailDto.setDeptLevel("良好");
            empSubAppraiseDetailDto.setDeptScores(90f);
            empSubAppraiseDetailDto.setDeptGrade("良好");
            empSubAppraiseDetailDto.setDeptRemark("");
            return empSubAppraiseDetailDto;
        }
        return EntityToDto(empAppraiseDetail);
    }*/

    @Override
    public List<EmpSubAppraiseDetailDto> getEmpSubAppraiseDetailsByParentFormNo(Long formNo,Long blongDeptId) {

       /* Criteria criteria=getSession().createCriteria(Employee.class);
        criteria.add(Restrictions.eq("dept.id",blongDeptId));
        List<Employee> empDtoList=criteria.list();

        Criteria criterion=getSession().createCriteria(EmpSubAppraiseDetail.class);
        criterion.add(Restrictions.eq("empAppraise.id",formNo));
        List<EmpSubAppraiseDetail> empSubAppraiseDetailDtos=criterion.list();
        List<EmpSubAppraiseDetailDto> empSubAppraiseDetailDtos1=new ArrayList<>();
        if(empSubAppraiseDetailDtos==null || empSubAppraiseDetailDtos.size()==0){
            for(Employee e:empDtoList){
                empSubAppraiseDetailDtos1.add(addConvertFrom(e));
            }
        }else{
            for(EmpSubAppraiseDetail empSubAppraiseDetail:empSubAppraiseDetailDtos){
                empSubAppraiseDetailDtos1.add(addConvertFrom2(empSubAppraiseDetail));
            }
            if(empSubAppraiseDetailDtos1.size()==empDtoList.size()){
                return empSubAppraiseDetailDtos1;
            }else {
                List<Employee> tempEmp=new ArrayList<>();
                for (Employee employee : empDtoList) {
                    for(EmpSubAppraiseDetail e:empSubAppraiseDetailDtos){
                        if(employee.getId()!=e.getEmployee().getId()){
                            tempEmp.add(employee);
                        }
                    }
                }
                for(Employee e:tempEmp){
                    empSubAppraiseDetailDtos1.add(addConvertFrom(e));
                }
            }
        }*/
        Criteria criterion=getSession().createCriteria(EmpSubAppraiseDetail.class);
        criterion.add(Restrictions.eq("empAppraise.id",formNo+1));
        List<EmpSubAppraiseDetail> empSubAppraiseDetailDtos=criterion.list();
        List<EmpSubAppraiseDetailDto> empSubAppraiseDetailDtoList=new ArrayList<EmpSubAppraiseDetailDto>();
        for(EmpSubAppraiseDetail empSubAppraiseDetail:empSubAppraiseDetailDtos){
           // EmpSubAppraiseDetailDto empSubAppraiseDetailDto= DtoFactory.convert(empSubAppraiseDetail, new EmpSubAppraiseDetailDto());
            EmpSubAppraiseDetailDto empSubAppraiseDetailDto=EntityToDto(empSubAppraiseDetail);
            empSubAppraiseDetailDtoList.add(empSubAppraiseDetailDto);
        }
        return empSubAppraiseDetailDtoList;
    }

    private EmpSubAppraiseDetailDto addConvertFrom2(EmpSubAppraiseDetail empSubAppraiseDetail) {
        EmpSubAppraiseDetailDto empSubAppraiseDetailDto=new EmpSubAppraiseDetailDto();

        return empSubAppraiseDetailDto;
    }

    public EmpSubAppraiseDetailDto addConvertFrom(Employee empDto){
        EmpSubAppraiseDetailDto empSubAppraiseDetailDto=new EmpSubAppraiseDetailDto();
        empSubAppraiseDetailDto.setEmpId(empDto.getId());
        empSubAppraiseDetailDto.setLoginName(empDto.getLoginName());
//        if(empDto.getDept()!=null) {
//            empSubAppraiseDetailDto.setDeptName(empDto.getDept().getDeptName());
//            empSubAppraiseDetailDto.setDeptId(empDto.getDept().getId());
//        }
        empSubAppraiseDetailDto.setEmpSelfScores(empSubAppraiseDetailDto.getEmpSelfScores());
        empSubAppraiseDetailDto.setDeptLevel("良好");
        empSubAppraiseDetailDto.setDeptRemark("良好");
        empSubAppraiseDetailDto.setDeptGrade("良好");
        empSubAppraiseDetailDto.setDeptScores(90f);
        return empSubAppraiseDetailDto;
    }

    public EmpSubAppraiseDetailDto EntityToDto(EmpSubAppraiseDetail empAppraiseDetail) {

        EmpSubAppraiseDetailDto empAppraiseDetailDto=new EmpSubAppraiseDetailDto();

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

        if(empAppraiseDetail.getDeptLevel()==null){
            empAppraiseDetailDto.setDeptLevel("良好");
        }else{
            empAppraiseDetailDto.setDeptLevel(empAppraiseDetail.getDeptLevel());
        }

        if(empAppraiseDetail.getDeptRemark()==null){
            empAppraiseDetailDto.setDeptRemark("良好");
        }else{
            empAppraiseDetailDto.setDeptRemark(empAppraiseDetail.getDeptRemark());
        }

        if(empAppraiseDetail.getDeptGrade()==null){
            empAppraiseDetailDto.setDeptGrade("良好");
        }else{
            empAppraiseDetailDto.setDeptGrade(empAppraiseDetail.getDeptGrade());
        }

        if(empAppraiseDetail.getDeptScores()==null){
            empAppraiseDetailDto.setDeptScores(90f);
        }else{
            empAppraiseDetailDto.setDeptScores(empAppraiseDetail.getDeptScores());
        }
//        empAppraiseDetailDto.setBlongDeptId(empAppraiseDetail.getEmpAppraise().getBlongDeptId());
//        empAppraiseDetailDto.setEmpId(empAppraiseDetail.getEmployee().getId());
//
////        if(empAppraiseDetail.getEmpAppraise()!=null) {
////            if(empAppraiseDetail.getEmpAppraise().getFormNo()!=null);
////            empAppraiseDetailDto.setFormNo(empAppraiseDetail.getEmpAppraise().getFormNo());
////        }
//        empAppraiseDetailDto.setId(empAppraiseDetail.getId());
        return  empAppraiseDetailDto;

    }

    private EmpSubAppraiseDetail convertFromDto(EmpSubAppraiseDetailDto empAppraiseDetailDto) {

        EmpSubAppraiseDetail empAppraiseDetail=new EmpSubAppraiseDetail();
        Employee employee=new Employee();
        if(empAppraiseDetailDto.getEmpId()!=null &&empAppraiseDetailDto.getEmpId()>0){
            employee =employeeRepository.get(empAppraiseDetailDto.getEmpId());
        }
        empAppraiseDetail.setEmployee(employee);

        EmpSubAppraise empAppraise=new EmpSubAppraise();
//        if(empAppraiseDetailDto.getFormNo()!=null && empAppraiseDetailDto.getFormNo()>0){
//            Criteria criterion=getSession().createCriteria(EmpSubAppraise.class);
//            criterion.add(Restrictions.eq("formNo", empAppraiseDetailDto.getFormNo()));
//            empAppraise=(EmpSubAppraise)criterion.uniqueResult();
//        }
        //empAppraiseDetail.setEmpAppraise(empAppraise);

        if(empAppraiseDetailDto.getEmpSelfLevel()!=null){
            empAppraiseDetail.setEmpSelfLevel(empAppraiseDetailDto.getEmpSelfLevel());
        }
        if(empAppraiseDetailDto.getEmpSelfScores()!=null &&empAppraiseDetailDto.getEmpSelfScores()>0){
            empAppraiseDetail.setEmpSelfScores(empAppraiseDetailDto.getEmpSelfScores());
        }
        if(empAppraiseDetailDto.getEmpSelfGrade()!=null){
            empAppraiseDetail.setEmpSelfGrade(empAppraiseDetailDto.getEmpSelfGrade());
        }
        if(empAppraiseDetailDto.getEmpSelfRemark()!=null) {
            empAppraiseDetail.setEmpSelfRemark(empAppraiseDetailDto.getEmpSelfRemark());
        }
        if(empAppraiseDetailDto.getDeptLevel()!=null){
            empAppraiseDetail.setDeptLevel(empAppraiseDetailDto.getDeptLevel());
        }
        if(empAppraiseDetailDto.getDeptScores()!=null &&empAppraiseDetailDto.getDeptScores() > 0) {
            empAppraiseDetail.setDeptScores(empAppraiseDetailDto.getDeptScores());
        }
        if(empAppraiseDetailDto.getDeptGrade() != null) {
            empAppraiseDetail.setDeptGrade(empAppraiseDetailDto.getDeptGrade());
        }
        if(empAppraiseDetailDto.getDeptRemark()!=null){
            empAppraiseDetail.setDeptRemark(empAppraiseDetailDto.getDeptRemark());
        }

        return empAppraiseDetail;
    }
}
