package com.sunesoft.lemon.syms.fileSys.application.criteria;

import com.sunesoft.lemon.fr.results.PagedCriteria;

import java.util.UUID;

/**
 * Created by zhouz on 2016/7/6.
 */
public class FileCriteria extends PagedCriteria{


    private Boolean isPublic;


    private String id;

    private Long userId;

    private Long deptId;

    private Long resourceId;

    private String userDefineType;

    private Long filePathId;

    private String fileName;

    private String keywords;

    protected String extensions;

    public Long getFilePathId() {
        return filePathId;
    }

    public void setFilePathId(Long filePathId) {
        this.filePathId = filePathId;
    }

    public Boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Boolean getPublic() {
        return isPublic;
    }

    public void setPublic(Boolean aPublic) {
        isPublic = aPublic;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public String getUserDefineType() {
        return userDefineType;
    }

    public void setUserDefineType(String userDefineType) {
        this.userDefineType = userDefineType;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getExtensions() {
        return extensions;
    }

    public void setExtensions(String extensions) {
        this.extensions = extensions;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
