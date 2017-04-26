package com.sunesoft.lemon.syms.eHr.application.criteria;

import com.sunesoft.lemon.fr.results.PagedCriteria;

/**
 * Created by xiazl on 2016/6/30.
 */
public class HolidayInfoCriteria extends PagedCriteria{

    /**
     * 节假日名称
     */
    private String hname;

    /**
     * 节假日月份
     */
    private String hmonth;

    public String getHmonth() {
        return hmonth;
    }

    public void setHmonth(String hmonth) {
        this.hmonth = hmonth;
    }

    public String getHname() {
        return hname;
    }

    public void setHname(String hname) {
        this.hname = hname;
    }
}
