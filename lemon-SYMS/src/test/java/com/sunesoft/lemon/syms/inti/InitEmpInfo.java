package com.sunesoft.lemon.syms.inti;

import com.sunesoft.lemon.fr.results.ListResult;
import com.sunesoft.lemon.fr.utils.DateHelper;
import com.sunesoft.lemon.syms.eHr.application.DeptmentService;
import com.sunesoft.lemon.syms.eHr.application.EmployeeService;
import com.sunesoft.lemon.syms.eHr.application.dtos.DeptmentDto;
import com.sunesoft.lemon.syms.eHr.application.dtos.EmpDto;
import com.sunesoft.lemon.syms.eHr.domain.empInfo.Employee;
import com.sunesoft.lemon.syms.eHr.domain.empInfo.EmployeeRepository;
import com.sunesoft.lemon.syms.hrForms.application.factory.UploadEmplFactory;
import com.sunesoft.lemon.syms.uAuth.application.EmpAuthService;
import com.sunesoft.lemon.syms.uAuth.application.SysRoleService;
import com.sunesoft.lemon.syms.uAuth.application.dtos.RoleDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhouz on 2016/7/19.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class InitEmpInfo {

    @Autowired
    EmployeeService empService;

    @Autowired
    EmpAuthService empAuthService;




    @Autowired
    DeptmentService deptmentService;

    @Autowired
    SysRoleService roleService;

    @Test
    public void initEmps(){


        List<DeptmentDto> deptmentDtoList = deptmentService.getAllDept();
        Integer i=0;
        try {
            InputStream is = new FileInputStream("C:\\Users\\zhaowy\\Desktop\\1.xls");
            ListResult<EmpDto>  empDtos=new UploadEmplFactory().readExcel(is);
            for(EmpDto empDto :empDtos.getItems()) {
                i=i+1;
                Boolean hasDept = false;
                for(DeptmentDto deptmentDto :deptmentDtoList){
                    if(deptmentDto.getDeptName().equals(empDto.getDeptName())){
                        empDto.setDeptId(deptmentDto.getId());
                        empDto.setDeptName(deptmentDto.getDeptName());
                        hasDept=true;
                        break;
                    }
                }
                if(!hasDept){
                    DeptmentDto deptmentDto = new DeptmentDto();
                    deptmentDto.setDeptName(empDto.getDeptName()+"(新增)");
                    deptmentDto.setDeptNo("DEPT_"+i);
                    deptmentService.addDept(deptmentDto);
                    empDtos.getItems().add(empDto);

                }


                Long empid = empService.addOrUpdateEpm(empDto).getId();
                List<String> list=new ArrayList<>();
                //todo 这里改过xzl 疑问原先 empDto.getRoleName()为String类型，是字符串，并不是roleId哦，为什么这样用
                list.add(empDto.getRoleName());
                empAuthService.setEmpRole(empid, list.toArray(new String[list.size()]));
                System.out.println("---------------------------------------------" + i + "--------------------------------------------------");
            //   break;
            }



        }catch (Exception ex){
            ex.printStackTrace();
        }


    }
    @Test
    public void updateEmps(){


        List<DeptmentDto> deptmentDtoList = deptmentService.getAllDept();
        Integer i=0;
        try {
            InputStream is = new FileInputStream("D:\\1.xls");
            ListResult<EmpDto>  empDtos=new UploadEmplFactory().readExcel(is);


            for(EmpDto empDto :empDtos.getItems()) {
                i=i+1;
                Employee employee = empService.getEmpByLoginName(empDto.getLoginName());
                if(employee!=null){
                    employee.setSex(empDto.getSex());
                    employee.setUserNo(empDto.getUserNo());
                    employee.setJoinDate(DateHelper.parse(empDto.getJoinTime(),"yyyy-MM-dd"));
                    employee.setBirthday(DateHelper.parse(empDto.getStrBirthday(),"yyyy-MM-dd"));
                    employee.setNation(empDto.getNation());
                    employee.setName(empDto.getName());
                    employee.setPhone(empDto.getPhone());
                    employee.setMobile(empDto.getMobile());
                    empService.save(employee);
                }
                else{
                    Boolean hasDept = false;
                    for(DeptmentDto deptmentDto :deptmentDtoList){
                        if(deptmentDto.getDeptName().equals(empDto.getDeptName())){
                            empDto.setDeptId(deptmentDto.getId());
                            empDto.setDeptName(deptmentDto.getDeptName());
                            hasDept=true;
                            break;
                        }
                    }
                    if(!hasDept){
                        DeptmentDto deptmentDto = new DeptmentDto();
                        deptmentDto.setDeptName(empDto.getDeptName()+"(新增)");
                        deptmentDto.setDeptNo("DEPT_"+i);
                        deptmentService.addDept(deptmentDto);
                        empDtos.getItems().add(empDto);
                        System.out.println("---------------------------------------------"+deptmentDto.getDeptName()+"--------------------------------------------------");
                    }


                    Long empid = empService.addOrUpdateEpm(empDto).getId();
                    //empAuthService.setEmpRole(empid,(long)Float.parseFloat(empDto.getRoleName()));
                    System.out.println("---------------------------------------------"+empDto.getName()+"--------------------------------------------------");
                    System.out.println("---------------------------------------------"+i+"--------------------------------------------------");
                }




                //   break;
            }



        }catch (Exception ex){
            ex.printStackTrace();
        }


    }
}
