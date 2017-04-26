package com.sunesoft.lemon.syms.eHr.domain.attendance;

import com.sunesoft.lemon.fr.ddd.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by temp on 2016/6/28.
 */
@Entity
@Table(name="syy_oa_hr_att_rule")
public class  AttendanceRuleSet extends BaseEntity{

    /**
     * 年
     */
    private Integer year;

    /**
     * 月
     */
    private Integer month;


    @OneToMany(fetch = FetchType.LAZY,cascade={CascadeType.ALL,CascadeType.PERSIST})
    @JoinColumn(name="rule_set_id")
    private List<RuleDate> ruleDates;

    public AttendanceRuleSet() {
        this.setLastUpdateTime(new Date());
        this.setCreateDateTime(new Date());
        this.setIsActive(true);

       this.ruleDates=new ArrayList<RuleDate>();
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public List<RuleDate> getRuleDates() {
        return ruleDates;
    }

    public void setRuleDates(List<RuleDate> ruleDates) {
        this.ruleDates = ruleDates;
    }
}
