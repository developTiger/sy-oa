package com.sunesoft.lemon.webapp.controller.workflow;

import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.ListResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.utils.JsonHelper;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.syms.eHr.application.EmployeeService;
import com.sunesoft.lemon.syms.eHr.application.dtos.EmpDto;
import com.sunesoft.lemon.syms.workflow.application.FormApproveListService;
import com.sunesoft.lemon.syms.workflow.application.FormApproveRoleService;
import com.sunesoft.lemon.syms.workflow.application.FormListService;
import com.sunesoft.lemon.syms.workflow.application.criteria.FormApproveRoleCriteria;
import com.sunesoft.lemon.syms.workflow.application.criteria.FormListCriteria;
import com.sunesoft.lemon.syms.workflow.application.dtos.FormAppListDto;
import com.sunesoft.lemon.syms.workflow.application.dtos.FormAppRoleDto;
import com.sunesoft.lemon.syms.workflow.application.dtos.FormListDto;
import com.sunesoft.lemon.syms.workflow.domain.FormApproveList;
import com.sunesoft.lemon.webapp.controller.Layout;
import com.sunesoft.lemon.webapp.controller.uAuth.LoginController;
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
import java.util.List;

/**
 * Created by swb on 2016/6/24.
 */
@Controller
public class FormApproveListController extends Layout {
    @Autowired
    FormListService formListService;
    @Autowired
    FormApproveListService formApproveListService;
    @Autowired
    FormApproveRoleService formApproveRoleService;
    @Autowired
    EmployeeService employeeService;

    @RequestMapping("_setFormAppList")
    public ModelAndView setFormAppList(HttpServletRequest request, HttpServletResponse response, Model model) {
        String id = request.getParameter("idTemp");
        if (!StringUtils.isNullOrWhiteSpace(id)) {
            FormListDto formListDto = formListService.getByKey(Long.parseLong(id));
            model.addAttribute("fmListDto", formListDto);
        }
        return view(layout, "workflow/formApproveList", model);
    }

    @RequestMapping("_queryFormApproveList")
    public void queryFormApproveList(HttpServletRequest request, HttpServletResponse response, Model model, FormListCriteria criteria) {
//        String formKind=request.getParameter("fd");
//        ListResult<FormApproveList> list=new ListResult<FormApproveList>(formApproveListService.getApproveListByFormKind(formKind));
//        String json= JsonHelper.toJson(list);
//        AjaxResponse.write(response,json);
//        criteria.setAppUserId(URI.deURI(criteria.getAppUserId().toString()));
        ListResult<FormAppListDto> list = new ListResult<FormAppListDto>(formApproveListService.getApproveListByCriteria(criteria));
        String json = JsonHelper.toJson(list);
        AjaxResponse.write(response, json);
    }

    @RequestMapping("_addFormApproveList")
    public ModelAndView addFormApproveList(Model model, HttpServletRequest request, HttpServletResponse response, FormApproveRoleCriteria criteria) {
        String formKind = request.getParameter("fd1");
        model.addAttribute("formKind", formKind);

        /*PagedResult<FormAppRoleDto> pagedResult=formApproveRoleService.getFormListPaged(criteria);
        model.addAttribute("roler",pagedResult);*/

        List<FormAppRoleDto> result = formApproveRoleService.getAll();
        model.addAttribute("roler", result);

        List<EmpDto> list = employeeService.getAllEmps();
        model.addAttribute("empuser", list);

        return view("workflow/_addFormApproveList", model);
    }

    @RequestMapping(value = "_add_Update_formAppList", method = RequestMethod.POST)
    public void ajaxAddOrUpdate(FormAppListDto dto, HttpServletRequest request, HttpServletResponse response, Model model) {
        String id = request.getParameter("hidId");


        String formKind = request.getParameter("formKind");
        String appUserId = request.getParameter("appUserId");
        String appName = request.getParameter("appName");

        String appSerial = request.getParameter("appSerial");
        String appRole = request.getParameter("appRoleId");

        if(!dto.getByDept()){
            dto.setDeptType(0);
        }
        //String appActor=request.getParameter("appActor");
        //String appName=request.getParameter("appName");
        //String approveType=request.getParameter("approveType");

        dto.setFormKind(formKind);
        dto.setAppName(appName);
        //dto.setAppActor(Long.parseLong(appActor));
        //dto.setAppActorName(appName);
        dto.setAppRoleId(Long.parseLong(appRole));


        //edit by  zhouzh
        dto.setAppSerial(Integer.parseInt(appSerial));
        //end edit
        CommonResult commonResult=null;
        if (!StringUtils.isNullOrWhiteSpace(id)){
            dto.setId(Long.parseLong(id));
            commonResult = formApproveListService.UpdateFormApproveList(dto);
        }else {
            commonResult= formApproveListService.AddFormApproveList(dto);
        }
        AjaxResponse.write(response, "text", JsonHelper.toJson(commonResult));

    }

    @RequestMapping("_deleteFormAppList")
    public void deleteEmp(HttpServletRequest request, HttpServletResponse response, Model model) {
        String id = request.getParameter("idDel");
        CommonResult result = formApproveListService.DeleteFormApproveList(Long.parseLong(id));
        AjaxResponse.write(response, "text", JsonHelper.toJson(result));
    }

    @RequestMapping(value = "_editFormApproveList")
    public ModelAndView editEmp(HttpServletRequest request,Model model){
        String id = request.getParameter("idDel");
        if (!StringUtils.isNullOrWhiteSpace(id)){
            FormAppListDto formAppListDto=formApproveListService.getById(Long.parseLong(id));
            model.addAttribute("beans",formAppListDto);
        }
        String formKind = request.getParameter("fd1");
        model.addAttribute("formKind", formKind);
        List<FormAppRoleDto> result = formApproveRoleService.getAll();
        model.addAttribute("roler", result);
        return view("workflow/_editFormApproveList",model);
    }

}
