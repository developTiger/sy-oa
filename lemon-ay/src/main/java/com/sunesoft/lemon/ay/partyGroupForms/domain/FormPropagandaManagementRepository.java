package com.sunesoft.lemon.ay.partyGroupForms.domain;

import java.util.List;

/**
 * Created by admin on 2016/9/5.
 */
public interface FormPropagandaManagementRepository {

    Long save(FormPropagandaManagement formPropagandaManagement);

    FormPropagandaManagement getById(Long id);

    List<FormPropagandaManagement> getAll();

    void delete(Long id);

    FormPropagandaManagement getByFormNo(Long formNo);


}
