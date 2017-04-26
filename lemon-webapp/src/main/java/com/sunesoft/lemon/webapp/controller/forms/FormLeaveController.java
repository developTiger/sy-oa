package com.sunesoft.lemon.webapp.controller.forms;

import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.utils.DateHelper;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.fr.utils.excel.ExpotExcel;
import com.sunesoft.lemon.fr.utils.word.WordPlaceHolder;
import com.sunesoft.lemon.fr.utils.word.wdWord;
import com.sunesoft.lemon.syms.eHr.application.DeptmentService;
import com.sunesoft.lemon.syms.eHr.application.EmployeeService;
import com.sunesoft.lemon.syms.eHr.application.dtos.DeptmentDto;
import com.sunesoft.lemon.syms.eHr.application.dtos.EmpDto;
import com.sunesoft.lemon.syms.eHr.application.dtos.EmpSessionDto;
import com.sunesoft.lemon.syms.hrForms.application.Dtos.DownloadFormLeaveDto;
import com.sunesoft.lemon.syms.hrForms.application.Dtos.FormLeaveDto;
import com.sunesoft.lemon.syms.hrForms.application.Dtos.FormTrainDto;
import com.sunesoft.lemon.syms.hrForms.application.criteria.LeaveFormCriteria;
import com.sunesoft.lemon.syms.hrForms.application.formleave.FormLeaveService;
import com.sunesoft.lemon.syms.hrForms.domain.enums.LeaveType;
import com.sunesoft.lemon.syms.workflow.application.FormHeaderService;
import com.sunesoft.lemon.syms.workflow.application.FormListService;
import com.sunesoft.lemon.syms.workflow.application.dtos.FormHeaderDto;
import com.sunesoft.lemon.syms.workflow.application.dtos.FormListDto;
import com.sunesoft.lemon.syms.workflow.application.dtos.InnerFormAppPointDto;
import com.sunesoft.lemon.syms.workflow.domain.FormApprover;
import com.sunesoft.lemon.webapp.controller.Layout;
import com.sunesoft.lemon.webapp.controller.partyGroup.InnovationAchievementController;
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
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * Created by zhouz on 2016/7/18.
 */
@Controller
public class FormLeaveController extends Layout {

    @Autowired
    FormLeaveService formLeaveService;
    @Autowired
    UserSession us;
    @Autowired
    FormListService formListService;
    @Autowired
    EmployeeService employeeService;

    @Autowired
    FormHeaderService headerService;
    @Autowired
    DeptmentService deptmentService;

    @RequestMapping("syy_rs_lc06_a")
    public ModelAndView apply(Model model, HttpServletRequest request) {

        String formKind = request.getParameter("formKind");
        FormListDto dto = null;
        if (!StringUtils.isNullOrWhiteSpace(formKind)) {
            //修改
            dto = formListService.getFormListInfo(formKind);

        } else {
            //新增
            dto = formListService.getFormListInfo("SYY_RS_LC06");
        }
        model.addAttribute("formKind", dto.getFormKind());
        model.addAttribute("formKindName", dto.getFormName());
        model.addAttribute("date", DateHelper.formatDate(new Date(), "yyyy-MM-dd"));

        String formNo = request.getParameter("formNo");
        //判断该员工是否能享受  带薪年假+疗养假
        EmpDto empDto = employeeService.getEmpById(us.getCurrentUser(request).getId());//获取到用户信息
        Integer workAge = empDto.getSeniority();//获得工龄

        model.addAttribute("workAge", workAge == null ? 0 : workAge);

        if (!StringUtils.isNullOrWhiteSpace(formNo)) {
            FormLeaveDto formLeave = formLeaveService.getFormByFormNo(Long.parseLong(formNo));
            model.addAttribute("beans", formLeave);
            model.addAttribute("formNo", formLeave.getFormNo());
        }

        return view(applyLayout, "forms/syy_rs_lc06_a", model);
    }


