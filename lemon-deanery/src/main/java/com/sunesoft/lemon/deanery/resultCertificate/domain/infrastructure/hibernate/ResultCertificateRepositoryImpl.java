package com.sunesoft.lemon.deanery.resultCertificate.domain.infrastructure.hibernate;

import com.sunesoft.lemon.deanery.resultCertificate.application.dtos.ResultCertificateDto;
import com.sunesoft.lemon.deanery.resultCertificate.domain.ResultCertificate;
import com.sunesoft.lemon.deanery.resultCertificate.domain.ResultCertificateRepository;
import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by pxj on 2016/9/28.
 */
@Service("resultCertificateRepository")
public class ResultCertificateRepositoryImpl  extends GenericHibernateRepository<ResultCertificate,Long> implements ResultCertificateRepository {
    @Override
    public ResultCertificate getByFormNo(Long formNo) {
        Criteria criteria=getSession().createCriteria(ResultCertificate.class);
        criteria.add(Restrictions.eq("formNo",formNo));
        criteria.add(Restrictions.eq("isActive",true));
       // List<ResultCertificate> list = criteria.list();
        return (ResultCertificate)criteria.uniqueResult();
    }

    @Override
    public ResultCertificate get(long l) {
        Criteria criteria=getSession().createCriteria(ResultCertificate.class);
        criteria.add(Restrictions.eq("id",l));
        criteria.add(Restrictions.eq("isActive",true));
       // List<ResultCertificate> list = criteria.list();
        return (ResultCertificate)criteria.uniqueResult();
    }

    @Override
    public List<ResultCertificate> findResultCertificate(ResultCertificateDto resultCertificateDto) {
        Criteria criteria=getSession().createCriteria(ResultCertificate.class);
        criteria.add(Restrictions.eq("isActive",true));
        criteria.add(Restrictions.eq("win_Result_Name",resultCertificateDto.getWin_Result_Name()));
        criteria.add(Restrictions.eq("applyer",resultCertificateDto.getApplyer()));
        List<ResultCertificate> list=criteria.list();
        return list;
    }

    /**
     *  成果证书按applerid查询
     * @param id
     * @return
     */
    @Override
    public List<ResultCertificate> query_ByApperId(long id) {
        Criteria criteria=getSession().createCriteria(ResultCertificate.class);
        criteria.add(Restrictions.eq("isActive",true));
        criteria.add(Restrictions.eq("applyer", id));
        criteria.add(Restrictions.eq("isComplete",true));
        List<ResultCertificate> list=criteria.list();
        return list;
    }
}
