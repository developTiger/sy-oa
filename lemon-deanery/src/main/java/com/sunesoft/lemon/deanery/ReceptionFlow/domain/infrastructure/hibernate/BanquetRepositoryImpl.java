package com.sunesoft.lemon.deanery.ReceptionFlow.domain.infrastructure.hibernate;

import com.sunesoft.lemon.deanery.ReceptionFlow.domain.Banquet;
import com.sunesoft.lemon.deanery.ReceptionFlow.domain.BanquetRepository;
import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wangwj on 2016/8/2 0002.
 */
@Service(value = "banquetRepository")
public class BanquetRepositoryImpl extends GenericHibernateRepository<Banquet,Long> implements BanquetRepository {
    @Override
    public List<Banquet> getByRecepId(Long recepId) {
        String sql = "from Banquet where receptionId = ?";
        Query query = this.getSession().createQuery(sql);
        query.setParameter(0,recepId);
        return query.list();
    }
}
