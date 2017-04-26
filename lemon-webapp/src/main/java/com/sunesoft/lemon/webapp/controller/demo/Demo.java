package com.sunesoft.lemon.webapp.controller.demo;

import com.sunesoft.lemon.syms.eHr.application.DeptmentService;
import com.sunesoft.lemon.syms.eHr.application.EmployeeService;
import com.sunesoft.lemon.syms.eHr.application.dtos.DeptmentDto;
import com.sunesoft.lemon.syms.eHr.application.dtos.EmpDto;
import com.sunesoft.lemon.webapp.controller.Layout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2016/7/3.
 */
@Controller
public class Demo extends Layout {

//    @RequestMapping(value = "sra_d_t")
//    public ModelAndView index(Model model){
//        return view(layout,"demon/tanchuang",model);
//    }
//
//

    @Autowired
    EmployeeService employeeService;

    @Autowired
    DeptmentService deptmentService;

    @RequestMapping(value = "select2_demon")
    public ModelAndView index(Model model) {
        List<EmpDto>  empDtos =employeeService.getAllEmps();
        List<DeptmentDto> deptmentDtos = deptmentService.getAllDept();
        List<MultiSelectUserWithDept> multiSelectUserWithDepts = new ArrayList<>();
        for(DeptmentDto dept:deptmentDtos){
            MultiSelectUserWithDept multiSelectUserWithDept = new MultiSelectUserWithDept(dept.getId(),dept.getDeptName());
            for(EmpDto emp:empDtos){
                if(emp.getDeptId().equals(dept.getId()))
                    multiSelectUserWithDept.getEmpDtos().add(emp);
            }
            multiSelectUserWithDepts.add(multiSelectUserWithDept);
        }
        model.addAttribute("empInfos", multiSelectUserWithDepts);
        return view(layout, "demon/select2", model);
    }





}
