package com.sunesoft.lemon.syms.eHr.application;

import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateFinder;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.results.ResultFactory;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.syms.eHr.application.criteria.DeptmentCriteria;
import com.sunesoft.lemon.syms.eHr.application.dtos.DeptmentDto;
import com.sunesoft.lemon.syms.eHr.application.factory.DtoFactory;
import com.sunesoft.lemon.syms.eHr.domain.dept.Deptment;
import com.sunesoft.lemon.syms.eHr.domain.dept.DeptmentRepository;
import com.sunesoft.lemon.syms.eHr.domain.empInfo.Employee;
import com.sunesoft.lemon.syms.eHr.domain.empInfo.EmployeeRepository;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.transform.Result;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by xiazl on 2016/6/22.
 */
@Service("deptmentService")
public class DeptmentServiceImpl extends GenericHibernateFinder implements DeptmentService {

    @Autowired
    DeptmentRepository deptRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public CommonResult addDept(DeptmentDto dto) {
        Deptment dept = new Deptment();
        List<DeptmentDto> list = getDeptsByName(dto.getDeptName());
        if (list != null && list.size() > 0) {
            String s = "该部门名已存在，请重新填写！";
            return ResultFactory.commonError(s);
        }
        List<DeptmentDto> deptmentDtos = this.getAllDept();
        for (DeptmentDto dep : deptmentDtos) {
            if (dep.getDeptNo().equals(dto.getDeptNo())) {
                String s = "该部门编号已经存在，请重新填写！";
                return ResultFactory.commonError(s);
            }
        }
        dept = DtoFactory.convert(dto, dept);
        if (dto != null && dto.getParentDeptId() != null && dto.getParentDeptId() > 0) {
            Deptment dt = deptRepository.get(dto.getParentDeptId());
            if (dt != null && dt.getIsActive() && dt.getStatus() != null && dt.getStatus() == 1) {
                dept.setParentDept(dt);
            }
        }


        return ResultFactory.commonSuccess(deptRepository.save(dept));

    }

    @Override
    public CommonResult deleteDept(List<Long> ids) {
        Criteria criterion = getSession().createCriteria(Deptment.class);
        if (ids == null || ids.size() < 1) {
            return ResultFactory.commonError("请选择将要删除的部门");
        }
        criterion.add(Restrictions.in("id", ids));
        List<Deptment> list = criterion.list();
        if (list != null && list.size() > 0) {
            for (Deptment dept : list) {
                dept.setIsActive(false);
                dept.setLastUpdateTime(new Date());
                deptRepository.save(dept);
            }
            return ResultFactory.commonSuccess();
        }
        String s = "此部门已不存在";
        return ResultFactory.commonError(s);
    }

    @Override
    public CommonResult setStatus(List<Long> ids, Integer status) {
        Criteria criterion = getSession().createCriteria(Deptment.class);
        if (ids == null || ids.size() < 1) {
            return ResultFactory.commonError("请选择将要设置的部门");
        }
        criterion.add(Restrictions.in("id", ids));
        List<Deptment> list = criterion.list();
        if (list != null && list.size() > 0) {
            for (Deptment dept : list) {
                dept.setStatus(status);
                dept.setLastUpdateTime(new Date());
                deptRepository.save(dept);
            }
            return ResultFactory.commonSuccess();
        }
        String s = "此部门已不存在";
        return ResultFactory.commonError(s);
    }

    @Override
    public List<DeptmentDto> getByDeptsIds(List<Long> ids) {
        List<DeptmentDto> dtos = new ArrayList<DeptmentDto>();
        Criteria criterion = getSession().createCriteria(Deptment.class);
        criterion.add(Restrictions.eq("isActive", true));
        criterion.addOrder(Order.asc("deptNo"));

        if (ids == null || ids.size() < 1) {
            return dtos;
        }
        criterion.add(Restrictions.in("id", ids));
        List<Deptment> list = criterion.list();
        if (list != null && list.size() > 0) {
            for (Deptment dept : list) {
                DeptmentDto dto = new DeptmentDto();
                dto = DtoFactory.convert(dept, dto);
                if (dept.getParentDept() != null) {
                    dto.setParentDeptId(dept.getParentDept().getId());
                    dto.setParentDeptName(dept.getParentDept().getDeptName());
                }
                dtos.add(dto);
            }
        }
        return dtos;
    }

    @Override
    public List<DeptmentDto> getByDeptsIds() {
        Criteria criterion = getSession().createCriteria(Deptment.class);
        criterion.add(Restrictions.eq("isActive", true));
        List<Deptment> list = criterion.list();
        List<DeptmentDto> dtos = new ArrayList<DeptmentDto>();
        if (list != null && list.size() > 0) {
            for (Deptment dept : list) {
                DeptmentDto dto = new DeptmentDto();
                dto = DtoFactory.convert(dept, dto);
                if (dept.getParentDept() != null) {
                    dto.setParentDeptId(dept.getParentDept().getId());
                    dto.setParentDeptName(dept.getParentDept().getDeptName());
                }
                dtos.add(dto);
            }
        }
        return dtos;
    }

    @Override
    public DeptmentDto getByDeptId(Long id) {
        DeptmentDto dto = new DeptmentDto();
        if (id != null && id > 0) {
            Deptment dp = deptRepository.get(id);
            if (dp.getIsActive()) {
                dto = DtoFactory.convert(dp, dto);
                if (dp.getParentDept() != null) {
                    dto.setParentDeptId(dp.getParentDept().getId());
                    dto.setParentDeptName(dp.getParentDept().getDeptName());
                }
            }
        }
        return dto;
    }


