package com.sunesoft.lemon.syms.EmpAppraise;

import com.sunesoft.lemon.syms.eHr.application.DeptmentService;
import com.sunesoft.lemon.syms.hrForms.application.formEmpAppraise.FormAppraiseDetailsService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by xiazl on 2016/6/22.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class EmpAppraiseTest {

    @Autowired
    DeptmentService deptService;

    @Autowired
    FormAppraiseDetailsService formAppraiseDetailsService;


/*    @Test
    public void ddd(){

        EmpAppraiseDetailDto empAppraiseDetailDto=new EmpAppraiseDetailDto();
        empAppraiseDetailDto.setFormNo(1386L);
        empAppraiseDetailDto.setEmpId(1100L);
        empAppraiseDetailDto.setEmpSelfGrade("高中低");
        empAppraiseDetailDto.setEmpSelfScores(100f);
        formAppraiseDetailsService.addOrUpdateFormAppraiseDetails(empAppraiseDetailDto);
    }*/


 /*   @Test
    public void dddd(){

        EmpAppraiseDetailDto empAppraiseDetail=formAppraiseDetailsService.getAppraiseDetails(1428L);
        System.out.println(empAppraiseDetail.getEmpId());
    }*/

}
