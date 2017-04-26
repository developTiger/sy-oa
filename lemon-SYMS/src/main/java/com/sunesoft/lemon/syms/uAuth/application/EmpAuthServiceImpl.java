package com.sunesoft.lemon.syms.uAuth.application;

import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateFinder;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.ResultFactory;
import com.sunesoft.lemon.fr.results.UniqueResult;
import com.sunesoft.lemon.fr.utils.BeanUtils;
import com.sunesoft.lemon.syms.eHr.application.dtos.EmpSessionDto;
import com.sunesoft.lemon.syms.eHr.domain.empInfo.Employee;
import com.sunesoft.lemon.syms.eHr.domain.empInfo.EmployeeRepository;
import com.sunesoft.lemon.syms.uAuth.application.dtos.AuthResDto;
import com.sunesoft.lemon.syms.uAuth.application.dtos.ResourceDto;
import com.sunesoft.lemon.syms.uAuth.domain.SysEmpAndRole;
import com.sunesoft.lemon.syms.uAuth.domain.SysEmpAndRoleRepository;
import com.sunesoft.lemon.syms.uAuth.domain.SysRole;
import com.sunesoft.lemon.syms.uAuth.domain.SysRoleRepository;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by zhouz on 2016/7/14.
 */
@Service("empAuthService")
public class EmpAuthServiceImpl extends GenericHibernateFinder implements EmpAuthService {
    @Autowired
    SysEmpAndRoleRepository sysEmpAndRoleRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    SysRoleRepository roleRepository;

    @Override
    public Map<Long, List<AuthResDto>> getAllAuthInfoByRole() {
        //获取所有权限
        System.out.println("- - - - - - - - - 初始化权限 - - - - - - - -- - - - - - - ");
        Map<Long, List<AuthResDto>> mapResult = new HashMap<>();
        String sql = "select distinct r.id roleId,s.id,s.icon_name iconName,s.id_code idCode,s.isRoot,s.sort,s.url,s.parent_res_id parentId,s.res_name resName ,s.resource_type resType,s.target from syy_oa_sys_role r join syy_oa_role_pmgroup rg on r.id=rg.user_role_id join syy_oa_sys_permit_group g on rg.sys_permitgroup_id=g.id join syy_oa_pmgroup_res " +
                "gs on g.id = gs.group_id join  syy_oa_sys_resource s on gs.res_id=s.id where r.is_active=1 and g.is_active=1 and s.is_active=1 order by s.sort  ";
        List<AuthResDto> dtos = queryForObjects(AuthResDto.class, sql, null);
        for (AuthResDto dto : dtos) {
                if (mapResult.get(dto.getRoleId()) != null) {
                    mapResult.get(dto.getRoleId()).add(dto);
                } else {
                    List<AuthResDto> dtoList = new ArrayList<>();
                    dtoList.add(dto);
                    mapResult.put(dto.getRoleId(), dtoList);
                }

            }
//        }
        //<editor-fold desc="修改role 多选">
        //return mapResult  方法结束



        //tree 的转换
//        for (Long key : mapResult.keySet()) {
//            List<AuthResDto> reslist = mapResult.get(key);
//            List<ResourceDto> resourceDtos = new ArrayList<>();
//            for (AuthResDto dto : reslist) {
//                if (dto.getParentId() == null) {
//                    ResourceDto r = new ResourceDto();
//                    BeanUtils.copyProperties(dto, r);
//                    r.setName(dto.getResName());
//                    resourceDtos.add(r);
//                }
//            }
//            for (AuthResDto dto : reslist) {
//                if (dto.getParentId() != null) {
//                    for (ResourceDto ddd : resourceDtos) {
//                        if (dto.getParentId().equals(ddd.getId())) {
//                            if (ddd.getChild() == null) {
//                                ddd.setChild(new ArrayList<ResourceDto>());
//                            }
//                            ResourceDto r = new ResourceDto();
//                            BeanUtils.copyProperties(dto, r);
//                            r.setName(dto.getResName());
//
//                            ddd.getChild().add(r);
//                        }
//                    }
//                }
//            }
//
//            result.put(key, resourceDtos);
//        }
        //</editor-fold>

        System.out.println(" - - - - - - - - - - -初始化权限结束- - - - - - - -- - - ");
        return mapResult;
    }

