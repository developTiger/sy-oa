package com.sunesoft.lemon.webapp.controller.equipForms;

import com.sunesoft.lemon.ay.equimentForms.application.dtos.FormAssessContentDto;
import com.sunesoft.lemon.ay.equimentForms.application.formAssessment.FormAssessmentService;
import com.sunesoft.lemon.ay.equimentForms.application.formCheck.FormCheckService;
import com.sunesoft.lemon.ay.equipment.application.AssessContentService;
import com.sunesoft.lemon.ay.equipment.application.Critera.AssessContentCritera;
import com.sunesoft.lemon.ay.equipment.application.EquipmentService;
import com.sunesoft.lemon.ay.equipment.application.dtos.AssessContentDto;
import com.sunesoft.lemon.ay.equipment.application.dtos.EquipmentDto;
import com.sunesoft.lemon.ay.equimentForms.application.dtos.FormAssessmentDto;
import com.sunesoft.lemon.ay.equipment.domain.AssessContent;
import com.sunesoft.lemon.ay.equipment.domain.EquipmentStation;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.utils.DateHelper;
import com.sunesoft.lemon.fr.utils.JsonHelper;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.syms.eHr.application.dtos.EmpSessionDto;
import com.sunesoft.lemon.syms.workflow.application.FormListService;
import com.sunesoft.lemon.syms.workflow.application.dtos.FormListDto;
import com.sunesoft.lemon.webapp.controller.Layout;
import com.sunesoft.lemon.webapp.function.UserSession;
import com.sunesoft.lemon.webapp.model.AssessmentModel;
import com.sunesoft.lemon.webapp.utils.AjaxResponse;
import com.sunesoft.lemon.webapp.utils.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 设备评估单 设备基本信息+设备评估内容
 * Created by admin on 2016/8/15.
 */
@Controller
public class TechnologyJudgementController extends Layout {


    @Autowired
    AssessContentService assessContentService;

    @Autowired
    EquipmentService equipmentService;

    @Autowired
    FormAssessmentService formAssessmentService;

    @Autowired
    UserSession userSession;

    @Autowired
    FormListService formListService;

