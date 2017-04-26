package com.sunesoft.lemon.deanery.car.domain;

import com.sunesoft.lemon.fr.ddd.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by swb on 2016/12/16.
 */
@Entity
@Table(name = "syy_oa_itemsproject")
public class ItemProject extends BaseEntity{
    @Column(name = "items_id",columnDefinition = "NUMBER(19)")
    private Long itemsId;

    public Long getItemsId() {
        return itemsId;
    }

    public void setItemsId(Long itemsId) {
        this.itemsId = itemsId;
    }

    @Column(name = "item_name",
            columnDefinition = "VARCHAR2(50)")
    private String itemName;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public ItemProject(Long itemsId, String itemName) {
        this.itemsId = itemsId;
        this.itemName = itemName;
    }

    public ItemProject() {
    }
}
