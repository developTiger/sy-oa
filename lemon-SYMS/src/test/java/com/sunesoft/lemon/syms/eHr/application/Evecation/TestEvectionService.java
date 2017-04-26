/*
package com.sunesoft.lemon.syms.eHr.application.Evecation;

import com.sunesoft.lemon.syms.eHr.domain.attendance.enums.AttendanceKind;
import com.sunesoft.lemon.syms.hrForms.application.Dtos.FormEvectionDto;
import com.sunesoft.lemon.syms.hrForms.application.formevection.FormEvectionService;
import com.sunesoft.lemon.syms.hrForms.domain.FormEvection;
import com.sunesoft.lemon.syms.workflow.domain.enums.FormStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.awt.*;
import java.util.Date;

*/
/**
 * Created by jiangkefan on 2016/7/19.
 *//*

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TestEvectionService {
    @Autowired
    public FormEvectionService evectionService;

    @Test
    public void testFormService_save(){
        FormEvectionDto formEvection = new FormEvectionDto();
//        formEvection.setEvectionType(AttendanceKind.Z);
        formEvection.setEvectionTime(new Date());
        formEvection.setReason("aaaa ");
//        formEvection.setTarget("考察");
//        formEvection.setToTime(new Date());
//        formEvection.setClStep(1);
//        formEvection.setIsComplete(true);
//        formEvection.setIsActive(true);
//        formEvection.setCountTime(1.2f);
        evectionService.addByDto(formEvection);



    }

     @Test
    public void testFormService_getByFormNo(){
         Long formNo = 1800L;
         FormEvectionDto f =  evectionService.getFormByFormNo(formNo);
         System.out.print(f.getId());
     }

}
*/
