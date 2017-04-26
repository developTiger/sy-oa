package com.sunesoft.lemon.syms.eHr.application.employeeTest;

import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.syms.eHr.application.EmpGroupService;
import com.sunesoft.lemon.syms.eHr.application.criteria.GroupCriteria;
import com.sunesoft.lemon.syms.eHr.application.dtos.EmpGroupDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiazl on 2016/6/22.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class EmpGroupServiceTest {

    @Autowired
    EmpGroupService groupService;

    @Test
    public void addGroupTest() {
        EmpGroupDto dto = new EmpGroupDto();
        dto.setName("w7");
        dto.setParentId(14L);
        CommonResult c = groupService.addGroup(dto);
        if (c != null) {
            System.out.println(c.getId() + "\t" + c.getIsSuccess() + "\t" + c.getMsg());
        } else {
            System.out.println("null");
        }
    }

    @Test
    public void deleteGroupTest() {
        List<Long> list = new ArrayList<Long>();
       for(Long i=1L;i<=13L;i++){
           list.add(i);
       }

        System.out.println(list.size());
        CommonResult c = groupService.deleteGroup(list);
        if (c != null) {
            System.out.println(c.getId() + "\t" + c.getIsSuccess() + "\t" + c.getMsg());
        } else {
            System.out.println("null");
        }
    }
    @Test
    public void setStatusTest() {
        List<Long> list = new ArrayList<Long>();
        for(Long i=1L;i<=8L;i++){
            list.add(i);
        }

        System.out.println(list.size());
        CommonResult c = groupService.setStatus(list,0);
        if (c != null) {
            System.out.println(c.getId() + "\t" + c.getIsSuccess() + "\t" + c.getMsg());
        } else {
            System.out.println("null");
        }
    }

        @Test
        public void getByGroupsIdsTest(){

            List<Long> list = new ArrayList<Long>();
            list.add(1L);
            list.add(2L);
            list.add(3L);
            list.add(4L);
            System.out.println(list.size());
            List<EmpGroupDto> dtos=groupService.getByGroupsIds(list);
            System.out.println(dtos.size());

        }

    @Test
    public void updateGroupTest() {
        EmpGroupDto dto=new EmpGroupDto();
        dto.setId(9L);
        dto.setName("uu");
        CommonResult c=groupService.updateGroup(dto);
        if (c != null) {
            System.out.println(c.getId() + "\t" + c.getIsSuccess() + "\t" + c.getMsg());
        } else {
            System.out.println("null");
        }
    }

    @Test
    public void getGroupsByNameTest() {
        List<EmpGroupDto> dtos=groupService.getGroupsByName("w7");
        if(dtos==null)System.out.println("null");
        System.out.println(dtos.size());

    }

    @Test
    public void findGroupsPagedTest() {
        GroupCriteria criteria=new GroupCriteria();
        criteria.setName("w");
        PagedResult<EmpGroupDto> pg=groupService.findGroupsPaged(criteria);
        if(pg!=null){
            System.out.println(pg.getPageNumber()+"\t"+pg.getPageSize()+"\t"+pg.getItems().size()+"\t"+pg.getPagesCount());
        }

    }

    }

