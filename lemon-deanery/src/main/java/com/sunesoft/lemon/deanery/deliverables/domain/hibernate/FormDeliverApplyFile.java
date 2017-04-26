package com.sunesoft.lemon.deanery.deliverables.domain.hibernate;

import com.sunesoft.lemon.fr.ddd.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by MJ003 on 2016/10/13.
 */
@Entity
@Table(name="syy_oa_form_apply_file_test")
public class FormDeliverApplyFile extends BaseEntity {
    @Column(name="file_name")
    private String fileName;

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

    @Column(name="file_id")
    private String fileId;
}
