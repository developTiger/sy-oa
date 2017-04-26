package com.sunesoft.lemon.deanery.car.domain;

import java.util.List;

/**
 * Created by swb on 2016/12/16.
 */
public interface ItemProjectRepository {
    Long save(ItemProject itemProject);

    void delete(Long itemsId);

    List<ItemProject> itemsList();

    ItemProject getItemsProject(Long itemsId);
}
