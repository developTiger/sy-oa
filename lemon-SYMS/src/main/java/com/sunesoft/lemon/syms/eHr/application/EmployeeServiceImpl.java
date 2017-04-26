package com.sunesoft.lemon.syms.eHr.application;


import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateFinder;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.results.ResultFactory;
import com.sunesoft.lemon.fr.utils.BeanUtils;
import com.sunesoft.lemon.fr.utils.DateHelper;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.syms.eHr.application.criteria.*;
import com.sunesoft.lemon.syms.eHr.application.dtos.*;
import com.sunesoft.lemon.syms.eHr.application.factory.DtoFactory;
import com.sunesoft.lemon.syms.eHr.domain.dept.Deptment;
import com.sunesoft.lemon.syms.eHr.domain.dept.DeptmentRepository;
import com.sunesoft.lemon.syms.eHr.domain.empInfo.*;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by zhouz on 2016/5/12.
 */

@Service("employeeService")
public class EmployeeServiceImpl extends GenericHibernateFinder implements EmployeeService {

    @Autowired
    EmployeeRepository empRepository;
    @Autowired
    DeptmentRepository deptRepository;
    @Autowired
    EmpGroupRepository groupRepository;

    @Autowired
    DeptmentService deptmentService;


//    @Override
//    public Employee getById(Long id) {
//        Employee emp = null;
//        emp = empRepository.get(id);
//        return emp;
//    }

    @Override
    public CommonResult addOrUpdateEpm(EmpDto dto) {

        Employee emp = null;
        Date proJoinTime = null;
        if (dto.getId() != null && dto.getId() > 0) {
            emp = empRepository.get(dto.getId());
            if (emp.getStatus() == null || emp.getStatus() == 0) {
                String s = "该用户已经被禁";
                return ResultFactory.commonError(s);
            }
            dto.setLoginName(emp.getLoginName());
            dto.setLastUpdateTime(new Date());
            proJoinTime = emp.getJoinDate();

        } else {
            List<EmpDto> list = getEmpsByUserName(dto.getLoginName());
            if (list != null && list.size() > 0) {
                String s = "该用户名已经存在";
                return ResultFactory.commonError(s);
            }
            emp = new Employee();

        }

        emp = DtoFactory.convert(dto, emp);
        if (dto.getGroupId() != null && dto.getGroupId() > 0) {
            EmpGroup group = groupRepository.get(dto.getGroupId());
            if (group != null && group.getIsActive()) {
                emp.setEmpGroup(group);
            }
        }
        if (dto.getLeaderId() != null && dto.getLeaderId() > 0) {
            Employee leader = empRepository.get(dto.getLeaderId());
            if (leader != null && leader.getIsActive()) {
                emp.setLeader(leader);
            }
        }
        if (dto.getDeptId() != null && dto.getDeptId() > 0) {
            Deptment dept = deptRepository.get(dto.getDeptId());
            if (dept != null && dept.getIsActive()) {
                emp.setDept(dept);
            }
        }
        if (dto.getStrBirthday() != null) {
            emp.setBirthday(DateHelper.parse(dto.getStrBirthday(), "yyyy-MM-dd"));
        }
        if (dto.getStrEnteryDate() != null) {
            emp.setEnteryDate(DateHelper.parse(dto.getStrEnteryDate(), "yyyy-MM-dd"));
        }
        if (dto.getJoinTime() != null)
            emp.setJoinDate(DateHelper.parse(dto.getJoinTime(), "yyyy-MM-dd"));

        emp.setAge(DtoFactory.timeSlot(emp.getBirthday()));
        emp.setSeniority(DtoFactory.timeSlot(emp.getJoinDate()));//工龄从参加工作时间算起
        if (emp.getId() != null) {
            //todo 是修改,查看是否修改入职时间，是，则对带薪年假与疗养假进行重置
            //疗养假  //带薪年假
            if (proJoinTime != null && !StringUtils.isNullOrWhiteSpace(dto.getJoinTime())) {
                if (!DateHelper.formatDate(proJoinTime, "yyyy-MM-dd").equals(dto.getJoinTime())) {
                    emp = getShouldSpa(emp);
                    emp.setNewSpaYear(null);

                    emp.setHasYear(getT(emp));
                    emp.setYearWithMoney(getT(emp));
                    emp.setNewRestYear(null);
                }
            }
        } else {
            //todo 是增加
            emp = getShouldSpa(emp);
            emp.setNewSpaYear(null);
            //带薪年假
            emp.setHasYear(getT(emp));
            emp.setYearWithMoney(getT(emp));
            emp.setNewRestYear(null);

        }

        Long id = empRepository.save(emp);
        return ResultFactory.commonSuccess(id);

    }


    @Override
    public EmpDto getEmpById(Long id) {
        Employee emp = empRepository.get(id);
        EmpDto dto = new EmpDto();
        if (emp != null && emp.getIsActive()) {
            dto = DtoFactory.convert(emp);
            dto.setJoinTime(DateHelper.formatDate(emp.getJoinDate(), "yyyy-MM-dd"));
            if (emp.getBirthday() != null) {
                dto.setStrBirthday(DateHelper.formatDate(emp.getBirthday()));
            }
            if (emp.getEnteryDate() != null) {
                dto.setStrEnteryDate(DateHelper.formatDate(emp.getEnteryDate(), "yyyy-MM-dd"));
            }
        }
        return dto;
    }

    @Override
    public EmpSessionDto login(String loginName, String password) {
        Criteria criterion = getSession().createCriteria(Employee.class);
        criterion.add(Restrictions.eq("isActive", true));
        criterion.add(Restrictions.eq("status", 1));
        criterion.add(Restrictions.eq("loginName", loginName));
        criterion.add(Restrictions.eq("password", password));
        List list = criterion.list();
        if (list.size() > 0) {
            Employee e = (Employee) list.get(0);
            e.setLastLoginTime(new Date());
            int i = e.getLoginCount();
            e.setLoginCount(++i);
            EmpSessionDto sessionDto = new EmpSessionDto();
            BeanUtils.copyProperties(e, sessionDto);
            empRepository.save(e);
            return sessionDto;
        }
        return null;
    }

    @Override
    public CommonResult changePassword(Long id, String password) {
        Employee emp = empRepository.get(id);
        if (emp != null && emp.getIsActive() && emp.getStatus() == 1) {
            emp.setPassword(password);
            emp.setLastUpdateTime(new Date());
            empRepository.save(emp);
            return ResultFactory.commonSuccess();
        }
        return ResultFactory.commonError("用户不存在，或已被禁用");
    }

    @Override
    public CommonResult userChangePassword(Long id, String oldPassword, String newPassword) {
        Employee emp = empRepository.get(id);
        if (!emp.getPassword().equals(oldPassword)) {
            return ResultFactory.commonError("原密码输入不正确！");
        }
        if (emp != null && emp.getIsActive() && emp.getStatus() == 1) {
            emp.setPassword(newPassword);
            emp.setLastUpdateTime(new Date());
            empRepository.save(emp);
            return ResultFactory.commonSuccess();
        }
        return ResultFactory.commonError("用户不存在，或已被禁用");

    }

    @Override
    public CommonResult setEmpStatus(List<Long> ids, int status) {
        Criteria criterion = getSession().createCriteria(Employee.class);
        criterion.add(Restrictions.eq("isActive", true));
        if (ids == null || ids.size() < 1) {
            return ResultFactory.commonError("请选择设置的对象");
        }
        criterion.add(Restrictions.in("id", ids));

        List<Employee> list = criterion.list();
        for (Employee e : list) {
            e.setStatus(status);
            e.setLastUpdateTime(new Date());
            empRepository.save(e);
        }
        return ResultFactory.commonSuccess();
    }

