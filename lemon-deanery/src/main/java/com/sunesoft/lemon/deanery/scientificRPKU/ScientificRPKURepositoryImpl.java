package com.sunesoft.lemon.deanery.scientificRPKU;

import com.sunesoft.lemon.deanery.productionData.ProductionDate;
import com.sunesoft.lemon.deanery.productionData.ProductionDateRepository;
import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import com.sunesoft.lemon.fr.utils.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zy on 2016/6/17 0017.
 */
@Service(value = "scientificRPKURepository")
public class ScientificRPKURepositoryImpl extends GenericHibernateRepository<ScientificRPKU,Long> implements ScientificRPKURepository {


    /**
     * 查询立项完成数据
     * @param number
     * @return
     */
    @Override
    public List<ScientificRPKU> queryProjectApprove(String number) {
        Criteria criteria=this.getSession().createCriteria(ScientificRPKU.class);
        if (!StringUtils.isNullOrWhiteSpace(number)){
            criteria.add(Restrictions.eq("projectNo",number));
        }else {
            criteria.add(Restrictions.eq("projectStatus","2"));
            criteria.add(Restrictions.eq("projectKTStatus","0"));
        }
        List<ScientificRPKU> list=criteria.list();
        return list;
    }
}
