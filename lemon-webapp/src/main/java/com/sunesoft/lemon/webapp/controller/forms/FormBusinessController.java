package com.sunesoft.lemon.webapp.controller.forms;

import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.results.ResultFactory;
import com.sunesoft.lemon.fr.utils.DateHelper;
import com.sunesoft.lemon.fr.utils.JsonHelper;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.fr.utils.excel.ExpotExcel;
import com.sunesoft.lemon.fr.utils.word.WordPlaceHolder;
import com.sunesoft.lemon.fr.utils.word.wdWord;
import com.sunesoft.lemon.syms.eHr.application.EmployeeService;
import com.sunesoft.lemon.syms.eHr.application.dtos.EmpSessionDto;
import com.sunesoft.lemon.syms.hrForms.application.Dtos.FormBusinessDownloadDto;
import com.sunesoft.lemon.syms.hrForms.application.Dtos.FormEvectionDto;
import com.sunesoft.lemon.syms.hrForms.application.criteria.EvectionCriteria;
import com.sunesoft.lemon.syms.hrForms.application.formevection.FormEvectionService;
import com.sunesoft.lemon.syms.workflow.application.FormHeaderService;
import com.sunesoft.lemon.syms.workflow.application.FormListService;
import com.sunesoft.lemon.syms.workflow.application.dtos.FormHeaderDto;
import com.sunesoft.lemon.syms.workflow.application.dtos.FormListDto;
import com.sunesoft.lemon.webapp.controller.Layout;
import com.sunesoft.lemon.webapp.function.UserSession;
import com.sunesoft.lemon.webapp.model.EmpAppraiseDetailDtoModel;
import com.sunesoft.lemon.webapp.utils.AjaxResponse;
import com.sunesoft.lemon.webapp.utils.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.print.*;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.io.*;
import java.util.*;

/**
 * Created by zhouz on 2016/7/18.
 */
@Controller
public class FormBusinessController extends Layout {


    @Autowired
    FormEvectionService formEvectionService;

    @Autowired
    FormListService formListService;

    @Autowired
    FormHeaderService headerService;

    @Autowired
    EmployeeService employeeService;


    @Autowired
    UserSession us;

    @RequestMapping("sra_b_business")
    public ModelAndView sra_b_business(Model model, HttpServletRequest request) {


        return view(layout, "uAuth/queryBusiness", model);
    }

    @RequestMapping(value = "ajax_business_list")
    public void updateUserRole(HttpServletRequest request, HttpServletResponse response, EvectionCriteria criteria) {

        String strfromTime = request.getParameter("strfromTime");
        String strtoTime = request.getParameter("strtoTime");
        String target = URI.deURI(request.getParameter("target"));
        String applyerName = URI.deURI(request.getParameter("applyerName"));
        criteria.setTarget(target.trim());
        criteria.setApplyerName(applyerName.trim());
        EmpSessionDto empSessionDto = us.getCurrentUser(request);
        //TODO 部门编码 hotfix
        if ("02YZBGS".equals(empSessionDto.getDeptNo()) || "05RSK".equals(empSessionDto.getDeptNo()) || "01YLD".equals(empSessionDto.getDeptNo())) {
            criteria.setDeptId(0l);
        } else {
            criteria.setDeptId(empSessionDto.getDeptId());
        }
        if (!StringUtils.isNullOrWhiteSpace(strfromTime)) {
            strfromTime = strfromTime + " 00:00:00";
            criteria.setFromTime(DateHelper.parse(strfromTime));
        }
        if (!StringUtils.isNullOrWhiteSpace(strtoTime)) {
            strtoTime = strtoTime + " 23:59:59";
            criteria.setToTime(DateHelper.parse(strtoTime));
        }
        PagedResult result = formEvectionService.getEvectionPage(criteria);
        String json = JsonHelper.toJson(result);
        System.out.println(result);
        AjaxResponse.write(response, json);
    }

