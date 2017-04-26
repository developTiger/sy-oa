package com.sunesoft.lemon.webapp.model;

import com.sunesoft.lemon.fr.results.ListResult;
import com.sunesoft.lemon.syms.fileSys.application.dtos.FileInfoDto;
import com.sunesoft.lemon.syms.fileSys.application.dtos.FilePathDto;

import java.util.List;

/**
 * Created by zhouz on 2016/7/13.
 */
public class SraFile {
    private Long parentId;

    private Boolean canCreateFloder;

    private String path;
    private List<FilePathDto> floders;

    private List<FileInfoDto> files;

    public List<FilePathDto> getFloders() {
        return floders;
    }

    public void setFloders(List<FilePathDto> floders) {
        this.floders = floders;
    }

    public List<FileInfoDto> getFiles() {
        return files;
    }

    public void setFiles(List<FileInfoDto> files) {
        this.files = files;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Boolean getCanCreateFloder() {
        return canCreateFloder;
    }

    public void setCanCreateFloder(Boolean canCreateFloder) {
        this.canCreateFloder = canCreateFloder;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
