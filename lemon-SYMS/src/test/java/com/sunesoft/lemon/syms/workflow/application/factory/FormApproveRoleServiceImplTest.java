/*
package com.sunesoft.lemon.syms.workflow.application.factory;

import com.sunesoft.lemon.fr.events.PublishEvent;
import com.sunesoft.lemon.fr.msg.MessageService;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.syms.workflow.application.FormApproveRoleService;
import com.sunesoft.lemon.syms.workflow.application.criteria.FormApproveRoleCriteria;
import com.sunesoft.lemon.syms.workflow.application.dtos.FormAppRoleDto;
import com.sunesoft.lemon.syms.workflow.domain.FormApproveRoleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

*/
/**
 * Created by MJ006 on 2016/6/21.
 *//*

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class FormApproveRoleServiceImplTest {
    @Autowired
    FormApproveRoleRepository formApproveRoleRepository;
    @Autowired
    FormApproveRoleService formApproveRoleService;
    @Test
    public void testGetByKey() throws Exception {
        Long id=new Long(1);
        FormAppRoleDto dto=formApproveRoleService.getByKey(id);
        System.out.print(dto);
    }

    @Test
    public void testAdd() throws Exception {
        FormAppRoleDto dto=new FormAppRoleDto();
        dto.setId(2L);
        Map<Long,String> map=new HashMap<Long,String>();
        map.put(new Long("1"),"张三");
       // dto.setEmpIdName(map);
        dto.setRoleName("张三");
        dto.setRoleType("主任");
  //      Long result=formApproveRoleService.add(dto);
      //  System.out.print(result);
    }

    @Test
    public void testUpdate() throws Exception {
        FormAppRoleDto dto=new FormAppRoleDto();
        dto.setId(2L);
        Map<Long,String> map=new HashMap<Long,String>();
        map.put(new Long("1"),"张三");
       // dto.setEmpIdName(map);
        dto.setRoleName("张三");
        dto.setRoleType("主任");
        CommonResult result=formApproveRoleService.update(dto);
        System.out.print(result);
    }

    @Test
    public void testDelete() throws Exception {
        Long id=1L;
        CommonResult result=formApproveRoleService.delete(id);
        System.out.print(result);
    }

    @Test
    public void testGetFormListPaged() throws Exception {
        FormApproveRoleCriteria criteria=new FormApproveRoleCriteria();
        PagedResult<FormAppRoleDto> result=formApproveRoleService.getFormListPaged(criteria);
        System.out.print(result);
    }
}*/
