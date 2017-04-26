package com.sunesoft.lemon.syms.hrForms.application.criteria;

import com.sunesoft.lemon.fr.results.PagedCriteria;
import com.sunesoft.lemon.syms.hrForms.application.Dtos.FormEvectionDto;

import java.util.Date;

/**
 * Created by kkk on 2017/2/17.
 */
public class EvectionCriteria extends PagedCriteria {


    //申请人姓名
    private String applyerName;

    //出差目的
    private String target;
    //申请时间
    private Date applyTime;
    //出差开始时间
    private Date fromTime;
    //出差结束时间
    private Date toTime;

    private Long deptId;

    public String getApplyerName() {
        return applyerName;
    }

    public void setApplyerName(String applyerName) {
        this.applyerName = applyerName;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public Date getFromTime() {
        return fromTime;
    }

    public void setFromTime(Date fromTime) {
        this.fromTime = fromTime;
    }

    public Date getToTime() {
        return toTime;
    }

    public void setToTime(Date toTime) {
        this.toTime = toTime;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }
}
