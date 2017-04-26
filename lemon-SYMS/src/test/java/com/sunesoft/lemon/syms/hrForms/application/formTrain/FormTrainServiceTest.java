package com.sunesoft.lemon.syms.hrForms.application.formTrain;

import com.sunesoft.lemon.syms.hrForms.application.Dtos.FormTrainDto;
import com.sunesoft.lemon.syms.hrForms.domain.FormTrainChooseEmp;
import com.sunesoft.lemon.syms.workflow.application.serviceSelector.FormKindManagerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by jiangkefan on 2016/7/27.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class FormTrainServiceTest {
    @Autowired
    FormKindManagerService formKindManagerService;

    @Autowired
    FormTrainService formTrainService;



    @Test
    public void aa() throws Exception{
        Object o = formKindManagerService.getFormService("SYY_RS_LC01").getFormByFormNo(255L);
        FormTrainChooseEmp formTrainChooseEmp=(FormTrainChooseEmp)o;
        System.out.print(formTrainChooseEmp.getTrainBeginDate());

    }

    @Test
    public void testFormTrain_getTrainDtoByEmpId(){
        List<FormTrainDto> gf = formTrainService.getFormTrainByEmpID(285L);
        System.out.println(gf.get(0).getId());
        System.out.println(gf.size());
    }



}
