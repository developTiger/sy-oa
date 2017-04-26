package com.sunesoft.lemon.syms.hrForms.application.formEmpAppraise;

import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateFinder;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.ResultFactory;
import com.sunesoft.lemon.syms.eHr.application.factory.DtoFactory;
import com.sunesoft.lemon.syms.eHr.domain.empInfo.Employee;
import com.sunesoft.lemon.syms.eHr.domain.empInfo.EmployeeRepository;
import com.sunesoft.lemon.syms.hrForms.application.Dtos.EmpAppraiseDetailDto;
import com.sunesoft.lemon.syms.hrForms.domain.EmpAppraiseDetail;
import com.sunesoft.lemon.syms.hrForms.domain.EmpAppraiseDetailRepository;
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
@Service("formAppraiseDetailsService")
public class FormAppraiseDetailsServiceImpl extends GenericHibernateFinder implements FormAppraiseDetailsService{

    @Autowired
    EmpAppraiseDetailRepository empAppraiseDetailRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public CommonResult addOrUpdateFormAppraiseDetails(EmpAppraiseDetailDto empAppraiseDetailDto) {

        /*
         * 员工自评分数，根据日期自评保存
         */
        EmpAppraiseDetail  empAppraiseDetail= empAppraiseDetailRepository.get(empAppraiseDetailDto.getId());

        if(empAppraiseDetailDto.getEmpSelfLevel()!=null){
            empAppraiseDetail.setEmpSelfLevel(empAppraiseDetailDto.getEmpSelfLevel());
        }
        if(empAppraiseDetailDto.getEmpSelfScores()!=null &&empAppraiseDetailDto.getEmpSelfScores()>0){
            empAppraiseDetail.setAppStatus(1);
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
        if(empAppraiseDetailDto.getAppStatus()!=null) {
            empAppraiseDetail.setAppStatus(empAppraiseDetailDto.getAppStatus());
        }

        if (empAppraiseDetailDto.getChargeLeaderScores() != null ){
            empAppraiseDetail.setChargeLeaderScores(empAppraiseDetailDto.getChargeLeaderScores());
        }
        if (empAppraiseDetailDto.getChargeLeaderLevel() != null){
            empAppraiseDetail.setChargeLeaderLevel(empAppraiseDetailDto.getChargeLeaderLevel());
        }
        if (empAppraiseDetailDto.getGroupScores() != null){
            empAppraiseDetail.setGroupScores(empAppraiseDetailDto.getGroupScores());
        }
        if (empAppraiseDetailDto.getGroupGrade() != null){
            empAppraiseDetail.setGroupGrade(empAppraiseDetailDto.getGroupGrade());
        }

        empAppraiseDetailRepository.save(empAppraiseDetail);
        return ResultFactory.commonSuccess();
    }

    @Override
    public EmpAppraiseDetailDto getByid(Long id) {

        return DtoFactory.convert(empAppraiseDetailRepository.get(id),new EmpAppraiseDetailDto());
    }

    @Override
    public List<EmpAppraiseDetailDto> getByEmpAndStatus(Long empId, Integer status) {

        String hql = "from EmpAppraiseDetail d where  d.appStatus="+status+" and d.employee.id="+empId
                +" order by d.createDateTime desc";
        Query query = getSession().createQuery(hql);

        List<EmpAppraiseDetail> details = query.list();
        List<EmpAppraiseDetailDto> dtos = new ArrayList<>();
        if (details != null && details.size() > 0) {
            for (EmpAppraiseDetail d : details) {
                EmpAppraiseDetailDto appraiseDetailDto = DtoFactory.convert(d, new EmpAppraiseDetailDto());
                appraiseDetailDto.setEmpId(d.getEmployee().getId());
                appraiseDetailDto.setLoginName(d.getEmployee().getName());
                if(d.getEmpAppraise()==null)continue;
                appraiseDetailDto.setAppName(d.getEmpAppraise().getAppraisTitle());
                dtos.add(appraiseDetailDto);
            }
        }
        return dtos;
    }
  /*  @Override
    public CommonResult addOrUpdateFormAppraiseDetails(EmpAppraiseDetailDto empAppraiseDetailDto) {

        empAppraiseDetailRepository.save(convertFromDto(empAppraiseDetailDto));
        return ResultFactory.commonSuccess();
    }

    @Override
    public EmpAppraiseDetailDto ByEmpIDgetEmpAppraiseDetails(Long id) {
        Criteria criterion=getSession().createCriteria(EmpAppraiseDetail.class);
        criterion.add(Restrictions.eq("employee.id",id));
        EmpAppraiseDetail empAppraiseDetail=(EmpAppraiseDetail)criterion.uniqueResult();
        if(empAppraiseDetail==null){
            return null;
        }
        return EntityToDto((EmpAppraiseDetail) criterion.uniqueResult());
    }*/



    public EmpAppraiseDetailDto EntityToDto(EmpAppraiseDetail empAppraiseDetail) {

        EmpAppraiseDetailDto empAppraiseDetailDto=new EmpAppraiseDetailDto();

        empAppraiseDetailDto.setEmpSelfScores(empAppraiseDetail.getEmpSelfScores());
        if(empAppraiseDetail.getEmpSelfGrade()==null){
            empAppraiseDetailDto.setEmpSelfGrade("");
        }else{
            empAppraiseDetailDto.setEmpSelfGrade(empAppraiseDetail.getEmpSelfGrade());
        }

        if(empAppraiseDetail.getEmpSelfLevel()==null){
            empAppraiseDetailDto.setEmpSelfLevel("");
        }else{
            empAppraiseDetailDto.setEmpSelfLevel(empAppraiseDetail.getEmpSelfLevel());
        }

        if(empAppraiseDetail.getEmpSelfRemark()==null){
            empAppraiseDetailDto.setEmpSelfRemark("");
        }else{
            empAppraiseDetailDto.setEmpSelfRemark(empAppraiseDetail.getEmpSelfRemark());
        }
        //empAppraiseDetailDto.setFormNo(empAppraiseDetail.getEmpAppraise().getFormNo());
        empAppraiseDetailDto.setId(empAppraiseDetail.getId());
        return  empAppraiseDetailDto;

    }

    private EmpAppraiseDetail convertFromDto(EmpAppraiseDetailDto empAppraiseDetailDto) {

        EmpAppraiseDetail empAppraiseDetail=null;

       Employee employee=new Employee();
        if(empAppraiseDetailDto.getEmpId()!=null &&empAppraiseDetailDto.getEmpId()>0){
            employee =employeeRepository.get(empAppraiseDetailDto.getEmpId());
            Criteria criterion=getSession().createCriteria(EmpAppraiseDetail.class);
            criterion.add(Restrictions.eq("employee.id", empAppraiseDetailDto.getEmpId()));
            empAppraiseDetail=(EmpAppraiseDetail)criterion.uniqueResult();
        }
        if(empAppraiseDetail==null){
            empAppraiseDetail=new EmpAppraiseDetail();
        }

        empAppraiseDetail.setEmployee(employee);
      /*  Employee employee=new Employee();
        employee.setId(empAppraiseDetailDto.getEmpId());
        empAppraiseDetail.setEmployee(employee);*/

      /*  EmpAppraise empAppraise=new EmpAppraise();
        if(empAppraiseDetailDto.getFormNo()!=null && empAppraiseDetailDto.getFormNo()>0){
            Criteria criterion=getSession().createCriteria(EmpAppraise.class);
            criterion.add(Restrictions.eq("formNo", empAppraiseDetailDto.getFormNo()));
            empAppraise=(EmpAppraise)criterion.uniqueResult();
        }
        empAppraiseDetail.setEmpAppraise(empAppraise);*/

        if(empAppraiseDetailDto.getEmpSelfLevel()!=null){
            empAppraiseDetail.setEmpSelfLevel(empAppraiseDetailDto.getEmpSelfLevel());
        }
        if(empAppraiseDetailDto.getEmpSelfScores()!=null && empAppraiseDetailDto.getEmpSelfScores()>0){
            empAppraiseDetail.setEmpSelfScores(empAppraiseDetailDto.getEmpSelfScores());
        }
        if(empAppraiseDetailDto.getEmpSelfGrade()!=null){
            empAppraiseDetail.setEmpSelfGrade(empAppraiseDetailDto.getEmpSelfGrade());
        }
        if(empAppraiseDetailDto.getEmpSelfRemark()!=null){
            empAppraiseDetail.setEmpSelfRemark(empAppraiseDetailDto.getEmpSelfRemark());
        }
        return empAppraiseDetail;

    }
}
