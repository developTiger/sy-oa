package com.sunesoft.lemon.deanery.car.domain.infrastructure.hibernate;

import com.sunesoft.lemon.deanery.car.domain.Company;
import com.sunesoft.lemon.deanery.car.domain.CompanyRepository;
import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import org.hibernate.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Queue;

/**
 * Created by Administrator on 2016/6/16 0016.
 */
@Service(value = "companyRepository")
public class CompanyRepositoryImpl extends GenericHibernateRepository<Company,Long> implements CompanyRepository {

    @Override
    public List<Company> getAllcoms() {
        String hql = " from Company where isActive=1 ";
        Query query = getSession().createQuery(hql);
        return query.list();
    }
}
