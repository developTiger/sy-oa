package com.sunesoft.lemon.deanery.car.application;

import com.sunesoft.lemon.deanery.car.application.criteria.ItemProjectCriteria;
import com.sunesoft.lemon.deanery.car.domain.ItemProject;
import com.sunesoft.lemon.fr.results.PagedResult;

import java.util.List;

/**
 * Created by swb on 2016/12/16.
 */
public interface ItemProjectService {
    Long save(ItemProject itemProject);

    void delete(Long itemsId);

    boolean deletes(Long[] ids);

    List<ItemProject> getType(Long id);

    List<ItemProject> itemsList();

    void update(Long itemsId);

    PagedResult<ItemProject> getType( ItemProjectCriteria itemProjectCriteria);
}
