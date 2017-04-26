/*
package com.sunesoft.lemon.syms.workflow.application.serviceSelector;

import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.utils.JsonHelper;

import com.sunesoft.lemon.syms.eHr.domain.attendance.enums.AttendanceKind;
import com.sunesoft.lemon.syms.hrForms.domain.FormLeave;
import com.sunesoft.lemon.syms.workflow.application.dtos.ApproveFormDto;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class FormServiceTest extends TestCase {


    @Autowired
    FormKindManagerService formKindManagerService;

    @Test
    public void  formKindManagerServiceTest(){
        FormLeave leave = new FormLeave();
        leave.setFormKind("SYY-RS-LC06");
        leave.setFormKindName("请假申请单");
        leave.setDeptId(1L);
        leave.setDeptName("中科院");
        leave.setApplyer(1L);
        leave.setLeaveType(AttendanceKind.B1);
        leave.setReason("生病");
        leave.setTarget("人民医院");
        leave.setApplyerName("zhouzh");
        formKindManagerService.getFormService(leave.getFormKind()).addByDto(leave);
    }

    @Test
    public void  submitformTest(){

     CommonResult result= formKindManagerService.getFormService("SYY-RS-LC06").submitForm(10L,"SYY-RS-LC06");
        System.out.print(JsonHelper.toJson(result));
}

    @Test
    public void approveTest() throws Exception {
        CommonResult result= formKindManagerService.getFormService("SYY-RS-LC06").doApprove(2L,"SYY-RS-LC06",1L,"zhouzh","同意",null);
        System.out.print(JsonHelper.toJson(result));
    }
}*/
