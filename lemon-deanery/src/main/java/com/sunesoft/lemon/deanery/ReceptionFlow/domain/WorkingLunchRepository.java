package com.sunesoft.lemon.deanery.ReceptionFlow.domain;

import java.util.List;

/**
 * Created by wangwj on 2016/8/2 0002.
 */
public interface WorkingLunchRepository {
    Long save(WorkingLunch workingLunch);
    void delete(Long id);
    WorkingLunch get(Long id);
    WorkingLunch getByRecepid(Long recepId);
}
