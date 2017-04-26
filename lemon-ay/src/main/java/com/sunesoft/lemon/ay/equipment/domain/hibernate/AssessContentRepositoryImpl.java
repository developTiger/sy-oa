package com.sunesoft.lemon.ay.equipment.domain.hibernate;

import com.sunesoft.lemon.ay.equipment.domain.AssessContectRepositroy;
import com.sunesoft.lemon.ay.equipment.domain.AssessContent;
import com.sunesoft.lemon.ay.equipment.domain.Assessment;
import com.sunesoft.lemon.ay.equipment.domain.AssessmentRepository;
import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import org.hibernate.Query;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jiangkefan on 2016/8/19.
 */
@Service("assessContectRepositroy")
public class AssessContentRepositoryImpl extends GenericHibernateRepository<AssessContent,Long> implements AssessContectRepositroy {

    @Override
    public List<AssessContent> getAllContents() {

        String hql  = "from AssessContent ";
        Query q  = getSession().createQuery(hql);
        return q.list();
    }

    @Override
    public List<AssessContent> getAllByEquipmentId(Long equipmentId) {
        String hql = "from AssessContent a where a.equipmentId="+equipmentId;
        Query query = getSession().createQuery(hql);
        return query.list();
    }
}
