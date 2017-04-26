package com.sunesoft.lemon.webapp.controller.projectInfo;

import com.sunesoft.lemon.deanery.car.application.ItemProjectService;
import com.sunesoft.lemon.deanery.car.application.criteria.DriverReportCriteria;

import com.sunesoft.lemon.deanery.car.domain.ItemProject;
import com.sunesoft.lemon.deanery.car.domain.SpecialtyType;
import com.sunesoft.lemon.deanery.project.application.ScientificResearchProjectService;
import com.sunesoft.lemon.deanery.project.application.criteria.ScientificResearchProjectCriteria;
import com.sunesoft.lemon.deanery.project.application.dtos.ScientificResearchProjectDto;
import com.sunesoft.lemon.deanery.project.application.dtos.ScientificResearchProjectRptDto;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.utils.JsonHelper;
import com.sunesoft.lemon.syms.eHr.application.DeptmentService;
import com.sunesoft.lemon.syms.eHr.application.dtos.DeptmentDto;
import com.sunesoft.lemon.syms.eHr.application.dtos.EmpDto;
import com.sunesoft.lemon.webapp.controller.Layout;
import com.sunesoft.lemon.webapp.utils.AjaxResponse;
import com.sunesoft.lemon.webapp.utils.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by user on 2016/6/28.
 */


@Controller
public class ProjectCountController extends Layout {
    @Autowired
    ScientificResearchProjectService scientificResearchProjectService;
    @Autowired
    DeptmentService deptmentService;
    @Autowired
    ScientificResearchProjectService srpService;
    @Autowired
    ItemProjectService itemProjectService;


    @RequestMapping(value = "sra_p_projectCount")
    public ModelAndView driverCountInfo(Model model) {
        return view(layout, "projectInfo/projectInfoCount", model);

    }

    @RequestMapping(value = "ajax_projectCount_query_list", method = RequestMethod.GET)
    public void getDriverlist(HttpServletRequest request, HttpServletResponse response, Model model) {
        ScientificResearchProjectRptDto scientificResearchProjectRptDto= scientificResearchProjectService.scientificResearchProjectRpt();
        String json = JsonHelper.toJson(scientificResearchProjectRptDto);
        AjaxResponse.write(response, json);
    }

    @RequestMapping(value = "ajax_project_query_list1")
    public ModelAndView a11ProjectInfo(ScientificResearchProjectCriteria criteria, Model model, HttpServletRequest request, HttpServletResponse response){
        model.addAttribute("projectYXStatus",criteria.getProjectStatus());
        List<DeptmentDto> dept= deptmentService.getAllDept();
        model.addAttribute("dept",dept);
        String dept1=request.getParameter("DEPTNAME");
        model.addAttribute("dept1",dept1);
        List<ItemProject> itemsList= itemProjectService.itemsList();
        model.addAttribute("itemsList",itemsList);
        return view(layout,"projectInfo/projectInfo",model);
    }
    @RequestMapping(value = "ajax_project_query_list2")
    public ModelAndView a11ProjectInfo1(Model model, HttpServletRequest request, HttpServletResponse response){
        String projectType= URI.deURI(request.getParameter("projectType")) ;
        model.addAttribute("projectType",projectType);
        List<ItemProject> itemsList= itemProjectService.itemsList();
        model.addAttribute("itemsList",itemsList);
        List<DeptmentDto> dept= deptmentService.getAllDept();
        model.addAttribute("dept",dept);
        String dept1=request.getParameter("DEPTNAME");
        model.addAttribute("dept1",dept1);
        return view(layout,"projectInfo/projectInfo",model);
    }

    @RequestMapping(value = "ajax_project_query_list3")
    public ModelAndView a11ProjectInfo3(ScientificResearchProjectCriteria criteria, Model model, HttpServletRequest request, HttpServletResponse response){
        List<DeptmentDto> dept= deptmentService.getAllDept();
        model.addAttribute("dept",dept);
        String dept1=request.getParameter("DEPTNAME");
        model.addAttribute("dept1",dept1);
        List<ItemProject> itemsList= itemProjectService.itemsList();
        model.addAttribute("itemsList",itemsList);
        return view(layout,"projectInfo/projectInfo",model);
    }

    @RequestMapping(value = "ajax_project_query_list4")
    public ModelAndView a11ProjectInfo4(ScientificResearchProjectCriteria criteria, Model model, HttpServletRequest request, HttpServletResponse response){
        List<DeptmentDto> dept= deptmentService.getAllDept();
        model.addAttribute("dept",dept);
        String dept1=request.getParameter("DEPTNAME");
        model.addAttribute("dept1",dept1);
        List<ItemProject> itemsList= itemProjectService.itemsList();
        model.addAttribute("itemsList",itemsList);
        model.addAttribute("niandu_Str",criteria.getNiandu_Str());
        return view(layout,"projectInfo/projectInfo",model);
    }
}