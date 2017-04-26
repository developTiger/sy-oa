package com.sunesoft.lemon.syms.hrForms.domain;

import com.sunesoft.lemon.syms.hrForms.application.Dtos.FormTrainChooseEmpDto;

import java.util.List;

/**
 * Created by jiangkefan on 2016/7/22.
 */
public interface FormTrainChooseEmpRepository {
    /**
     * add or save
     * @param formTrainChooseEmp
     * @return
     */
    Long save(FormTrainChooseEmp formTrainChooseEmp);
    /**
     * delete
     * @param id
     */
    void delete(Long id);
    /**
     * get
     * @param id
     * @return
     */
    FormTrainChooseEmp get(Long id);

    List<FormTrainChooseEmpDto> getAll();

    public FormTrainChooseEmp getByFormNo(Long formNo);

}
