package com.sunesoft.lemon.deanery.car.domain.infrastructure.hibernate;

import com.sunesoft.lemon.deanery.car.domain.Car;
import com.sunesoft.lemon.deanery.car.domain.CarRepository;
import com.sunesoft.lemon.deanery.car.domain.ItemProject;
import com.sunesoft.lemon.deanery.car.domain.ItemProjectRepository;
import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by swb on 2016/12/16.
 */
@Service(value = "ItemProjectRepository")
public class ItemProjectRepositoryImpl extends GenericHibernateRepository<ItemProject,Long> implements ItemProjectRepository{

    @Override
    public List<ItemProject> itemsList() {
        Criteria criteria = getSession().createCriteria(ItemProject.class);
        criteria.add(Restrictions.eq("isActive",true));
        List<ItemProject> list = criteria.list();
        if(null == list || 0 == list.size()){
            return null;
        }
        return list;
    }

    @Override
    public ItemProject getItemsProject(Long itemsId) {
        ItemProject itemProject=(ItemProject) getSession().get(ItemProject.class,itemsId);
        return itemProject;
    }
}