    /**
     * 指定流程选择 请假申请流程-院领导
     *
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "syy_leave_v")
    public ModelAndView getForm(Model model, HttpServletRequest request) {
        Long formNo = Long.parseLong(request.getParameter("formNo"));
        String formKind = request.getParameter("formKind");

        Boolean isviewOnly = Boolean.valueOf(request.getParameter("viewOnly"));
        FormHeaderDto dto = headerService.getHeaderByFormNo(formNo);

        model.addAttribute("header", dto);
        isviewOnly = false;
        model.addAttribute("viewOnly", isviewOnly);
        return view(formLayout, "forms/syy_rs_lc06_v", model);
    }


    @RequestMapping(value = "ajax_add_update_leave")
    @ResponseBody
    public CommonResult addOrUpdateLeave(FormLeaveDto formLeave, HttpServletRequest request) {
        EmpSessionDto empSessionDto = us.getCurrentUser(request);

        String fromTime = request.getParameter("strFromTime");
        String toTime = request.getParameter("strToTime");
        formLeave.setFromTime(setHMS(DateHelper.parse(fromTime, "yyyy-MM-dd"), 0));
        formLeave.setToTime(setHMS(DateHelper.parse(toTime, "yyyy-MM-dd"), 1));
        if (!formLeave.getToTime().after(formLeave.getFromTime())) {
            return new CommonResult(false, "结束时间需晚于起始时间");
        }
        String leaveType = request.getParameter("type");

        CommonResult result = null;
        String formNo = request.getParameter("formNo");
        String id = request.getParameter("id");
        if (!StringUtils.isNullOrWhiteSpace(id)) {
            //修改
            result = formLeaveService.addByDto(formLeave);
        } else {
            formLeave.setApplyer(empSessionDto.getId());
            formLeave.setApplyerName(empSessionDto.getName());
            formLeave.setDeptId(empSessionDto.getDeptId());//申请人部门Id->no

            formLeave.setDeptName(empSessionDto.getDeptName());

//            if (("legalHolidays").equals(leaveType)) {
//                formLeave.setLeaveType(LeaveType.legalHolidays);
//            }
            if (("visitFamily").equals(leaveType)) {
                formLeave.setLeaveType(LeaveType.visitFamily);
            }
            if (("paidAnnual").equals(leaveType)) {
                formLeave.setLeaveType(LeaveType.paidAnnual);
            }
            if (("recuperation").equals(leaveType)) {
                formLeave.setLeaveType(LeaveType.recuperation);
            }
            if (("sick").equals(leaveType)) {
                formLeave.setLeaveType(LeaveType.sick);
            }
            if (("thing").equals(leaveType)) {
                formLeave.setLeaveType(LeaveType.thing);
            }
            if (("marry").equals(leaveType)) {
                formLeave.setLeaveType(LeaveType.marry);
            }
            if (("funeral").equals(leaveType)) {
                formLeave.setLeaveType(LeaveType.funeral);
            }
            if (("birth").equals(leaveType)) {
                formLeave.setLeaveType(LeaveType.birth);
            }
            if (("onlyChild").equals(leaveType)) {
                formLeave.setLeaveType(LeaveType.onlyChild);
            }
            if (("abortion").equals(leaveType)) {
                formLeave.setLeaveType(LeaveType.abortion);
            }
            if (("escort").equals(leaveType)) {
                formLeave.setLeaveType(LeaveType.escort);
            }

            //如果申请人是院领导 走另外一个流程：院领导->办公室->主要领导
            if (empSessionDto.getDeptNo().equals("01YLD")) {        //TODO 部门编码 hotfix
                if (("visitFamily").equals(leaveType)) {
                    formLeave.setLeaveType(LeaveType.visitFamily);
                    formLeave.setFormKind("SYY_RS_LC06_07");
                }
                if (("paidAnnual").equals(leaveType)) {
                    formLeave.setLeaveType(LeaveType.paidAnnual);
                    formLeave.setFormKind("SYY_RS_LC06_07");
                }
                if (("recuperation").equals(leaveType)) {
                    formLeave.setLeaveType(LeaveType.recuperation);
                    formLeave.setFormKind("SYY_RS_LC06_08");
                }
                if (("sick").equals(leaveType)) {
                    formLeave.setLeaveType(LeaveType.sick);
                    formLeave.setFormKind("SYY_RS_LC06_07");
                }
                if (("thing").equals(leaveType)) {
                    formLeave.setLeaveType(LeaveType.thing);
                    formLeave.setFormKind("SYY_RS_LC06_07");
                }
                if (("marry").equals(leaveType)) {
                    formLeave.setLeaveType(LeaveType.marry);
                    formLeave.setFormKind("SYY_RS_LC06_08");
                }
                if (("funeral").equals(leaveType)) {
                    formLeave.setLeaveType(LeaveType.funeral);
                    formLeave.setFormKind("SYY_RS_LC06_07");
                }
                if (("birth").equals(leaveType)) {
                    formLeave.setLeaveType(LeaveType.birth);
                    formLeave.setFormKind("SYY_RS_LC06_08");
                }
                if (("onlyChild").equals(leaveType)) {
                    formLeave.setLeaveType(LeaveType.onlyChild);
                    formLeave.setFormKind("SYY_RS_LC06_07");
                }
                if (("abortion").equals(leaveType)) {
                    formLeave.setLeaveType(LeaveType.abortion);
                    formLeave.setFormKind("SYY_RS_LC06_08");
                }
                if (("escort").equals(leaveType)) {
                    formLeave.setLeaveType(LeaveType.escort);
                    formLeave.setFormKind("SYY_RS_LC06_08");
                }
                if ("injury".equals(leaveType)) {
                    formLeave.setLeaveType(LeaveType.injury);
                    formLeave.setFormKind("SYY_RS_LC06_06");
                }
            } else if (empSessionDto.getPosition().equals("机关单位领导")) {//TODO 岗位名称 hotfix   l

                if (("visitFamily").equals(leaveType)) {
                    formLeave.setLeaveType(LeaveType.visitFamily);
                    formLeave.setFormKind("SYY_RS_LC06_04");
                }
                if (("paidAnnual").equals(leaveType)) {
                    formLeave.setLeaveType(LeaveType.paidAnnual);
                    formLeave.setFormKind("SYY_RS_LC06_04");
                }
                if (("recuperation").equals(leaveType)) {
                    formLeave.setLeaveType(LeaveType.recuperation);
                    formLeave.setFormKind("SYY_RS_LC06_05");
                }
                if (("sick").equals(leaveType)) {
                    formLeave.setLeaveType(LeaveType.sick);
                    formLeave.setFormKind("SYY_RS_LC06_04");
                }
                if (("thing").equals(leaveType)) {
                    formLeave.setLeaveType(LeaveType.thing);
                    formLeave.setFormKind("SYY_RS_LC06_04");
                }
                if (("marry").equals(leaveType)) {
                    formLeave.setLeaveType(LeaveType.marry);
                    formLeave.setFormKind("SYY_RS_LC06_05");
                }
                if (("funeral").equals(leaveType)) {
                    formLeave.setLeaveType(LeaveType.funeral);
                    formLeave.setFormKind("SYY_RS_LC06_04");
                }
                if (("birth").equals(leaveType)) {
                    formLeave.setLeaveType(LeaveType.birth);
                    formLeave.setFormKind("SYY_RS_LC06_05");
                }
                if (("onlyChild").equals(leaveType)) {
                    formLeave.setLeaveType(LeaveType.onlyChild);
                    formLeave.setFormKind("SYY_RS_LC06_04");
                }
                if (("abortion").equals(leaveType)) {
                    formLeave.setLeaveType(LeaveType.abortion);
                    formLeave.setFormKind("SYY_RS_LC06_05");
                }
                if (("escort").equals(leaveType)) {
                    formLeave.setLeaveType(LeaveType.escort);
                    formLeave.setFormKind("SYY_RS_LC06_05");
                }
                if ("injury".equals(leaveType)) {
                    formLeave.setLeaveType(LeaveType.injury);
                    formLeave.setFormKind("SYY_RS_LC06_03");
                }
            } else {
                if (("visitFamily").equals(leaveType)) {
                    formLeave.setLeaveType(LeaveType.visitFamily);
                    formLeave.setFormKind("SYY_RS_LC06_00");
                }
                if (("paidAnnual").equals(leaveType)) {
                    formLeave.setLeaveType(LeaveType.paidAnnual);
                    formLeave.setFormKind("SYY_RS_LC06_00");
                }
                if (("recuperation").equals(leaveType)) {
                    formLeave.setLeaveType(LeaveType.recuperation);
                    formLeave.setFormKind("SYY_RS_LC06_01");
                }
                if (("sick").equals(leaveType)) {
                    formLeave.setLeaveType(LeaveType.sick);
                    formLeave.setFormKind("SYY_RS_LC06_00");
                }
                if (("thing").equals(leaveType)) {
                    formLeave.setLeaveType(LeaveType.thing);
                    formLeave.setFormKind("SYY_RS_LC06_00");
                }
                if (("marry").equals(leaveType)) {
                    formLeave.setLeaveType(LeaveType.marry);
                    formLeave.setFormKind("SYY_RS_LC06_01");
                }
                if (("funeral").equals(leaveType)) {
                    formLeave.setLeaveType(LeaveType.funeral);
                    formLeave.setFormKind("SYY_RS_LC06_00");
                }
                if (("birth").equals(leaveType)) {
                    formLeave.setLeaveType(LeaveType.birth);
                    formLeave.setFormKind("SYY_RS_LC06_01");
                }
                if (("onlyChild").equals(leaveType)) {
                    formLeave.setLeaveType(LeaveType.onlyChild);
                    formLeave.setFormKind("SYY_RS_LC06_00");
                }
                if (("abortion").equals(leaveType)) {
                    formLeave.setLeaveType(LeaveType.abortion);
                    formLeave.setFormKind("SYY_RS_LC06_01");
                }
                if (("escort").equals(leaveType)) {
                    formLeave.setLeaveType(LeaveType.escort);
                    formLeave.setFormKind("SYY_RS_LC06_01");
                }
                if ("injury".equals(leaveType)) {
                    formLeave.setLeaveType(LeaveType.injury);
                    formLeave.setFormKind("SYY_RS_LC06_02");
                }
            }

            // todo  检查员工申请的假日是带薪年假与疗养假  这里只做检查不做处理
            if (formLeave.getLeaveType().equals(LeaveType.paidAnnual) || formLeave.getLeaveType().equals(LeaveType.recuperation)) {
                CommonResult c = formLeaveService.checkLeave(formLeave.getApplyer(), formLeave.getLeaveType(), formLeave.getFromTime(), formLeave.getToTime(), formLeave.getCountTime());
                if (!c.getIsSuccess()) return c;
            }
            result = formLeaveService.addByDto(formLeave);
        }

        return formLeaveService.submitForm(result.getId(), formLeave.getFormKind());
    }

    @RequestMapping("sra_b_leave_form")
    public ModelAndView sra_b_leave(Model model, HttpServletRequest request) {

        EmpSessionDto user = us.getCurrentUser(request);
        if (user.getDeptNo() != null && (user.getDeptNo().equals("05RSK") || user.getDeptNo().equals("04JWCWK"))) {
//        if (user.getDeptId() != null && (user.getDeptId()==10 || user.getDeptId()==14)) {
            model.addAttribute("canAll", true);//人事科，计财科，可以看到全部
            List<DeptmentDto> dtos = deptmentService.getAllSimpleDept();
            model.addAttribute("depts", dtos);
        }
        return view(layout, "uAuth/queryLeave", model);
    }

    @RequestMapping("ajax_syy_query_leaveForm")
    @ResponseBody
    public PagedResult<FormLeaveDto> queryLeaveForm(LeaveFormCriteria criteria, HttpServletRequest request, HttpServletResponse response) {
        EmpSessionDto empSessionDto = us.getCurrentUser(request);
        String begin = request.getParameter("strfromTime");
        String end = request.getParameter("strtoTime");
        String deptId = request.getParameter("deptId");
        String personName = request.getParameter("personName");
        if (!StringUtils.isNullOrWhiteSpace(begin)) {
            criteria.setBegin(DateHelper.parse(begin, "yyyy-MM-dd"));
        }
        if (!StringUtils.isNullOrWhiteSpace(end)) {
            criteria.setEnd(DateHelper.parse(end, "yyyy-MM-dd"));
        }
        if (!StringUtils.isNullOrWhiteSpace(personName)) {
            criteria.setPersonName(URI.deURI(personName).trim());
        }
        if (deptId == null) {
            criteria.setDeptId(empSessionDto.getDeptId());
        } else {
            criteria.setDeptId(Long.parseLong(deptId));
        }
        PagedResult<FormLeaveDto> pagedResult = formLeaveService.page(criteria);
        return pagedResult;
    }

    /**
     * 统计请假的列表下载
     * @param criteria
     * @param request
     * @param response
     */
    @RequestMapping("js_leave_form_download")
    @ResponseBody
    public void download_leave_form(LeaveFormCriteria criteria, HttpServletRequest request, HttpServletResponse response) {
        EmpSessionDto empSessionDto = us.getCurrentUser(request);
        String begin = request.getParameter("strfromTime");
        String end = request.getParameter("strtoTime");
        String deptId = request.getParameter("deptId");
        String personName = request.getParameter("personName");
        if (!StringUtils.isNullOrWhiteSpace(begin)) {
            criteria.setBegin(DateHelper.parse(begin, "yyyy-MM-dd"));
        }
        if (!StringUtils.isNullOrWhiteSpace(end)) {
            criteria.setEnd(DateHelper.parse(end, "yyyy-MM-dd"));
        }
        if (!StringUtils.isNullOrWhiteSpace(personName)) {
            criteria.setPersonName(URI.deURI(personName).trim());
        }
        if (deptId == null) {
            criteria.setDeptId(empSessionDto.getDeptId());
        } else {
            criteria.setDeptId(Long.parseLong(deptId));
        }
        criteria.setPageSize(Integer.MAX_VALUE);
        List<DownloadFormLeaveDto> list = formLeaveService.download(criteria);
        ExpotExcel<DownloadFormLeaveDto> expotExcel = new ExpotExcel<>();
        String[] headers = new String[]{"申请人", "请假类型", "前往地点", "请假天数", "请假日期", "申请时间", "归属部门"};
        expotExcel.doExportExcel("请假统计", headers, list, "yyyy-MM-dd", response);
    }

