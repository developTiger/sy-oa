package com.sunesoft.lemon.ay.partyGroupForms.domain;

import java.util.List;

/**
 * Created by admin on 2016/9/2.
 */
public interface FormWorkProjectRepository {

    Long save(FormWorkProject formWorkProject);

    FormWorkProject getById(Long id);

    List<FormWorkProject> getAll();

    FormWorkProject getByFormNo(Long formNo);
}
