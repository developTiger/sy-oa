package com.sunesoft.lemon.deanery.car.domain.infrastructure.hibernate;

import com.sunesoft.lemon.deanery.car.application.criteria.DriverReportCriteria;
import com.sunesoft.lemon.deanery.car.domain.Car;
import com.sunesoft.lemon.deanery.car.domain.Driver;
import com.sunesoft.lemon.deanery.car.domain.DriverRepository;
import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateFinder;
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
 * Created by xubo on 2016/6/17 0017.
 */
@Service(value="driverRepository")
public class DriverRepositoryImpl
        extends GenericHibernateRepository<Driver,Long> implements DriverRepository{

    /**
     * 司机信息汇总
     * @param driverReportCriteria
     * @return
     */
    @Override
    public List<Map<String, Object>> driverReport(DriverReportCriteria driverReportCriteria) {
        StringBuffer sb = new StringBuffer();
        if("Oracle10gDialect".equals(this.getDialectName())){
            sb.append("SELECT a.*,b.name company_name from (SELECT t.company_id,t.driver_doctype,decode(t.status,1,'待命',2,'出车',3,'离岗','') status,count(1) drivertotal from syy_oa_driverinfo t WHERE t.IS_ACTIVE = 1 ");
        }else if("MySQL5Dialect".equals(this.getDialectName())){
            sb.append("SELECT a.*,b.name company_name from (SELECT t.company_id,t.driver_doctype,CASE WHEN t.status = 1 THEN '待命' WHEN t.status = 2 THEN '出车' WHEN t.status = 3 THEN '离岗' END status,count(1) drivertotal from syy_oa_driverinfo t WHERE t.IS_ACTIVE = 1 ");
        }
        if(driverReportCriteria.getCompanyId() != null && driverReportCriteria.getCompanyId() > 0){
            sb.append(" and t.company_id = '").append(driverReportCriteria.getCompanyId()).append("' ");
        }
        if(driverReportCriteria.getStatus() != null){
            sb.append(" and t.status = '").append(driverReportCriteria.getStatus()).append("' ");
        }
        if(driverReportCriteria.getDriverDocType() != null && !"".equals(driverReportCriteria.getDriverDocType())){
            sb.append(" and t.driver_doctype = '").append(driverReportCriteria.getDriverDocType()).append("' ");
        }
        sb.append(" GROUP BY t.company_id,t.driver_doctype,t.status) a LEFT JOIN syy_oa_car_company b on a.company_id = b.id");
        Query query = this.getSession().createSQLQuery(sb.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return query.list();
    }

    @Override
    public List<Driver> getAlldriver() {
        Criteria criteria = getSession().createCriteria(Driver.class);
        criteria.add(Restrictions.eq("isActive",true));
        criteria.add(Restrictions.eq("status",1));
        List<Driver> list = criteria.list();
        if ( null == list || 0 == list.size() ){
            return null;
        }
        return list;
    }

    @Override
    public Driver get(Long driverId, Boolean isActive) {
        Criteria criteria = getSession().createCriteria(Driver.class);
        criteria.add(Restrictions.eq("id",driverId));
        criteria.add(Restrictions.eq("isActive",isActive));
        List<Driver> list = new ArrayList<Driver>();
        list = criteria.list();
        if (list.size() == 0){
            return null;
        }
        return list.get(0);
    }
}