    @RequestMapping("ajax_syy_rs_lc06_data")
    @ResponseBody
    public FormLeaveDto applyView(Model model, HttpServletRequest request) {
        String formKind = request.getParameter("formNo");
        String viewOnly = request.getParameter("viewOnly");
        FormLeaveDto leave = formLeaveService.getFormByFormNo(Long.parseLong(formKind));
        leave.setIsViewOnly(Boolean.valueOf(viewOnly));
        return leave;
    }

    /**
     * 早上10点上班，晚上19点下班的工作制度
     *
     * @param time
     * @param i
     * @return
     */
    private Date setHMS(Date time, int i) {
        Date d = null;
        if (i == 0) {//属于开始时间
            d = DateHelper.addHour(time, 10);
        } else {//属于结束时间
            d = DateHelper.addHour(time, 19);
        }
        return d;
    }

    @RequestMapping("print_leave_pref")
    public void downLoad_leave_pref(HttpServletRequest request, HttpServletResponse response) {
        String formNo = request.getParameter("formNo");
        FormLeaveDto dto = formLeaveService.getFormByFormNo(Long.parseLong(formNo));
        FormHeaderDto formHeaderDto = headerService.getHeaderByFormNo(dto.getFormNo());
        EmpDto empDto = employeeService.getEmpById(dto.getApplyer());
        if (formHeaderDto.getFormKind().equals("SYY_RS_LC06_05") || formHeaderDto.getFormKind().equals("SYY_RS_LC06_01")) {
            doDOCExport(response, dto, formHeaderDto, empDto, 2, 5);
        }
        if (formHeaderDto.getFormKind().equals("SYY_RS_LC06_08")) {
            doDOCExport(response, dto, formHeaderDto, empDto, 1, null);
        }

    }

