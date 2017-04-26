package com.sunesoft.lemon.syms.uAuth.application;

import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.UniqueResult;
import com.sunesoft.lemon.syms.eHr.application.dtos.EmpSessionDto;
import com.sunesoft.lemon.syms.uAuth.application.dtos.AuthResDto;
import com.sunesoft.lemon.syms.uAuth.application.dtos.ResourceDto;
import com.sunesoft.lemon.syms.uAuth.domain.SysEmpAndRole;

import java.util.List;
import java.util.Map;

/**
 * Created by zhouz on 2016/7/14.
 */
public interface EmpAuthService {

    Map<Long, List<AuthResDto>> getAllAuthInfoByRole();


    UniqueResult<EmpSessionDto>  getEmpInfoByLogin(String userName,String password);

    UniqueResult<EmpSessionDto>  getEmpInfoByLogin(String userName);

    SysEmpAndRole getEmpRole(Long empId);


    CommonResult setEmpRole(Long empId,String[] roleIds);

    UniqueResult<EmpSessionDto> GetEmpSessionDtoById(Long empId);


}
