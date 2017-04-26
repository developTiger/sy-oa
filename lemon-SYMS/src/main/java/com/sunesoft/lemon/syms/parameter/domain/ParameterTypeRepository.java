package com.sunesoft.lemon.syms.parameter.domain;

import java.util.List;

/**
 * Created by zy on 2016/6/2.
 */
public interface ParameterTypeRepository {

    Long save(ParameterType parameterType);

    void delete(Long  parameterTypeId);

    ParameterType get(Long parameterTypeId);

   // List<Parameter> getAllParameter();
}
