package com.sunesoft.lemon.webapp.controller.equipForms;

import com.sunesoft.lemon.ay.equimentForms.application.dtos.CheckEquipInfo;
import com.sunesoft.lemon.ay.equimentForms.application.equipmentFactory.UploadEquipment;
import com.sunesoft.lemon.ay.equimentForms.application.formEquiment.FormEquimentService;
import com.sunesoft.lemon.ay.equimentForms.application.dtos.FormEquipmentDto;
import com.sunesoft.lemon.ay.equipment.application.EquipmentService;
import com.sunesoft.lemon.ay.equipment.application.dtos.EquipmentDto;
import com.sunesoft.lemon.ay.equipment.domain.Equipment;
import com.sunesoft.lemon.ay.equipment.domain.EquipmentStation;
import com.sunesoft.lemon.fr.loggers.Logger;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.ListResult;
import com.sunesoft.lemon.fr.results.ResultFactory;
import com.sunesoft.lemon.fr.utils.DateHelper;
import com.sunesoft.lemon.fr.utils.JsonHelper;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.fr.utils.excel.ExpotExcel;
import com.sunesoft.lemon.syms.eHr.application.DeptmentService;
import com.sunesoft.lemon.syms.eHr.application.EmployeeService;
import com.sunesoft.lemon.syms.eHr.application.dtos.DeptmentDto;
import com.sunesoft.lemon.syms.eHr.application.dtos.EmpDto;
import com.sunesoft.lemon.syms.eHr.application.dtos.EmpSessionDto;
import com.sunesoft.lemon.syms.fileSys.application.FileService;
import com.sunesoft.lemon.syms.fileSys.application.dtos.FileInfoDto;
import com.sunesoft.lemon.syms.workflow.application.FormHeaderService;
import com.sunesoft.lemon.syms.workflow.application.FormListService;
import com.sunesoft.lemon.syms.workflow.application.dtos.FormHeaderDto;
import com.sunesoft.lemon.syms.workflow.application.dtos.FormListDto;
import com.sunesoft.lemon.webapp.controller.Layout;
import com.sunesoft.lemon.webapp.function.UserSession;
import com.sunesoft.lemon.webapp.utils.AjaxResponse;
import com.sunesoft.lemon.webapp.utils.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by admin on 2016/8/15.
 */
@Controller
public class EquipApplyController extends Layout {

    @Autowired
    FormHeaderService formHeaderService;
    @Autowired
    FormEquimentService formEquimentService;

    @Autowired
    UserSession us;

    @Autowired
    DeptmentService deptService;

    @Autowired
    EmployeeService empService;

    @Autowired
    FormListService formListService;
    @Autowired
    EquipmentService equipmentService;
    @Autowired
    FileService fileService;

    @Autowired
    Logger logger;

    @RequestMapping(value = "syy_ay_lc01_a")
    public ModelAndView index(Model model, HttpServletRequest request) {
        FormListDto dto = formListService.getFormListInfo("SYY_AY_LC01");
        String formNo = request.getParameter("formNo");
        model.addAttribute("formKind", dto.getFormKind());
        model.addAttribute("formKindName", dto.getFormName());
        model.addAttribute("date", DateHelper.formatDate(new Date(), "yyyy-MM-dd"));
        List<DeptmentDto> deptAll = deptService.getAllSimpleDept();
        model.addAttribute("deptAll", deptAll);
//保管人，分组查询
        List<EmpDto> empAll = empService.getAllSimpleEmps();
        model.addAttribute("empAll", empAll);
        //todo T1节点返回，点击修改要看到之前填写的数据
        if (!StringUtils.isNullOrWhiteSpace(formNo)) {
            FormEquipmentDto formEquipmentDto = formEquimentService.getFormByFormNo(Long.parseLong(formNo));
            model.addAttribute("formEquipment", formEquipmentDto);
            model.addAttribute("formNo", formEquipmentDto.getFormNo());

        }
        return view(applyLayout, "equipForms/syy_ay_lc01_a", model);
    }

    @RequestMapping(value = "editEquipmentInfo")
    public ModelAndView edit_equipment_info(Model model, HttpServletRequest request) {
        EmpSessionDto dto = us.getCurrentUser(request);
        model.addAttribute("userInfo", dto);
        model.addAttribute("date", DateHelper.formatDate(new Date(), "yyyy-MM-dd"));
        List<DeptmentDto> deptAll = deptService.getAllDept();
        model.addAttribute("deptAll", deptAll);
        //保管人，分组查询
        List<EmpDto> empAll = empService.getAllEmps();
        model.addAttribute("empAll", empAll);
        String id = request.getParameter("id");
        if (!StringUtils.isNullOrWhiteSpace(id)) {
            EquipmentDto equipmentDto = equipmentService.getEquipById(Long.parseLong(id));
            model.addAttribute("equipmentDto", equipmentDto);
            model.addAttribute("help", Helper.class);
        }
        return view(layout, "equipment/editEquipmentInfo", model);
    }