    @Override
    public CommonResult deleteEpm(List<Long> ids) {
        Criteria criterion = getSession().createCriteria(Employee.class);
        criterion.add(Restrictions.eq("isActive", true));
        if (ids == null || ids.size() < 1) {
            return ResultFactory.commonError("请选择删除的对象");
        }
        criterion.add(Restrictions.in("id", ids));

        List<Employee> list = criterion.list();
        for (Employee e : list) {
            e.setIsActive(false);
            e.setLastUpdateTime(new Date());
            empRepository.save(e);
        }
        return ResultFactory.commonSuccess();
    }


    public PagedResult<EmpDto> findEmpsByDeptPaged(EmpDeptsCriteria criteria) {
        //该方法是train培训用的，根据部门id（多个）查询出部门里的所有员工

        Criteria criterion = getSession().createCriteria(Employee.class);
        criterion.add(Restrictions.eq("isActive", true));

        if (!StringUtils.isNullOrWhiteSpace(criteria.getName())) {
            criterion.add(Restrictions.like("name", "%" + criteria.getName() + "%"));
        }
        if (criteria.getDeptId() != null) {

//            criterion.add(Restrictions.eq("dept", criteria.getDeptId()));//这边我先注释掉，如果需要，再写（孙文标）

            //createAlias  并不创建一个新的 Criteria实例
            criterion.createAlias("dept", "dept");//获取dept实体类数据
//            criterion.add(Restrictions.eq("dept", criteria.getDeptId()));
            String[] deptids = criteria.getDeptId().split(",");
            List<Long> ddd = new ArrayList<>();
            for (String id : deptids) {
                ddd.add(Long.parseLong(id));
            }
            criterion.add(Restrictions.in("dept.id", ddd));
//            Deptment dp = deptRepository.get(criteria.getDeptId());
//            if (dp != null)
//                criterion.add(Restrictions.eq("dept", dp));
        }
        if (!StringUtils.isNullOrWhiteSpace(criteria.getLoginName())) {
            criterion.add(Restrictions.like("loginName", "%" + criteria.getLoginName() + "%"));
        }
        if (!StringUtils.isNullOrWhiteSpace(criteria.getHighestEdu())) {
            criterion.add(Restrictions.eq("highestEdu", criteria.getHighestEdu()));
        }
        if (!StringUtils.isNullOrWhiteSpace(criteria.getCurrentQualifications())) {
            criterion.add(Restrictions.eq("currentQualifications", criteria.getCurrentQualifications()));
        }
        if (!StringUtils.isNullOrWhiteSpace(criteria.getCurrenttechPosition())) {
            criterion.add(Restrictions.eq("currenttechPosition", criteria.getCurrenttechPosition()));
        }
        int totalCount = ((Long) criterion.setProjection(Projections.rowCount()).uniqueResult()).intValue();
        criterion.addOrder(Order.asc("name"));
        criterion.setProjection(null);
        criterion.setResultTransformer(Criteria.ROOT_ENTITY);//过滤数据 只查询实体根，因为里面有emp和dept
        criterion.setFirstResult((criteria.getPageNumber() - 1) * criteria.getPageSize()).setMaxResults(criteria.getPageSize());
        List<Employee> list = criterion.list();
        List<EmpDto> dtos = new ArrayList<EmpDto>();
        if (list != null && list.size() > 0) {
            for (Employee e : list) {
                EmpDto dto = DtoFactory.convert(e);
                dto.setJoinTime(DateHelper.formatDate(e.getJoinDate()));
                if (e.getBirthday() != null) {
                    dto.setStrBirthday(DateHelper.formatDate(e.getBirthday()));
                }
                if (e.getEnteryDate() != null) {
                    dto.setStrEnteryDate(DateHelper.formatDate(e.getEnteryDate()));
                }
                dtos.add(dto);
            }
        }
        return new PagedResult<EmpDto>(dtos, criteria.getPageNumber(), criteria.getPageSize(), totalCount);
    }


    @Override
    public PagedResult<EmpDto> findEmpsPaged(EmpCriteria criteria) {

        Criteria criterion = getSession().createCriteria(Employee.class);
        criterion.createAlias("dept", "dept");
//        criterion.addOrder(Order.asc("dept.deptNo"));
        criterion.addOrder(Order.asc("dept"));
        criterion.add(Restrictions.eq("isActive", true));
        if (!StringUtils.isNullOrWhiteSpace(criteria.getName())) {
            criterion.add(Restrictions.like("name", "%" + criteria.getName() + "%"));
        }
        if (criteria.getDeptId() != null && criteria.getDeptId() > 0) {
            //criterion.createAlias("dept","dept");
            criterion.add(Restrictions.eq("dept.id", criteria.getDeptId()));
//            Deptment dp = deptRepository.get(criteria.getDeptId());
//            if (dp != null)
//                criterion.add(Restrictions.eq("dept", dp));
        }
        if (!StringUtils.isNullOrWhiteSpace(criteria.getLoginName())) {
            criterion.add(Restrictions.like("name", "%" + criteria.getLoginName() + "%"));
        }
        if (!StringUtils.isNullOrWhiteSpace(criteria.getHighestEdu())) {
            criterion.add(Restrictions.eq("highestEdu", criteria.getHighestEdu()));
        }
        if (!StringUtils.isNullOrWhiteSpace(criteria.getCurrentQualifications())) {
            criterion.add(Restrictions.like("currentQualifications", "%" + criteria.getCurrentQualifications() + "%"));
        }
        if (!StringUtils.isNullOrWhiteSpace(criteria.getCurrenttechPosition())) {
            criterion.add(Restrictions.eq("workKind", criteria.getCurrenttechPosition()));
        }
        int totalCount = ((Long) criterion.setProjection(Projections.rowCount()).uniqueResult()).intValue();
        criterion.setProjection(null);
        criterion.setFirstResult((criteria.getPageNumber() - 1) * criteria.getPageSize()).setMaxResults(criteria.getPageSize());

        List<Object[]> list = criterion.list();
        List<EmpDto> dtos = new ArrayList<EmpDto>();
        if (list != null && list.size() > 0) {
            for (Object[] em : list) {
                Employee e = (Employee) em[1];
                EmpDto dto = DtoFactory.convert(e);
                dto.setJoinTime(DateHelper.formatDate(e.getJoinDate()));
                if (e.getBirthday() != null) {
                    dto.setStrBirthday(DateHelper.formatDate(e.getBirthday()));
                }
                if (e.getEnteryDate() != null) {
                    dto.setStrEnteryDate(DateHelper.formatDate(e.getEnteryDate()));
                }
                dtos.add(dto);
            }
        }
        return new PagedResult<EmpDto>(dtos, criteria.getPageNumber(), criteria.getPageSize(), totalCount);
    }

    @Override
    public List<EmpDto> findEmpsByDept(Long deptId) {

//        String hql = " select new list(e.id,e.name,d.id,d.deptName) from Employee e inner join e.dept d ";
//        if (deptId != null)
//            hql = hql + " where d.id=" + deptId;
//        List<List> lists = getSession().createQuery(hql).list();

        Criteria criterion = getSession().createCriteria(Employee.class);
        criterion.add(Restrictions.eq("isActive", true));
        if (deptId != null)
            criterion.add(Restrictions.eq("dept.id", deptId));
        criterion.addOrder(Order.asc("name"));

//        System.out.print("开始时间：");
        List<Employee> list = criterion.list();
//        System.out.print("List 耗时：" + (new Date().getTime() - date_1.getTime()));
        //Date date_2 = new Date();
        List<EmpDto> dtos = new ArrayList<EmpDto>();
        if (list != null && list.size() > 0) {
            for (Employee e : list) {
                EmpDto dto = new EmpDto();
                dto = DtoFactory.convert(e);

                if (e.getJoinDate() != null)
                    dto.setJoinTime(DateHelper.formatDate(e.getJoinDate()));
                if (e.getBirthday() != null) {
                    dto.setStrBirthday(DateHelper.formatDate(e.getBirthday()));
                }
                if (e.getEnteryDate() != null) {
                    dto.setStrEnteryDate(DateHelper.formatDate(e.getEnteryDate()));
                }
                dtos.add(dto);
            }
        }
        //System.out.print("对象拆装箱耗时："+ (new Date().getTime() - date_2.getTime()));

        return dtos;

    }

