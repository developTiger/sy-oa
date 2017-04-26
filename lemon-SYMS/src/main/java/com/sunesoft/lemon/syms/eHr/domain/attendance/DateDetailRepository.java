package com.sunesoft.lemon.syms.eHr.domain.attendance;

import java.util.List;

/**
 * Created by admin on 2016/11/7.
 */
public interface DateDetailRepository {

    Long save(DateDetail dateDetail);
    void delete(Long dateDetailId);
    DateDetail get(Long id);
    List<DateDetail> getAll();

}
