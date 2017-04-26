package com.sunesoft.lemon.syms.eHr.application;

import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.syms.eHr.domain.attendance.DateDetail;

import java.util.List;

/**
 * Created by admin on 2016/11/7.
 */
public interface DateDetailService {

    public CommonResult addDateDetail(DateDetail dateDetail);
    public CommonResult updateDateDetail(DateDetail dateDetail);
    public DateDetail getByid(Long id);
    public CommonResult deleteByIds(Long id);
    public List<DateDetail> getAll();

}