    @Override
    public List<EmpDto> findEmpsByDeptId(Long deptId) {
        List<EmpDto> empDtos = new ArrayList<>();
        String hql = " select new list(e.id,e.name,d.id,d.deptName) from Employee e inner join e.dept d order by e.name";
        if (deptId != null)
            hql = hql + " where d.id=" + deptId;
        List<List> lists = getSession().createQuery(hql).list();
        if (lists != null && lists.size() > 0) {
            for (List l : lists) {
                EmpDto empDto = new EmpDto();
                int i = 0;
                empDto.setId((Long) l.get(i++));
                empDto.setName((String) l.get(i++));
                empDto.setDeptId((Long) l.get(i++));
                empDto.setDeptName((String) l.get(i));
                empDtos.add(empDto);
            }
        }
        return empDtos;
    }

    @Override
    public List<EmpDto> findEmpsByDeptIdAndName(Long deptId, String name) {
        List<EmpDto> empDtos = new ArrayList<>();
        String hql = " select new list(e.id,e.name,d.id,d.deptName) from Employee e inner join e.dept d  where e.isActive=1 and d.isActive=1 and e.status is not null and e.status=1 ";
        if (deptId != null)
            hql = hql + " and d.id=" + deptId;
        if (!StringUtils.isNullOrWhiteSpace(name)) {
            hql = hql + " and e.name like '%" + name + "%'";
        }
        List<List> lists = getSession().createQuery(hql).list();
        if (lists != null && lists.size() > 0) {
            for (List l : lists) {
                EmpDto empDto = new EmpDto();
                int i = 0;
                empDto.setId((Long) l.get(i++));
                empDto.setName((String) l.get(i++));
                empDto.setDeptId((Long) l.get(i++));
                empDto.setDeptName((String) l.get(i));
                empDtos.add(empDto);
            }
        }
        return empDtos;
    }

    @Override
    public List<EmpDto> getEmpsByUserName(String loginName) {
        Criteria criterion = getSession().createCriteria(Employee.class);
        criterion.add(Restrictions.eq("isActive", true));
        criterion.add(Restrictions.eq("loginName", loginName));
        List<Employee> list = criterion.list();
        List<EmpDto> dtos = new ArrayList<EmpDto>();
        if (list != null && list.size() > 0) {
            for (Employee e : list) {
                EmpDto dto = new EmpDto();
                dto = DtoFactory.convert(e);
                if (e.getBirthday() != null) {
                    dto.setStrBirthday(DateHelper.formatDate(e.getBirthday()));
                }
                if (e.getEnteryDate() != null) {
                    dto.setStrEnteryDate(DateHelper.formatDate(e.getEnteryDate()));
                }
                dto.setJoinTime(DateHelper.formatDate(e.getJoinDate()));
                dtos.add(dto);
            }
        }
        return dtos;
    }

    @Override
    public List<EmpDto> getAllEmps() {
        Criteria criterion = getSession().createCriteria(Employee.class);
        criterion.createAlias("dept", "dept");
        criterion.addOrder(Order.asc("dept.deptNo"));
        criterion.add(Restrictions.eq("isActive", true));
        List<Employee> list = criterion.list();
        List<EmpDto> dtos = new ArrayList<EmpDto>();
        if (list != null && list.size() > 0) {
            for (Employee e : list) {

                EmpDto dto = DtoFactory.convert(e);
                if (e.getBirthday() != null) {
                    dto.setStrBirthday(DateHelper.formatDate(e.getBirthday()));
                }
                if (e.getEnteryDate() != null) {
                    dto.setStrEnteryDate(DateHelper.formatDate(e.getEnteryDate()));
                }
                dto.setJoinTime(DateHelper.formatDate(e.getJoinDate()));

                dtos.add(dto);
            }

        }
        return dtos;
    }

    @Override
    public List<EmpDto> getAllSimpleEmps() {
        String hql = " select new list(e.id,e.name,d.id) from Employee e inner join e.dept d ";
        List<List> lists = getSession().createQuery(hql).list();
        List<EmpDto> empDtos = new ArrayList<>();
        if (lists != null && lists.size() > 0) {
            for (List l : lists) {
                EmpDto empDto = new EmpDto();
                empDto.setId((Long) l.get(0));
                empDto.setName((String) l.get(1));
                empDto.setDeptId(((Long) l.get(2)));
                empDtos.add(empDto);
            }
        }
        return empDtos;
    }

    @Override
    public List<EmpDto> getAllLeaders(Long id) {
        Criteria criterion = getSession().createCriteria(Employee.class);
        criterion.add(Restrictions.eq("isActive", true));
        criterion.createAlias("dept", "dept");
        criterion.add(Restrictions.eq("dept.id", id));
        criterion.addOrder(Order.asc("dept.deptNo"));
        List<Employee> list = criterion.list();
        List<EmpDto> dtos = new ArrayList<EmpDto>();
        if (list != null && list.size() > 0) {
            for (Employee e : list) {
                EmpDto dto = new EmpDto();
                dto = DtoFactory.convert(e);

                dtos.add(dto);
            }
        }
        return dtos;
    }

    @Override
    public List<EmpDto> getAllLeaders(String deptNo) {
        Criteria criterion = getSession().createCriteria(Employee.class);
        criterion.add(Restrictions.eq("isActive", true));
        criterion.createAlias("dept", "dept");
        criterion.add(Restrictions.eq("dept.deptNo", deptNo));
        criterion.addOrder(Order.asc("dept.deptNo"));
        List<Employee> list = criterion.list();
        List<EmpDto> dtos = new ArrayList<EmpDto>();
        if (list != null && list.size() > 0) {
            for (Employee e : list) {
                EmpDto dto = new EmpDto();
                dto = DtoFactory.convert(e);

                dtos.add(dto);
            }
        }
        return dtos;
    }

    @Override
    public EmpDto getLeader(Long leaderId) {
        Employee employee = empRepository.get(leaderId);
        EmpDto empDto = new EmpDto();
        BeanUtils.copyProperties(employee, empDto);
        return empDto;
    }

    @Override
    public List<EmpDto> getEmpByDeptName(String deptName) {
        Criteria criterion = getSession().createCriteria(Employee.class);
        criterion.add(Restrictions.eq("isActive", true));

        criterion.createAlias("dept", "dept");
        if (!StringUtils.isNullOrWhiteSpace(deptName)) {
            criterion.add(Restrictions.eq("dept.deptName", deptName));
        }
        criterion.addOrder(Order.asc("name"));
        List<Employee> list = criterion.list();
        List<EmpDto> dtos = new ArrayList<EmpDto>();
        if (list != null && list.size() > 0) {
            for (Employee e : list) {
                EmpDto dto = new EmpDto();
                dto = DtoFactory.convert(e);

                dtos.add(dto);
            }
        }
        return dtos;
    }