    @RequestMapping(value = "ajax_update_print", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult ajax_update_print(HttpServletRequest request, HttpServletResponse response) {
        String formKind = request.getParameter("formKind");
        CommonResult result = formEvectionService.updatePrint(formKind);
        return result;
    }

    @RequestMapping(value = "download_business_list")
    public void download_business_list(HttpServletRequest request, HttpServletResponse response, EvectionCriteria criteria) {

        String strfromTime = request.getParameter("sFromTime");
        String strtoTime = request.getParameter("sToTime");
        String target = URI.deURI(request.getParameter("targets"));
        String applyerName = URI.deURI(request.getParameter("aName"));
        criteria.setTarget(target.trim());
        criteria.setApplyerName(applyerName.trim());
        if (!StringUtils.isNullOrWhiteSpace(strfromTime)) {
            strfromTime = strfromTime + " 00:00:00";
            criteria.setFromTime(DateHelper.parse(strfromTime));
        }
        if (!StringUtils.isNullOrWhiteSpace(strtoTime)) {
            strtoTime = strtoTime + " 23:59:59";
            criteria.setToTime(DateHelper.parse(strtoTime));
        }
        EmpSessionDto empSessionDto = us.getCurrentUser(request);
        criteria.setDeptId(empSessionDto.getDeptId());
        criteria.setPageSize(1000000000);
        PagedResult<FormBusinessDownloadDto> result = formEvectionService.getDownlodEvection(criteria);

        ExpotExcel<FormBusinessDownloadDto> expotExcel = new ExpotExcel<>();
        String[] header = new String[]{"出差日期", "截止时间", "申请人", "出差目的", "历时多久", "出差类别", "出差属性", "任务来源", "任务内容"};
        expotExcel.doExportExcel("出差统计", header, result.getItems(), "yyyy-MM-dd", response);
    }


    @RequestMapping("syy_rs_lc05_a")
    public ModelAndView apply(Model model, HttpServletRequest request) {

        FormListDto dto = formListService.getFormListInfo("SYY_RS_LC05");
        model.addAttribute("formKind", dto.getFormKind());
        model.addAttribute("formKindName", dto.getFormName());
        model.addAttribute("date", DateHelper.formatDate(new Date(), "yyyy-MM-dd"));

        String formNo = request.getParameter("formNo");
        if (!StringUtils.isNullOrWhiteSpace(formNo)) {
            FormEvectionDto evectionDto = formEvectionService.getFormByFormNo(Long.parseLong(formNo));
            model.addAttribute("dto", evectionDto);
            model.addAttribute("formNo", evectionDto.getFormNo());
        }

        return view(applyLayout, "forms/syy_rs_lc05_a", model);
    }

    @RequestMapping(value = "ajax_add_update_eve", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult addOrUpdateFormevection(HttpServletRequest request, HttpServletResponse response) {
        EmpSessionDto empSessionDto = us.getCurrentUser(request);
        FormEvectionDto formEvection = new FormEvectionDto();

        String id = request.getParameter("id");
        String formNo = request.getParameter("formNo");


        formEvection.setReason(request.getParameter("reason"));
        String target = request.getParameter("target");
        String targets = request.getParameter("targets");
        formEvection.setTarget(target + "(" + targets + ")");
        formEvection.setTaskContent(request.getParameter("taskContent"));
        formEvection.setTaskSource(request.getParameter("taskSource"));
        formEvection.setEvectionTime(setHMS(DateHelper.parse(request.getParameter("strFromTime"), "yyyy-MM-dd"), 0));
        formEvection.setToTime(setHMS(DateHelper.parse(request.getParameter("strToTime"), "yyyy-MM-dd"), 1));

        String evecAttr = request.getParameter("evecAttr");
        String category = request.getParameter("category");
        if (!StringUtils.isNullOrWhiteSpace("category")) {
            formEvection.setCategory(category);
        }
        if (!StringUtils.isNullOrWhiteSpace("evecAttr")) {
            formEvection.setEvecAttr(evecAttr);
        }

        formEvection.setFormKind(request.getParameter("formKind"));
        formEvection.setFormKindName(request.getParameter("formKindName"));


        //院领导
        if (employeeService.isLeader(empSessionDto.getId())) {
            formEvection.setFormKind("SYY_RS_LC05_01");
            formEvection.setFormKindName("出差申请流程-院领导");
        }

        //机关科室长

        if (!StringUtils.isNullOrWhiteSpace(empSessionDto.getPosition()) && empSessionDto.getPosition().equals("机关单位领导")) {
            formEvection.setFormKind("SYY_RS_LC05_02");
            formEvection.setFormKindName("出差申请流程-机关科室长");
        }


        if (!StringUtils.isNullOrWhiteSpace(id)) {
            formEvection.setId(Long.parseLong(id));
            formEvection.setFormNo(Long.parseLong(formNo));

        }

        formEvection.setApplyer(empSessionDto.getId());
        formEvection.setApplyerName(empSessionDto.getName());
        formEvection.setDeptId(empSessionDto.getDeptId());
        formEvection.setDeptName(empSessionDto.getDeptName());
        String f = String.valueOf((formEvection.getToTime().getTime() - formEvection.getEvectionTime().getTime()) / (1000 * 60 * 60 * 24));
        formEvection.setCountTime(Float.parseFloat(f)+1);
        CommonResult result = formEvectionService.addByDto(formEvection);
        CommonResult commonResult = formEvectionService.submitForm(result.getId(), formEvection.getFormKind());
        return commonResult;
    }

    @RequestMapping("ajax_download_business")
    public void ajax_download_business(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {

        String formNo = request.getParameter("formNo");

        FormEvectionDto evectionDto=formEvectionService.getFormByFormNo(Long.parseLong(formNo));
        if (!evectionDto.getPrintFlag()) {
            //出差证编号没生成进入 已生成可多次打印
            CommonResult result = formEvectionService.updatePrint(formNo);
        }

        FormHeaderDto dto = headerService.getHeaderByFormNo(Long.parseLong(formNo));
        FormEvectionDto formEvectionDto = formEvectionService.getFormByFormNo(Long.parseLong(formNo));

        //   String filePath1 = FormBusinessController.class.getClassLoader().getResource("出差证模板.docx").getPath();

        String filePath = request.getServletContext().getRealPath("/WEB-INF/classes/出差证模板.docx");

        WordPlaceHolder hoder = new WordPlaceHolder(filePath);

        hoder = hoder.addTextHolder("deptName", dto.getDeptName());
        hoder = hoder.addTextHolder("applyerName", dto.getApplyerName());
        hoder = hoder.addTextHolder("proviousApprover", dto.getProviousApprover());
        hoder = hoder.addTextHolder("target", formEvectionDto.getTarget());
        hoder = hoder.addTextHolder("evectionTime", DateHelper.formatDate(formEvectionDto.getEvectionTime(), "yyyy-MM-dd"));
        hoder = hoder.addTextHolder("toTime", DateHelper.formatDate(formEvectionDto.getToTime(), "yyyy-MM-dd"));
//        hoder = hoder.addTextHolder("businessDay", String.valueOf(DateHelper.daysBetween(formEvectionDto.getEvectionTime(), formEvectionDto.getToTime())+1));
        hoder = hoder.addTextHolder("businessDay", String.valueOf(formEvectionDto.getCountTime()));
        hoder = hoder.addTextHolder("category", formEvectionDto.getCategory());
        hoder = hoder.addTextHolder("evecAttr", formEvectionDto.getEvecAttr());
        hoder = hoder.addTextHolder("reason", formEvectionDto.getReason());
        hoder = hoder.addTextHolder("sNumber", showNumberNo(formEvectionDto.getNumberNo()));
        hoder = hoder.addTextHolder("year", String.valueOf(DateHelper.getYear(new Date())));
//        if (StringUtils.isNullOrWhiteSpace(dto.getRemark())) {
//            hoder = hoder.addTextHolder("remark", " ");
//        } else {
//            hoder = hoder.addTextHolder("remark", dto.getRemark());
//        }
        Boolean flag = wdWord.exportWordByTemplate(hoder, response, "出差打印证.doc");
        response.sendRedirect("/sra_fm_form?formNo="+formNo+"&formKind="+formEvectionDto.getFormKind()+"&viewurl=forms");
    }

    private String showNumberNo(Integer numberNo) {
        String number = "";
        switch (numberNo.toString().length()) {
            case 1:
                number = "000" + numberNo;
                break;
            case 2:
                number = "00" + numberNo;
                break;
            case 3:
                number = "0" + numberNo;
                break;
            case 4:
                number = String.valueOf(numberNo);
                break;
            default:
                number = "";
                break;
        }
        return number;
    }

    @RequestMapping("ajax_syy_rs_lc05_data")
    @ResponseBody
    public FormEvectionDto applyView5(Model model, HttpServletRequest request) {
        String formKind = request.getParameter("formNo");
        String viewOnly = request.getParameter("viewOnly");
        FormEvectionDto formEvectionDto = formEvectionService.getFormByFormNo(Long.parseLong(formKind));
        FormHeaderDto dto = headerService.getHeaderByFormNo(Long.parseLong(formKind));
        formEvectionDto.setCurrentPointName(dto.getCurrentPointName());
        formEvectionDto.setYear(DateHelper.getYear(new Date()));
        int t = formEvectionDto.getFormNo().toString().length();
        String forNo = formEvectionDto.getFormNo().toString();
        formEvectionDto.setStrFormNo(forNo.substring(t - 4, t));
        formEvectionDto.setIsViewOnly(Boolean.valueOf(viewOnly));
        return formEvectionDto;
    }

    /**
     * 指定流程 出差申请流程-院领导
     * 院领导和机关科室长走的流程虽然不一样，就是签核流程不一样，但是审核页面数据是一致的
     *
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "syy_business_v_01")
    public ModelAndView getForm(Model model, HttpServletRequest request) {
        Long formNo = Long.parseLong(request.getParameter("formNo"));
        String formKind = request.getParameter("formKind");

        Boolean isviewOnly = Boolean.valueOf(request.getParameter("viewOnly"));
        FormHeaderDto dto = headerService.getHeaderByFormNo(formNo);

        model.addAttribute("header", dto);
        model.addAttribute("viewOnly", isviewOnly);
        return view(formLayout, "forms/syy_rs_lc05_v", model);
    }

    /**
     * 指定流程 出差申请路程-机关科室长
     *
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "syy_business_v_02")
    public ModelAndView getForm1(Model model, HttpServletRequest request) {
        Long formNo = Long.parseLong(request.getParameter("formNo"));
        String formKind = request.getParameter("formKind");

        Boolean isviewOnly = Boolean.valueOf(request.getParameter("viewOnly"));
        FormHeaderDto dto = headerService.getHeaderByFormNo(formNo);

        model.addAttribute("header", dto);
        model.addAttribute("viewOnly", isviewOnly);

        if (!isviewOnly)
            return view(formLayout, "forms/syy_rs_lc05_v", model);

        return view(formViewLayout, "forms/syy_rs_lc05_v", model);

    }

    /**
     * 出差算全天
     *
     * @param time
     * @param i
     * @return
     */
    private Date setHMS(Date time, int i) {
        Date d = null;
        if (i == 0) {//属于开始时间
            d = time;
        } else {//属于结束时间
            d = DateHelper.addHour(time, 23);
            d = DateHelper.addMinute(d, 59);
            d = DateHelper.addSecond(d, 59);

        }
        return d;
    }
}
