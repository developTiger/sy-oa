package com.sunesoft.lemon.syms.eHr.application;

import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateFinder;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.results.ResultFactory;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.syms.eHr.application.criteria.GroupCriteria;
import com.sunesoft.lemon.syms.eHr.application.dtos.DeptmentDto;
import com.sunesoft.lemon.syms.eHr.application.dtos.EmpGroupDto;
import com.sunesoft.lemon.syms.eHr.application.factory.DtoFactory;
import com.sunesoft.lemon.syms.eHr.domain.empInfo.EmpGroup;
import com.sunesoft.lemon.syms.eHr.domain.empInfo.EmpGroupRepository;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by xiazl on 2016/6/22.
 */
@Service("empGroupService")
public class EmpGroupServiceImpl extends GenericHibernateFinder implements EmpGroupService {

    @Autowired
    EmpGroupRepository groupRepository;

    @Override
    public CommonResult addGroup(EmpGroupDto dto) {
        EmpGroup eg = new EmpGroup();
        List<EmpGroupDto> list = getGroupsByName(dto.getName());
        if (list != null && list.size() > 0) {
            String s = "该组名已存在";
            return ResultFactory.commonError(s);
        }
        eg = DtoFactory.convert(dto, eg);
        if(dto!=null&&dto.getParentId()!=null&&dto.getParentId()>0){
            EmpGroup group=groupRepository.get(dto.getParentId());
            if(group!=null&&group.getStatus()!=null&&group.getStatus()==1){
               eg.setParent(group);
            }

        }
        return ResultFactory.commonSuccess(groupRepository.save(eg));


    }

    @Override
    public CommonResult deleteGroup(List<Long> ids) {
        Criteria criterion = getSession().createCriteria(EmpGroup.class);
        if(ids==null||ids.size()<1){
            return ResultFactory.commonError("请选择将要删除的员工组");
        }
        criterion.add(Restrictions.in("id", ids));
        List<EmpGroup> list = criterion.list();
        if (list != null && list.size() > 0) {
            for (EmpGroup eg : list) {
                eg.setIsActive(false);
                eg.setLastUpdateTime(new Date());
                groupRepository.save(eg);
            }
            return ResultFactory.commonSuccess();
        }
        String s = "此员工组已不存在";
        return ResultFactory.commonError(s);
    }

    @Override
    public CommonResult setStatus(List<Long> ids, Integer status) {
        Criteria criterion = getSession().createCriteria(EmpGroup.class);
        if(ids==null||ids.size()<1){
            return ResultFactory.commonError("请选择将要设置的员工组");
        }
        criterion.add(Restrictions.in("id", ids));
        List<EmpGroup> list = criterion.list();
        if (list != null && list.size() > 0) {
            for (EmpGroup eg : list) {
               eg.setStatus(status);
                eg.setLastUpdateTime(new Date());
                groupRepository.save(eg);
            }
            return ResultFactory.commonSuccess();
        }
        String s = "此员工组已不存在";
        return ResultFactory.commonError(s);
    }

    @Override
    public List<EmpGroupDto> getByGroupsIds(List<Long> ids) {
        Criteria criterion = getSession().createCriteria(EmpGroup.class);
        criterion.add(Restrictions.eq("isActive", true));
        criterion.add(Restrictions.in("id", ids));
        List<EmpGroup> list = criterion.list();
        List<EmpGroupDto> dtos = new ArrayList<EmpGroupDto>();
        if (list != null && list.size() > 0) {
            for (EmpGroup eg : list) {
                EmpGroupDto dto = new EmpGroupDto();
                dto = DtoFactory.convert(eg, dto);
                if(eg.getParent()!=null){
                    dto.setParentName(eg.getParent().getName());
                    dto.setParentId(eg.getParent().getId());
                }
                dtos.add(dto);
            }
        }
        return dtos;
    }

