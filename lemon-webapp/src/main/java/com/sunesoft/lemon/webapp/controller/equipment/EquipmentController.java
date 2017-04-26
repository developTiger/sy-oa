package com.sunesoft.lemon.webapp.controller.equipment;

import com.sunesoft.lemon.ay.equimentForms.application.dtos.FormAssessContentDto;
import com.sunesoft.lemon.ay.equimentForms.application.dtos.FormAssessmentDto;
import com.sunesoft.lemon.ay.equimentForms.application.formAssessment.FormAssessmentService;
import com.sunesoft.lemon.ay.equipment.application.AssessContentService;
import com.sunesoft.lemon.ay.equipment.application.Critera.AssessContentCritera;
import com.sunesoft.lemon.ay.equipment.application.Critera.EquipmentCriteria;
import com.sunesoft.lemon.ay.equipment.application.Critera.EquipmentMaintenanceCriteria;
import com.sunesoft.lemon.ay.equipment.application.EquipmentMaintenanceService;
import com.sunesoft.lemon.ay.equipment.application.EquipmentService;
import com.sunesoft.lemon.ay.equipment.application.dtos.AssessContentDto;
import com.sunesoft.lemon.ay.equipment.application.dtos.AssessmentDto;
import com.sunesoft.lemon.ay.equipment.application.dtos.EquipmentDto;
import com.sunesoft.lemon.ay.equipment.application.dtos.EquipmentMaintenanceDto;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.ListResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.results.ResultFactory;
import com.sunesoft.lemon.fr.utils.DateHelper;
import com.sunesoft.lemon.fr.utils.JsonHelper;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.syms.eHr.application.DeptmentService;
import com.sunesoft.lemon.syms.eHr.application.dtos.DeptmentDto;
import com.sunesoft.lemon.syms.eHr.application.dtos.EmpSessionDto;
import com.sunesoft.lemon.webapp.controller.Layout;
import com.sunesoft.lemon.webapp.function.UserSession;
import com.sunesoft.lemon.webapp.utils.AjaxResponse;
import com.sunesoft.lemon.webapp.utils.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by liulin on 2016/8/13.
 */
@Controller
public class EquipmentController extends Layout {

    @Autowired
    EquipmentService equipmentService;

    @Autowired
    EquipmentMaintenanceService equipmentMaintenanceService;

    @Autowired
    AssessContentService assessContentService;

    @Autowired
    FormAssessmentService formAssessmentService;
    @Autowired
    UserSession us;

    @Autowired
    DeptmentService deptmentService;


    @RequestMapping(value = "sra_e_e")
    public ModelAndView index(Model model) {
        return view(layout, "equipment/equipmentIndex", model);
    }

    @RequestMapping(value = "sra_e_mg")
    public ModelAndView equipment_manage_index(Model model, HttpServletRequest request) {

        List<DeptmentDto> list = deptmentService.getAllDept();
        model.addAttribute("depts", list);
        int canDeleteAll = 0;
        EmpSessionDto dto = us.getCurrentUser(request);
        if (!StringUtils.isNullOrWhiteSpace(dto.getDeptNo()) && dto.getDeptNo().equals("06ZLAQYX")) {
            canDeleteAll = 1;
            model.addAttribute("canDeleteAll", canDeleteAll);//安运科可以删除
        }

        return view(layout, "equipment/manageEquipmentIndex", model);
    }

    @RequestMapping(value = "ajax_equipment_query_list")
    public void queryData(HttpServletResponse response, EquipmentCriteria equipmentCriteria, HttpServletRequest request) {
        String measuringName = request.getParameter("wmeasuringName");
        //汉字的处理
        if (!StringUtils.isNullOrWhiteSpace(measuringName))
            equipmentCriteria.setMeasuringName(URI.deURI(measuringName));
        equipmentCriteria.setBeginDate(null);
        equipmentCriteria.setEndDate(null);
        String begin = request.getParameter("beginDate");
        begin = begin.replace("%3A", ":");
        if (!StringUtils.isNullOrWhiteSpace(begin))
            equipmentCriteria.setBeginDate(begin);
        String end = request.getParameter("endDate");
        end = end.replace("%3A", ":");
        if (!StringUtils.isNullOrWhiteSpace(end))
            equipmentCriteria.setEndDate(end);

        EmpSessionDto empSessionDto = us.getCurrentUser(request);
        equipmentCriteria.setDeptId(String.valueOf(empSessionDto.getDeptId()));

        PagedResult<EquipmentDto> pagedResult = equipmentService.getPageEquipmentDto(equipmentCriteria);
        String json = JsonHelper.toJson(pagedResult);
        AjaxResponse.write(response, json);
    }

    @RequestMapping(value = "detailEquipmentInfo")
    public ModelAndView detailEquipmentInfo(Model model, HttpServletRequest request) {
        String id = request.getParameter("id");
        Long userId = us.getCurrentUser(request).getId();
        if (!StringUtils.isNullOrWhiteSpace(id)) {
            EquipmentDto equipmentDto = equipmentService.getEquipById(Long.parseLong(id));
            model.addAttribute("equipmentDto", equipmentDto);
            if (equipmentDto != null && equipmentDto.getSavePersonId() != null) {
                model.addAttribute("canOpera", equipmentDto.getSavePersonId().equals(userId) ? 1 : 0);
            } else {
                model.addAttribute("canOpera", 0);
            }
        }

        return view(layout, "equipment/detailEquipmentInfo", model);
    }

    @RequestMapping(value = "ajax_query_equipment")
    @ResponseBody
    public EquipmentDto queryEquipment(HttpServletResponse response, HttpServletRequest request) {
        String id = request.getParameter("id");
        EquipmentDto equipmentDto = null;
        if (!StringUtils.isNullOrWhiteSpace(id))
            equipmentDto = equipmentService.getEquipById(Long.parseLong(id));
        return equipmentDto;
    }

