package com.sunesoft.lemon.webapp.controller.workflow;

import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.utils.JsonHelper;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.syms.eHr.application.DeptmentService;
import com.sunesoft.lemon.syms.eHr.application.EmployeeService;
import com.sunesoft.lemon.syms.eHr.application.dtos.EmpSimpleDto;
import com.sunesoft.lemon.syms.eHr.application.dtos.MultiSelectUserWithDeptDto;
import com.sunesoft.lemon.syms.workflow.application.FormApproveRoleService;
import com.sunesoft.lemon.syms.workflow.application.criteria.FormApproveRoleCriteria;
import com.sunesoft.lemon.syms.workflow.application.dtos.FormAppRoleDto;
import com.sunesoft.lemon.webapp.controller.Layout;
import com.sunesoft.lemon.webapp.controller.demo.MultiSelectUserWithDept;
import com.sunesoft.lemon.webapp.utils.AjaxResponse;
import com.sunesoft.lemon.webapp.utils.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by swb on 2016/6/21.
 */
@Controller
public class ApproveRoleController extends Layout {
    @Autowired
    FormApproveRoleService formApproveRoleService;
    @Autowired
    EmployeeService employeeService;

    @Autowired
    DeptmentService deptmentService;

    @RequestMapping("sra_fm_approveRole")
    public ModelAndView ApproveRoleInfo(Model model) {
        return view(layout, "workflow/queryApproveRole", model);
    }

    @RequestMapping("ajax_queryApproveRole")
    public void queryApproveRole(FormApproveRoleCriteria criteria, HttpServletResponse response, Model model) {
        criteria.setRoleName(URI.deURI(criteria.getRoleName()));
        PagedResult<FormAppRoleDto> pagedResult = formApproveRoleService.getFormListPaged(criteria);
        model.addAttribute("role", pagedResult);
        String json = JsonHelper.toJson(pagedResult);
        AjaxResponse.write(response, json);
    }

    @RequestMapping("_addOrUpdateAppRole")
    public ModelAndView addOrUpdateAppRole(HttpServletRequest request, Model model) {
        String id = request.getParameter("id");
        if (!StringUtils.isNullOrWhiteSpace(id)) {
            FormAppRoleDto formAppRoleDto = formApproveRoleService.getByKey(Long.parseLong(id));
            model.addAttribute("formAppRoleDto", formAppRoleDto);
        }

        return view("workflow/addApproveRole", model);
    }

    @RequestMapping("_editOrUpdateAppRole")
    public ModelAndView editOrUpdateAppRole(HttpServletRequest request, Model model) {
        String id = request.getParameter("id");
        if (!StringUtils.isNullOrWhiteSpace(id)) {
            FormAppRoleDto formAppRoleDto = formApproveRoleService.getByKey(Long.parseLong(id));
            model.addAttribute("formAppRoleDto", formAppRoleDto);
        }

        return view(layout, "workflow/editApproveRole", model);
    }

    @RequestMapping("ajax_add_Update_appRole")
    public void addOrUpdate(FormAppRoleDto dto, HttpServletRequest request, HttpServletResponse response, Model model) {
        CommonResult result;
        Long id = dto.getId();
        if (id != null && id > 0) {
            result = formApproveRoleService.update(dto);
        } else {
            result = formApproveRoleService.add(dto);
        }
        AjaxResponse.write(response, "text", JsonHelper.toJson(result));
    }

    @RequestMapping(value = "ajax_edit_Update_appRole")
    public void editOrUpdate(FormAppRoleDto dto, HttpServletRequest request, HttpServletResponse response, Model model) {
        CommonResult result;
        Long id = dto.getId();
        if (id != null && id > 0) {
            if (dto.getRoleName().equals("APPLYER_SELF") || dto.getRoleName().equals("CHARGE_LEADERS")||dto.getRoleName().equals("CHARGE_PROFICIENT"))

                result = new CommonResult(false, "内置角色请勿修改!");
            else
                result = formApproveRoleService.update(dto);
        } else {
            result = formApproveRoleService.add(dto);
        }
        AjaxResponse.write(response, "text", JsonHelper.toJson(result));
    }

    @RequestMapping("_deleteAppRole")
    public ModelAndView deleteAppRole(HttpServletRequest request, Model model) {
        String id = request.getParameter("id");
        model.addAttribute("aidTemp", id);
        return view("workflow/deleteApproveRole", model);
    }

    @RequestMapping("ajax_delete_ARole_id")
    public void deleteAppRoleId(HttpServletRequest request, HttpServletResponse response, Model model) {
        String id = request.getParameter("id");
        CommonResult commonResult = formApproveRoleService.delete(Long.parseLong(id));
        AjaxResponse.write(response, "text", commonResult != null ? "success" : "error");
    }

    @RequestMapping("ajax_query_emp")
    public void getAllEmps(Model model, HttpServletRequest request, HttpServletResponse response, FormApproveRoleCriteria criteria) {
        String id = request.getParameter("id");
        String roleName = request.getParameter("roleName");
        FormAppRoleDto formAppRoleDto = new FormAppRoleDto();
        if (!StringUtils.isNullOrWhiteSpace(id)) {
            formAppRoleDto = formApproveRoleService.getByKey(Long.parseLong(id));
            model.addAttribute("formAppRole", formAppRoleDto);
        }
//        PagedResult<FormAppRoleDto> pagedResult=formApproveRoleService.getFormListPaged(criteria);
//        model.addAttribute("result", pagedResult);
        //todo 用户查询按钮，对数据的处理
        if(!StringUtils.isNullOrWhiteSpace(roleName)&& formAppRoleDto.getEmpList()!=null&&formAppRoleDto.getEmpList().size()>0){
            roleName=URI.deURI(roleName.trim());
            List<EmpSimpleDto> dtos=new ArrayList<>();
            for(EmpSimpleDto dto: formAppRoleDto.getEmpList()){
                if(dto.getName()!=null&&dto.getName().contains(roleName)){
                    dtos.add(dto);
                }
            }
            formAppRoleDto.setEmpList(dtos);
        }
        String json = JsonHelper.toJson(formAppRoleDto);
        AjaxResponse.write(response, json);
    }

    @RequestMapping("_addEmp")
    public ModelAndView addEmpUser(HttpServletRequest request, Model model) {
        String roleId = request.getParameter("roleId");
        model.addAttribute("roleId", roleId);
        List<MultiSelectUserWithDeptDto>  empDtos =employeeService.getAllDeptEmp();

        model.addAttribute("empInfos",empDtos);
        return view("workflow/addEmpUser", model);
    }

    @RequestMapping("ajax_add_emp_appRole")
    public void addOrUpdateEmp(HttpServletRequest request, HttpServletResponse response, Model model) {
        String roleId = request.getParameter("roleId");
        String empIds = request.getParameter("empIds");
        String[]  arrempIds =empIds.split(",");
        List<Long> empids = new ArrayList<>();
        for(String id :arrempIds){
            empids.add(Long.parseLong(id));
        }
        CommonResult result = formApproveRoleService.addRoleEmp(Long.parseLong(roleId),empids);
        AjaxResponse.write(response, "text", JsonHelper.toJson(result));
    }

    @RequestMapping("_delEmp")
    public void deleteEmp(HttpServletRequest request, HttpServletResponse response, Model model) {
        String roleId = request.getParameter("roleId");
        String empId = request.getParameter("empId");
        CommonResult result = formApproveRoleService.removeRoleEmp(Long.parseLong(roleId), Long.parseLong(empId));
        AjaxResponse.write(response, "text", JsonHelper.toJson(result));
    }
}
