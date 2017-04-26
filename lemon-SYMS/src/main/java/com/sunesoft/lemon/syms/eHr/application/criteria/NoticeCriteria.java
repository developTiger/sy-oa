package com.sunesoft.lemon.syms.eHr.application.criteria;

import com.sunesoft.lemon.fr.results.PagedCriteria;
import com.sunesoft.lemon.syms.eHr.domain.notice.NoticeType;

/**
 * Created by zhouz on 2016/7/21.
 */
public class NoticeCriteria  extends PagedCriteria {

    /**
     * 通知名称
     */
    private String noticeName;

    /**
     * 通知类型
     */
    private NoticeType noticeType;

    public NoticeType getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(NoticeType noticeType) {
        this.noticeType = noticeType;
    }

    public String getNoticeName() {
        return noticeName;
    }

    public void setNoticeName(String noticeName) {
        this.noticeName = noticeName;
    }
}
