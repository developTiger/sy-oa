package com.sunesoft.lemon.webapp.controller.uAuth;

import com.sun.jndi.toolkit.url.Uri;
import com.sunesoft.lemon.fr.loggers.Logger;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.utils.JsonHelper;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.syms.eHr.application.DeptmentService;
import com.sunesoft.lemon.syms.eHr.application.EmpGroupService;
import com.sunesoft.lemon.syms.eHr.application.EmployeeService;
import com.sunesoft.lemon.syms.eHr.application.criteria.DeptmentCriteria;
import com.sunesoft.lemon.syms.eHr.application.criteria.GroupCriteria;
import com.sunesoft.lemon.syms.eHr.application.dtos.DeptmentDto;
import com.sunesoft.lemon.syms.eHr.application.dtos.EmpDto;
import com.sunesoft.lemon.syms.eHr.application.dtos.EmpGroupDto;
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
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhouz on 2016/5/19.
 */
@Controller
public class EmpGroupAndDeptController extends Layout {

    @Autowired
    Logger logger;

    @Autowired
    DeptmentService deptmentService;

    @Autowired
    EmpGroupService empGroupService;

    @Autowired
    EmployeeService employeeService;

    @RequestMapping(value = "sra_u_dept")
    public ModelAndView userInfos(Model model) {
        return view(layout, "uAuth/queryDepartment", model);
    }

    @RequestMapping(value="sra_u_group")
    public ModelAndView userInfo(Model model,HttpServletRequest request,HttpServletResponse response){
        return view(layout,"/uAuth/queryEmpGroup",model);
    }

    @RequestMapping(value="_addDeptForm")
    public ModelAndView addMenuForm(Model model, HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        if (!StringUtils.isNullOrWhiteSpace(id)) {
            List<Long> ids=new ArrayList<>();
            ids.add(Long.parseLong(id));
            List<DeptmentDto> deptmentDtos = deptmentService.getByDeptsIds(ids);

            model.addAttribute("bean", deptmentDtos.get(0));
        }
        DeptmentCriteria deptmentCriteria=new DeptmentCriteria();
        deptmentCriteria.setDeptName("");
        deptmentCriteria.setPageSize(100);
        PagedResult<DeptmentDto> pagedResult=deptmentService.findDeptsPaged(deptmentCriteria);
        if(pagedResult.getItems().size()>0) {
            model.addAttribute("permGroup", pagedResult.getItems());
        }else{
            model.addAttribute("permGroup", null);
        }

        List<EmpDto> yuanLeader=employeeService.getAllLeaders("01YLD");
//        List<EmpDto> yuanLeader = new ArrayList<>();
//        for (EmpDto empDto:list){
//            if (empDto.getDeptId() == 2055){
//                yuanLeader.add(empDto);
//            }
//        }
        model.addAttribute("yuanLeader",yuanLeader);

        return view("uAuth/_addDeptForm", model);
    }

    @RequestMapping(value = "ajax_dept_query_list")
    public void deptQuery_PageList(DeptmentCriteria criteria, HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter("deptName");
        if (!StringUtils.isNullOrWhiteSpace(name))
        criteria.setDeptName(URI.deURI(name));
        PagedResult PagedResult =deptmentService.findDeptsPaged(criteria);
        String json = JsonHelper.toJson(PagedResult);
        System.out.println(json);
        AjaxResponse.write(response, json);
    }

    @RequestMapping(value = "ajax_add_update_dept", method = RequestMethod.POST)
    public void addOrUpdateUser(DeptmentDto deptmentDto, HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        String hidName = request.getParameter("hidName");
        if (!StringUtils.isNullOrWhiteSpace(hidName))
            deptmentDto.setChargeLeaderName(hidName);

        CommonResult commonResult=null;
        if (!StringUtils.isNullOrWhiteSpace(id)) {
            deptmentDto.setId(Long.parseLong(id));
            commonResult=deptmentService.updateDept(deptmentDto);

        }else {
            commonResult = deptmentService.addDept(deptmentDto);
        }
        if(commonResult==null){
            commonResult=new CommonResult(true,"ddd");
        }
        String json=JsonHelper.toJson(commonResult);

        AjaxResponse.write(response,json);
    }

    @RequestMapping(value="ajax_deleteDept", method = RequestMethod.POST)
    public void ajax_deleteMenu(HttpServletRequest request,HttpServletResponse response){
        String ids = request.getParameter("ids");
        List<Long> listId = new ArrayList<>();
        if(ids!=null && !"".equals(ids)) {
            String[] stringDs = ids.split(",");
            for (String id : stringDs) {
                listId.add(Long.parseLong(id));
            }
        }
        if (listId.size() == 0) {
            AjaxResponse.write(response, "text", "请选择要操作的部门");
        } else {
            CommonResult commonResult = deptmentService.deleteDept(listId);
            String json=JsonHelper.toJson(commonResult);
            AjaxResponse.write(response,json);
        }
    }

    /*
     * 员工组
     */
    @RequestMapping(value="_addEmpGroupForm")
    public ModelAndView _addEmpGroupForm(Model model, HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        if (!StringUtils.isNullOrWhiteSpace(id)) {
            List<Long> ids=new ArrayList<>();
            ids.add(Long.parseLong(id));
            List<EmpGroupDto> empGroupDtos = empGroupService.getByGroupsIds(ids);
            model.addAttribute("bean", empGroupDtos.get(0));
        }
        GroupCriteria groupCriteria=new GroupCriteria();
        groupCriteria.setName("");
        List<EmpGroupDto> pagedResult=empGroupService.getGroupsByName("");
        model.addAttribute("permGroup",pagedResult);
        return view("uAuth/_addEmpGroupForm", model);
    }

    @RequestMapping(value = "ajax_EmpGroup_query_list")
    public void EmpGroupQuery_PageList(GroupCriteria criteria, HttpServletRequest request, HttpServletResponse response) {
        String roleName = request.getParameter("name");
        if (!StringUtils.isNullOrWhiteSpace(roleName)){
            criteria.setName(URI.deURI(roleName));
        }
        PagedResult PagedResult =empGroupService.findGroupsPaged(criteria);
        String json = JsonHelper.toJson(PagedResult);
        AjaxResponse.write(response, json);
    }

    @RequestMapping(value = "ajax_add_update_empGroup", method = RequestMethod.POST)
    public void addOrUpdateEmpGroup(EmpGroupDto empGroupDto, HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        CommonResult commonResult=null;
        if (!StringUtils.isNullOrWhiteSpace(id)) {
            empGroupDto.setId(Long.parseLong(id));
            commonResult=empGroupService.updateGroup(empGroupDto);

        }else {
            commonResult = empGroupService.addGroup(empGroupDto);
        }
        if(commonResult==null){
            commonResult=new CommonResult(true,"ddd");
        }
        String json=JsonHelper.toJson(commonResult);

        AjaxResponse.write(response,json);
    }

    @RequestMapping(value="ajax_deleteEmpGroup", method = RequestMethod.POST)
    public void ajax_deleteDept(HttpServletRequest request,HttpServletResponse response){
        String ids = request.getParameter("ids");
        List<Long> listId = new ArrayList<>();
        if(ids!=null && !"".equals(ids)) {
            String[] stringDs = ids.split(",");
            for (String id : stringDs) {
                listId.add(Long.parseLong(id));
            }
        }
        if (listId.size() == 0) {
            AjaxResponse.write(response, "text", "请选择要操作的员工组");
        } else {
            CommonResult commonResult = empGroupService.deleteGroup(listId);
            String json=JsonHelper.toJson(commonResult);
            AjaxResponse.write(response,json);
        }
    }
}
