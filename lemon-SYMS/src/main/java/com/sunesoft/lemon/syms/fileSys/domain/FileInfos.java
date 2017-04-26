package com.sunesoft.lemon.syms.fileSys.domain;

import com.sunesoft.lemon.fr.ddd.BaseEntity;
import com.sunesoft.lemon.syms.eHr.domain.dept.Deptment;
import com.sunesoft.lemon.syms.fileSys.domain.enums.DocType;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.InputStream;

/**
 * Created by zhouz on 2016/6/25.
 */

@Entity
@Table(name="syy_oa_file_info")
public class FileInfos extends BaseEntity {

//    private Long fileId;
    /**
     * 文件类型
     */
    private DocType docType;


    /**
     * 是否公开
     */
    private Boolean isPublic;

    /**
     * 扩展名
     */
    private String extensions;
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


    /**
     * 文件路径
     */
    @ManyToOne
    @JoinColumn(name="file_path_id")
    private FilePath filePath;

//
//    private InputStream inputStream;

//    public Long getFileId() {
//        return fileId;
//    }
//
//    public void setFileId(Long fileId) {
//        this.fileId = fileId;
//    }

    public Boolean getPublic() {
        return isPublic;
    }

//    public InputStream getInputStream() {
//        return inputStream;
//    }
//
//    public void setInputStream(InputStream inputStream) {
//        this.inputStream = inputStream;
//    }

    public void setPublic(Boolean aPublic) {
        isPublic = aPublic;
    }

    public DocType getDocType() {
        return docType;
    }

    public void setDocType(DocType docType) {
        this.docType = docType;
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

    public Boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }


    public String getExtensions() {
        return extensions;
    }

    public void setExtensions(String extensions) {
        this.extensions = extensions;
    }

    public FilePath getFilePath() {
        return filePath;
    }

    public void setFilePath(FilePath filePath) {
        this.filePath = filePath;
    }
}