    @Override
    public List<EmpGroupDto> getByGroupsIds() {
        Criteria criterion = getSession().createCriteria(EmpGroup.class);
        criterion.add(Restrictions.eq("isActive", true));
        List<EmpGroup> list = criterion.list();
        List<EmpGroupDto> dtos = new ArrayList<EmpGroupDto>();
        if (list != null && list.size() > 0) {
            for (EmpGroup eg : list) {
                EmpGroupDto dto = new EmpGroupDto();
                dto = DtoFactory.convert(eg, dto);
                if(eg.getParent()!=null){
                    dto.setParentId(eg.getParent().getId());
                    dto.setParentName(eg.getParent().getName());
                }
                dtos.add(dto);
            }
        }
        return dtos;
    }

    @Override
    public CommonResult updateGroup(EmpGroupDto dto) {

        List<EmpGroupDto> list=this.getAll();
        for (EmpGroupDto emp:list){
            if (emp.getId().equals(dto.getId())){
                list.remove(emp);
                break;
            }
        }
        for (EmpGroupDto empGroupDto:list){
            if (empGroupDto.getName().equals(dto.getName())) {
                String result = "员工组名称已存在，请重新填写";
                return ResultFactory.commonError(result);
            }
        }

       EmpGroup eg=groupRepository.get(dto.getId());
        if(eg!=null&&eg.getIsActive()&&eg.getStatus()==1){
            eg=DtoFactory.convert(dto,eg);
            if(dto.getParentId()!=null&&dto.getParentId()>0){
                EmpGroup group=groupRepository.get(dto.getParentId());
                if(group!=null&&group.getIsActive()&&group.getStatus()!=null&&group.getStatus()==1){
                    eg.setParent(group);
                }
            }
            return ResultFactory.commonSuccess(groupRepository.save(eg));
        }
        String s="此员工组不存在,或被禁用！";
        return ResultFactory.commonError(s);
    }

    @Override
    public List<EmpGroupDto> getGroupsByName(String name) {
        Criteria criterion = getSession().createCriteria(EmpGroup.class);
        criterion.add(Restrictions.eq("isActive", true));
        criterion.add(Restrictions.like("name", name));
        List<EmpGroup> list = criterion.list();
        List<EmpGroupDto> dtos = new ArrayList<EmpGroupDto>();
        if (list != null && list.size() > 0) {
            for (EmpGroup eg : list) {
                EmpGroupDto dto = new EmpGroupDto();
                dto = DtoFactory.convert(eg, dto);
                if(eg.getParent()!=null){
                    dto.setParentName(eg.getParent().getName());
                    dto.setParentId(eg.getParent().getId());
                }
                dtos.add(dto);
            }
        }
        return dtos;
    }

    @Override
    public PagedResult<EmpGroupDto> findGroupsPaged(GroupCriteria criteria) {
        Criteria criterion = getSession().createCriteria(EmpGroup.class);
        criterion.add(Restrictions.eq("isActive", true));
        if (!StringUtils.isNullOrWhiteSpace(criteria.getName())) {
            criterion.add(Restrictions.like("name", "%" + criteria.getName() + "%"));
        }
        int totalCount = ((Long) criterion.setProjection(Projections.rowCount()).uniqueResult()).intValue();
        criterion.setProjection(null);
        criterion.setFirstResult((criteria.getPageNumber() - 1) * criteria.getPageSize()).setMaxResults(criteria.getPageSize());
        List<EmpGroup> list = criterion.list();
        List<EmpGroupDto> dtos = new ArrayList<EmpGroupDto>();
        if (list != null && list.size() > 0) {
            for (EmpGroup e : list) {
                EmpGroupDto dto = new EmpGroupDto();
                if(e.getParent()!=null){
                    dto.setParentName(e.getParent().getName());
                    dto.setParentId(e.getParent().getId());
                }
                dtos.add(DtoFactory.convert(e, dto));
            }
        }
        return new PagedResult<EmpGroupDto>(dtos, criteria.getPageNumber(), criteria.getPageSize(), totalCount);
    }

    @Override
    public List<EmpGroupDto> getAll() {
        Criteria criteria = getSession().createCriteria(EmpGroup.class);
        criteria.add(Restrictions.eq("isActive",true));
        List<EmpGroup> empGroups = criteria.list();
        List<EmpGroupDto> empGroupDtos = new ArrayList<>();
        for (EmpGroup emp:empGroups){
            empGroupDtos.add(DtoFactory.convert(emp,new EmpGroupDto()));
        }
        return empGroupDtos;
    }
}
