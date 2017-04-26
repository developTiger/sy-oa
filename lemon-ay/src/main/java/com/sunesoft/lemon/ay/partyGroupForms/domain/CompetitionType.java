package com.sunesoft.lemon.ay.partyGroupForms.domain;

import com.sunesoft.lemon.fr.ddd.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by admin on 2017/2/13.
 */
@Entity
@Table(name = "syy_oa_dq_workPro_tionType")
public class CompetitionType extends BaseEntity {

    public CompetitionType(){
        this.setCreateDateTime(new Date());
        this.setLastUpdateTime(new Date());
        this.setIsActive(true);
    }

    private String name;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
