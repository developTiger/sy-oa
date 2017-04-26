package com.sunesoft.lemon.syms.fileSys.application.dtos;

import com.sunesoft.lemon.syms.fileSys.domain.enums.DocType;

import java.util.UUID;

/**
 * Created by zhouz on 2016/7/12.
 */
public class FileDto {

    private UUID id;

    private Long filePathId;

    /**
     * 文件类型
     */
    private DocType docType;
    /**
     * 是否公开
     */
    private Boolean isPublic;
    /**
     * 部门
     * dept_1
     */
    private Long deptId;
    /**
     * 用户
     * user_1
     */
    private Long userId;


    /**
     * 文件名
     */
    private String fileName;


    private String resourceId;


    private String userDefineType;


    private String uuidName;

//    private UUID


    /**
     * 扩展名
     */
    private String extensions;

    public Long getFilePathId() {
        return filePathId;
    }

    public void setFilePathId(Long filePathId) {
        this.filePathId = filePathId;
    }

    public DocType getDocType() {
        return docType;
    }

    public void setDocType(DocType docType) {
        this.docType = docType;
    }

    public Boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getUserDefineType() {
        return userDefineType;
    }

    public void setUserDefineType(String userDefineType) {
        this.userDefineType = userDefineType;
    }

    public String getUuidName() {
        return uuidName;
    }

    public void setUuidName(String uuidName) {
        this.uuidName = uuidName;
    }

    public String getExtensions() {
        return extensions;
    }

    public void setExtensions(String extensions) {
        this.extensions = extensions;
    }
}