    /**
     * 打印相应的word
     *
     * @param response
     * @param dto
     * @param formHeaderDto
     * @param empDto
     * @param s1
     * @param s2
     */
    private void doDOCExport(HttpServletResponse response, FormLeaveDto dto, FormHeaderDto formHeaderDto, EmpDto empDto, Integer s1, Integer s2) {
        if (dto.getLeaveType().toString().equals("escort")) {
            //todo 男士陪护假
            String docPath = FormLeaveController.class.getClassLoader().getResource("实验检测研究院男职工陪护假证明.doc").getPath().substring(1).replace("/", File.separator);
            WordPlaceHolder wph = getSameWordPlaceHolder(dto, formHeaderDto, docPath, s1, s2);
            wdWord.exportServletWordByTemplate(wph, response, "实验检测研究院男职工陪护假证明.doc");

        } else if (dto.getLeaveType().toString().equals("abortion")) {
            //todo 女生流产产假
            String docPath = FormLeaveController.class.getClassLoader().getResource("实验检测研究院女职工流产休假证明.doc").getPath().substring(1).substring(1).replace("/", File.separator);
            WordPlaceHolder wph = getSameWordPlaceHolder(dto, formHeaderDto, docPath, s1, s2);
            wdWord.exportServletWordByTemplate(wph, response, "实验检测研究院女职工流产休假证明.doc");

        } else if (dto.getLeaveType().toString().equals("birth")) {
            String docPath = FormLeaveController.class.getClassLoader().getResource("实验检测研究院女职工生育假证明.doc").getPath().substring(1).replace("/", File.separator);
            //todo 女生生育假
            WordPlaceHolder wph = getSameWordPlaceHolder(dto, formHeaderDto, docPath, s1, s2);
            wdWord.exportServletWordByTemplate(wph, response, "实验检测研究院女职工生育假证明.doc");
        } else if (dto.getLeaveType().toString().equals("marry")) {
            //todo 职工婚假
            String docPath = FormLeaveController.class.getClassLoader().getResource("实验检测研究院职工婚假证.doc").getPath().substring(1).replace("/", File.separator);
            WordPlaceHolder wph = getSameWordPlaceHolder(dto, formHeaderDto, docPath, s1, s2);
            if (empDto.getSex() != null) {
                wph.addTextHolder("sex", empDto.getSex().toString().equals("1") ? "男" : "女");
            } else {
                wph.addTextHolder("sex", "-");
            }

            wph.addTextHolder("nation", empDto.getNation() == null ? "--" : empDto.getNation());
            if (!StringUtils.isNullOrWhiteSpace(empDto.getStrBirthday())) {
                Date date = DateHelper.parse(empDto.getStrBirthday());
                wph.addTextHolder("bY", String.valueOf(DateHelper.getYear(date)));
                wph.addTextHolder("bM", String.valueOf(DateHelper.getMonth(date)));
                wph.addTextHolder("bD", String.valueOf(DateHelper.getDay(date)));
                wph.addTextHolder("birth", empDto.getStrBirthday().substring(0, 10));
            }
            //todo 日期
            Date begin = dto.getFromTime();
            wph.addTextHolder("xDate", DateHelper.formatDate(begin, "yyyy年MM月dd日"));
            wph.addTextHolder("applyDate", DateHelper.formatDate(formHeaderDto.getBeginDate(), "yyyy年MM月dd日"));
            wdWord.exportServletWordByTemplate(wph, response, "实验检测研究院职工婚假证.doc");
        }
    }

