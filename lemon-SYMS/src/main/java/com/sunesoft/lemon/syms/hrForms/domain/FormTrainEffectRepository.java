package com.sunesoft.lemon.syms.hrForms.domain;

/**
 * Created by jiangkefan on 2016/7/27.
 */
public interface FormTrainEffectRepository {

    Long save(FormTrainEffect formTrainChooseEmp);
    void delete(Long id);
    FormTrainEffect get(Long id);
    FormTrainEffect getByFormNo(Long formNo);

}
