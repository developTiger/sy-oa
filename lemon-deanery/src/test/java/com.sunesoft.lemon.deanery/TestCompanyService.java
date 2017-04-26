package com.sunesoft.lemon.deanery;

import com.sunesoft.lemon.deanery.car.application.CompanyService;
import com.sunesoft.lemon.deanery.car.application.dtos.CompanyDto;
import com.sunesoft.lemon.deanery.car.domain.Company;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by jiangkefan on 2016/6/22.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TestCompanyService {
    @Autowired
    CompanyService companyService;
    @Test
    public void testCompany_addOr_update(){
        CompanyDto companyDto = new CompanyDto();
    //    companyDto.setName("aaa");
        companyDto.setUserId(1L);
     //   companyService.addOrUpdate(companyDto);
    }

    @Test
    public void testCompany_del(){
        companyService.delComanyName(new Long[]{1L,2L});
    }

    @Test
    public void testCompany_get(){
        CompanyDto companyDto = companyService.getCompany(10L);
    //    System.out.print(companyDto.getName());
    }

    @Test
    public void testCompany_checkCompanyName(){
        String name ="";
        Boolean bool = companyService.checkCompanyName(name);
        System.out.print(bool);
    }
}
