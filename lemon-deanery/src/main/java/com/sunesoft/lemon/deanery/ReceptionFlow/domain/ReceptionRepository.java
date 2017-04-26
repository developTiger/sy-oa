package com.sunesoft.lemon.deanery.ReceptionFlow.domain;

/**
 * Created by wangwj on 2016/8/2 0002.
 */
public interface ReceptionRepository {
    Long save(ReceptionNB receptionNB);
    void delete(Long id);
    ReceptionNB get(Long id);
    ReceptionNB getByFormNo(Long formNo);
}
