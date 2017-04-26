package com.sunesoft.lemon.deanery.productionData;

import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import org.springframework.stereotype.Service;

/**
 * Created by zy on 2016/6/17 0017.
 */
@Service(value = "productionDateRepository")
public class ProductionDateRepositoryImpl extends GenericHibernateRepository<ProductionDate,Long> implements ProductionDateRepository {

}
