package com.sunesoft.lemon.ay.equimentForms.domain.hibernate;

import com.sunesoft.lemon.ay.equimentForms.domain.FormEquipment;
import com.sunesoft.lemon.ay.equimentForms.domain.FormEquipmentRepository;
import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import org.hibernate.Query;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jiangkefan on 2016/8/15.
 */
@Service("formEquipmentRepository")
public class FormEquipmentRepositoryImpl extends GenericHibernateRepository<FormEquipment,Long> implements FormEquipmentRepository{


    @Override
    public List<FormEquipment> getAll() {
       String hql = "from FormEquipment ";
        Query q = getSession().createQuery(hql);
        return q.list();
    }
}
