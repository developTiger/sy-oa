package com.sunesoft.lemon.deanery.ReceptionFlow.domain.infrastructure.hibernate;

import com.sunesoft.lemon.deanery.ReceptionFlow.domain.ReceptionNB;
import com.sunesoft.lemon.deanery.ReceptionFlow.domain.ReceptionRepository;
import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import org.hibernate.Query;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wangwj on 2016/8/2 0002.
 */
@Service(value = "receptionRepository")
public class ReceptionRepositoryImpl extends GenericHibernateRepository<ReceptionNB,Long> implements ReceptionRepository {
    @Override
    public ReceptionNB getByFormNo(Long formNo) {
        String sql = "from ReceptionNB where formNo = ?";
        Query query = this.getSession().createQuery(sql);
        query.setParameter(0,formNo);
        List<ReceptionNB> lists = query.list();
        return lists != null && lists.size() > 0 ? lists.get(0) : null;
    }
}
