package com.sunesoft.lemon.deanery.ReceptionFlow.domain.infrastructure.hibernate;

import com.sunesoft.lemon.deanery.ReceptionFlow.domain.WorkingLunch;
import com.sunesoft.lemon.deanery.ReceptionFlow.domain.WorkingLunchRepository;
import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import org.hibernate.Query;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wangwj on 2016/8/2 0002.
 */
@Service(value = "workingLunchRepository")
public class WorkingLunchRepositoryImpl extends GenericHibernateRepository<WorkingLunch,Long> implements WorkingLunchRepository {
    @Override
    public WorkingLunch getByRecepid(Long recepId) {
        String sql = "from WorkingLunch where receptionId = ?";
        Query query = this.getSession().createQuery(sql);
        query.setParameter(0,recepId);
        List<WorkingLunch> lists = query.list();
        return lists != null && lists.size() > 0 ? lists.get(0) : null;
    }
}
