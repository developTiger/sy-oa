package com.sunesoft.lemon.syms.eHr.application.dtos;


/**
 * Created by xiazl on 2017/1/18.
 */
public class RoleTypeDto {

    private Long id;

    private Boolean isActive;

    /**
     * 名称
     */
    private String name;
    /**
     * 描述
     */
    private String description;
    /**
     * 是否禁用
     */
    private Boolean forbide;

    private String slastUpdateTime; // 最后修改时间

    private String screateDateTime; // 创建时间

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getForbide() {
        return forbide;
    }

    public void setForbide(Boolean forbide) {
        this.forbide = forbide;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScreateDateTime() {
        return screateDateTime;
    }

    public void setScreateDateTime(String screateDateTime) {
        this.screateDateTime = screateDateTime;
    }

    public String getSlastUpdateTime() {
        return slastUpdateTime;
    }

    public void setSlastUpdateTime(String slastUpdateTime) {
        this.slastUpdateTime = slastUpdateTime;
    }
}
