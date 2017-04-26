package com.sunesoft.lemon.syms.parameter.application;

import com.sunesoft.lemon.syms.parameter.domain.ParameterType;
import org.junit.Test;

/**
 * Created by user on 2016/6/3.
 */
public class ParameterTypeServiceImplTest {

    @Test
    public void testAddParameterType() throws Exception {
        ParameterType parameterType =new ParameterType();
      //  Parameter parameter =new Parameter();

        parameterType.setParamTypeName("小明");
        parameterType.setParamDesc("身高是1米78");
      //  parameterType.setParameters();
        System.out.println("?????????????????????????????????"+parameterType);
    }
}