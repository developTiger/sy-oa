package com.sunesoft.lemon.syms.eHr.application.dtos;

import com.sunesoft.lemon.syms.eHr.domain.notice.NoticeType;

import java.util.Date;

/**
 * Created by zhouz on 2016/7/21.
 */
public class NoticeDto {

    private Long id;
    /**
     * 通知名称
     */
    private String noticeName;

    private String noticeDesc;
    /**
     * 通知类型(废弃)
     */
    private NoticeType noticeType;

    /**
     * 通知类型
     */
    private String adviceType;


    /**
     * 通知到部门
     */
    private String toDept;


    private String toDeptName;

    /**
     * 开始时间
     */
    private Date  sDate;


    /**
     * 结束时间
     */
    private Date  eDate;
    /**
     * 地址（培训）
     */
    private String locations;


    /**
     * 正文
     */
    private String content;

    /**
     * 文件Id
     */
    private String fileId;

    /**
     * 文件名称
     */
    private String filename;

    private Date createDateTime;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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


    public Date getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(Date createDateTime) {
        this.createDateTime = createDateTime;
    }

    public String getToDeptName() {
        return toDeptName;
    }

    public void setToDeptName(String toDeptName) {
        this.toDeptName = toDeptName;
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
