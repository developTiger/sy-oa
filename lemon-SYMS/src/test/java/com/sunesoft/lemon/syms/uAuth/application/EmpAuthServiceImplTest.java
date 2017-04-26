package com.sunesoft.lemon.syms.uAuth.application;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class EmpAuthServiceImplTest extends TestCase {

    @Autowired
    EmpAuthService empAuthService;
    @Test
    public void testGetAllAuthInfoByRole() throws Exception {
        empAuthService.getAllAuthInfoByRole();
    }

    public void testGetEmpInfoByLogin() throws Exception {

    }


    public void testGetAllAuthInfoByRole1() throws Exception {

    }

    public void testGetEmpInfoByLogin1() throws Exception {

    }

    public void testGetEmpRole() throws Exception {

    }

    public void testSetEmpRole() throws Exception {

    }

    public void testGetEmpSessionDtoById() throws Exception {

    }
}