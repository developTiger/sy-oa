package com.sunesoft.lemon.syms.eHr.application;

import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.utils.DateHelper;
import com.sunesoft.lemon.syms.eHr.application.dtos.AttendDto;
import com.sunesoft.lemon.syms.eHr.application.factory.AttendFactory;
import com.sunesoft.lemon.syms.eHr.domain.attend.Attend;
import com.sunesoft.lemon.syms.eHr.domain.attend.AttendType;
import com.sunesoft.lemon.syms.eHr.domain.attend.AttendTypeRepository;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class AttendServiceTest extends TestCase {
@Autowired
 AttendService service;
    @Autowired
    AttendTypeService attendTypeRepository;
    @Test
    public void testCreate() throws Exception {

        Attend a=new Attend();
        AttendType attendType=new AttendType();
        attendType.setId(8303940l);
        a.setEmpName("杨伟鸿");
        a.setType(attendType);
        a.setDepId(10L);
        a.setDepName("尚易");
        a.setDateTime(DateHelper.addDay(new Date(),-1));
        CommonResult result = service.create(a);
        System.out.println("ddd");
    }

    @Test
    public void dd(){
       Date date=DateHelper.addDay(new Date(),-1);

    }


    @Test
    public void type(){
        AttendType attendType=new AttendType();
        attendType.setCord("I");
        attendType.setName("出勤");
        attendTypeRepository.create(attendType);
        List<AttendType> attendType1=attendTypeRepository.t();

        AttendType a=attendTypeRepository.get(8303939l);
        System.out.println("1");
    }

    @Test
    public void testUpdate() throws Exception {

    }
    @Test
    public void testGet() throws Exception {

    }
    @Test
    public void testDelete() throws Exception {

    }
    @Test
    public void testPage() throws Exception {

    }
    @Test
    public void testEnsureOne() throws Exception {

    }
    @Test
    public void testEnsureDept() throws Exception {

    }
}