package com.sunesoft.lemon.deanery.projectWorkFlow.domain;

import com.sunesoft.lemon.fr.ddd.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Created by swb on 2016/8/23.
 */
@Entity
@Table(name="syy_oa_form_proj_open_file")
public class FormOpenProjectFile extends BaseEntity {

    private String fileId;

    private String fileName;

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
