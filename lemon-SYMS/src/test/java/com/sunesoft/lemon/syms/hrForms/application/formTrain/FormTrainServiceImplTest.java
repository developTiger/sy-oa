/*
package com.sunesoft.lemon.syms.hrForms.application.formTrain;

import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.utils.JsonHelper;
import com.sunesoft.lemon.syms.eHr.application.dtos.EmpEasyDto;
import com.sunesoft.lemon.syms.hrForms.application.Dtos.FormTrainDto;
import com.sunesoft.lemon.syms.hrForms.domain.FormTrainChooseEmp;
import com.sunesoft.lemon.syms.workflow.application.serviceSelector.FormKindManagerService;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")


public class FormTrainServiceImplTest extends TestCase {
    @Autowired
    FormKindManagerService formKindManagerService;


    @Test
    public void  FormTrainAddTest(){

        FormTrainDto dto = new FormTrainDto();

        //基础信息
        dto.setApplyer(2L);
        dto.setApplyerName("hot");
        dto.setDeptId(2L);
        dto.setDeptName("small");
        dto.setFormKind("SYY_RS_LC01");
        dto.setFormKindName("培训申请单");
        //表单信息
        dto.setDept("1,2,3");
        dto.setTrainPlace("new york");
        dto.setTrainContent("培训内容：123");
//        dto.setTrainDate(new Date());
        dto.setTrainBeginDate(new Date());
        dto.setTrainEndDate(new Date());
        dto.setTrainName("Ping++ Java SDK 的源代码培训");
        dto.setEmpLists(new ArrayList<EmpEasyDto>());
        formKindManagerService.getFormService(dto.getFormKind()).addByDto(dto);
    }


    @Test
    public void  submitformTest(){
        CommonResult result= formKindManagerService.getFormService("SYY_RS_LC01").submitForm(145L,"SYY_RS_LC01");
        System.out.print(JsonHelper.toJson(result));
    }

    @Test
    public void approveTest() throws Exception {
        CommonResult result= formKindManagerService.getFormService("SYY_RS_LC01").doApprove(2L,"SYY_RS_LC01",1L,"zhouzh","同意",null);
        System.out.print(JsonHelper.toJson(result));
    }

    @Test
    public void aa() throws Exception{
        Object o = formKindManagerService.getFormService("SYY_RS_LC01_01").getFormByFormNo(255L);
        FormTrainChooseEmp formTrainChooseEmp=(FormTrainChooseEmp)o;
        System.out.print(formTrainChooseEmp.getTrainBeginDate());

    }
}*/
