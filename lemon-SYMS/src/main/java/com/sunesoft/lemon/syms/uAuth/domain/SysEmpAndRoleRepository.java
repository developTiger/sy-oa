package com.sunesoft.lemon.syms.uAuth.domain;

import java.util.List;

/**
 * Created by zhouz on 2016/5/12.
 */
public interface SysEmpAndRoleRepository {

    Long save(SysEmpAndRole emp);

    void delete(Long empId);

    /**
     * 多个删除
     * @param ids
     */
    void delete(List<Long> ids);

    SysEmpAndRole get(Long empId);

}
