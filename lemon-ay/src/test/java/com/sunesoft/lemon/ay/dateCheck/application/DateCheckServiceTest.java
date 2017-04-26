package com.sunesoft.lemon.ay.dateCheck.application;

import com.sunesoft.lemon.ay.dateCheck.application.dtos.DateCheckDto;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.utils.DateHelper;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class DateCheckServiceTest extends TestCase {
    @Autowired
    DateCheckService service;
    @Test
    public void testAdd() throws Exception {
        DateCheckDto dto=new DateCheckDto();
        dto.setCheckPerson("x1");
        dto.setEquipId(1l);
        dto.setScheckTime(DateHelper.formatDate(new Date(),"yyyy-MM-dd"));
        CommonResult c=service.add(dto);
        if(c.getIsSuccess()){
            System.out.println(c.getId());
        }

    }
    @Test
    public void testUpdate() throws Exception {

    }
    @Test
    public void testGetById() throws Exception {

    }
    @Test
    public void testDeletes() throws Exception {

    }
    @Test
    public void testDelete() throws Exception {

    }
    @Test
    public void testGetByName() throws Exception {

    }
    @Test
    public void testPaged() throws Exception {

    }
}