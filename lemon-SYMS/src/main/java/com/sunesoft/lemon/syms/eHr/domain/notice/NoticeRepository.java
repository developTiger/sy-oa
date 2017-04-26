package com.sunesoft.lemon.syms.eHr.domain.notice;

import com.sunesoft.lemon.syms.eHr.domain.empInfo.Employee;

import java.util.List;

/**
 * Created by zhouz on 2016/5/12.
 */
public interface NoticeRepository {

    /**
     * add or save
     * @param notice
     * @return
     */
    Long save(Notice notice);

    /**
     * delete
     * @param noticeId
     */
    void delete(Long noticeId);

    /**
     * get
     * @param nitice
     * @return
     */
    Notice get(Long nitice);

    /**
     * get list by ids
     * @param ids
     * @return
     */
    List<Notice> getByIds(List<Long> ids);
}
