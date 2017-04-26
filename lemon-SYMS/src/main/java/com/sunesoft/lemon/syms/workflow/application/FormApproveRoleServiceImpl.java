package com.sunesoft.lemon.syms.workflow.application;

import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateFinder;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.results.ResultFactory;
import com.sunesoft.lemon.fr.utils.BeanUtils;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.syms.eHr.application.dtos.EmpSimpleDto;
import com.sunesoft.lemon.syms.eHr.domain.empInfo.Employee;
import com.sunesoft.lemon.syms.eHr.domain.empInfo.EmployeeRepository;
import com.sunesoft.lemon.syms.workflow.application.criteria.FormApproveRoleCriteria;
import com.sunesoft.lemon.syms.workflow.application.dtos.FormAppRoleDto;
import com.sunesoft.lemon.syms.workflow.domain.FormApproveRole;
import com.sunesoft.lemon.syms.workflow.domain.FormApproveRoleRepository;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MJ006 on 2016/6/20.
 */
@Service("FormApproveRoleService")
public class FormApproveRoleServiceImpl extends GenericHibernateFinder implements FormApproveRoleService {
    @Autowired
    FormApproveRoleRepository formApproveRoleRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public FormAppRoleDto getByKey(Long id) {
        FormApproveRole formApproveRole = formApproveRoleRepository.get(id);
        FormAppRoleDto dto = new FormAppRoleDto();
        BeanUtils.copyProperties(formApproveRole, dto);
        dto.setEmpList(new ArrayList<EmpSimpleDto>());
        for(Employee emp : formApproveRole.getApprover()){
            EmpSimpleDto empdto = new EmpSimpleDto();
            empdto.setId(emp.getId());
            empdto.setLoginName(emp.getLoginName());
            empdto.setName(emp.getName());
            empdto.setUserNo(emp.getUserNo());
            dto.getEmpList().add(empdto);
        }
        return dto;
    }

    @Override
    public CommonResult add(FormAppRoleDto dto) {
        FormApproveRole formApproveRole = new FormApproveRole();
        BeanUtils.copyProperties(dto, formApproveRole);

        Long id = formApproveRoleRepository.save(formApproveRole);
        return ResultFactory.commonSuccess(id);
    }

    @Override
    public CommonResult update(FormAppRoleDto dto) {
        FormApproveRole formApproveRole = formApproveRoleRepository.get(dto.getId());
        BeanUtils.copyProperties(dto, formApproveRole);
        formApproveRoleRepository.save(formApproveRole);
        return ResultFactory.commonSuccess();
    }

    @Override
    public CommonResult delete(Long id) {
        formApproveRoleRepository.delete(id);
        return ResultFactory.commonSuccess();
    }

    @Override
    public CommonResult addRoleEmp(Long roleId, Long empId) {
        FormApproveRole formApproveRole = formApproveRoleRepository.get(roleId);
        Employee emp = employeeRepository.get(empId);
        formApproveRole.getApprover().add(emp);
        formApproveRoleRepository.save(formApproveRole);
        return ResultFactory.commonSuccess();
    }

    @Override
    public CommonResult addRoleEmp(Long roleId, List<Long> empId) {
        FormApproveRole formApproveRole = formApproveRoleRepository.get(roleId);
        List<Employee> emps = employeeRepository.getByIds(empId);
        formApproveRole.getApprover().addAll(emps);
        formApproveRoleRepository.save(formApproveRole);
        return ResultFactory.commonSuccess();
    }

    @Override
    public CommonResult removeRoleEmp(Long roleId, Long empId) {
        FormApproveRole formApproveRole = formApproveRoleRepository.get(roleId);
      //  List<Employee> employeeList = formApproveRole.getApprover();
        for(  Employee emp : formApproveRole.getApprover()){
            if(emp.getId().equals(empId)){
                formApproveRole.getApprover().remove(emp);
                break;
            }
        }

        formApproveRoleRepository.save(formApproveRole);
        return ResultFactory.commonSuccess();
    }

    @Override
    public PagedResult<FormAppRoleDto> getFormListPaged(FormApproveRoleCriteria criteria) {
        Criteria criterion = getSession().createCriteria(FormApproveRole.class);
        if (!StringUtils.isNullOrWhiteSpace(criteria.getRoleName())) {
            criterion.add(Restrictions.like("roleName", "%" + criteria.getRoleName() + "%"));
        }
        if (!StringUtils.isNullOrWhiteSpace(criteria.getRoleType())) {
            criterion.add(Restrictions.eq("roleType", criteria.getRoleType()));
        }
        int totalCount = ((Long) criterion.setProjection(Projections.rowCount()).uniqueResult()).intValue();
        criterion.setProjection(null);
        criterion.setFirstResult((criteria.getPageNumber() - 1) * criteria.getPageSize()).setMaxResults(criteria.getPageSize());
        List<FormApproveRole> beans = criterion.list();
        List<FormAppRoleDto> list = new ArrayList<FormAppRoleDto>();
        for (FormApproveRole user : beans) {
            FormAppRoleDto dto = new FormAppRoleDto();
            BeanUtils.copyProperties(user, dto);
            list.add(dto);
        }
        return new PagedResult<FormAppRoleDto>(list, criteria.getPageNumber(), criteria.getPageSize(), totalCount);
    }

    @Override
    public List<FormAppRoleDto> getAll(){
        Criteria criterion = getSession().createCriteria(FormApproveRole.class);
        criterion.addOrder(Order.asc("roleName"));
        List<FormApproveRole> beans=criterion.list();
        List<FormAppRoleDto> list = new ArrayList<FormAppRoleDto>();
        for (FormApproveRole user : beans) {
            FormAppRoleDto dto = new FormAppRoleDto();
            BeanUtils.copyProperties(user, dto);
            list.add(dto);
        }
        return list;
    }

    @Override
    public List<FormAppRoleDto> getTreatiseRoleID(String roleName) {
        Criteria criterion = getSession().createCriteria(FormApproveRole.class);
        criterion.add(Restrictions.eq("roleName",roleName));
        List<FormApproveRole> beans=criterion.list();
        List<FormAppRoleDto> list = new ArrayList<FormAppRoleDto>();
        for (FormApproveRole user : beans) {
            FormAppRoleDto dto = new FormAppRoleDto();
            BeanUtils.copyProperties(user, dto);
            list.add(dto);
        }
        return list;
    }
}
