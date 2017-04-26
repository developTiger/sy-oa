package com.sunesoft.lemon.syms.workflow.domain.infrastructure.hibernate;

import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import com.sunesoft.lemon.syms.workflow.domain.FormHeader;
import com.sunesoft.lemon.syms.workflow.domain.FormList;
import com.sunesoft.lemon.syms.workflow.domain.FormListRepository;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhouz on 2016/5/30.
 */
@Service("formListRepository")
public class FormListRepositoryImpl extends GenericHibernateRepository<FormList,Long> implements FormListRepository {

    @Override
    public FormList getByFormKind(String formKind) {
        Criteria criterion = getSession().createCriteria(FormList.class);
        criterion.add(Restrictions.eq("isActive", true));
        criterion.add(Restrictions.eq("formKind", formKind));
        return (FormList)criterion.list().get(0);
    }
}
