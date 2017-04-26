package com.sunesoft.lemon.deanery.typeOfTreatise.domain.infrastructure.hibernate;

import com.sunesoft.lemon.deanery.typeOfTreatise.domain.TypeOfTreaiseRepository;
import com.sunesoft.lemon.deanery.typeOfTreatise.domain.TypeOfTreatise;
import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by pxj on 2016/12/15.
 */
@Service("typeOfTreatiseRepository")
public class TypeOfTreatiseRepositoryImpl  extends GenericHibernateRepository<TypeOfTreatise,Long> implements TypeOfTreaiseRepository{

    @Override
    public TypeOfTreatise findById(String id) {
        Criteria criterion = getSession().createCriteria(TypeOfTreatise.class);
        criterion.add(Restrictions.eq("isActive", true));
        criterion.add(Restrictions.eq("id",Long.parseLong(id)));
        TypeOfTreatise list = (TypeOfTreatise) criterion.uniqueResult();
        return list;
    }

    @Override
    public List<TypeOfTreatise> findAll() {
        Criteria criterion = getSession().createCriteria(TypeOfTreatise.class);
        criterion.add(Restrictions.eq("isActive", true));
        List<TypeOfTreatise> list = criterion.list();
        return list;
    }

    @Override
    public Long findByName(String awards_type) {
        Criteria criteria=getSession().createCriteria(TypeOfTreatise.class);
        criteria.add(Restrictions.eq("isActive",true));
        criteria.add(Restrictions.eq("Type_Treatise_Name",awards_type));
        TypeOfTreatise typeOfTreatise= (TypeOfTreatise) criteria.uniqueResult();
        return typeOfTreatise.getDept_Id();
    }

}
