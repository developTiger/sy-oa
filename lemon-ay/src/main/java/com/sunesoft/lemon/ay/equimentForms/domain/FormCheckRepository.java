package com.sunesoft.lemon.ay.equimentForms.domain;

import java.util.List;

/**
 * Created by jiangkefan on 2016/8/17.
 */
public interface FormCheckRepository {

    Long save(FormCheck formCheck);

    FormCheck get(Long id);

    List<FormCheck> getAll();
}
