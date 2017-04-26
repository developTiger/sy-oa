package com.sunesoft.lemon.syms.eHr.domain.notice;

import com.sunesoft.lemon.fr.ddd.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 通知信息
 * Created by zhouz on 2016/7/21.
 */
@Entity
@Table(name="syy_oa_notice")
public class Notice extends BaseEntity {


    public Notice(){
        this.setCreateDateTime(new Date());
        this.setLastUpdateTime(new Date());
//        this.setsDate(new Date());
//        this.seteDate(new Date());
        this.setIsActive(true);
    }

    /**
     * 通知名称
     */
    @Column(name="notice_name")
    private String noticeName;

    @Column(name="notice_desc")
    private String noticeDesc;
    /**
     * 通知类型(废弃)
     */
    @Column(name="notice_type")
    private NoticeType noticeType;

    /**
     * 通知类型
     */
    @Column(name="advice_type")
    private String adviceType;

    /**
     * 通知到部门
     */
    @Column(name="toDept")
    private String toDept;


    /**
     * 开始时间
     */
    @Column(name = "s_date")
    private Date  sDate;


    /**
     * 结束时间
     */
    @Column(name = "e_date")
    private Date  eDate;

    /**
     * 地址（培训）
     */
    private String locations;


    /**
     * 正文
     */
    @Column(name = "content", columnDefinition = "CLOB DEFAULT NULL")
    private String content;


    /**
     * 文件Id
     */
    @Column(name="file_id")
    private String fileId;

    /**
     * 文件名称
     */
    @Column(name="file_name")
    private String filename;

    public String getNoticeName() {
        return noticeName;
    }

    public void setNoticeName(String noticeName) {
        this.noticeName = noticeName;
    }

    public NoticeType getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(NoticeType noticeType) {
        this.noticeType = noticeType;
    }

    public String getToDept() {
        return toDept;
    }

    public void setToDept(String toDept) {
        this.toDept = toDept;
    }

    public Date getsDate() {
        return sDate;
    }

    public void setsDate(Date sDate) {
        this.sDate = sDate;
    }

    public Date geteDate() {
        return eDate;
    }

    public void seteDate(Date eDate) {
        this.eDate = eDate;
    }

    public String getLocations() {
        return locations;
    }

    public void setLocations(String locations) {
        this.locations = locations;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getNoticeDesc() {
        return noticeDesc;
    }

    public void setNoticeDesc(String noticeDesc) {
        this.noticeDesc = noticeDesc;
    }

    public String getAdviceType() {
        return adviceType;
    }

    public void setAdviceType(String adviceType) {
        this.adviceType = adviceType;
    }
}
