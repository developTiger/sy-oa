package com.sunesoft.lemon.syms.hrForms.domain.hibernate;

import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import com.sunesoft.lemon.syms.eHr.domain.empInfo.Employee;
import com.sunesoft.lemon.syms.hrForms.domain.FormLeave;
import com.sunesoft.lemon.syms.hrForms.domain.FormLeaveRepository;
import com.sunesoft.lemon.syms.hrForms.domain.FormTrain;
import com.sunesoft.lemon.syms.hrForms.domain.FormTrainRepository;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhouz on 2016/7/1.
 */
@Service("formTrainRepository")
public class FormTrainRepositoryImpl extends GenericHibernateRepository<FormTrain,Long> implements FormTrainRepository {

    @Override
    public FormTrain getByFormNo(Long formNo) {

        Criteria criteria = getSession().createCriteria(FormTrain.class);
        criteria.add(Restrictions.eq("isActive", true));
        criteria.add(Restrictions.eq("formNo",formNo));
        return (FormTrain)criteria.uniqueResult();
    }

    @Override
    public List<FormTrain> getByEmpId(Long empId) {
//        Criteria criteria = getSession().createCriteria(FormTrain.class);
        String hql ="from Formtrainemp t where t.empId = ?";
        Query query = getSession().createQuery(hql);
        query.setParameter(0,empId);
        return query.list();
    }

    @Override
    public List<FormTrain> getAll() {
        String hql = "from FormTrain";
        Query q = getSession().createQuery(hql);
        return q.list();
    }
//
//    @Override
//    public List<FormTrain> getByEmpid(Long id){
//        Criteria criteria = getSession().createCriteria(FormTrain.class);
//        criteria.createAlias("emps","emps");
//        criteria.add(Restrictions.eq("emps.id",id));
//        return criteria.list();
//    }


}
