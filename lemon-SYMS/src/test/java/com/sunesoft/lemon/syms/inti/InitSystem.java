package com.sunesoft.lemon.syms.inti;

import com.sunesoft.lemon.syms.eHr.application.EmployeeService;
import com.sunesoft.lemon.syms.eHr.application.dtos.EmpDto;
import com.sunesoft.lemon.syms.uAuth.application.EmpAuthService;
import com.sunesoft.lemon.syms.uAuth.application.SysRoleService;
import com.sunesoft.lemon.syms.uAuth.application.dtos.RoleDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhouz on 2016/7/19.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class InitSystem {

    @Autowired
    EmployeeService empService;

    @Autowired
    EmpAuthService empAuthService;

    @Autowired
    SysRoleService roleService;

    @Test
    public void initAdminUser(){
        EmpDto empDto = new EmpDto();
//        dto.setId(6L);
        empDto.setLoginName("admin");
        empDto.setName("admin");
        empDto.setPassword("admin");
        empDto.setEmail("");
        empDto.setPhone("");
        empDto.setStrBirthday("1980-01-01 12:12:12");
        empDto.setStrEnteryDate("2016-01-01 12:12:12");
        empDto.setJoinTime("2016-01-01 12:12:12");



        Long empid =empService.addOrUpdateEpm(empDto).getId();

        RoleDto roleDto = new RoleDto();
        roleDto.setName("admin");
        roleDto.setIdCode("admin");
        roleDto.setDescription("admin");
        roleDto.setSort(12.0);

       Long roleid = roleService.addRole(roleDto);
// todo 这里改过xzl
        List<String> list=new ArrayList<>();
        list.add(roleid.toString());
       empAuthService.setEmpRole(empid,list.toArray(new String[list.size()]));
    }

}