    //设备管理信息修改，这里不需要再经过审核
    @RequestMapping(value = "ajax_update_equipment", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateEquipApply(HttpServletRequest request, EquipmentDto equipmentDto) {
        EmpSessionDto empSessionDto = us.getCurrentUser(request);

//        String buyTime = request.getParameter("wbuyTime");//购进日期
        String productTime = request.getParameter("wproductTime");//投产日期
        String nextTestTime = request.getParameter("wnextTestTime");//下次检定校准时间
        String testTime = request.getParameter("wtestTime");//检定校准时间
        String savePerson = request.getParameter("savePerson");//检定校准时间
        equipmentDto.setSavePersonId(Long.parseLong(savePerson));
        EmpDto empDto = empService.getEmpById(Long.parseLong(savePerson));
        equipmentDto.setSavePersonName(empDto.getName());

        //日期类型的转化
//        equipmentDto.setBuyTime(DateHelper.parse(buyTime, "yyyy-MM-dd"));
        equipmentDto.setProductTime(DateHelper.parse(productTime, "yyyy-MM-dd"));
        equipmentDto.setTestTime(DateHelper.parse(testTime, "yyyy-MM-dd"));
        equipmentDto.setNextTestTime(DateHelper.parse(nextTestTime, "yyyy-MM-dd"));
        return equipmentService.addOrUpEquipment(equipmentDto);
    }


    /**
     * 导出更新设备检定(下载指定格式的excel)
     *
     * @param response
     */
    @RequestMapping(value = "outputEquipInfo")
    public void outputEquipInfo(HttpServletResponse response) {
        List list = new ArrayList<>();
        CheckEquipInfo output = new CheckEquipInfo();
        output.setMeasuringName("设备名称");
        output.setPropertyNum("资产编号");
//        output.setPropertyType("资产类别名称");
        output.setMeasurement("计量单位");
        output.setStandard("规格型号");
        output.setFactoryName("制造厂家");
        output.setOutFactoryNum("出厂编号");
        output.setType("设备类型");
//        output.setDeposit("存放地点");
        output.setPost("使用岗位");
        output.setOriginal("原值");
        output.setNetValue("净值");
        output.setStandardValue(3.0);
        output.setMinValue(1.0);
        output.setMaxValue(3.0);
        output.setTestValue(4.0);
        output.setUseStation("使用状况");
        output.setUnitName("质量安全运行科");
        output.setRemark("备注");
        list.add(output);
        ExpotExcel<CheckEquipInfo> expotExcel = new ExpotExcel<>();
        String[] header = new String[]{"设备名称", "资产编号", "计量单位", "规格型号",
                "制造厂家", "出厂编号", "设备类型", "使用岗位",
                "原值", "净值", "技术参数标准值", "参数范围(最小值)", "参数范围(最大值)",
                "检定或校准值", "使用状况", "使用单位", "备注"};
        try {
            expotExcel.doExportExcel("更新设备信息——导入模板", header, list, "yyyy-MM-dd", response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 项目计划导入
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "inputEquipInfo")
    public void inputProjectPlan(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CommonResult result = null;
        String originalFilename = null;
        List<MultipartFile> myfiles = ((DefaultMultipartHttpServletRequest) request).getFiles("filename");
        StringBuilder unsuccessInfo = new StringBuilder("\r\n");
        for (MultipartFile myfile : myfiles) {
            if (myfile.isEmpty()) {
                result = new CommonResult(false, "请选择要上传的文件！");
                AjaxResponse.write(response, JsonHelper.toJson(result));
                return;
            } else {
                originalFilename = myfile.getOriginalFilename();
                try {
                    UploadEquipment factory = new UploadEquipment();
                    ListResult<EquipmentDto> dto = factory.readExcel(myfile.getInputStream());
                    if (dto.getIsSuccess() && dto.getItems().size() > 0) {
                        for (EquipmentDto d : dto.getItems()) {
                            d.setCurrentStation(EquipmentStation.Normal);
                            d.setTestResult(true);
                            result = equipmentService.editBySystemNumber(d);
                            if (!result.getIsSuccess()) {
                                unsuccessInfo.append("其中" + result.getMsg() + "\r\n");
                                System.out.println(result.getMsg());
                                //AjaxResponse.write(response, JsonHelper.toJson(result.getMsg()));
                                logger.error(result.getMsg());
                                continue;
                            } else
                                System.out.println(d.getPropertyNum());
                        }

                    } else {
                        result = new CommonResult(dto.getIsSuccess(), dto.getMsg());
                    }
                } catch (IOException e) {
                    System.out.println("文件[" + originalFilename + "]上传失败,堆栈轨迹如下");
                    e.printStackTrace();
                    CommonResult result1 = new CommonResult(false, "文件上传失败，请重试！！");
                    logger.error(result1.getMsg() + "\n erro msg:" + e.getMessage() + "\n StackTrace:" + e.getStackTrace());
                    break;
                }
            }
        }
        CommonResult result1 = new CommonResult(result.getIsSuccess(), result.getMsg() + unsuccessInfo.toString());
        AjaxResponse.write(response, JsonHelper.toJson(result1));
    }


    @RequestMapping(value = "ajax_add_update_equipApply", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult addOrUpdateEquipApply(HttpServletRequest request, FormEquipmentDto formEquipmentDto) {
        String propertyNum = formEquipmentDto.getPropertyNum();
        if (StringUtils.isNullOrWhiteSpace(propertyNum)) {
            return new CommonResult(false,"请输入资产编号");
        }
        EquipmentDto equipmentDto = equipmentService.getEquipByNun(formEquipmentDto.getPropertyNum());
        if (equipmentDto != null) {
           return new CommonResult(false,"该资产编号已存在！");
        }
        EmpSessionDto empSessionDto = us.getCurrentUser(request);
        String saveId = request.getParameter("savePerson");//必选项
        EmpDto savePerson = empService.getEmpById(Long.parseLong(saveId));
        formEquipmentDto.setSavePersonId(savePerson.getId());
        formEquipmentDto.setSavePersonName(savePerson.getName());

//        String buyTime = request.getParameter("wbuyTime");//购进日期
        String productTime = request.getParameter("wproductTime");//投产日期
        String nextTestTime = request.getParameter("wnextTestTime");//下次检定校准时间
        String testTime = request.getParameter("wtestTime");//检定校准时间
        //日期类型的转化
//        formEquipmentDto.setBuyTime(DateHelper.parse(buyTime, "yyyy-MM-dd"));
        formEquipmentDto.setProductTime(DateHelper.parse(productTime, "yyyy-MM-dd"));
        formEquipmentDto.setTestTime(DateHelper.parse(testTime, "yyyy-MM-dd"));
        formEquipmentDto.setNextTestTime(DateHelper.parse(nextTestTime, "yyyy-MM-dd"));

        String deptId = request.getParameter("hiddenDeptId");
        formEquipmentDto.setUnitId(deptId);


        //申请人+他的部门
        formEquipmentDto.setApplyer(empSessionDto.getId());
        formEquipmentDto.setApplyerName(empSessionDto.getName());
        formEquipmentDto.setDeptId(empSessionDto.getDeptId());
        formEquipmentDto.setDeptName(empSessionDto.getDeptName());

        //上传文件
        List<MultipartFile> myfiles = ((DefaultMultipartHttpServletRequest) request).getFiles("filename");
        for (MultipartFile myfile : myfiles) {
            if (!myfile.isEmpty()) {
                try {
                    String fileName = myfile.getOriginalFilename();
                    String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
                    FileInfoDto fileInfoDto = new FileInfoDto();
                    fileInfoDto.setFileName(fileName);
                    fileInfoDto.setExtensions(extension);
                    fileInfoDto.setUserName(us.getCurrentUser(request).getName());
                    fileInfoDto.setInputStream(myfile.getInputStream());
                    String id = fileService.upload(fileInfoDto);
                    if (!StringUtils.isNullOrWhiteSpace(id)) {
                        formEquipmentDto.setFileId(id);
                        formEquipmentDto.setFileName(fileName);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        String formNo = request.getParameter("formNo");
        CommonResult result = null;
        if (StringUtils.isNullOrWhiteSpace(formNo)) {
            result = formEquimentService.addByDto(formEquipmentDto);
        } else {
            formEquipmentDto.setFormNo(Long.parseLong(formNo));
            result = formEquimentService.updateByDto(formEquipmentDto);
        }

        return formEquimentService.submitForm(result.getId(), formEquipmentDto.getFormKind());
    }

    @RequestMapping(value = "ajax_syy_ay_lc01_data")
    @ResponseBody
    public FormEquipmentDto queryData(HttpServletRequest request) {
        String formNo = request.getParameter("formNo");
        String viewOnly = request.getParameter("viewOnly");
        FormEquipmentDto formEquipmentDto = formEquimentService.getFormByFormNo(Long.parseLong(formNo));
        formEquipmentDto.setIsViewOnly(Boolean.valueOf(viewOnly));
        return formEquipmentDto;
    }

}