    @Override
    public CommonResult addEdu(EducationDto dto) {
        Employee e = empRepository.get(dto.getEmpId());
        if (e == null) {
            e = new Employee();
            dto.setEmpId(e.getId());
        }
        if (e.getEducations().size() > 0 && dto.getIsCurrent()) {
            for (Education educa : e.getEducations()) {
                educa.setIsCurrent(false);
            }
            e.setHighestEdu(dto.getLevel());
        }
        Education edu = new Education();
        edu = DtoFactory.convert(dto, edu);
        if (dto.getStrObtain() != null) {
            edu.setObtain(DateHelper.parse(dto.getStrObtain(), "yyyy-MM-dd"));
        }
        if (dto.getStrGraduation() != null) {
            edu.setGraduation(DateHelper.parse(dto.getStrGraduation(), "yyyy-MM-dd"));
        }
        e.getEducations().add(edu);
        e.setLastUpdateTime(new Date());
        Long id = empRepository.save(e);
        return ResultFactory.commonSuccess(id);
    }

    @Override
    public EducationDto getByEduId(Long empId, Long eduId) {
        EducationDto dto = new EducationDto();
        Employee emp = empRepository.get(empId);
        if (emp != null) {
            List<Education> list = emp.getEducations();
            if (list != null && list.size() > 0) {
                for (Education e : list) {
                    if (e.getId().equals(eduId)) {
                        dto = DtoFactory.convert(e, dto);
                        if (e.getGraduation() != null) {
                            dto.setStrGraduation(DateHelper.formatDate(e.getGraduation()));
                        }
                        if (e.getObtain() != null) {
                            dto.setStrObtain(DateHelper.formatDate(e.getObtain()));
                        }
                        return dto;
                    }
                }
            }
        }
        return dto;
    }

    @Override
    public CommonResult updateEdu(EducationDto dto) {
        Employee e = empRepository.get(dto.getEmpId());
        List<Education> list = e.getEducations();
        for (Education edu : list) {
            if (dto.getIsCurrent() && !edu.getId().equals(dto.getId())) {
                edu.setIsCurrent(false);
            }
            if (dto.getId().equals(edu.getId())) {
                edu = DtoFactory.convert(dto, edu);
                edu.setLastUpdateTime(new Date());
                if (dto.getStrObtain() != null) {
                    edu.setObtain(DateHelper.parse(dto.getStrObtain(), "yyyy-MM-dd"));
                }
                if (dto.getStrGraduation() != null) {
                    edu.setGraduation(DateHelper.parse(dto.getStrGraduation(), "yyyy-MM-dd"));
                }
                if (edu.getIsCurrent() && dto.getIsCurrent()) {
                    e.setHighestEdu(dto.getLevel());
                }
                e.setEducations(list);
                e.setLastUpdateTime(new Date());
            }
        }
        Long l = empRepository.save(e);
        return ResultFactory.commonSuccess(l);


    }


    @Override
    public CommonResult deleteEdu(Long empId, Long eduId) {
        Employee employee = empRepository.get(empId);
        if (employee != null) {
            List<Education> list = employee.getEducations();
            if (list != null && list.size() > 0) {
                for (Education e : list) {
                    if (e.getId().equals(eduId)) {
                        e.setIsActive(false);
                        e.setLastUpdateTime(new Date());
                        list.remove(e);
                        if (e.getIsCurrent() && list.size() > 0) {
                            list.get(0).setIsCurrent(true);
                        }
                        employee.setEducations(list);
                        employee.setLastUpdateTime(new Date());
                        Long l = empRepository.save(employee);
                        return ResultFactory.commonSuccess(l);
                    }
                }
                String s = "此条教育信息不存在";
                return ResultFactory.commonError(s);
            }
        }

        String s = "此用户不存在";
        return ResultFactory.commonError(s);
    }

    @Override
    public List<EducationDto> getAllEdus(Long empId) {
        Employee employee = empRepository.get(empId);
        List<EducationDto> dtos = new ArrayList<EducationDto>();
        if (employee != null) {
            List<Education> list = employee.getEducations();
            if (list != null && list.size() > 0) {
                for (Education e : list) {
                    EducationDto dto = new EducationDto();
                    dto = DtoFactory.convert(e, dto);
                    if (e.getObtain() != null) {
                        dto.setStrObtain(DateHelper.formatDate(e.getObtain()));
                    }
                    if (e.getGraduation() != null) {
                        dto.setStrGraduation(DateHelper.formatDate(e.getGraduation()));
                    }
                    dtos.add(dto);
                }

                return dtos;
            }
        }
        return Collections.emptyList();
    }

    @Override
    public PagedResult<EducationDto> finderEdusPaged(Long empId, EduCriteria edu) {
        Criteria criterion = getSession().createCriteria(Education.class);
        criterion.add(Restrictions.eq("empId", empId));
        criterion.add(Restrictions.eq("isActive", true));
        if (!StringUtils.isNullOrWhiteSpace(edu.getDegree())) {
            criterion.add(Restrictions.like("degree", "%" + edu.getDegree() + "%"));
        }
        if (!StringUtils.isNullOrWhiteSpace(edu.getMajor())) {
            criterion.add(Restrictions.like("major", "%" + edu.getMajor() + "%"));
        }
        if (!StringUtils.isNullOrWhiteSpace(edu.getSchool())) {
            criterion.add(Restrictions.like("school", "%" + edu.getSchool() + "%"));
        }
        int totalCount = ((Long) criterion.setProjection(Projections.rowCount()).uniqueResult()).intValue();
        criterion.setProjection(null);
        criterion.setFirstResult((edu.getPageNumber() - 1) * edu.getPageSize()).setMaxResults(edu.getPageSize());
        List<Education> list = criterion.list();
        List<EducationDto> dtos = new ArrayList<EducationDto>();
        for (Education e : list) {
            EducationDto dto = new EducationDto();
            dto = DtoFactory.convert(e, dto);
            if (e.getObtain() != null) {
                dto.setStrObtain(DateHelper.formatDate(e.getObtain()));
            }
            if (e.getGraduation() != null) {
                dto.setStrGraduation(DateHelper.formatDate(e.getGraduation()));
            }
            dtos.add(dto);
        }
        return new PagedResult<EducationDto>(dtos, edu.getPageNumber(), edu.getPageSize(), totalCount);
    }


    @Override
    public CommonResult addFam(FamilyDto dto) {
        Employee emp = empRepository.get(dto.getEmpId());
        if (emp == null) {
            emp = new Employee();
            dto.setEmpId(emp.getId());
        }
        List<Family> list = emp.getFamilies();
        Family fam = new Family();
        fam = DtoFactory.convert(dto, fam);
        if (dto.getStrBirthday() != null) {
            fam.setBirthday(DateHelper.parse(dto.getStrBirthday(), "yyyy-MM-dd"));
        }
        list.add(fam);
        emp.setFamilies(list);
        emp.setLastUpdateTime(new Date());
        return ResultFactory.commonSuccess((empRepository.save(emp)));

    }

    @Override
    public FamilyDto getByFamId(Long empId, Long famId) {
        FamilyDto dto = new FamilyDto();
        Employee emp = empRepository.get(empId);
        if (emp != null && emp.getFamilies() != null && emp.getFamilies().size() > 0) {
            for (Family f : emp.getFamilies()) {
                if (f.getId().equals(famId)) {
                    dto = DtoFactory.convert(f, dto);
                    if (f.getBirthday() != null) {
                        dto.setStrBirthday(DateHelper.formatDate(f.getBirthday()));
                    }
                    return dto;
                }
            }
        }
        return dto;
    }

    @Override
    public CommonResult deleteFam(Long empId, Long id) {
        Employee emp = empRepository.get(empId);
        if (emp == null) {
            String s = "该员工不存在";
            return ResultFactory.commonError(s);
        }
        List<Family> families = emp.getFamilies();
        if (families != null && families.size() > 0) {
            for (Family f : families) {
                if (f.getId().equals(id)) {
                    f.setIsActive(false);
                    f.setLastUpdateTime(new Date());
                    emp.getFamilies().remove(f);
                    emp.setFamilies(families);
                    emp.setLastUpdateTime(new Date());
                    empRepository.save(emp);
                    return ResultFactory.commonSuccess();
                }
            }
            String s = "该家庭成员不存在";
            return ResultFactory.commonError(s);
        }
        String s = "该员工目前还没有添加家庭成员";
        return ResultFactory.commonError(s);
    }

