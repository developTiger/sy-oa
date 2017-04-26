/*package com.sunesoft.lemon.syms.eHr.application.TrainChooseEmp;

import com.sunesoft.lemon.syms.hrForms.application.Dtos.FormTrainChooseEmpDto;
import com.sunesoft.lemon.syms.hrForms.application.formTrainChoose.FormTrainChooseEmpService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

*//**
* Created by jiangkefan on 2016/7/22.
*//*
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TestTrainChooseEmp {
    @Autowired
    FormTrainChooseEmpService formTrainChooseEmpService;
    @Test
    public void test_add(){
        FormTrainChooseEmpDto formTrainChooseEmpDto = new FormTrainChooseEmpDto();
        formTrainChooseEmpDto.setTrainName("来考研啊");
        formTrainChooseEmpDto.setTrainPlace("花果山");
        formTrainChooseEmpDto.setTrainContent("自习");
        formTrainChooseEmpDto.setTrainEndDate(new Date());
        formTrainChooseEmpService.addByDto(formTrainChooseEmpDto);
    }
}*/
