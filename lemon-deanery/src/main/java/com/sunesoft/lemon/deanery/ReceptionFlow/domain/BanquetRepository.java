package com.sunesoft.lemon.deanery.ReceptionFlow.domain;

import java.util.List;

/**
 * Created by wangwj on 2016/8/2 0002.
 */
public interface BanquetRepository {
    Long save(Banquet banquet);
    void delete(Long id);
    Banquet get(Long id);
    List<Banquet> getByRecepId(Long recepId);
}