    @Override
    public CommonResult updateFam(FamilyDto dto) {
        Employee e = empRepository.get(dto.getEmpId());
        if (e == null) {
            String s = "该员工不存在";
            return ResultFactory.commonError(s);
        }
        List<Family> list = e.getFamilies();
        if (list != null && list.size() > 0) {
            for (Family fam : list) {
                if (dto.getId().equals(fam.getId())) {
                    fam = DtoFactory.convert(dto, fam);
                    if (dto.getStrBirthday() != null) {
                        fam.setBirthday(DateHelper.parse(dto.getStrBirthday(), "yyyy-MM-dd"));
                    }
                    fam.setLastUpdateTime(new Date());
                    e.setLastUpdateTime(new Date());
                    e.setFamilies(list);

                    return ResultFactory.commonSuccess(empRepository.save(e));
                }
            }
        }
        String s = "该员工目前还没有添加家庭成员";
        return ResultFactory.commonError(s);
    }

    @Override
    public List<FamilyDto> getAllFams(Long empId) {
        Employee employee = empRepository.get(empId);
        List<FamilyDto> dtos = new ArrayList<FamilyDto>();
        if (employee != null) {
            List<Family> list = employee.getFamilies();
            if (list != null && list.size() > 0) {
                for (Family f : list) {
                    FamilyDto dto = new FamilyDto();
                    dto = DtoFactory.convert(f, dto);
                    if (f.getBirthday() != null) {
                        dto.setStrBirthday(DateHelper.formatDate(f.getBirthday()));
                    }
                    dtos.add(dto);
                }

                return dtos;
            }
        }
        return Collections.emptyList();
    }

    @Override
    public PagedResult<FamilyDto> findFamsPaged(Long empId, FamCriteria fam) {
        Criteria criterion = getSession().createCriteria(Family.class);
        criterion.add(Restrictions.eq("empId", empId));
        criterion.add(Restrictions.eq("isActive", true));
        if (!StringUtils.isNullOrWhiteSpace(fam.getLabel())) {
            criterion.add(Restrictions.like("label", "%" + fam.getLabel() + "%"));
        }
        if (!StringUtils.isNullOrWhiteSpace(fam.getName())) {
            criterion.add(Restrictions.like("name", "%" + fam.getName() + "%"));
        }

        int totalCount = ((Long) criterion.setProjection(Projections.rowCount()).uniqueResult()).intValue();
        criterion.setProjection(null);
        criterion.setFirstResult((fam.getPageNumber() - 1) * fam.getPageSize()).setMaxResults(fam.getPageSize());
        List<Family> list = criterion.list();
        List<FamilyDto> dtos = new ArrayList<FamilyDto>();
        for (Family f : list) {
            FamilyDto dto = new FamilyDto();
            dto = DtoFactory.convert(f, dto);
            if (f.getBirthday() != null) {
                dto.setStrBirthday(DateHelper.formatDate(f.getBirthday()));
            }
            dtos.add(dto);
        }
        return new PagedResult<FamilyDto>(dtos, fam.getPageNumber(), fam.getPageSize(), totalCount);
    }

    @Override
    public CommonResult addQualification(QualificationDto dto) {
        Employee e = empRepository.get(dto.getEmpId());
        if (e == null) {
            e = new Employee();
            dto.setEmpId(e.getId());
        }
        List<Qualification> list = e.getQualifications();
        if (list.size() > 0 && dto.getIsCurrent()) {
            for (Qualification educa : e.getQualifications()) {
                educa.setIsCurrent(false);
            }
            e.setCurrentQualifications(dto.getName());
        }
        Qualification qua = new Qualification();
        qua = DtoFactory.convert(dto, qua);

        list.add(qua);
        e.setLastUpdateTime(new Date());
        e.setQualifications(list);
        return ResultFactory.commonSuccess(empRepository.save(e));
    }

    @Override
    public QualificationDto getByQuaId(Long empId, Long quaId) {
        QualificationDto dto = new QualificationDto();
        Employee emp = empRepository.get(empId);
        if (emp != null && emp.getQualifications() != null && emp.getQualifications().size() > 0) {
            for (Qualification q : emp.getQualifications()) {
                if (q.getId().equals(quaId)) {
                    dto = DtoFactory.convert(q, dto);
                    return dto;
                }
            }
        }
        return dto;
    }

    @Override
    public CommonResult updateQualification(QualificationDto dto) {
        Employee e = empRepository.get(dto.getEmpId());
        if (e == null) {
            String s = "该员工不存在";
            return ResultFactory.commonError(s);
        }
        List<Qualification> list = e.getQualifications();
        if (list != null && list.size() > 0) {
            for (Qualification qua : list) {
                if (dto.getIsCurrent()) {
                    qua.setIsCurrent(false);
                }
                if (dto.getId().equals(qua.getId())) {
                    qua = DtoFactory.convert(dto, qua);
                    qua.setLastUpdateTime(new Date());
                    e.setLastUpdateTime(new Date());
                    e.setQualifications(list);
                    if (dto.getIsCurrent()) {
                        e.setCurrentQualifications(qua.getName());
                    }
                    return ResultFactory.commonSuccess(empRepository.save(e));
                }
            }
        }
        String s = "该员工目前还没有添加家庭成员";
        return ResultFactory.commonError(s);
    }

    @Override
    public CommonResult deleteQualification(Long empId, Long quaId) {
        Employee employee = empRepository.get(empId);
        if (employee != null) {
            List<Qualification> list = employee.getQualifications();
            if (list != null && list.size() > 0) {
                for (Qualification q : list) {
                    if (q.getId().equals(quaId)) {
                        q.setIsActive(false);
                        q.setLastUpdateTime(new Date());
                        list.remove(q);
                        if (q.getIsCurrent() && list.size() > 0) {
                            list.get(0).setIsCurrent(true);
                        }
                        employee.setQualifications(list);
                        employee.setLastUpdateTime(new Date());
                        empRepository.save(employee);
                        return ResultFactory.commonSuccess();
                    }
                }
                String s = "没有将要删除的目标数据";
                return ResultFactory.commonError(s);
            }
        }
        String s = "该员工目前还没有添加！";
        return ResultFactory.commonError(s);
    }

    @Override
    public List<QualificationDto> getAllQualifications(Long empId) {

        Employee employee = empRepository.get(empId);
        List<QualificationDto> dtos = new ArrayList<QualificationDto>();
        if (employee != null) {
            List<Qualification> list = employee.getQualifications();
            if (list != null && list.size() > 0) {
                for (Qualification q : list) {
                    QualificationDto dto = new QualificationDto();
                    dto = DtoFactory.convert(q, dto);
                    dtos.add(dto);
                }

                return dtos;
            }
        }
        return Collections.emptyList();
    }

