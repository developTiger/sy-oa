package com.sunesoft.lemon.deanery.treatiseCG.domain.infrastructure.hibernate;

import com.sunesoft.lemon.deanery.treatiseCG.application.dtos.TreatiseDto;
import com.sunesoft.lemon.deanery.treatiseCG.domain.Treatise;
import com.sunesoft.lemon.deanery.treatiseCG.domain.TreatiseRepository;
import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import com.sunesoft.lemon.syms.workflow.domain.enums.FormStatus;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by xubo on 2016/7/6 0006.
 * 实现论著数仓借口
 */
@Service("treatiseRepository")
public class TreatiseRepositoryImpl
        extends GenericHibernateRepository<Treatise,Long>
        implements TreatiseRepository{
//    @Override
//    public Treatise update(TreatiseDto treatiseDto) {
//        String hql =
//                "update Treatise w set w.create_datetime=? w.is_active=? w.last_update_time=? w.is_core=? w.is_cooperate=? w.make_no = ? w.publish_time = ? w.treatise_level =? w.treatise_press= ? w.unit = ? " +
//                "where w.id =?";
//        Query query = this.getSession().createQuery(hql);
//        query.setLong(0,treatiseDto.getId());
//        query.setBoolean(0,treatiseDto.getIs_Core());
//        query.setBoolean(0,treatiseDto.getIs_cooperate());
//        query.setString(0,treatiseDto.getMake_No());
//        query.setString(0,treatiseDto.getTreatise_Level());
//        query.setString(0,treatiseDto.getTreatise_Name());
//        query.setString(0,treatiseDto.getTreatise_Press());
//        query.setString(0,treatiseDto.getUnit());
//        return (Treatise)query.uniqueResult();
//    }

    /**
     * 论著统计查询
     * 目前根据论著级别、是否核心、是否合著
     * treatise_level 论著级别
     * is_core 是否核心
     * is_cooperate 是否合著
     * treatise_num 论著数量
     * @return
     */
    @Override
    public List<Map<String, Object>> treatiseReport() {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT t.treatise_level,t.is_core,t.is_cooperate,count(1) treatise_num FROM syy_oa_treatise t where t.IS_ACTIVE = 1 GROUP BY t.treatise_level,t.is_core,t.is_cooperate");
        Query query = this.getSession().createSQLQuery(sb.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return query.list();
    }

    @Override
    public List<Treatise> getAllTreatise() {
        String hql = "from Treatise";
        Query query = getSession().createQuery(hql);
        return query.list();
    }

    @Override
    public List<Treatise> findTreatise(TreatiseDto treatiseDto) {
        Criteria criterion = getSession().createCriteria(Treatise.class);
        criterion.add(Restrictions.eq("isActive", true));
        criterion.add(Restrictions.eq("treatise_Name",treatiseDto.getTreatise_Name()));
        criterion.add(Restrictions.eq("applyer",treatiseDto.getApplyer()));
        List<Treatise> list = criterion.list();
        return list;
    }
}
