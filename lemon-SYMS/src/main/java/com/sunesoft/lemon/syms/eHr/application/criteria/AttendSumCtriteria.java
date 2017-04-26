package com.sunesoft.lemon.syms.eHr.application.criteria;

import com.sunesoft.lemon.fr.results.PagedCriteria;
import org.apache.poi.hslf.dev.PPDrawingTextListing;


/**
 * Created by xiazl on 2017/3/4.
 */
public class AttendSumCtriteria extends PagedCriteria {
    private String begin;

    private String end;

    private Long deptId;
    private String name;

    public String getBegin() {
        return begin;
    }

    public void setBegin(String begin) {
        this.begin = begin;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
