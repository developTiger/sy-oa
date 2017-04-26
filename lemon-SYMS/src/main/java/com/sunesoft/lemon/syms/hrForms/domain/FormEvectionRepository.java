package com.sunesoft.lemon.syms.hrForms.domain;

import java.util.List;

/**
 * Created by jiangkefan on 2016/7/19.
 */
public interface FormEvectionRepository {
    /**
     * add or save
     * @param formEvection
     * @return
     */
     Long save(FormEvection formEvection);
    /**
     * delete
     * @param FormEvectionId
     */
     void delete(Long FormEvectionId);
    /**
     * get
     * @param FormEvectionId
     * @return
     */
     FormEvection get(Long FormEvectionId);

    Integer getMaxNo();

}