    /**
     * 公共部分处理
     *
     * @param dto
     * @param formHeaderDto
     * @param path
     * @param s1
     * @param s2
     * @return
     */
    private WordPlaceHolder getSameWordPlaceHolder(FormLeaveDto dto, FormHeaderDto formHeaderDto, String path, Integer s1, Integer s2) {
        WordPlaceHolder wph = new WordPlaceHolder(path);
        wph.addTextHolder("dept", dto.getDeptName());
        wph.addTextHolder("userName", dto.getApplyerName());
        Date begin = dto.getFromTime();
        Date end = dto.getActualTime();
        Long days = dto.getCountTime().longValue();
        wph.addTextHolder("days", days.toString());
        wph.addTextHolder("beginY", DateHelper.formatDate(begin, "yyyy"));
        wph.addTextHolder("beginM", DateHelper.formatDate(begin, "MM"));
        wph.addTextHolder("beginD", DateHelper.formatDate(begin, "dd"));
        wph.addTextHolder("endY", DateHelper.formatDate(end, "yyyy"));
        wph.addTextHolder("endM", DateHelper.formatDate(end, "MM"));
        wph.addTextHolder("endD", DateHelper.formatDate(end, "dd"));
        wph.addTextHolder("formNo", formHeaderDto.getFormNo().toString());
        wph.addTextHolder("userNameDate", DateHelper.formatDate(formHeaderDto.getBeginDate(), "yyyy年MM月dd日"));
        List<FormApprover> list = formHeaderDto.getFormApprovers();
        if (list != null && list.size() > 0) {
            for (FormApprover approver : list) {
                if (s2 != null) {
                    if (approver.getClStep().equals(s2) && approver.getApproveStatus().toString().equals("P")) {//基础领导
                        wph.addTextHolder("uniter", approver.getAppActorName());
                        wph.addTextHolder("unDate", DateHelper.formatDate(approver.getEndDate(), "yyyy年MM月dd日"));
                    }
                } else {
                    wph.addTextHolder("uniter", "———");
                    wph.addTextHolder("unDate", "———");
                }
                if (s1 != null) {
                    if (approver.getClStep().equals(s1) && approver.getApproveStatus().toString().equals("P")) {//计划生育办公室

                        wph.addTextHolder("office", approver.getAppActorName());
                        wph.addTextHolder("offDate", DateHelper.formatDate(approver.getEndDate(), "yyyy年MM月dd日"));
                    }
                } else {
                    if (s1 == null) {
                        wph.addTextHolder("office", "--");
                        wph.addTextHolder("offDate", "--");
                    }
                }
            }
        }
        return wph;
    }
}