    @RequestMapping(value = "ajax_query_equipmentMaintenance")
    @ResponseBody
    public PagedResult<EquipmentMaintenanceDto> queryEquipmentMaintenance(EquipmentMaintenanceCriteria criteria, HttpServletResponse response, EquipmentMaintenanceCriteria equipmentMaintenanceCriteria, HttpServletRequest request) {

        String id = request.getParameter("id");
        PagedResult<EquipmentMaintenanceDto> pagedResult = null;
        if (!StringUtils.isNullOrWhiteSpace(id)) {
            pagedResult = equipmentMaintenanceService.getPageEquipmentMaintenance(criteria, Long.parseLong(id));
        }
        return pagedResult;
    }

    @RequestMapping(value = "ajax_query_technologyJudgementInfo")
    @ResponseBody
    public PagedResult<AssessmentDto> queryTechnologyJudgement(AssessContentCritera criteria, HttpServletRequest request) {


        String id = request.getParameter("id");
        PagedResult<AssessmentDto> list = null;
        if (!StringUtils.isNullOrWhiteSpace(id)) {
            list = formAssessmentService.getPages(criteria, Long.parseLong(id));
        }

        return list;
    }

//    @RequestMapping(value = "ajax_query_equipmentResult")
//    public void queryEquipmentResult(HttpServletResponse response) {
//        List list = new ArrayList();
//        for (int i = 0; i < 9; i++) {
//            Result result = new Result();
//            result.setName("数控抽床");
//            result.setConfirmer("李四");
//            result.setEquipmentMaintenances("设备运行正常");
//            result.setEquipments("机箱");
//            result.setNum("0223" + i);
//            result.setEquipmentStatuses("维修");
//            result.setPeriodDate(new Date());
//            result.setVerification(new Date());
//            result.setVerifyDate(new Date());
//
//            list.add(result);
//        }
//        PagedResult p = new PagedResult(list, 1, 10, 1);
//        String json = JsonHelper.toJson(p);
//        AjaxResponse.write(response, json);
//    }
//
//    @RequestMapping(value = "ajax_query_equipmentStatus")
//    public void queryEquipmentStatus(HttpServletResponse response) {
//        List list = new ArrayList();
//        for (int i = 0; i < 9; i++) {
//            Sta status = new Sta();
//            status.setName("数控抽床");
//            status.setPerson("张三");
//            status.setRunTime(new Date());
//            status.setStatus("在使用");
//            status.setStopTime(new Date());
//            status.setWorkContent("工作内容" + i);
//
//            list.add(status);
//        }
//        PagedResult p = new PagedResult(list, 1, 10, 1);
//        String json = JsonHelper.toJson(p);
//        AjaxResponse.write(response, json);
//    }


    @RequestMapping(value = "ajax_equiManage_query_list")
    public void queryData_1(HttpServletResponse response, EquipmentCriteria equipmentCriteria, HttpServletRequest request) {
        String measuringName = request.getParameter("wmeasuringName");
        //汉字的处理
        if (!StringUtils.isNullOrWhiteSpace(measuringName))
            equipmentCriteria.setMeasuringName(URI.deURI(measuringName));
        equipmentCriteria.setBeginDate(null);
        equipmentCriteria.setEndDate(null);
        String begin = request.getParameter("beginDate");
        begin = begin.replace("%3A", ":");
        if (!StringUtils.isNullOrWhiteSpace(begin))
            equipmentCriteria.setBeginDate(begin);
        String end = request.getParameter("endDate");
        end = end.replace("%3A", ":");
        if (!StringUtils.isNullOrWhiteSpace(end))
            equipmentCriteria.setEndDate(end);

        String useDepartment = request.getParameter("useDepartment");


        EmpSessionDto empSessionDto = us.getCurrentUser(request);
//        equipmentCriteria.setDeptId(String.valueOf(empSessionDto.getDeptId()));


        //增加cd的判断，这种逻辑不该放在这
        if (empSessionDto.getDeptNo().equals("06zlaqyx"))//TODO 部门编码 hotfix
            equipmentCriteria.setDeptId(null);

        if (!StringUtils.isNullOrWhiteSpace(useDepartment))
            equipmentCriteria.setDeptId(useDepartment);

        PagedResult<EquipmentDto> pagedResult = equipmentService.getPageEquipmentDto(equipmentCriteria);
        if (!StringUtils.isNullOrWhiteSpace(empSessionDto.getDeptNo()) && empSessionDto.getDeptNo().equals("06ZLAQYX"))
            pagedResult.setCanOpera(1);
        String json = JsonHelper.toJson(pagedResult);
        AjaxResponse.write(response, json);
    }

    @RequestMapping("delete_one_equipment")
    @ResponseBody
    public CommonResult deleteOne(HttpServletRequest request, HttpServletResponse response) {
        CommonResult result = null;
        String id = request.getParameter("id");
        if (!StringUtils.isNullOrWhiteSpace(id)) {
            result = equipmentService.deleteOne(Long.parseLong(id));
        } else {
            result = ResultFactory.commonError("删除失败！");
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("delete_more_equipments")
    public CommonResult deleteMore(HttpServletRequest request, HttpServletResponse response) {
        String ids = request.getParameter("ids");
        CommonResult result = null;
        if (!StringUtils.isNullOrWhiteSpace(ids)) {
            List<Long> list = new ArrayList<>();
            for (String s : ids.split(",")) {
                list.add(Long.parseLong(s));
            }
            result = equipmentService.deleteMore(list);
        } else {
            result = ResultFactory.commonError("请选择删除的设备！");
        }
        return result;
    }
}