    @Override
    public UniqueResult<EmpSessionDto> getEmpInfoByLogin(String userName, String password) {

        EmpSessionDto sessionDto = new EmpSessionDto();
        Criteria criterion = getSession().createCriteria(SysEmpAndRole.class);

        criterion.createAlias("employee", "employee");
        criterion.add(Restrictions.eq("employee.isActive", true));
        criterion.add(Restrictions.eq("employee.loginName", userName));
        List<SysEmpAndRole> list = criterion.list();
        if (list.size() > 0) {
            SysEmpAndRole result = list.get(0);
            Employee e = result.getEmployee();
            if (e.getStatus().equals(0)) {
                return new UniqueResult<EmpSessionDto>("该用户已被禁用！");
            }
            if (e.getPassword().equals(password)) {
                e.setLastLoginTime(new Date());
                int i = e.getLoginCount();
                e.setLoginCount(++i);

                BeanUtils.copyProperties(e, sessionDto);
                if(e.getDept()!=null){
                    sessionDto.setDeptId(e.getDept().getId());
                    sessionDto.setDeptName(e.getDept().getDeptName());
                    sessionDto.setDeptNo(e.getDept().getDeptNo());
                }
//                if (result.getRole() != null)
//                sessionDto.setRoleId(result.getRole().getId());
                List<Long> ls=new ArrayList<>();
                for(SysEmpAndRole s:list){
                    ls.add(s.getRole().getId());
                }
                sessionDto.setRoleIds(ls);

               // employeeRepository.save(e);
            } else {
                return new UniqueResult<EmpSessionDto>("用户名或密码错误！");
            }
        } else {
            return new UniqueResult<EmpSessionDto>("用户名或密码错误！");
        }
        return new UniqueResult<EmpSessionDto>(sessionDto);
    }

    @Override
    public UniqueResult<EmpSessionDto> getEmpInfoByLogin(String userName) {
        EmpSessionDto sessionDto = new EmpSessionDto();
        Criteria criterion = getSession().createCriteria(SysEmpAndRole.class);

        criterion.createAlias("employee", "employee");
        criterion.add(Restrictions.eq("employee.isActive", true));
        criterion.add(Restrictions.eq("employee.loginName", userName));
        List<SysEmpAndRole> list = criterion.list();
        if (list.size() > 0) {
            SysEmpAndRole result = list.get(0);
            Employee e = result.getEmployee();
            if (e.getStatus().equals(0)) {
                return new UniqueResult<EmpSessionDto>("该用户已被禁用！");
            }
                e.setLastLoginTime(new Date());
                int i = e.getLoginCount();
                e.setLoginCount(++i);

                BeanUtils.copyProperties(e, sessionDto);
                if(e.getDept()!=null){
                    sessionDto.setDeptId(e.getDept().getId());
                    sessionDto.setDeptName(e.getDept().getDeptName());
                    sessionDto.setDeptNo(e.getDept().getDeptNo());
                }
                List<Long> ls=new ArrayList<>();
                for(SysEmpAndRole s:list){
                    ls.add(s.getRole().getId());
                }
                sessionDto.setRoleIds(ls);

        } else {
            return new UniqueResult<>("用户名不存在！");
        }
        return new UniqueResult<>(sessionDto);
    }

    @Override
    public SysEmpAndRole getEmpRole(Long empId) {
        Criteria criterion = getSession().createCriteria(SysEmpAndRole.class);
        criterion.add(Restrictions.eq("isActive", true));
        criterion.createAlias("employee", "employee");
        criterion.add(Restrictions.eq("employee.id", empId));
        List<SysEmpAndRole> sysEmpAndRoles = criterion.list();
        if (sysEmpAndRoles != null && sysEmpAndRoles.size() > 0)
            return sysEmpAndRoles.get(0);
        return null;
    }

