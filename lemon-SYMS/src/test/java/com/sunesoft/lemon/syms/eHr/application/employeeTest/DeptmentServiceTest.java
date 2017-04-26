package com.sunesoft.lemon.syms.eHr.application.employeeTest;

import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.syms.eHr.application.DeptmentService;
import com.sunesoft.lemon.syms.eHr.application.EmployeeService;
import com.sunesoft.lemon.syms.eHr.application.criteria.DeptmentCriteria;
import com.sunesoft.lemon.syms.eHr.application.dtos.DeptmentDto;
import com.sunesoft.lemon.syms.hrForms.application.formEmpAppraise.FormAppraiseDetailsService;
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
public class DeptmentServiceTest {

    @Autowired
    DeptmentService deptService;

    @Autowired
    FormAppraiseDetailsService formAppraiseDetailsService;

    @Autowired
    EmployeeService employeeService;

    @Test
    public void addDeptTest() {

        String[] a = new String[]
                {
                        "地质实验中心",
                        "质量标准化研究所",
                        "质量安全运行科",
                        "党群工作科（工会办公室）",
                        "环境监测(水质)中心",
                        "物资供应站",
                        "开发实验中心",
                        "节能监测中心",
                        "油田化学剂产品质量监督检验站",
                        "人事（组织）科",
                        "实验数据信息中心",
                        "纪委监察科",
                        "对外合作中心",
                        "计划财务科",
                        "采收率研究所",
                        "院长（党委）办公室",
                        "科研管理科",
                        "计量监督检测中心",
                        "采油工艺实验中心",
                };
        for (int i = 0; i < a.length; i++) {
            DeptmentDto dto = new DeptmentDto();
            dto.setDeptName(a[i].trim());
            dto.setDeptType(1);
            dto.setDeptNo(String.valueOf(1001 + i));
            dto.setStatus(1);
            dto.setParentDeptId(1l);
            CommonResult c = deptService.addDept(dto);
            if (c != null) {
                System.out.println(c.getId() + "\t" + c.getIsSuccess() + "\t" + c.getMsg());
            } else {
                System.out.println("null");
            }
        }
    }
/*
    @Test
    public void ddd(){

        EmpAppraiseDetailDto empAppraiseDetailDto=new EmpAppraiseDetailDto();
        empAppraiseDetailDto.setFormNo(1386L);
        empAppraiseDetailDto.setEmpId(1100L);
        empAppraiseDetailDto.setEmpSelfGrade("高中低");
        empAppraiseDetailDto.setEmpSelfScores(100f);
        formAppraiseDetailsService.addOrUpdateFormAppraiseDetails(empAppraiseDetailDto);
    }*/


    @Test
    public void deleteDeptTest() {
        List<Long> list = new ArrayList<Long>();
        list.add(1L);
        list.add(2L);
        list.add(3L);
        System.out.println(list.size());
        CommonResult c = deptService.deleteDept(list);
        if (c != null) {
            System.out.println(c.getId() + "\t" + c.getIsSuccess() + "\t" + c.getMsg());
        } else {
            System.out.println("null");
        }

    }

    @Test
    public void getByDeptsIdsTest() {
        List<Long> list = new ArrayList<Long>();
        for (long l = 1; l < 15; l++) {
            list.add(l);
        }
        System.out.println(list.size());
        List<DeptmentDto> dtos = deptService.getByDeptsIds(list);
        if (dtos != null) {
            System.out.println(dtos.size());
        } else {
            System.out.println("null");
        }



    }

    @Test
    public void updateDeptTest() {
        DeptmentDto dto = new DeptmentDto();
        dto.setId(1L);
        dto.setDeptName("po");
        CommonResult c = deptService.updateDept(dto);
        if (c != null) {
            System.out.println(c.getId() + "\t" + c.getIsSuccess() + "\t" + c.getMsg());
        } else {
            System.out.println("null");
        }

    }

    @Test
    public void getDeptsByNameTest() {
        List<DeptmentDto> dtos = deptService.getDeptsByName("w1");
        if (dtos != null) {
            System.out.println(dtos.size());
        } else {
            System.out.println("null");
        }

    }

    @Test
    public void findDeptsPagedTest() {
        DeptmentCriteria criteria = new DeptmentCriteria();
        criteria.setDeptName("w");
        PagedResult<DeptmentDto> pr = deptService.findDeptsPaged(criteria);
        if (pr != null) {
            System.out.println(pr.getItems().size() + "\t" + pr.getPageNumber() + "\t" + pr.getPageSize() + "\t" + pr.getPagesCount());
        } else {
            System.out.println("null");
        }

    }


}
