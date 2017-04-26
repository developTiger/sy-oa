package com.sunesoft.lemon.syms.fileSys.application.dtos;

/**
 * Created by MJ006 on 2016/7/8.
 */
public class FilePathDto {
    private Long id;

    private String pathName;

    private String pathDesc;

    private Long belongDept;

    private Boolean isPublis;


    private Long parentId;


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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
