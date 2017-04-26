package com.sunesoft.lemon.deanery.car.domain.infrastructure.hibernate;

import com.sunesoft.lemon.deanery.car.application.criteria.CarReportCriteria;
import com.sunesoft.lemon.deanery.car.domain.Car;
import com.sunesoft.lemon.deanery.car.domain.CarRepository;
import com.sunesoft.lemon.deanery.car.domain.SpecialtyType;
import com.sunesoft.lemon.deanery.car.domain.SpectityTypeRepository;
import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by wangwj on 2016/6/16 0016.
 */
@Service(value = "spectityTypeRepository")
public class SpectityTypeRepositoryImpl extends GenericHibernateRepository<SpecialtyType,Long> implements SpectityTypeRepository {

    @Override
    public List<SpecialtyType> getAllType() {
        String hql = " from SpecialtyType where isActive=1 ";
        Query query = getSession().createQuery(hql);
        return query.list();
    }
}
