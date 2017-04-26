package com.sunesoft.lemon.deanery.car.domain.infrastructure.hibernate;

import com.sunesoft.lemon.deanery.car.application.criteria.CarReportCriteria;
import com.sunesoft.lemon.deanery.car.domain.Car;
import com.sunesoft.lemon.deanery.car.domain.CarRepository;
import com.sunesoft.lemon.deanery.carWorkFlow.domain.CarApply;
import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.hibernate.internal.SessionImpl;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.ListResourceBundle;
import java.util.Map;

/**
 * Created by wangwj on 2016/6/16 0016.
 */
@Service(value = "carRepository")
public class CarRepositoryImpl extends GenericHibernateRepository<Car,Long> implements CarRepository {
    /**
     * 车辆信息汇总统计报表
     * @param carReportCriteria 车辆信息汇总查询条件类
     * @return
     */
    @Override
    public List<Map<String, Object>> carReport(CarReportCriteria carReportCriteria) {
        StringBuffer sb = new StringBuffer();
//        System.out.println("-------------------------------------"+this.getDialectName());
        if("Oracle10gDialect".equals(this.getDialectName())){
            sb.append("SELECT a.*,b.name company_name FROM (SELECT t.company_id,t.car_type,decode(STATUS,1,'待命',2,'出车',3,'维修',4,'停运','') status,count(1) cartotal FROM syy_oa_carinfo t WHERE t.IS_ACTIVE = 1 ");
        }else if("MySQL5Dialect".equals(this.getDialectName())){
            sb.append("SELECT a.*,b.name company_name FROM (SELECT t.company_id,t.car_type,CASE WHEN t.status = 1 THEN '待命' WHEN t.status = 2 THEN '出车' WHEN t.status = 3 THEN '维修' WHEN t.status = 4 THEN '停运' END status,count(1) cartotal FROM syy_oa_carinfo t WHERE t.IS_ACTIVE = 1 ");
        }
//        sb.append("SELECT a.*,b.name company_name FROM (SELECT t.company_id,t.car_type,CASE WHEN t.status = 1 THEN '待命' WHEN t.status = 2 THEN '出车' WHEN t.status = 3 THEN '维修' WHEN t.status = 4 THEN '停运' END status,count(1) cartotal FROM syy_oa_carinfo t WHERE 1=1 ");
        if(carReportCriteria.getCompanyId() != null && carReportCriteria.getCompanyId() > 0){
            sb.append(" and t.company_id = '").append(carReportCriteria.getCompanyId()).append("' ");
        }
        if(carReportCriteria.getCarType() != null && !"".equals(carReportCriteria.getCarType())){
            sb.append(" and t.car_type = '").append(carReportCriteria.getCarType()).append("' ");
        }
        if(carReportCriteria.getStatus() != null){
            sb.append(" and t.STATUS = '").append(carReportCriteria.getStatus()).append("' ");
        }
        sb.append(" GROUP BY t.company_id,t.car_type,t. STATUS) a LEFT JOIN syy_oa_car_company b ON a.company_id = b.id");
        Query query = this.getSession().createSQLQuery(sb.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return query.list();
    }

    @Override
    public List<Map<String, Object>> carTypeList() {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT DISTINCT t.car_type from syy_oa_carinfo t where t.IS_ACTIVE = 1");
        Query query = this.getSession().createSQLQuery(sb.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return query.list();
    }

    @Override
    public List<Car> getAllcar() {
        Criteria criteria = getSession().createCriteria(Car.class);
        criteria.add(Restrictions.eq("isActive",true));
        criteria.add(Restrictions.eq("status",1));
        List<Car> list = criteria.list();
        if ( null == list || 0 == list.size() ){
            return null;
        }
        return list;
    }

    @Override
    public Car get(Long carId, Boolean isActive) {
        Criteria criteria = getSession().createCriteria(Car.class);
        criteria.add(Restrictions.eq("id",carId));
        criteria.add(Restrictions.eq("isActive",isActive));
        List<Car> list = new ArrayList<Car>();
        list = criteria.list();
        if (list.size() == 0){
            return null;
        }
        return list.get(0);
    }


}
