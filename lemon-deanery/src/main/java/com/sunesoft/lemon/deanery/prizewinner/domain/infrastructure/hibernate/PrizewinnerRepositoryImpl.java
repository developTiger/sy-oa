package com.sunesoft.lemon.deanery.prizewinner.domain.infrastructure.hibernate;

import com.sunesoft.lemon.deanery.prizewinner.domain.Prizewinner;
import com.sunesoft.lemon.deanery.prizewinner.domain.PrizewinnerRepository;
import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/6 0006.
 */
@Service(value = "prizewinnerRepository")
public class PrizewinnerRepositoryImpl extends GenericHibernateRepository<Prizewinner,Long> implements PrizewinnerRepository {
    @Override
    public List<Prizewinner> getPrizewinnerByCGName(Long cgId, String cgName) {
        StringBuffer sb = new StringBuffer(" from Prizewinner where IS_ACTIVE = 1 and cgId = ? and cgName = ? order by sortNo asc");
        Query query = this.getSession().createQuery(sb.toString());
        query.setLong(0,cgId);
        query.setString(1, cgName);
        return query.list();
    }

    @Override
    public String getPrizeWinnerNameInfos(Long cgId, String cgName) {
        StringBuffer sb = new StringBuffer();
        //TODO 当前写法为mysql写法
        if("Oracle10gDialect".equals(this.getDialectName())){
            sb.append("select wm_concat(a.real_name) real_name ");
            sb.append(" from (SELECT decode(t1.real_name, null, t.winner_id, t1.real_name) real_name ");
            sb.append("  FROM syy_oa_prizewinner t ");
            sb.append(" left join syy_oa_hr_employees t1 ");
            sb.append(" on t.winner_id = (t1.id || '') where t.IS_ACTIVE = 1 ");
            sb.append(" AND t.cg_id = '").append(cgId).append("' ");
            sb.append(" AND t.cg_name = '").append(cgName).append("'");
            sb.append(" order by t.sort_no asc) a ");
        }else if("MySQL5Dialect".equals(this.getDialectName())){
            sb.append("select GROUP_CONCAT(if(t1.real_name is NULL,t.winner_id,t1.real_name) ORDER BY t.sort_no ASC) real_name from syy_oa_prizewinner t LEFT JOIN syy_oa_hr_employees t1 ON t.winner_id = t1.id WHERE t.IS_ACTIVE = 1 ");
            sb.append(" AND t.cg_id = '").append(cgId).append("' ");
            sb.append(" AND t.cg_name = '").append(cgName).append("'");
        }
        Query query = this.getSession().createSQLQuery(sb.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<Map<String,Object>> list = query.list();
        if (list != null && list.size() > 0)
            return String.valueOf(list.get(0).get("real_name".toUpperCase()));
        else
            return "";
    }
}
