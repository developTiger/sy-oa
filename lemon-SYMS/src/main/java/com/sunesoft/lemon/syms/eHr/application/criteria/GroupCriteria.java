package com.sunesoft.lemon.syms.eHr.application.criteria;

import com.sunesoft.lemon.fr.results.PagedCriteria;

/**
 * Created by xiazl on 2016/6/16.
 */
public class GroupCriteria extends PagedCriteria {
    /**
     * 员工组名
     */
    private String name;//员工组名

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