    @Override
    public PagedResult<QualificationDto> findQualificationsPaged(Long empId, QualificationCriteria qua) {
        Criteria criterion = getSession().createCriteria(Qualification.class);
        criterion.add(Restrictions.eq("empId", empId));
        criterion.add(Restrictions.eq("isActive", true));
        if (!StringUtils.isNullOrWhiteSpace(qua.getName())) {
            criterion.add(Restrictions.like("name", "%" + qua.getName() + "%"));
        }
        if (!StringUtils.isNullOrWhiteSpace(qua.getIdCard())) {
            criterion.add(Restrictions.like("idCard", "%" + qua.getIdCard() + "%"));
        }

        int totalCount = ((Long) criterion.setProjection(Projections.rowCount()).uniqueResult()).intValue();
        criterion.setProjection(null);
        criterion.setFirstResult((qua.getPageNumber() - 1) * qua.getPageSize()).setMaxResults(qua.getPageSize());
        List<Qualification> list = criterion.list();
        List<QualificationDto> dtos = new ArrayList<QualificationDto>();
        for (Qualification q : list) {
            QualificationDto dto = new QualificationDto();
            dto = DtoFactory.convert(q, dto);
            dtos.add(dto);
        }
        return new PagedResult<QualificationDto>(dtos, qua.getPageNumber(), qua.getPageSize(), totalCount);
    }


    @Override
    public CommonResult addTechPosition(TechPositionDto dto) {
        Employee e = empRepository.get(dto.getEmpId());
        if (e == null) {
            e = new Employee();
            dto.setEmpId(e.getId());
        }
        if (dto.getIsCurrent() && e.getTechPositions().size() > 0) {
            for (TechPosition educa : e.getTechPositions()) {
                educa.setIsCurrent(false);
            }
            e.setCurrenttechPosition(dto.getName());
        }
        TechPosition edu = new TechPosition();
        edu = DtoFactory.convert(dto, edu);

        if (dto.getStrInTime() != null) {
            edu.setInTime(DateHelper.parse(dto.getStrInTime(), "yyyy-MM-dd"));
        }
        if (dto.getStrOutTime() != null) {
            edu.setOutTime(DateHelper.parse(dto.getStrOutTime(), "yyyy-MM-dd"));
        }
        List<TechPosition> list = e.getTechPositions();
        if (dto.getIsCurrent()) {
            e.setCurrenttechPosition(dto.getName());
        }
        list.add(edu);
        e.setLastUpdateTime(new Date());
        e.setTechPositions(list);
        return ResultFactory.commonSuccess(empRepository.save(e));
    }

    @Override
    public TechPositionDto getByTPId(Long empId, Long tpId) {
        TechPositionDto dto = new TechPositionDto();
        Employee emp = empRepository.get(empId);
        if (emp != null && emp.getTechPositions() != null && emp.getTechPositions().size() > 0) {
            for (TechPosition t : emp.getTechPositions()) {
                if (t.getId().equals(tpId)) {
                    dto = DtoFactory.convert(t, dto);

                    if (t.getInTime() != null) {
                        dto.setStrInTime(DateHelper.formatDate(t.getInTime()));
                    }
                    if (t.getOutTime() != null) {
                        dto.setStrOutTime(DateHelper.formatDate(t.getOutTime()));
                    }
                    return dto;
                }
            }
        }
        return dto;
    }

    @Override
    public CommonResult updateTechPosition(TechPositionDto dto) {
        Employee e = empRepository.get(dto.getEmpId());
        if (e == null) {
            String s = "此员工暂未添加";
            return ResultFactory.commonError(s);
        }
        List<TechPosition> list = e.getTechPositions();
        if (list != null && list.size() > 0) {
            for (TechPosition tp : list) {
                if (dto.getIsCurrent()) {
                    tp.setIsCurrent(false);
                }
                if (dto.getId().equals(tp.getId())) {
                    tp = DtoFactory.convert(dto, tp);
                    if (dto.getStrOutTime() != null) {
                        tp.setOutTime(DateHelper.parse(dto.getStrOutTime(), "yyyy-MM-dd"));
                    }

                    if (dto.getStrInTime() != null) {
                        tp.setInTime(DateHelper.parse(dto.getStrInTime(), "yyyy-MM-dd"));
                    }
                    tp.setLastUpdateTime(new Date());
                    e.setLastUpdateTime(new Date());
                    if (dto.getIsCurrent()) {
                        e.setCurrenttechPosition(dto.getName());
                    }
                    e.setTechPositions(list);
                    return ResultFactory.commonSuccess(empRepository.save(e));
                }
            }
        }
        String s = "此员工还未添加资质信息";
        return ResultFactory.commonError(s);
    }

    @Override
    public CommonResult deleteTechPosition(Long empId, Long tqId) {
        Employee employee = empRepository.get(empId);
        if (employee != null) {
            List<TechPosition> list = employee.getTechPositions();
            if (list != null && list.size() > 0) {
                for (TechPosition tp : list) {
                    if (tp.getId().equals(tqId)) {
                        tp.setIsActive(false);
                        tp.setLastUpdateTime(new Date());
                        list.remove(tp);
                        if (tp.getIsCurrent() && list.size() > 0) {
                            list.get(0).setIsCurrent(true);
                        }
                        employee.setTechPositions(list);
                        employee.setLastUpdateTime(new Date());
                        empRepository.save(employee);
                        return ResultFactory.commonSuccess();
                    }
                }
                String s = "此员工资质信息还未添加";
                return ResultFactory.commonError(s);
            }
        }

        String s = "此员工还未添加";
        return ResultFactory.commonError(s);
    }

    @Override
    public List<TechPositionDto> getAllTechPositions(Long empId) {
        Employee employee = empRepository.get(empId);
        List<TechPositionDto> dtos = new ArrayList<TechPositionDto>();
        if (employee != null) {
            List<TechPosition> list = employee.getTechPositions();
            if (list != null && list.size() > 0) {
                for (TechPosition tp : list) {
                    TechPositionDto dto = new TechPositionDto();
                    dto = DtoFactory.convert(tp, dto);
                    if (tp.getOutTime() != null) {
                        dto.setStrOutTime(DateHelper.formatDate(tp.getOutTime()));
                    }
                    if (tp.getInTime() != null) {
                        dto.setStrInTime(DateHelper.formatDate(tp.getInTime()));
                    }

                    dtos.add(dto);
                }

                return dtos;
            }
        }
        return Collections.emptyList();
    }

    @Override
    public PagedResult<TechPositionDto> findTechPositionsPaged(Long empId, TechPositionCriteria tp) {
        Criteria criterion = getSession().createCriteria(TechPosition.class);
        criterion.add(Restrictions.eq("empId", empId));
        criterion.add(Restrictions.eq("isActive", true));
        if (!StringUtils.isNullOrWhiteSpace(tp.getName())) {
            criterion.add(Restrictions.like("name", "%" + tp.getName() + "%"));
        }
        if (!StringUtils.isNullOrWhiteSpace(tp.getMajor())) {
            criterion.add(Restrictions.like("idCard", "%" + tp.getMajor() + "%"));
        }
        if (!StringUtils.isNullOrWhiteSpace(tp.getLevel())) {
            criterion.add(Restrictions.eq("level", tp.getLevel()));
        }

        int totalCount = ((Long) criterion.setProjection(Projections.rowCount()).uniqueResult()).intValue();
        criterion.setProjection(null);
        criterion.setFirstResult((tp.getPageNumber() - 1) * tp.getPageSize()).setMaxResults(tp.getPageSize());
        List<TechPosition> list = criterion.list();
        List<TechPositionDto> dtos = new ArrayList<TechPositionDto>();
        for (TechPosition q : list) {
            TechPositionDto dto = new TechPositionDto();
            dto = DtoFactory.convert(q, dto);


            if (q.getInTime() != null) {
                dto.setStrInTime(DateHelper.formatDate(q.getInTime()));
            }
            if (q.getOutTime() != null) {
                dto.setStrOutTime(DateHelper.formatDate(q.getOutTime()));
            }
            dtos.add(dto);
        }
        return new PagedResult<TechPositionDto>(dtos, tp.getPageNumber(), tp.getPageSize(), totalCount);
    }

