package com.sunesoft.lemon.syms.eHr.domain.notice;

import com.sunesoft.lemon.fr.ddd.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 新闻通知类型,
 * Created by xiazl on 2017/1/18.
 */
@Entity
@Table(name = "syy_oa_advice_type")
public class AdviceType extends BaseEntity {
    /**
     * 名称
     */
    @Column(name = "type_name")
    private String name;
    /**
     * 描述
     */
    private String description;
    /**
     * 是否禁用
     */
    private Boolean forbide;

    public AdviceType() {
        //todo 删除为false
        setIsActive(true);
        setCreateDateTime(new Date());
        setLastUpdateTime(new Date());
    }

    public Boolean getForbide() {
        return forbide;
    }

    public void setForbide(Boolean forbide) {
        this.forbide = forbide;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
