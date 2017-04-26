package com.sunesoft.lemon.syms.uAuth.domain;

/**
 * Created by zhouz on 2016/5/12.
 */
@Deprecated
public interface SysUserRepository {


    Long save(SysUser emp);

    void delete(Long empId);

    SysUser get(Long inventorId);



}