    @Override
    public CommonResult addWorkExperience(WorkExperienceDto dto) {
        Employee emp = empRepository.get(dto.getEmpId());
        if (emp == null) {
            emp = new Employee();
            dto.setEmpId(emp.getId());
        }
        List<WorkExperience> list = emp.getWorkExperiences();
        if (list != null && list.size() > 0 && dto.getIsCurrent()) {
            for (WorkExperience w : list) {
                w.setIsCurrent(false);
            }
        }
        WorkExperience we = new WorkExperience();
        we = DtoFactory.convert(dto, we);
        if (dto.getStrStrartTime() != null) {
            we.setStrartTime(DateHelper.parse(dto.getStrStrartTime(), "yyyy-MM-dd"));
        }
        if (dto.getStrOverTime() != null) {
            we.setOverTime(DateHelper.parse(dto.getStrOverTime(), "yyyy-MM-dd"));
        }
        list.add(we);
        emp.setWorkExperiences(list);
        emp.setLastUpdateTime(new Date());
        return ResultFactory.commonSuccess(empRepository.save(emp));

    }

    @Override
    public WorkExperienceDto getByWorkExperienceId(Long empId, Long wkId) {
        WorkExperienceDto dto = new WorkExperienceDto();
        Employee emp = empRepository.get(empId);
        if (emp != null && emp.getWorkExperiences() != null && emp.getWorkExperiences().size() > 0) {
            for (WorkExperience w : emp.getWorkExperiences()) {
                if (w.getId().equals(wkId)) {
                    dto = DtoFactory.convert(w, dto);
                    if (w.getOverTime() != null) {
                        dto.setStrOverTime(DateHelper.formatDate(w.getOverTime()));
                    }
                    if (w.getStrartTime() != null) {
                        dto.setStrStrartTime(DateHelper.formatDate(w.getStrartTime()));
                    }
                    return dto;
                }
            }
        }
        return dto;
    }

    @Override
    public CommonResult updateWorkExperience(WorkExperienceDto dto) {
        Employee e = empRepository.get(dto.getEmpId());
        List<WorkExperience> list = e.getWorkExperiences();
        if (list != null && list.size() > 0 && dto.getIsCurrent()) {
            for (WorkExperience w : list) {
                w.setIsCurrent(false);
            }
        }
        for (WorkExperience tp : list) {
            if (dto.getId().equals(tp.getId())) {
                tp = DtoFactory.convert(dto, tp);
                if (dto.getStrOverTime() != null) {
                    tp.setOverTime(DateHelper.parse(dto.getStrOverTime(), "yyyy-MM-dd"));
                }
                if (dto.getStrStrartTime() != null) {
                    tp.setStrartTime(DateHelper.parse(dto.getStrStrartTime(), "yyyy-MM-dd"));
                }
                tp.setLastUpdateTime(new Date());
                e.setLastUpdateTime(new Date());
                e.setWorkExperiences(list);
                empRepository.save(e);
                return ResultFactory.commonSuccess(tp.getId());
            }
        }
        String s = "暂无工作经历，或该员工不存在";
        return ResultFactory.commonError(s);
    }

    @Override
    public CommonResult deleteWorkExperience(Long empId, Long weId) {
        Employee employee = empRepository.get(empId);
        if (employee != null) {
            List<WorkExperience> list = employee.getWorkExperiences();
            if (list != null && list.size() > 0) {
                for (WorkExperience tp : list) {
                    if (tp.getId().equals(weId)) {
                        tp.setIsActive(false);
                        tp.setLastUpdateTime(new Date());
                        list.remove(tp);
                        if (tp.getIsCurrent() && list.size() > 0) {
                            list.get(0).setIsCurrent(true);
                        }
                        employee.setWorkExperiences(list);
                        employee.setLastUpdateTime(new Date());
                        empRepository.save(employee);
                        return ResultFactory.commonSuccess();
                    }
                }
                String s = "该员工经验还未添加";
                return ResultFactory.commonError(s);
            }
        }

        String s = "该员工经验还未添加";

        return ResultFactory.commonError(s);
    }

    @Override
    public List<WorkExperienceDto> getAllWorkExperiences(Long empId) {
        Employee employee = empRepository.get(empId);
        List<WorkExperienceDto> dtos = new ArrayList<WorkExperienceDto>();
        if (employee != null) {
            List<WorkExperience> list = employee.getWorkExperiences();
            if (list != null && list.size() > 0) {
                for (WorkExperience wk : list) {
                    WorkExperienceDto dto = new WorkExperienceDto();
                    dto = DtoFactory.convert(wk, dto);
                    if (wk.getStrartTime() != null) {
                        dto.setStrStrartTime(DateHelper.formatDate(wk.getStrartTime()));
                    }
                    if (wk.getOverTime() != null) {
                        dto.setStrOverTime(DateHelper.formatDate(wk.getOverTime()));
                    }
                    dtos.add(dto);
                }

                return dtos;
            }
        }
        return Collections.emptyList();
    }

    @Override
    public PagedResult<WorkExperienceDto> findWorkExperiencesPaged(Long empId, WorkExperienceCriteria criteria) {
        Criteria criterion = getSession().createCriteria(WorkExperience.class);
        criterion.add(Restrictions.eq("empId", empId));
        criterion.add(Restrictions.eq("isActive", true));
        if (!StringUtils.isNullOrWhiteSpace(criteria.getWorkName())) {
            criterion.add(Restrictions.like("workName", "%" + criteria.getWorkName() + "%"));
        }
        if (!StringUtils.isNullOrWhiteSpace(criteria.getCompany())) {
            criterion.add(Restrictions.like("company", "%" + criteria.getCompany() + "%"));
        }

        int totalCount = ((Long) criterion.setProjection(Projections.rowCount()).uniqueResult()).intValue();
        criterion.setProjection(null);
        criterion.setFirstResult((criteria.getPageNumber() - 1) * criteria.getPageSize()).setMaxResults(criteria.getPageSize());
        List<WorkExperience> list = criterion.list();
        List<WorkExperienceDto> dtos = new ArrayList<WorkExperienceDto>();
        for (WorkExperience we : list) {
            WorkExperienceDto dto = new WorkExperienceDto();
            dto = DtoFactory.convert(we, dto);
            if (we.getOverTime() != null) {
                dto.setStrOverTime(DateHelper.formatDate(we.getOverTime()));
            }
            if (we.getStrartTime() != null) {
                dto.setStrStrartTime(DateHelper.formatDate(we.getStrartTime()));
            }
            dtos.add(dto);
        }
        return new PagedResult<WorkExperienceDto>(dtos, criteria.getPageNumber(), criteria.getPageSize(), totalCount);
    }

    @Override
    public EmpSessionDto GetEmpDtoById(Long id) {


        Employee e = empRepository.get(id);
        EmpSessionDto dto = null;
        DtoFactory.convert(e, dto);
        dto.setDeptNo(e.getDept().getDeptNo());
        dto.setDeptName(e.getDept().getDeptName());

        return dto;

    }

    @Override
    public List<Employee> GetOtherPeople(Long id) {
        Criteria criteria = getSession().createCriteria(Employee.class);
        criteria.add(Restrictions.eq("isActive", true));
        criteria.add(Restrictions.ne("id", id));
        List<Employee> list = criteria.list();
        return list;

    }

    @Override
    public Boolean isLeader(Long id) {
        Employee e = empRepository.get(id);
        if (e.getDept().getParentDept() == null) {
            return true;
        } else
            return false;
    }

    @Override
    public List<EmpDto> getAllHeader(Long id) {
        Criteria criteria = getSession().createCriteria(Employee.class);
        criteria.createAlias("dept", "dept");
        criteria.add(Restrictions.eq("isActive", true));
        criteria.add(Restrictions.eq("dept.id", id));
        criteria.addOrder(Order.asc("dept.deptNo"));
        List<Employee> list = criteria.list();
        List<EmpDto> empDtos = new ArrayList<>();
        for (Employee employee : list) {
            EmpDto dto = DtoFactory.convert(employee, new EmpDto());
            empDtos.add(dto);
        }
        return empDtos;
    }

