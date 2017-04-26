package com.sunesoft.lemon.deanery.ReceptionFlow.domain.infrastructure.hibernate;

import com.sunesoft.lemon.deanery.ReceptionFlow.domain.Accommodation;
import com.sunesoft.lemon.deanery.ReceptionFlow.domain.AccommodationReposiroty;
import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import org.hibernate.Query;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wangwj on 2016/8/2 0002.
 */
@Service(value = "accommodationRepository")
public class AccommodationRepositoryImpl extends GenericHibernateRepository<Accommodation,Long> implements AccommodationReposiroty {
    @Override
    public Accommodation getByRecepId(Long recepId) {
        String sql = "from Accommodation where receptionId = ?";
        Query query = this.getSession().createQuery(sql);
        query.setParameter(0,recepId);
        List<Accommodation> lists = query.list();
        return lists != null && lists.size() > 0 ? lists.get(0) : null;
    }
}
