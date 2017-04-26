/*
package com.sunesoft.lemon.syms.eHr.application.Train;

import com.sunesoft.lemon.syms.eHr.application.dtos.EmpEasyDto;
import com.sunesoft.lemon.syms.hrForms.application.Dtos.FormTrainDto;
import com.sunesoft.lemon.syms.hrForms.application.formTrain.FormTrainService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

*/
/**
 * Created by jiangkefan on 2016/7/22.
 *//*

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TestTrainService {

    @Autowired
    FormTrainService formTrainService;

    @Test
    public void testFromTrainService_add(){
        FormTrainDto formTrainDto = new FormTrainDto();
        formTrainDto.setTrainPlace("菜园子");
        formTrainDto.setDept("测试部");

        formTrainDto.setTrainName("测试大赛");
        formTrainDto.setTrainContent("测试技能培训");

        EmpEasyDto empEasyDto1 = new EmpEasyDto();
        empEasyDto1.setDeptId(1l);
        EmpEasyDto empEasyDto2 = new EmpEasyDto();
        empEasyDto2.setDeptId(2l);

        List<EmpEasyDto> lists = new ArrayList<>();
        formTrainDto.setEmpLists(lists);
        formTrainService.addByDto(formTrainDto);

    }

    @Test
    public void testFormTrainService_get(){
//        formTrainService.getFormByFormNo(1);

    }
}
*/
