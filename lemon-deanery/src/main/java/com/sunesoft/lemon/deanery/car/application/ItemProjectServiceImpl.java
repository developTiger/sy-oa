package com.sunesoft.lemon.deanery.car.application;

import com.sunesoft.lemon.deanery.car.application.criteria.ItemProjectCriteria;
import com.sunesoft.lemon.deanery.car.application.dtos.DriverDto;
import com.sunesoft.lemon.deanery.car.domain.ItemProject;
import com.sunesoft.lemon.deanery.car.domain.ItemProjectRepository;
import com.sunesoft.lemon.deanery.car.domain.SpecialtyType;
import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateFinder;
import com.sunesoft.lemon.fr.results.PagedResult;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by swb on 2016/12/16.
 */
@Service("ItemProjectService")
public class ItemProjectServiceImpl extends GenericHibernateFinder implements ItemProjectService {
    @Autowired
    ItemProjectRepository itemProjectRepository;
    @Override
    public Long save(ItemProject itemProject) {
        itemProject.setIsActive(true);
        return itemProjectRepository.save(itemProject);
    }

    @Override
    public void delete(Long itemsId) {
        itemProjectRepository.delete(itemsId);
    }

    @Override
    public List<ItemProject> itemsList() {
        return itemProjectRepository.itemsList();
    }

    @Override
    public void update(Long itemsId) {
        ItemProject itemProject=itemProjectRepository.getItemsProject(itemsId);
        itemProject.setItemName(itemProject.getItemName());
        itemProjectRepository.save(itemProject);
    }

    @Override
    public PagedResult<ItemProject> getType(ItemProjectCriteria itemProjectCriteria) {
        Criteria criterion = getSession().createCriteria(ItemProject.class);
        criterion.add(Restrictions.eq("isActive", true));
        if(itemProjectCriteria.getItemName()!=null&&!"".equals(itemProjectCriteria.getItemName())){
            criterion.add(Restrictions.like("itemName","%"+ itemProjectCriteria.getItemName()+"%"));
        }
        int totalCount = ((Long)criterion.setProjection(Projections.rowCount()).uniqueResult()).intValue();
        criterion.setProjection(null);
        criterion.setFirstResult((itemProjectCriteria.getPageNumber() - 1) * itemProjectCriteria.getPageSize()).setMaxResults(itemProjectCriteria.getPageSize());
        List<ItemProject> itemProjects = criterion.list();
        return new PagedResult<ItemProject>(itemProjects,itemProjectCriteria.getPageNumber(),itemProjectCriteria.getPageSize(),totalCount);
    }

    @Override
    public boolean deletes(Long[] ids)
    {
        Criteria criterion = getSession().createCriteria(ItemProject.class);
        if (ids!=null&&ids.length>0) {
            criterion.add(Restrictions.in("id", ids));
        }
        List<ItemProject> beans = criterion.list();
        for (ItemProject bean : beans) {
            bean.setIsActive(false);
            itemProjectRepository.save(bean);
        }
        return true;
    }

    @Override
    public List<ItemProject> getType(Long id) {
        Criteria criteria = getSession().createCriteria(ItemProject.class);
        criteria.add(Restrictions.eq("id", id));
        criteria.add(Restrictions.eq("isActive",true));
        List<ItemProject> list = criteria.list();
        return list;
}
}
