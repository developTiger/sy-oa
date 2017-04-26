package com.sunesoft.lemon.syms.eHr.application;

import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateFinder;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.syms.eHr.application.criteria.AttendCriteria;
import com.sunesoft.lemon.syms.eHr.application.criteria.AttendTypeCriteria;
import com.sunesoft.lemon.syms.eHr.domain.attend.AttendType;
import com.sunesoft.lemon.syms.eHr.domain.attend.AttendTypeRepository;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by xiazl on 2017/3/2.
 */
@Service("attendTypeService")
public class AttendTypeServiceImpl extends GenericHibernateFinder implements AttendTypeService {
    @Autowired
    AttendTypeRepository attendTypeRepository;

    @Override
    public Long create(AttendType type) {
        AttendType att=attendTypeRepository.getByCord(type.getCord());
        if(att!=null) return -1l;//该标识的类型已经存在
        type.setIsActive(true);
        type.setCreateDateTime(new Date());
        type.setLastUpdateTime(new Date());
        return attendTypeRepository.save(type);
    }

    @Override
    public Long edit(AttendType type) {
        AttendType t=attendTypeRepository.get(type.getId());
        AttendType t1=attendTypeRepository.getByCord(type.getCord());
        if(t.getIsActive() && t1!=null && t.getId()!=t1.getId()) return -1l;//修改后的标识符也存在
        if (t != null && t.getIsActive()) {
            t.setName(type.getName());
            t.setCord(type.getCord());
            t.setLastUpdateTime(new Date());
            return attendTypeRepository.save(t);
        }
        return -1l;
    }

    @Override
    public AttendType get(Long id) {
        AttendType type= attendTypeRepository.get(id);
        if (type != null && type.getIsActive()) {
            return type;
        }
        return null;
    }

    @Override
    public AttendType getByCord(String cord) {
        return attendTypeRepository.getByCord(cord);
    }

    @Override
    public boolean delete(Long id) {
        AttendType type = attendTypeRepository.get(id);
        if (type != null && type.getIsActive()) {
            type.setIsActive(false);
            type.setLastUpdateTime(new Date());
            attendTypeRepository.save(type);
            return true;
        }
        return true;
    }

    @Override
    public List<AttendType> getByName(String name) {
        return attendTypeRepository.getList(name);
    }

    @Override
    public PagedResult<AttendType> page(AttendTypeCriteria criteria) {
        return attendTypeRepository.page(criteria);
    }

    @Override
    public List<AttendType> t() {
        Criteria criteria = getSession().createCriteria(AttendType.class);
        List<AttendType> lis=criteria.list();
        return lis;
    }


}
