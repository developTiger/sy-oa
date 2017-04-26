package com.sunesoft.lemon.syms.fileSys.domain;

import com.sunesoft.lemon.fr.ddd.BaseEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by zhouz on 2016/7/6.
 */
@Entity
@Table(name="syy_oa_file_path")
public class FilePath extends BaseEntity {

    private String pathName;

    private String pathDesc;


    private Long belongDept;

    private Boolean isPublis;

    private Boolean hasParent;

    @ManyToOne
    @JoinColumn(name="parent_id")
    private FilePath parent;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL, CascadeType.PERSIST})
    @JoinColumn(name="parent_id")
    private List<FilePath> childPaths;

    public FilePath(){
        this.setIsActive(true);
        this.setCreateDateTime(new Date());
        this.setLastUpdateTime(new Date());

    }

    public Boolean getHasParent() {
        return hasParent;
    }

    public void setHasParent(Boolean hasParent) {
        this.hasParent = hasParent;
    }

    public String getPathName() {
        return pathName;
    }

    public void setPathName(String pathName) {
        this.pathName = pathName;
    }

    public String getPathDesc() {
        return pathDesc;
    }

    public void setPathDesc(String pathDesc) {
        this.pathDesc = pathDesc;
    }

    public List<FilePath> getChildPaths() {
        return childPaths;
    }

    public void setChildPaths(List<FilePath> childPaths) {
        this.childPaths = childPaths;
    }

    public Long getBelongDept() {
        return belongDept;
    }

    public void setBelongDept(Long belongDept) {
        this.belongDept = belongDept;
    }

    public Boolean getIsPublis() {
        return isPublis;
    }

    public void setIsPublis(Boolean isPublis) {
        this.isPublis = isPublis;
    }

    public FilePath getParent() {
        return parent;
    }

    public void setParent(FilePath parent) {
        this.parent = parent;
    }
}
