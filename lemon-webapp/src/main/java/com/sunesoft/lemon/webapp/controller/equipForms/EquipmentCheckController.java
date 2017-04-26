package com.sunesoft.lemon.webapp.controller.equipForms;

import com.sunesoft.lemon.ay.equimentForms.application.formCheck.FormCheckService;
import com.sunesoft.lemon.ay.equipment.application.EquipmentMaintenanceService;
import com.sunesoft.lemon.ay.equimentForms.application.dtos.FormCheckDto;
import com.sunesoft.lemon.ay.equipment.application.EquipmentService;
import com.sunesoft.lemon.ay.equipment.application.dtos.EquipmentDto;
import com.sunesoft.lemon.ay.equipment.application.dtos.EquipmentMaintenanceDto;
import com.sunesoft.lemon.ay.equipment.domain.EquipmentMaintenance;
import com.sunesoft.lemon.ay.equipment.domain.EquipmentStation;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.utils.DateHelper;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.syms.eHr.application.DeptmentService;
import com.sunesoft.lemon.syms.eHr.application.EmployeeService;
import com.sunesoft.lemon.syms.eHr.application.dtos.DeptmentDto;
import com.sunesoft.lemon.syms.eHr.application.dtos.EmpDto;
import com.sunesoft.lemon.syms.eHr.application.dtos.EmpSessionDto;
import com.sunesoft.lemon.syms.workflow.application.FormListService;
import com.sunesoft.lemon.syms.workflow.application.dtos.FormListDto;
import com.sunesoft.lemon.webapp.controller.Layout;
import com.sunesoft.lemon.webapp.function.UserSession;
import com.sunesoft.lemon.webapp.utils.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by admin on 2016/8/15.
 */
@Controller
public class EquipmentCheckController extends Layout {

    @Autowired
    EquipmentMaintenanceService equipmentMaintenanceService;

    @Autowired
    FormCheckService formCheckService;
    @Autowired
    FormListService formListService;
    @Autowired
    UserSession us;
    @Autowired
    EquipmentService equipmentService;
    @Autowired
    DeptmentService deptmentService;
    @Autowired
    EmployeeService employeeService;

    @RequestMapping(value = "syy_ay_lc03_a")
    public ModelAndView index(Model model, HttpServletRequest request) {
        FormListDto dto = formListService.getFormListInfo("SYY_AY_LC03");
        model.addAttribute("formKind", dto.getFormKind());
        model.addAttribute("formKindName", dto.getFormName());
        model.addAttribute("date", DateHelper.formatDate(new Date(), "yyyy-MM-dd"));

//        List<EquipmentMaintenance> list=equipmentMaintenanceService.getAll();
        List<EquipmentDto> list = equipmentService.getRepairAll(EquipmentStation.Repair);//获取到所有可维修的设备

        model.addAttribute("beans", list);
        List<DeptmentDto> depts=deptmentService.getAllDept();
        if(depts!=null&& depts.size()>0){
            model.addAttribute("depts",depts);//获取所有部门
        }
        List<EmpDto> empDtos=employeeService.getAllEmps();
        if(empDtos!=null&&empDtos.size()>0){
            model.addAttribute("emps",empDtos);//获取所有人员
        }

        String formNo=request.getParameter("formNo");
        if(!StringUtils.isNullOrWhiteSpace(formNo)) {
            FormCheckDto formCheckDto = formCheckService.getFormByFormNo(Long.parseLong(formNo));
            model.addAttribute("formCheck",formCheckDto);
            model.addAttribute("formNo",formCheckDto.getFormNo());
        }
        return view(applyLayout, "equipForms/syy_ay_lc03_a", model);
    }

    @RequestMapping(value = "ajax_query_equipmentData")
    @ResponseBody
    public EquipmentDto queryData(HttpServletRequest request) {
        String id = request.getParameter("id");
        EquipmentDto equipmentDto = equipmentService.getEquipById(Long.parseLong(id));
        return equipmentDto;
    }

    @RequestMapping(value = "ajax_add_update_equipmentCheck", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult addOrUpdateEquimentCheck(HttpServletRequest request, FormCheckDto formCheckDto) {
        EmpSessionDto empSessionDto = us.getCurrentUser(request);

        String name = request.getParameter("hiddenName");
        String testers = URI.deURI(request.getParameter("testers").replace(",","，"));//增加空格间距
        formCheckDto.setTesters(testers);//直接保存字符串，否则不好储存，
        formCheckDto.setApplyer(empSessionDto.getId());
        formCheckDto.setApplyerName(empSessionDto.getName());
        formCheckDto.setDeptId(empSessionDto.getDeptId());
        formCheckDto.setDeptName(empSessionDto.getDeptName());
        formCheckDto.setBlongDeptId(formCheckDto.getBelongUnitId());

        formCheckDto.setResName(name);
        String formNo=request.getParameter("formNo");
        CommonResult commonResult=null;
        if(StringUtils.isNullOrWhiteSpace(formNo)) {
             commonResult = formCheckService.addByDto(formCheckDto);
        }else {
            formCheckDto.setFormNo(Long.parseLong(formNo));
             commonResult = formCheckService.updateByDto(formCheckDto);
        }
        return formCheckService.submitForm(commonResult.getId(), formCheckDto.getFormKind());
    }

    @RequestMapping(value = "ajax_syy_ay_lc03_data")
    @ResponseBody
    public FormCheckDto queryFormCheckData(HttpServletRequest request) {
        String formNo = request.getParameter("formNo");
        String viewOnly = request.getParameter("viewOnly");
        FormCheckDto formCheckDto = formCheckService.getFormByFormNo(Long.parseLong(formNo));
        formCheckDto.setIsViewOnly(Boolean.valueOf(viewOnly));
        return formCheckDto;
    }


}

