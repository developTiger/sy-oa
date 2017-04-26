package com.sunesoft.lemon.syms.hrForms.domain;

import java.util.List;

/**
 * Created by jiangkefan on 2016/7/22.
 */
public interface FormTrainRepository {
    /**
     * add or save
     * @param formTrain
     * @return
     */
    Long save(FormTrain formTrain);
    /**
     * delete
     * @param trainId
     */
    void delete(Long trainId);
    /**
     * get
     * @param trainId
     * @return
     */
    FormTrain get(Long trainId);

    /**
     * get
     * @param formNo
     * @return
     */
    FormTrain getByFormNo(Long formNo);


    List<FormTrain> getByEmpId(Long empId);

    List<FormTrain> getAll();

}
