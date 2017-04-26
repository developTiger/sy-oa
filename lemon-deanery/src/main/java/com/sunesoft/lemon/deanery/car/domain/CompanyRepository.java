package com.sunesoft.lemon.deanery.car.domain;

import java.util.List;

/**
 * Created by Administrator on 2016/6/16 0016.
 */
public interface CompanyRepository {
    Long save(Company company);

    void delete(Long companyId);

    Company get(Long companyId);

    List<Company> getAllcoms();
}
