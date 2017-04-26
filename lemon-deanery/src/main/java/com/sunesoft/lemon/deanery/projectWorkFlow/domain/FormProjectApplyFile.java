package com.sunesoft.lemon.deanery.projectWorkFlow.domain;

import com.sunesoft.lemon.fr.ddd.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by pxj on 2016/10/7.
 */
@Entity
@Table(name = "syy_oa_form_project_apply_file")
public class FormProjectApplyFile extends BaseEntity {
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    @Column(name="file_name")
    private String fileName;

    @Column(name="file_id")
    private String fileId;

}
