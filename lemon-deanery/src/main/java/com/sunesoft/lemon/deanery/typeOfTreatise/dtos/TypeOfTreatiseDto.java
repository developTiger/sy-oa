package com.sunesoft.lemon.deanery.typeOfTreatise.dtos;

import com.sunesoft.lemon.fr.results.PagedCriteria;

import java.util.Date;

/**
 * Created by pxj on 2016/12/15.
 */
public class TypeOfTreatiseDto extends PagedCriteria {

    private Long id;

    /**
     * 部门id
     */
    private Long dept_Id;

    /**
     * 论著类型名称
     */
    private String Type_Treatise_Name;

    /**
     * 部门名称
     */

    private String dept_Name;

    /**
     * false代表删除， true代表未删除
     */
    private Boolean isActive;


    /**
     * 创建时间
     */
    private Date createDateTime;

    /**
     *  修改时间
     */
    private Date lastUpdateTime;

    /**
     * 创建时间str
     */
    private String createDateTime_Str;

    /**
     *  修改时间str
     */
    private String lastUpdateTime_Str;

    public String getType_Treatise_Name() {
        return Type_Treatise_Name;
    }

    public void setType_Treatise_Name(String type_Treatise_Name) {
        Type_Treatise_Name = type_Treatise_Name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDept_Id() {
        return dept_Id;
    }

    public void setDept_Id(Long dept_Id) {
        this.dept_Id = dept_Id;
    }

    public String getDept_Name() {
        return dept_Name;
    }

    public void setDept_Name(String dept_Name) {
        this.dept_Name = dept_Name;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
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

    public String getCreateDateTime_Str() {
        return createDateTime_Str;
    }

    public void setCreateDateTime_Str(String createDateTime_Str) {
        this.createDateTime_Str = createDateTime_Str;
    }

    public String getLastUpdateTime_Str() {
        return lastUpdateTime_Str;
    }

    public void setLastUpdateTime_Str(String lastUpdateTime_Str) {
        this.lastUpdateTime_Str = lastUpdateTime_Str;
    }
}