    /**
     * 首页
     *
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "syy_ay_lc05_a")
    public ModelAndView index(Model model, HttpServletRequest request) {
        FormListDto dto =formListService.getFormListInfo("SYY_AY_LC05");
        model.addAttribute("formKind", dto.getFormKind());
        model.addAttribute("formKindName", dto.getFormName());
        model.addAttribute("date", DateHelper.formatDate(new Date(), "yyyy-MM-dd"));
        String formNo=request.getParameter("formNo");
        List<EquipmentDto> list = equipmentService.getAll();
        //除掉报废的设备.在这里可以是正常设备，也可以是维修中的设备，申请维修的设备
        List<EquipmentDto> dellist=new ArrayList<>();
        for(EquipmentDto d:list){
            if(d.getCurrentStation()!=null&&d.getCurrentStation().equals(EquipmentStation.Scrap))
                dellist.add(d);//已经报废的设备就得除掉
        }
        list.removeAll(dellist);
        model.addAttribute("beans", list);
        if(!StringUtils.isNullOrWhiteSpace(formNo)){
            //TODO 申请评估的设备，在t1节点被驳回时，修改数据的展示
            FormAssessmentDto formAssessmentDto = formAssessmentService.getFormByFormNo(Long.parseLong(formNo));
            model.addAttribute("formAssessment",formAssessmentDto);
        }

        return view(applyLayout, "equipForms/syy_ay_lc05_a", model);
    }

    /**
     * 设备评估单 设备评估内容+表单 新增
     *
     * @param request
     * @param formAssessmentDto
     * @return
     */
    @RequestMapping(value = "ajax_add_update_technologyJudgement", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult addOrUpdateTechnologyJudgement(HttpServletRequest request, FormAssessmentDto formAssessmentDto) {
         EmpSessionDto empSessionDto = userSession.getCurrentUser(request);

        String id = request.getParameter("hiddenId");//通过id判断是新增还是修改
        String formNo = request.getParameter("formNo");//通过formNo判断表单是否存在
        String equipment_id = request.getParameter("equipment_id");//关联设备（id）

        String parameterName = URI.deURI(request.getParameter("parameterName").trim());
        String parameterRange = request.getParameter("parameterRange").trim();
        String testValue = URI.deURI(request.getParameter("testValue").trim());
        String conform = URI.deURI(request.getParameter("conform").trim());
        String testCrew = URI.deURI(request.getParameter("testCrew").trim());
        String suggest = URI.deURI(request.getParameter("suggest").trim());
        String implement = URI.deURI(request.getParameter("implement").trim());

        FormAssessContentDto formAssessContentDto = new FormAssessContentDto();
        formAssessContentDto.setParameterName(parameterName);
        formAssessContentDto.setParameterRange(parameterRange);
        formAssessContentDto.setTestValue(testValue);
        formAssessContentDto.setConform(conform);
        formAssessContentDto.setSuggest(suggest);
        formAssessContentDto.setTestCrew(testCrew);
        formAssessContentDto.setImplement(implement);

        EquipmentDto equipmentDto = new EquipmentDto();
        if (!StringUtils.isNullOrWhiteSpace(equipment_id)) {
            equipmentDto.setId(Long.parseLong(equipment_id));
            formAssessContentDto.setEquipmentDto(equipmentDto);
        }
        //修改
        if (!StringUtils.isNullOrWhiteSpace(id)) {
            formAssessContentDto.setId(Long.parseLong(id));
        }

        String hiddenName = request.getParameter("hiddenName");//设备名称
        formAssessmentDto.setName(hiddenName);

        //第一次直接创建表单，只保存表单必要的信息，生成formNo，接下来的提交都不需要创建formNo、
        //formKind等表单字段，只需要保存内容即可
        // 最终提交 保存表单所有信息 不包括content，因为content的字段formNo已经存在，没必要保存
        //保存的时候，两种情况需要考虑
        if (!StringUtils.isNullOrWhiteSpace(formNo)) {
            formAssessContentDto.setFormNo(Long.parseLong(formNo));
        } else {
            formAssessmentDto.setApplyer(empSessionDto.getId());
            formAssessmentDto.setApplyerName(empSessionDto.getName());
            formAssessmentDto.setDeptId(empSessionDto.getDeptId());
            formAssessmentDto.setDeptName(empSessionDto.getDeptName());
            CommonResult commonResult = formAssessmentService.addByDto(formAssessmentDto);
            formAssessContentDto.setFormNo(commonResult.getId());
        }
        return formAssessmentService.addOrUpdateContent(formAssessContentDto);
    }

    /**
     * 设备评估单 提交
     * @param request
     * @param formAssessmentDto
     * @return
     */
    @RequestMapping(value = "ajax_submit_technologyJudgement", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult submitTechnologyJudgement(HttpServletRequest request, FormAssessmentDto formAssessmentDto) {
        String formNo = request.getParameter("formNo");//通过formNo判断表单是否存在
        String equipment_id = request.getParameter("equipment_id");//关联设备（id）

        formAssessmentDto.setTime(new Date());//这是评估时间

        if (!StringUtils.isNullOrWhiteSpace(equipment_id)) {
            EquipmentDto equipmentDto = new EquipmentDto();
            equipmentDto.setId(Long.parseLong(equipment_id));
            formAssessmentDto.setEquipmentDto(equipmentDto);
        }

        if (!StringUtils.isNullOrWhiteSpace(formNo)) {
            CommonResult result = formAssessmentService.updateByDto(formAssessmentDto);
            if (result.getIsSuccess()) {
                formAssessmentService.submitForm(formAssessmentDto.getFormNo(), "SYY_AY_LC05");
            }
            return result;
        }
        return new CommonResult(false, "表单生成失败");
    }

    /**
     * 表单审核 数据展示
     * @param request
     * @return
     */
    @RequestMapping(value = "ajax_syy_ay_lc05_data")
    @ResponseBody
    public FormAssessmentDto queryDate(HttpServletRequest request) {
        String formNo = request.getParameter("formNo");
        String viewOnly = request.getParameter("viewOnly");
        FormAssessmentDto formAssessmentDto = formAssessmentService.getFormByFormNo(Long.parseLong(formNo));
        formAssessmentDto.setIsViewOnly(Boolean.valueOf(viewOnly));
        return formAssessmentDto;
    }

    /**
     *通过设备id查询设备信息
     * @param request
     * @return
     */
    @RequestMapping(value = "ajax_query_technologyJudgement")
    @ResponseBody
    public EquipmentDto queryEquipmentMaintenance(HttpServletRequest request) {
        String id = request.getParameter("id");
        EquipmentDto equipmentDto = equipmentService.getEquipById(Long.parseLong(id));
        return equipmentDto;
    }

    /**
     * 设备评估内容 新增和修改
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "ajax_add_update_addContent")
    @ResponseBody
    public String addContent(HttpServletRequest request) {
          /*String id = request.getParameter("id");//通过id判断是新增还是修改
        String formNo = request.getParameter("formNo");//通过formNo判断表单是否存在

        String parameterName = URI.deURI(request.getParameter("parameterName"));
        String parameterRange = request.getParameter("parameterRange");
        String testValue = URI.deURI(request.getParameter("testValue"));
        String conform = URI.deURI(request.getParameter("conform"));
        String testCrew = URI.deURI(request.getParameter("testCrew"));
        String suggest = URI.deURI(request.getParameter("suggest"));
        String implement = URI.deURI(request.getParameter("implement"));

        FormAssessContentDto formAssessContentDto = new FormAssessContentDto();
        formAssessContentDto.setParameterName(parameterName);
        formAssessContentDto.setParameterRange(parameterRange);
        formAssessContentDto.setTestValue(testValue);
        formAssessContentDto.setConform(conform);
        formAssessContentDto.setSuggest(suggest);
        formAssessContentDto.setTestCrew(testCrew);
        formAssessContentDto.setImplement(implement);

        if (!StringUtils.isNullOrWhiteSpace(id)){
            formAssessContentDto.setId(Long.parseLong(id));
        }
//        assessContentService.addOrUpdateContent(assessContentDto);
        formAssessmentService.addOrUpdateContent(formAssessContentDto);*/
        return "success";
    }

    /**
     * 设备评估内容 显示数据
     *
     * @param assessContentCritera
     * @param response
     */
    @RequestMapping(value = "ajax_query_addJudgementCon_info")
    public void queryAddJudgementContentInfo(AssessContentCritera assessContentCritera, HttpServletRequest request, HttpServletResponse response) {
        String formNo = request.getParameter("formNo");
        FormAssessmentDto dto = new FormAssessmentDto();
        if (!StringUtils.isNullOrWhiteSpace(formNo))
            dto = formAssessmentService.getFormByFormNo(Long.parseLong(formNo));

        String json = JsonHelper.toJson(dto);
        AjaxResponse.write(response, json);
    }

    /**
     * 设备评估内容 点击修改 页面显示数据
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "ajax_edit_judgementContent")
    @ResponseBody
    public FormAssessContentDto editjudgementContent(HttpServletRequest request) {
        String id = request.getParameter("id");
        FormAssessContentDto assessContent = null;
        if (!StringUtils.isNullOrWhiteSpace(id)) {
            assessContent = formAssessmentService.getContentById(Long.parseLong(id));
        }
        return assessContent;
    }

    /**
     * 设备评估内容 删除
     *
     * @param request
     */
    @RequestMapping(value = "ajax_delete_judgementContent")
    @ResponseBody
    public CommonResult deleteJudgementContent(HttpServletRequest request) {
        String id = request.getParameter("id");
        String formNo = request.getParameter("formNo");
        CommonResult commonResult = formAssessmentService.delContent(Long.parseLong(formNo),Long.parseLong(id));
        return commonResult;
    }
}
