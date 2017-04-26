package com.sunesoft.lemon.deanery.patentCG.domain.infrastructure.hibernate;

import com.sunesoft.lemon.deanery.patentCG.domain.Patent;
import com.sunesoft.lemon.deanery.patentCG.domain.PatentRepository;
import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by xubo on 2016/7/6 0006.
 * 实现专利数据仓库借口
 */
@Service("patentRepository")
public class PatentRepositoryImpl
        extends GenericHibernateRepository<Patent,Long>
        implements PatentRepository{
    /**
     * 专利统计查询
     * 目前根据专利类型统计查询
     * patent_type 专利类型
     * patentnum 专利数量
     * @return
     */
    @Override
    public List<Map<String, Object>> patentReport() {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT t.patent_type,count(1) patentnum FROM syy_oa_patent t where t.IS_ACTIVE = 1 GROUP BY t.patent_type");
        Query query = this.getSession().createSQLQuery(sb.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return query.list();
    }


    @Override
    public List<Patent> getAllPatents() {
        String hql = "from Patent";
        Query query = getSession().createQuery(hql);
        List<Patent> list = query.list();
        return list;
    }

    @Override
    public Long findPatentId(Long formNo) {
        String hq= "from Patent  WHERE FORM_NO=?";
        Query query=getSession().createQuery(hq);
        query.setParameter(0,formNo);
        Patent list = (Patent) query.uniqueResult();
        Long id= list.getId();
        return id;
    }



}
