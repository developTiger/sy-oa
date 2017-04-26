package com.sunesoft.lemon.syms.eHr.domain.dept.infrastructure.hibernate;

import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import com.sunesoft.lemon.syms.eHr.domain.dept.Deptment;
import com.sunesoft.lemon.syms.eHr.domain.dept.DeptmentRepository;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * Created by xiazl on 2016/6/22.
 */
@Service("deptmentRepository")
public class DeptmentRepositoryImpl extends GenericHibernateRepository<Deptment, Long> implements DeptmentRepository {
    @Override
    public List<Deptment> getByIds(List<Long> ids) {
        return null;
    }

    @Override
    public HashMap<Long, Long> getDeptChargeLeaderIds() {
        String hql =  "select id,chargeLeaderId from Deptment d where d.isActive = true";
        Query query = getSession().createQuery(hql);
        List<Object[]> links = query.list();
        HashMap<Long,Long> depats = new HashMap<>();
        for(Object[] link : links) {
            depats.put((Long)link[0],(Long)link[1]);
        }
        return depats;
    }

    @Override
    public List<Deptment> getAll() {
        Criteria criterion = getSession().createCriteria(Deptment.class);
        criterion.add(Restrictions.eq("isActive",true));
        return criterion.list();
    }
}
