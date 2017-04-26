package com.sunesoft.lemon.webapp.controller.equipForms;

import com.sunesoft.lemon.ay.equimentForms.application.formEquiMain.FormEquiMainService;
import com.sunesoft.lemon.ay.equipment.application.EquipmentService;
import com.sunesoft.lemon.ay.equipment.application.dtos.EquipmentDto;
import com.sunesoft.lemon.ay.equimentForms.application.dtos.FormEquiMainDto;
import com.sunesoft.lemon.ay.equipment.domain.Equipment;
import com.sunesoft.lemon.ay.equipment.domain.EquipmentStation;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.utils.DateHelper;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.syms.eHr.application.DeptmentService;
import com.sunesoft.lemon.syms.eHr.application.dtos.DeptmentDto;
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
import java.util.Date;
import java.util.List;

/**
 * Created by admin on 2016/8/15.
 */
@Controller
public class EquipmentMaintenanceController extends Layout {

    @Autowired
    FormEquiMainService formEquiMainService;

    @Autowired
    EquipmentService equipmentService;
    @Autowired
    DeptmentService deptmentService;

    @Autowired
    UserSession us;
    @Autowired
    FormListService formListService;
    @RequestMapping(value = "syy_ay_lc02_a")
    public ModelAndView index(Model model,HttpServletRequest request){
        FormListDto dto =formListService.getFormListInfo("SYY_AY_LC02");
        String formNo=request.getParameter("formNo");
        model.addAttribute("formKind", dto.getFormKind());
        model.addAttribute("formKindName", dto.getFormName());
        model.addAttribute("date", DateHelper.formatDate(new Date(), "yyyy-MM-dd"));

        List<DeptmentDto> depats = deptmentService.getAllDept();

        model.addAttribute("depats",depats);
        //todo T1节点返回的时候涉及到修该
        if(!StringUtils.isNullOrWhiteSpace(formNo)){

            FormEquiMainDto formEquiMainDto=formEquiMainService.getFormByFormNo(Long.parseLong(formNo));
            //TODO 因为申请的时候设备已经被修改为“申请维修状态”，所以在列表中看不到该设备了，这里需要再将设备改为正常状态
            CommonResult c=equipmentService.setStation(formEquiMainDto.getResId(),EquipmentStation.Normal);
            if(c.getIsSuccess())
            model.addAttribute("formEquiMain",formEquiMainDto);
            model.addAttribute("formNo",formEquiMainDto.getFormNo());
        }
        List<EquipmentDto> list= equipmentService.getRepairAll(EquipmentStation.Normal);//正常的设备才可以送去维修
        model.addAttribute("beans",list);

        return view(applyLayout,"equipForms/syy_ay_lc02_a",model);
    }

    @RequestMapping(value = "ajax_add_update_equipmentMaintenance",method = RequestMethod.POST)
    @ResponseBody
    public CommonResult addOrUpdateEquipmentManitenance(HttpServletRequest request,FormEquiMainDto formEquiMainDto){
//        String time = request.getParameter("time");
        EmpSessionDto empSessionDto = us.getCurrentUser(request);

        String hiddenEquipmentNumber = request.getParameter("hiddenEquipmentNumber");//设备编号
        String equipmentId = request.getParameter("name");//设备id
        String productTime = request.getParameter("wproductTime");

//        String expectDate = request.getParameter("wexpectDate");
//       formEquiMainDto.setExpectDate(DateHelper.parse(expectDate,"yyyy-MM-dd"));

        formEquiMainDto.setApplyer(empSessionDto.getId());
        formEquiMainDto.setApplyerName(empSessionDto.getName());
        formEquiMainDto.setDeptId(empSessionDto.getDeptId());
        formEquiMainDto.setDeptName(empSessionDto.getDeptName());

        formEquiMainDto.setResNum(hiddenEquipmentNumber.trim());
        formEquiMainDto.setResId(Long.parseLong(equipmentId));
        EquipmentDto equipmentDto = new EquipmentDto();

        equipmentDto.setId(Long.parseLong(equipmentId));
        formEquiMainDto.setEquipmentDto(equipmentDto);
        formEquiMainDto.setUseDate(DateHelper.parse(productTime,"yyyy-MM-dd"));
        CommonResult commonResult=null;
        String formNo=request.getParameter("formNo");
        if(StringUtils.isNullOrWhiteSpace(formNo)) {
            commonResult = formEquiMainService.addByDto(formEquiMainDto);
        }else {
            formEquiMainDto.setFormNo(Long.parseLong(formNo));
            commonResult=formEquiMainService.updateByDto(formEquiMainDto);
        }
        if(commonResult.getIsSuccess()){
            //将该设备设置为已申请维修状态
            CommonResult station=equipmentService.setStation(Long.parseLong(equipmentId),EquipmentStation.applied);
            if(!station.getIsSuccess()){
                return station;
            }
        }else{
            return null;
        }
        return  formEquiMainService.submitForm(commonResult.getId(),formEquiMainDto.getFormKind());
    }

    @RequestMapping(value = "ajax_syy_ay_lc02_data")
    @ResponseBody
    public FormEquiMainDto queryData(HttpServletRequest request){
        String formNo = request.getParameter("formNo");
        String viewOnly = request.getParameter("viewOnly");
        FormEquiMainDto formEquiMainDto=formEquiMainService.getFormByFormNo(Long.parseLong(formNo));
        formEquiMainDto.setIsViewOnly(Boolean.valueOf(viewOnly));
        return formEquiMainDto;
    }

    @RequestMapping(value = "ajax_query_equipmentInfo")
    @ResponseBody
    public EquipmentDto queryEquipmentInfo(HttpServletRequest request){
        String id = request.getParameter("id");
        EquipmentDto equipmentDto=equipmentService.getEquipById(Long.parseLong(id));
        return equipmentDto;
    }

}
