package com.sunesoft.lemon.syms.uAuth.domain;

import com.sunesoft.lemon.fr.ddd.BaseEntity;
import com.sunesoft.lemon.syms.uAuth.application.dtos.PermissionGroupDto;

import javax.persistence.*;
import java.util.*;

/**
 * Created by zhouz on 2016/5/19.
 */
@Entity
@Table(name="syy_oa_sys_permit_group")
public class SysPermissionGroup  extends BaseEntity {

    public SysPermissionGroup(String name,Integer sort){
          this.name = name ;
          this.sort = sort;
    }

    public SysPermissionGroup(){
        this.setIsActive(true);
        this.setCreateDateTime(new Date());
        this.setLastUpdateTime(new Date());
        resources = Collections.emptyList();
    }

    /**
     * 组别分类
     */
    private String Type; //
    /**
     * 权限组名称
     */
    private String name; // 权限组名称

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "syy_oa_pmgroup_res", inverseJoinColumns = @JoinColumn(name = "res_id"), joinColumns = @JoinColumn(name = "group_id"))
    private List<SysResource> resources; // 组下所拥有的权限


//    @ManyToMany(fetch = FetchType.LAZY,mappedBy = "resource")
//    public Set<SysResource> gets(){
//        return resources;
//    }

    private Integer sort; // 排序

//    private List<Long> menuIds;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany
    @JoinColumn(name = "parent_res_id")
    private List<SysPermissionGroup> childPermissionGroup; // 子菜单


    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public List<SysResource> getResources() {
        return resources;
    }

    public void setResources(List<SysResource> resources) {
        this.resources = resources;
    }

    public List<SysPermissionGroup> getChildPermissionGroup() {
        return childPermissionGroup;
    }

    public void setChildPermissionGroup(List<SysPermissionGroup> childPermissionGroup) {
        this.childPermissionGroup = childPermissionGroup;
    }

//    public List<Long> getMenuIds() {
//        return menuIds;
//    }
//
//    public void setMenuIds(List<Long> menuIds) {
//        this.menuIds = menuIds;
//    }
}