    @Override
    public CommonResult setEmpRole(Long empId, String[] roleIds) {
//todo 这里更改过
        Criteria criterion = getSession().createCriteria(SysEmpAndRole.class);
        criterion.createAlias("employee", "employee");
        criterion.add(Restrictions.eq("employee.id", empId));
        List<SysEmpAndRole> sysEmpAndRoles = criterion.list();
        List<Long> ids=new ArrayList<>();
        if(sysEmpAndRoles!=null&&sysEmpAndRoles.size()>0){
            for(SysEmpAndRole s:sysEmpAndRoles){
                ids.add(s.getId());
            }
        }
        sysEmpAndRoleRepository.delete(ids);//删除原有的
        List<SysRole> sysRoles = roleRepository.gets(roleIds);
        Employee emp = employeeRepository.get(empId);
        if(sysRoles!=null&&sysRoles.size()>0){
            String roleNames="";
            for (SysRole s:sysRoles){
                roleNames=roleNames+","+s.getName();
                SysEmpAndRole newS = new SysEmpAndRole();
                emp.setRoleName(s.getName());
                newS.setEmployee(emp);
                newS.setRole(s);
                sysEmpAndRoleRepository.save(newS);
            }
            emp.setRoleName(roleNames.substring(1));
            employeeRepository.save(emp);
        }

//        SysEmpAndRole sysEmpAndRole = null;
//        if (sysEmpAndRoles != null && sysEmpAndRoles.size() > 0)
//            sysEmpAndRole = sysEmpAndRoles.get(0);
//        for(String roleId:roleIds){// 多个循环
//
//            Employee emp = new Employee();
//            if (sysEmpAndRole == null) {
//                sysEmpAndRole = new SysEmpAndRole();
//
//                emp.setRoleName(sysRole.getName());
//                sysEmpAndRole.setEmployee(emp);
//                sysEmpAndRole.setRole(sysRole);
//            } else {
//                emp = sysEmpAndRole.getEmployee();
//                emp.setRoleName(sysRole.getName());
//                sysEmpAndRole.setRole(sysRole);
//            }
//            sysEmpAndRoleRepository.save(sysEmpAndRole);
//            employeeRepository.save(emp);
//        }
        return ResultFactory.commonSuccess();
    }

    @Override
    public UniqueResult<EmpSessionDto> GetEmpSessionDtoById(Long empId) {
        Criteria criterion = getSession().createCriteria(SysEmpAndRole.class);
        criterion.createAlias("employee", "employee");
        criterion.add(Restrictions.eq("employee.isActive", true));
        criterion.add(Restrictions.eq("employee.status", 1));
        criterion.add(Restrictions.eq("employee.id", empId));
        List<SysEmpAndRole> sysEmpAndRoles = criterion.list();
        SysEmpAndRole sysEmpAndRole=null;
        List<Long> ls=new ArrayList<>();
        if (sysEmpAndRoles != null && sysEmpAndRoles.size() > 0) {
            sysEmpAndRole = sysEmpAndRoles.get(0);
            for(SysEmpAndRole s:sysEmpAndRoles){
                ls.add(s.getRole().getId());
            }
        }
        if (sysEmpAndRole == null) new UniqueResult<EmpSessionDto>("用户不存在或已经被禁用");
        Employee e = sysEmpAndRole.getEmployee();
        EmpSessionDto sessionDto = new EmpSessionDto();
        BeanUtils.copyProperties(e, sessionDto);
        if(e.getDept()!=null){
            sessionDto.setDeptId(e.getDept().getId());
            sessionDto.setDeptName(e.getDept().getDeptName());
        }
//        if (sysEmpAndRole.getRole() != null)
//            sessionDto.setRoleId(sysEmpAndRole.getRole().getId());
          sessionDto.setRoleIds(ls);


        return new UniqueResult<EmpSessionDto>(sessionDto);
    }
}
