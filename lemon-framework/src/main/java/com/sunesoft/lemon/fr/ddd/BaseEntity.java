package com.sunesoft.lemon.fr.ddd;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by 中浩 on 2016/4/26.
 */
@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="syy_oa_id_seq")
    @SequenceGenerator(name="syy_oa_id_seq", sequenceName="syy_oa_seq_id",allocationSize = 1)
//    @GeneratedValue(strategy= GenerationType.IDENTITY)
//    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "is_active")
    private Boolean isActive;
    //private String createBy;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_datetime")
    private Date createDateTime; // 创建时间
   // private String lastUpdateBy;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_update_time")
    private Date lastUpdateTime; // 最后修改时间

    public Long getId() {
        return id;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public void markAsRemoved() {
        isActive = false;
    }

    public Date getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(Date createDateTime) {
        this.createDateTime = createDateTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
}
