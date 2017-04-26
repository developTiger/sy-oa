package com.sunesoft.lemon.deanery.car.application.criteria;

import com.sunesoft.lemon.fr.results.PagedCriteria;

/**
 * Created by jiangkefan on 2016/6/20.
 */
public class CompanyCriteria  extends PagedCriteria {

    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
