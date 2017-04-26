/*
package com.sunesoft.lemon.syms.hrForms.application.formTrainChoose;

import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.utils.JsonHelper;
import com.sunesoft.lemon.syms.hrForms.application.Dtos.FormTrainChooseEmpDto;
import com.sunesoft.lemon.syms.hrForms.application.Dtos.FormTrainDto;
import com.sunesoft.lemon.syms.hrForms.domain.FormTrainChooseEmp;
import com.sunesoft.lemon.syms.workflow.application.serviceSelector.FormKindManagerService;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")

public class FormTrainChooseEmpServiceImplTest extends TestCase {
    @Autowired
    FormKindManagerService formKindManagerService;


    @Test
    public void  FormTrainChooseEmpAddTest(){
        FormTrainChooseEmpDto dto = new FormTrainChooseEmpDto();

        //基础信息
        dto.setApplyer(1L);
        dto.setApplyerName("aaaaa");
        dto.setDeptId(1L);
        dto.setDeptName("testdept");
        dto.setFormKind("SYY_RS_LC02");
        dto.setFormKindName("培训申请单");


        //表单信息
//        dto.setTrainDepts("1,2,3");
        dto.setTrainPlace("new york");
        dto.setTrainContent("rc 为 Ping++ Java SDK 的源代码，可以关联到 pingpp-java-x.x.x.jar 文件。或者直接把源代码引入到工程之中");
//        dto.setTrainDate(new Date());
        dto.setTrainBeginDate(new Date());
        dto.setTrainEndDate(new Date());
        dto.setTrainName("Ping++ Java SDK 的源代码培训");

        formKindManagerService.getFormService(dto.getFormKind()).addByDto(dto);
    }


    @Test
    public void  submitformTest(){
        CommonResult result= formKindManagerService.getFormService("SYY_RS_LC02").submitForm(149L,"SYY_RS_LC02");
        System.out.print(JsonHelper.toJson(result));
    }

    @Test
    public void approverTest() throws  Exception{
        CommonResult result= formKindManagerService.getFormService("SYY_RS_LC02").doApprove(2L, "SYY_RS_LC01", 1L, "zhouzh", "同意", null);
        System.out.print(JsonHelper.toJson(result));
    }

}*/
