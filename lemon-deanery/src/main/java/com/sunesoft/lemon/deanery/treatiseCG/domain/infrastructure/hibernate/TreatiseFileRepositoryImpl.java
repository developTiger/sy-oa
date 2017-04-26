package com.sunesoft.lemon.deanery.treatiseCG.domain.infrastructure.hibernate;

import com.sunesoft.lemon.deanery.treatiseCG.domain.TreatiseFile;
import com.sunesoft.lemon.deanery.treatiseCG.domain.TreatiseFileRepository;
import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by pxj on 2016/9/26.
 */
@Service("TreatiseFileRepository")
public class TreatiseFileRepositoryImpl extends GenericHibernateRepository<TreatiseFile,Long>
        implements TreatiseFileRepository {
    /**
     * 查询文件
     * @param  id    treatiseFileid
     * @param syy_oa_treatise_file
     * @return
     */
    @Override
    public List<TreatiseFile> getTreatiseFileByTreatiseFileID(Long id) {
        Criteria criteria=getSession().createCriteria(TreatiseFile.class);
        criteria.add(Restrictions.eq("treatiseFileid",id));
        List<TreatiseFile> treatiseFile=criteria.list();
        return treatiseFile;
    }
}
