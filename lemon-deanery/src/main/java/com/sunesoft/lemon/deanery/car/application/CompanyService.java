package com.sunesoft.lemon.deanery.car.application;

import com.sunesoft.lemon.deanery.car.application.criteria.CompanyCriteria;
import com.sunesoft.lemon.deanery.car.application.criteria.DriverReportCriteria;
import com.sunesoft.lemon.deanery.car.application.dtos.CompanyDto;
import com.sunesoft.lemon.deanery.car.domain.Company;
import com.sunesoft.lemon.fr.results.PagedResult;

import java.util.List;
import java.util.Map;

/**
 * Created by wangwj on 2016/6/17 0017.
 */
public interface CompanyService {

    //检查公司名称是否存在
    public boolean checkCompanyName(String companyName);

    //查询所有公司
    public PagedResult<CompanyDto> getCompany(CompanyCriteria companyCriteria);

    //根据ID查询公司
    public CompanyDto getCompany(Long id);

    //查询所有公司
    public PagedResult<CompanyDto> getCompany(CompanyDto dto);

    //添加公司信息
    public boolean addComanyName(CompanyDto dto);

    //根据主键删除信息
    public boolean delComanyName(Long id);

    //根据对象删除信息
    public boolean delComanyName(CompanyDto dto);

    //多条删除公司
    public boolean delComanyName(Long[] ids);

    //修改公司信息
    public Long addOrUpdate(CompanyDto companyDto);

}
