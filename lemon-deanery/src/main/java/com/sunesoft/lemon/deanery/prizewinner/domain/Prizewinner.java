package com.sunesoft.lemon.deanery.prizewinner.domain;

import com.sunesoft.lemon.fr.ddd.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Administrator on 2016/7/6 0006.
 */
@Entity
@Table(name = "syy_oa_prizewinner")
public class Prizewinner extends BaseEntity {
    /**
     * 所属成果表名
     * 例如：syy_oa_patent 专利
     */
    @Column(name = "cg_name",columnDefinition = "VARCHAR2(50)")
    private String cgName;
    /**
     * 所属成果表中对应成果的ID
     */
    @Column(name = "cg_id",columnDefinition = "NUMBER(19)")
    private Long cgId;
    /**
     * 获奖人信息，前端传过来的字符串信息
     * 0@1@id,0@2@id,1@3@张三,...
     * 第一个@前面的表示是否内部人员，后面的表示序号
     * 第二个@后面的表示人员的id或者姓名
     */
    @Column(name = "winner_infos",columnDefinition = "VARCHAR2(500)")
    private String winnerInfos;
    /**
     * 是否是本系统用户
     * 0:外部用户
     * 1:内部用户
     */
    @Column(name = "is_our_system",columnDefinition = "VARCHAR2(2)")
    private String isOurSystem;
    /**
     * 获奖人对应用户表中的ID，如果是外部人员则以中文名称进行存储
     */
    @Column(name = "winner_id",columnDefinition = "VARCHAR2(50)")
    private String winnerId;
    /**
     * 获奖人的排序
     */
    @Column(name = "sort_no",columnDefinition = "NUMBER(10)")
    private Integer sortNo;

    public String getCgName() {
        return cgName;
    }

    public void setCgName(String cgName) {
        this.cgName = cgName;
    }

    public Long getCgId() {
        return cgId;
    }

    public void setCgId(Long cgId) {
        this.cgId = cgId;
    }

    public String getWinnerInfos() {
        return winnerInfos;
    }

    public void setWinnerInfos(String winnerInfos) {
        this.winnerInfos = winnerInfos;
    }

    public String getIsOurSystem() {
        return isOurSystem;
    }

    public void setIsOurSystem(String isOurSystem) {
        this.isOurSystem = isOurSystem;
    }

    public String getWinnerId() {
        return winnerId;
    }

    public void setWinnerId(String winnerId) {
        this.winnerId = winnerId;
    }

    public Integer getSortNo() {
        return sortNo;
    }

    public void setSortNo(Integer sortNo) {
        this.sortNo = sortNo;
    }
}