    @Override
    public List<EmpDto> getHeader() {
        List<EmpDto> empDtos = new ArrayList<>();
        List<DeptmentDto> deptmentDtos = deptmentService.getDeptsByName("院领导");//分管领导为院领导部门下人员
        if (deptmentDtos != null) {
            Criteria criteria = getSession().createCriteria(Employee.class);
            criteria.createAlias("dept", "dept");
            criteria.add(Restrictions.eq("isActive", true));
            criteria.add(Restrictions.eq("dept.id", deptmentDtos.get(0).getId()));
            criteria.addOrder(Order.asc("dept.deptNo"));
            List<Employee> list = criteria.list();

            for (Employee employee : list) {
                EmpDto dto = DtoFactory.convert(employee, new EmpDto());
                empDtos.add(dto);
            }
        }
        return empDtos;
    }


    /**
     * 将员工的年假与带薪休假重置
     *
     * @param empIds
     * @return
     */
    @Override
    public CommonResult resetYearAndSpa(List<Long> empIds) {
        Criteria criteria = getSession().createCriteria(Employee.class);
        criteria.add(Restrictions.eq("isActive", true));
        if (empIds != null && empIds.size() > 0) {
            criteria.add(Restrictions.in("id", empIds));
        }
        List<Employee> employees = criteria.list();
        if (employees != null && employees.size() > 0) {

            for (Employee e : employees) {
                e.setSeniority(DtoFactory.timeSlot(e.getJoinDate()));
                if (e.getSeniority() != null && e.getSeniority() >= 1) {//置年假
                    e.setYearWithMoney(getT(e));
                    e.setHasYear(getT(e));
                    e.setNewSpaYear(null);
                    if (e.getSeniority() >= 10) {//置疗养假
                        getShouldSpa(e);
                    }
                    empRepository.save(e);
                }
            }
            return ResultFactory.commonSuccess();
        }
        return new CommonResult(false, "重置年假失败！");
    }

    @Override
    public List<RestSpanAndYear> getCanRestYearOrSpan(Date time, int type) {
        List<RestSpanAndYear> list = new ArrayList<>();
        if (type == 1) {
            list.addAll(getCanRestYear(time));
        } else {
            list.addAll(getCanRestSpan(time));
        }
        return list;
    }

    @Override
    public List<MultiSelectUserWithDeptDto> getAllDeptEmp() {
        List<EmpDto> empDtos = this.getAllEmps();
        List<DeptmentDto> deptmentDtos = deptmentService.getAllDept();
        List<MultiSelectUserWithDeptDto> multiSelectUserWithDepts = new ArrayList<>();
        for (DeptmentDto dept : deptmentDtos) {
            MultiSelectUserWithDeptDto multiSelectUserWithDept = new MultiSelectUserWithDeptDto(dept.getId(), dept.getDeptName());
            for (EmpDto emp : empDtos) {
                if (emp.getDeptId() != null && emp.getDeptId().equals(dept.getId()))
                    multiSelectUserWithDept.getEmpDtos().add(emp);
            }
            multiSelectUserWithDepts.add(multiSelectUserWithDept);
        }
        return multiSelectUserWithDepts;
    }

    //计算可请的带薪年假
    private Float getT(Employee e) {
        Integer t = e.getSeniority() == null ? 0 : e.getSeniority();
        Float canRest = 0f;
        if (t >= 1 && t < 5) {
            canRest = 4f;
        } else if (t >= 5 && t < 10) {
            canRest = 7f;
        } else if (t >= 10 && t < 15) {
            canRest = 10f;
        } else if (t >= 15) {
            canRest = 14f;
        } else {
            canRest = 0f;
        }
        return canRest;
    }

    /**
     * 计算可请的疗养假
     *
     * @return
     */
    private Employee getShouldSpa(Employee e) {
        Integer t = e.getSeniority() == null ? 0 : e.getSeniority();
        Float cSpa = 0f;
        if (t >= 10 && t < 20) {//10-19年
            if (e.getNewSpaYear() == null) {
                cSpa = 15f;
                e.setCanSpa(true);
            } else {
                if (DateHelper.getYear(new Date()) - e.getNewSpaYear() == 4) {
                    cSpa = 15f;
                    e.setCanSpa(true);
                }
            }
        } else if (t >= 20 && t < 29) {
            if (e.getNewSpaYear() == null) {
                cSpa = 15f;
                e.setCanSpa(true);
            } else {
                if (DateHelper.getYear(new Date()) - e.getNewSpaYear() == 3) {
                    cSpa = 15f;
                    e.setCanSpa(true);
                }
            }
        } else if (t >= 30) {
            if (e.getNewSpaYear() == null) {
                cSpa = 15f;
                e.setCanSpa(true);
            } else {
                if (DateHelper.getYear(new Date()) - e.getNewSpaYear() == 2) {
                    cSpa = 15f;
                    e.setCanSpa(true);
                }
            }
        }
        e.setSpaWithMoney(cSpa);
        return e;
    }


    @Override
    public Employee getEmpByLoginName(String loginName) {
        return empRepository.getEmpByLoginName(loginName);
    }


    @Override
    public Map<Long, String> getEmployIdWithName(Long deptId) {
        return empRepository.getAllByDeptId(deptId);
    }


    @Override
    public CommonResult save(Employee emp) {
        empRepository.save(emp);
        return ResultFactory.commonSuccess();
    }

    /**
     * 带薪年假 可休人员
     *
     * @param time
     * @return
     */
    private List<RestSpanAndYear> getCanRestYear(Date time) {
        Integer year = DateHelper.getYear(time);
        String hql = " select new com.sunesoft.lemon.syms.eHr.application.dtos.RestSpanAndYear(e.name,e.userNo,t.deptName,e.hasYear) from Employee e join e.dept t where e.isActive=1 and e.seniority>=1 and (e.newRestYear is null or e.newRestYear<" + year + ") and (e.newSpaYear is null or e.newSpaYear!=" + year + ")";
        Query query = getSession().createQuery(hql);
        List<RestSpanAndYear> list = query.list();
        return list;
    }

    /**
     * 疗养假 可休人员
     *
     * @param time
     * @return
     */
    private List<RestSpanAndYear> getCanRestSpan(Date time) {
        Integer year = DateHelper.getYear(time);
        //用sql语句查询的的数据，数据类型装好出现问题，Float->integer
        String sql="select e.real_name as hname,e.user_no as hno,d.dept_name as deptName, e.spawithmoney as days from syy_oa_hr_employees e join syy_oa_hr_dept d on e.emp_dept_id=d.id where e.is_active=1 and e.seniority>=10 and e.spawithmoney>0 and (e.newrestyear is NULL or e.newrestyear !="+year+") and (e.newspayear is null or (select (case when c.seniority>=30 then c.newspayear+2 when c.seniority>=20 then c.newspayear+3 else c.newspayear+4 end) as b from syy_oa_hr_employees c where e.id=c.id and c.is_active=1 and c.seniority>=10 and c.newspayear is not null )<="+year+")";
        Query query2=getSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<Map> employee=query2.list();
        List<RestSpanAndYear> list=new ArrayList<>();
        if(employee!=null&&employee.size()>0){
            for(Map map:employee) {
                RestSpanAndYear rs=new RestSpanAndYear((String)map.get("HNAME"),(String)map.get("HNO"),(String)map.get("DEPTNAME"));
                BigDecimal b=(BigDecimal)map.get("DAYS");
                rs.setDays(b==null?null:b.intValue());
                list.add(rs);
            }
        }
        return list;
    }

}
