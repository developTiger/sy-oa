package com.sunesoft.lemon.deanery.car.application.criteria;

import com.sunesoft.lemon.fr.ddd.BaseEntity;
import com.sunesoft.lemon.fr.results.PagedCriteria;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by swb on 2016/12/16.
 */
public class ItemProjectCriteria extends PagedCriteria {

    private String itemName;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
