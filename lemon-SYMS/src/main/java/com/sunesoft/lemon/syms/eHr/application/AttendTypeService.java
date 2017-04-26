package com.sunesoft.lemon.syms.eHr.application;

import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.syms.eHr.application.criteria.AttendCriteria;
import com.sunesoft.lemon.syms.eHr.application.criteria.AttendTypeCriteria;
import com.sunesoft.lemon.syms.eHr.domain.attend.AttendType;

import java.util.List;

/**
 * Created by xiazl on 2017/3/2.
 */
public interface AttendTypeService {

    public Long create(AttendType type);

    public Long edit(AttendType type);

    public AttendType get(Long id);
    public AttendType getByCord(String cord);

    public boolean delete(Long id);

    public List<AttendType> getByName(String name);

    public PagedResult<AttendType> page(AttendTypeCriteria criteria);

    public  List<AttendType> t();



}