    @Override
    public CommonResult updateDept(DeptmentDto dto) {

        List<DeptmentDto> list = this.getAllDept();
        for (DeptmentDto dept : list) {
            if (dept.getId().equals(dto.getId())) {
                list.remove(dept);
                break;
            }
        }
        for (DeptmentDto dep : list) {
            if (dep.getDeptNo().equals(dto.getDeptNo()) || dep.getDeptName().equals(dto.getDeptName())) {
                String result = "部门编号或部门名称已存在，请重新填写";
                return ResultFactory.commonError(result);
            }
        }

        Deptment dt = deptRepository.get(dto.getId());

        if (dt != null && dt.getStatus() == 1) {
            dt = DtoFactory.convert(dto, dt);
            if (dto.getParentDeptId() != null && dto.getParentDeptId() > 0) {
                Deptment d = deptRepository.get(dto.getParentDeptId());
                if (d != null && d.getIsActive() && d.getStatus() != null && d.getStatus() == 1) {
                    dt.setParentDept(d);
                }
            }
            dt.setLastUpdateTime(new Date());
            return ResultFactory.commonSuccess(deptRepository.save(dt));
        }
        String s = "此部门不存在，或已被禁用！";
        return ResultFactory.commonError(s);
    }

    @Override
    public List<DeptmentDto> getDeptsByName(String name) {
        Criteria criterion = getSession().createCriteria(Deptment.class);
        criterion.add(Restrictions.eq("isActive", true));
        criterion.add(Restrictions.eq("deptName", name));
        List<Deptment> list = criterion.list();
        List<DeptmentDto> dtos = new ArrayList<DeptmentDto>();
        if (list != null && list.size() > 0) {
            for (Deptment eg : list) {
                DeptmentDto dto = new DeptmentDto();
                dto = DtoFactory.convert(eg, dto);
                if (eg.getParentDept() != null) {
                    dto.setParentDeptId(eg.getParentDept().getId());
                    dto.setParentDeptName(eg.getParentDept().getDeptName());
                }
                dtos.add(dto);
            }
        }
        return dtos;
    }

    @Override
    public List<DeptmentDto> getAllDept() {
        Criteria criterion = getSession().createCriteria(Deptment.class);
        criterion.add(Restrictions.eq("isActive", true));
        criterion.addOrder(Order.asc("deptNo"));
        List<Deptment> list = criterion.list();
        List<DeptmentDto> dtos = new ArrayList<DeptmentDto>();
        if (list != null && list.size() > 0) {
            for (Deptment eg : list) {
                DeptmentDto dto = new DeptmentDto();
                dto = DtoFactory.convert(eg, dto);
                if(eg.getParentDept()!=null){
                    dto.setParentDeptId(eg.getParentDept().getId());
                    dto.setParentDeptName(eg.getParentDept().getDeptName());
                }
                dtos.add(dto);
            }
        }
        return dtos;

    }

    @Override
    public List<DeptmentDto> getAllSimpleDept() {
        String hql = " select new list(s.id,s.deptName) from Deptment s where s.isActive=1 order by s.deptNo";
        Query query = getSession().createQuery(hql);
        List<List> lists = query.list();
        List<DeptmentDto> dtos = new ArrayList<DeptmentDto>();

        if (lists != null && lists.size() > 0) {
            for (List eg : lists) {
                DeptmentDto dto = new DeptmentDto();
                dto.setId((Long) eg.get(0));
                dto.setDeptName((String) eg.get(1));
                dtos.add(dto);
            }
        }
        return dtos;
    }

    @Override
    public PagedResult<DeptmentDto> findDeptsPaged(DeptmentCriteria criteria) {
        Criteria criterion = getSession().createCriteria(Deptment.class);
        criterion.add(Restrictions.eq("isActive", true));
        if (!StringUtils.isNullOrWhiteSpace(criteria.getDeptName())) {
            criterion.add(Restrictions.like("deptName", "%" + criteria.getDeptName() + "%"));
        }
        if (!StringUtils.isNullOrWhiteSpace(criteria.getDeptNo())) {
            criterion.add(Restrictions.like("deptNo", "%" + criteria.getDeptNo() + "%"));
        }
        if (criteria.getDeptType() != null) {
            criterion.add(Restrictions.eq("deptType", criteria.getDeptType()));
        }
        int totalCount = ((Long) criterion.setProjection(Projections.rowCount()).uniqueResult()).intValue();
        criterion.addOrder(Order.asc("deptNo"));
        criterion.setProjection(null);
        criterion.setFirstResult((criteria.getPageNumber() - 1) * criteria.getPageSize()).setMaxResults(criteria.getPageSize());
        List<Deptment> list = criterion.list();
        List<DeptmentDto> dtos = new ArrayList<DeptmentDto>();
        if (list != null && list.size() > 0) {
            for (Deptment d : list) {
                DeptmentDto dto = new DeptmentDto();
                if (d.getParentDept() != null) {
                    dto.setParentDeptId(d.getParentDept().getId());
                    dto.setParentDeptName(d.getParentDept().getDeptName());
                }
                dtos.add(DtoFactory.convert(d, dto));
            }
        }
        return new PagedResult<DeptmentDto>(dtos, criteria.getPageNumber(), criteria.getPageSize(), totalCount);
    }
}
