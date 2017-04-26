package com.sunesoft.lemon.webapp.controller.attendance;

import com.sunesoft.lemon.fr.loggers.Logger;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.ListResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.utils.DateHelper;
import com.sunesoft.lemon.fr.utils.JsonHelper;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.fr.utils.excel.ExpotExcel;
import com.sunesoft.lemon.syms.eHr.application.*;
import com.sunesoft.lemon.syms.eHr.application.criteria.*;
import com.sunesoft.lemon.syms.eHr.application.dtos.*;
import com.sunesoft.lemon.syms.eHr.application.factory.AttendDownloadFactory;
import com.sunesoft.lemon.syms.eHr.domain.attendance.enums.AttendanceKind;
import com.sunesoft.lemon.syms.hrForms.application.formevection.FormEvectionService;
import com.sunesoft.lemon.syms.hrForms.application.formleave.FormLeaveService;
import com.sunesoft.lemon.webapp.controller.Layout;
import com.sunesoft.lemon.webapp.function.UserSession;
import com.sunesoft.lemon.webapp.model.EmpDynamicsModel;
import com.sunesoft.lemon.webapp.model.PageEmpDynamicsModel;
import com.sunesoft.lemon.webapp.model.SevenDayModel;
import com.sunesoft.lemon.webapp.utils.AjaxResponse;
import com.sunesoft.lemon.webapp.utils.Helper;
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

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by zhouz on 2016/5/19.
 */

@Controller
public class WorkAttendController extends Layout {

    @Autowired
    Logger logger;

    @Autowired
    UserSession us;

    @Autowired
    AttendanceService attendanceService;
    @Autowired
    HolidayInfoService holidayInfoService;
    @Autowired
    DeptmentService deptmentService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    AttendService attendService;
    @Autowired
    AttendanceEnsureService attendanceEnsureService;

    @Autowired
    FormLeaveService formLeaveService;

    @Autowired
    FormEvectionService formEvectionService;

    @RequestMapping(value = "sra_b_businessPrint")
    public ModelAndView businessPrint(Model model, HttpServletRequest request, HttpServletResponse response) {
        return view(layout, "workflow/businessPrint", model);
    }

    @RequestMapping(value = "sra_a_playAttendance")
    public ModelAndView playAttendance(Model model, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute("deptId", us.getCurrentUser(request).getDeptId());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        model.addAttribute("NowTime", dateFormat.format(new Date()));
        return view(layout, "attendance/playAttendance", model);
    }

    @RequestMapping(value = "sra_r_all_playAttendance")
    public ModelAndView allPlayedAttendance(Model model, HttpServletRequest request, HttpServletResponse response) {

        DeptmentCriteria deptmentCriteria = new DeptmentCriteria();
        deptmentCriteria.setDeptName("");
        deptmentCriteria.setDeptNo("");

        PagedResult deptmentDto = deptmentService.findDeptsPaged(deptmentCriteria);

        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        model.addAttribute("NowTime", dateFormat.format(date));
        model.addAttribute("deptAll", deptmentDto.getItems());
        return view(layout, "attendance/allPlayedAttendance", model);
    }


    @RequestMapping(value = "ajax_allAttendance_Query_list")
    public void ajax_allAttendance_Query_list(HttpServletRequest request, HttpServletResponse response) {
        EmpAttendancesCriteria attendanceCriteria = new EmpAttendancesCriteria();
        String strDate = request.getParameter("attDate");
        String s = "%3A";
        String name = request.getParameter("loginName");
        if (!StringUtils.isNullOrWhiteSpace(name)) {
            attendanceCriteria.setName(URI.deURI(name));
        }
        String temp = strDate.replaceAll(s, ":");
        if (!StringUtils.isNullOrWhiteSpace(strDate)) {
            attendanceCriteria.setAttDate(DateHelper.parse(temp, "yyyy-MM-dd"));
        } else {
            attendanceCriteria.setAttDate(new Date());
        }
        String deptId = request.getParameter("deptId");
        if (!StringUtils.isNullOrWhiteSpace(deptId) && !"0".equals(deptId)) {
            attendanceCriteria.setDeptId(Long.parseLong(deptId));
        }
        /*String loginName=request.getParameter("loginName");

        if(!StringUtils.isNullOrWhiteSpace(loginName)){
            attendanceCriteria.setEmpId();
        }*/
        List<EmpAttendanceDto> list = attendanceEnsureService.getEmpAttendanceInfo(attendanceCriteria);
        ListResult pagedResult = new ListResult(list);
        String json = JsonHelper.toJson(pagedResult);
        AjaxResponse.write(response, json);
    }

    @RequestMapping(value = "ajax_playAttendance_Query_list")
    public void ajax_playAttendance_Query_list(HttpServletRequest request, HttpServletResponse response) {
        EmpAttendancesCriteria attendanceCriteria = new EmpAttendancesCriteria();
        String strDate = request.getParameter("attDate");

        String name = request.getParameter("loginName");
        if (!StringUtils.isNullOrWhiteSpace(name)) {
            attendanceCriteria.setName(URI.deURI(name).trim());
        }
//        String s = "%3A";
//        String temp = strDate.replaceAll(s, ":");
        if (!StringUtils.isNullOrWhiteSpace(strDate)) {
            attendanceCriteria.setAttDate(DateHelper.parse(strDate, "yyyy-MM-dd"));
        } else {
            attendanceCriteria.setAttDate(new Date());
        }
        String deptId = request.getParameter("deptId");
        if (!StringUtils.isNullOrWhiteSpace(deptId) && !"0".equals(deptId)) {
            attendanceCriteria.setDeptId(Long.parseLong(deptId));
        } else {
            EmpSessionDto empSessionDto = us.getCurrentUser(request);
            attendanceCriteria.setDeptId(empSessionDto.getDeptId());
        }

        List<EmpAttendanceDto> list = attendanceEnsureService.getEmpAttendanceInfo(attendanceCriteria);


        AtendanceEnsureInfoDto atendanceEnsureInfoDto = attendanceEnsureService.getDeptInfoByDay(us.getCurrentUser(request).getDeptId(), strDate);

        Boolean isEnsured = false;
        if (atendanceEnsureInfoDto != null)
            isEnsured = atendanceEnsureInfoDto.getIsSure();
        ListResult pagedResult = new ListResult(list, isEnsured);
        String b = holidayInfoService.getEmpWorkStateByHoliday(strDate);
        pagedResult.setDayStation(b);
        String json = JsonHelper.toJson(pagedResult);
        AjaxResponse.write(response, json);
    }

    @RequestMapping(value = "ajax_doPlayAttendance")
    public void ajax_doPlayAttendance(HttpServletRequest request, HttpServletResponse response) {
        String deptId = request.getParameter("deptId");
        String attDate = request.getParameter("attD");
        String b = holidayInfoService.getEmpWorkStateByHoliday(attDate);
        Date date = Helper.formateStringToDate(attDate.toString(), "yyyy-MM-dd");
        CommonResult list = attendanceEnsureService.ensureAttendance(Long.parseLong(deptId), Helper.formateMinDateToString(date), b);
        String json = JsonHelper.toJson(list);
        System.out.println(json);
        AjaxResponse.write(response, json);
    }


    /**
     * 给单个员工打考勤
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "ajax_doPlayOneAttendance")
    public void ajax_doPlayOneAttendance(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        String attDate = request.getParameter("date");
        Date date = Helper.formateStringToDate(attDate.toString(), "yyyy-MM-dd");
        String kind = request.getParameter("attendanceKind");
        // todo 默认情况下页面上的出勤状态其实为"",这时候要预先修改为出勤状态
        if (kind.equals("")) {
            AttendanceOperateDto dto = attendanceEnsureService.getInfoDetail(Long.parseLong(id), attDate);
            if (dto == null) {
                dto = new AttendanceOperateDto();
                EmpDto empDto = employeeService.getEmpById(Long.parseLong(id));
                if (!StringUtils.isNullOrWhiteSpace(attDate))
                    dto.setAttDate(Helper.formateStringToDate(attDate, "yyyy-MM-dd HH:mm:ss"));
                dto.setEmpId(Long.parseLong(id));
                dto.setDepId(empDto.getDeptId());
                dto.setName(empDto.getName());
                dto.setAttDate(Helper.formateStringToDate(attDate));
                String b = holidayInfoService.getEmpWorkStateByHoliday(attDate);
                if (b.equals("休息")) {
                    dto.setAttendanceKind(AttendanceKind.WX);
                } else if (b.equals("上班")) {
                    dto.setAttendanceKind(AttendanceKind.I);
                } else if (b.equals("公休加班")) {
                    dto.setAttendanceKind(AttendanceKind.J1);
                } else {
                    dto.setAttendanceKind(AttendanceKind.J2);
                }
                dto.setTimeRange(1f);
            }
            CommonResult commonResult = attendanceEnsureService.addOrUpdateEnsureInfo(dto);
            if (!commonResult.getIsSuccess()) {
                AjaxResponse.write(response, JsonHelper.toJson(commonResult));
                return;
            }

        }
        CommonResult list = attendanceEnsureService.ensureOneAttendance(Long.parseLong(id), Helper.formateMinDateToString(date), kind == "" ? AttendanceKind.I : AttendanceKind.values()[Integer.parseInt(kind)]);
        String json = JsonHelper.toJson(list);
        System.out.println(json);
        AjaxResponse.write(response, json);
    }

    @RequestMapping(value = "add_update_attendance", method = RequestMethod.POST)
    public void add_update_attendance(HttpServletRequest request, HttpServletResponse response) {
        String empId = request.getParameter("empId");
        String empName = request.getParameter("empName");
        String attendanceKind = request.getParameter("attendanceKind");
//        String strBeginDate = request.getParameter("strBeginDate");
//        String strEndDate = request.getParameter("strEndDate");
//        String leaveCount = request.getParameter("leaveCount");
        String attDate = request.getParameter("attDate");
//        String timeRange = request.getParameter("timeRange");
        String deptId = request.getParameter("deptId");

        AttendanceOperateDto attendanceOperateDto = new AttendanceOperateDto();
        attendanceOperateDto.setEmpId(Long.parseLong(empId));
        attendanceOperateDto.setName(empName);
        AttendanceKind kind = AttendanceKind.valueOf(attendanceKind);
        attendanceOperateDto.setAttendanceKind(kind);
        // attendanceOperateDto.setBeginDate(Helper.formateStringToDate(strBeginDate));
        //attendanceOperateDto.setEndDate(Helper.formateStringToDate(strEndDate));
        if (!StringUtils.isNullOrWhiteSpace(attDate))
            attendanceOperateDto.setAttDate(Helper.formateStringToDate(attDate));
        attendanceOperateDto.setTimeRange(1F);
        attendanceOperateDto.setDepId(Long.parseLong(deptId));
        CommonResult commonResult = attendanceEnsureService.addOrUpdateEnsureInfo(attendanceOperateDto);
        String json = JsonHelper.toJson(commonResult);
        AjaxResponse.write(response, json);
    }

    @RequestMapping(value = "_addOrUpdateAttendance")
    public ModelAndView _addOrUpdateAttendance(Model model, HttpServletRequest request, HttpServletResponse response) {
        String empId = request.getParameter("id");
        String date = request.getParameter("date");
        AttendanceOperateDto dto = attendanceEnsureService.getInfoDetail(Long.parseLong(empId), date);
        if (dto == null) {
            dto = new AttendanceOperateDto();
            EmpDto empDto = employeeService.getEmpById(Long.parseLong(empId));
            if (!StringUtils.isNullOrWhiteSpace(date))
                dto.setAttDate(Helper.formateStringToDate(date, "yyyy-MM-dd HH:mm:ss"));
            dto.setEmpId(Long.parseLong(empId));
            dto.setDepId(empDto.getDeptId());
            dto.setName(empDto.getName());
            dto.setAttDate(Helper.formateStringToDate(date));
            dto.setAttendanceKind(AttendanceKind.I);
            //dto.setDepName(deptName);
        }
        if (dto.getBeginDate() != null)
            model.addAttribute("beginDate", Helper.formateMinDateToString(dto.getBeginDate()));
        if (dto.getEndDate() != null)
            model.addAttribute("endDate", Helper.formateMinDateToString(dto.getEndDate()));
        model.addAttribute("attDate", Helper.formateMinDateToString(dto.getAttDate()));

        model.addAttribute("bean", dto);
        return view("attendance/_addOrUpdateAttendance", model);
    }

    @RequestMapping(value = "sra_a_empDynamics")
    public ModelAndView empDynamics(Model model, HttpServletRequest request, HttpServletResponse response) {

        String beginDate = DateHelper.formatDate(DateHelper.setDay(1), "yyyy-MM-dd");
        String endDate = DateHelper.formatDate(new Date(), "yyyy-MM-dd");
        model.addAttribute("beginDate", beginDate);
        model.addAttribute("endDate", endDate);

        Date date = new Date();
        String month = Helper.formatDateToString(date, "MM");
        String day = Helper.formatDateToString(date, "dd");

        model.addAttribute("isDept", true);
        model.addAttribute("NowMonth", month);
        model.addAttribute("NowDday", day);

        return view(layout, "attendance/empDynamics", model);
    }

    @RequestMapping(value = "sra_r_empDynamics")
    public ModelAndView hrempDynamics(Model model, HttpServletRequest request, HttpServletResponse response) {
        DeptmentCriteria criteria = new DeptmentCriteria();
        criteria.setPageSize(100);
        //TODO change method
        PagedResult deptmentDto = deptmentService.findDeptsPaged(criteria);

        Date date = new Date();
        String month = Helper.formatDateToString(date, "MM");
        String day = Helper.formatDateToString(date, "dd");
        model.addAttribute("isDept", false);
        model.addAttribute("NowMonth", month);
        model.addAttribute("NowDday", day);
        model.addAttribute("deptAll", deptmentDto.getItems());
        return view(layout, "attendance/empDynamics", model);
    }

    @RequestMapping(value = "dowload_attend")
    public void dowload_attend(HttpServletRequest request, HttpServletResponse response) {
        String begin_Date = request.getParameter("sBeginDate");
        String end_Date = request.getParameter("sEndDate");
        String loginName = URI.deURI(request.getParameter("empName"));
        AttendanceCriteria attendanceCriteria = new AttendanceCriteria();
        String deptId = request.getParameter("deptId");
        if (StringUtils.isNullOrWhiteSpace(deptId)) {
            EmpSessionDto empSessionDto = us.getCurrentUser(request);
            attendanceCriteria.setDepId(empSessionDto.getDeptId());
//            attendanceCriteria.setDepId(65l);
        } else if (!deptId.equals("0")) {
            attendanceCriteria.setDepId(Long.parseLong(deptId));
        }
        if (!StringUtils.isNullOrWhiteSpace(loginName)) {
            attendanceCriteria.setEmpName(loginName);
        }
        if (!StringUtils.isNullOrWhiteSpace(begin_Date)) {
            attendanceCriteria.setBeginDate(DateHelper.parse(begin_Date + " 00:00:00", "yyyy-MM-dd HH:mm:ss"));
        } else {
            attendanceCriteria.setBeginDate(DateHelper.parse(DateHelper.formatDate(DateHelper.setDay(1), "yyyy-MM-dd 00:00:00")));
        }

        if (!StringUtils.isNullOrWhiteSpace(end_Date)) {
            attendanceCriteria.setEndDate(DateHelper.parse(end_Date + " 23:59:59", "yyyy-MM-dd HH:mm:ss"));
        } else {
            attendanceCriteria.setEndDate(DateHelper.parse(DateHelper.formatDate(DateHelper.setDay(DateHelper.getDay(new Date())), "yyyy-MM-dd 23:59:59")));
        }
        List<DayModel> list = getDayMap(attendanceCriteria.getBeginDate(), attendanceCriteria.getEndDate());
        List<AttendDowloadsDto> dowloadsDtos = attendService.getDowlodsDto(list, attendanceCriteria);

        AttendDownloadFactory expotExcel = new AttendDownloadFactory();
        String[] header = new String[]{};
        expotExcel.doExportExcel("考勤统计表", header, dowloadsDtos, "yyyy-MM-dd", response);

    }

    @RequestMapping(value = "ajax_empDynamics_Query_list")
    public void ajax_empDynamicsQuery_list_and_list(HttpServletRequest request, HttpServletResponse response) {

        AttendanceCriteria attendanceCriteria = new AttendanceCriteria();
        String pageNumber = request.getParameter("pageNumber");
        if (!StringUtils.isNullOrWhiteSpace(pageNumber)) {
            attendanceCriteria.setPageNumber(Integer.parseInt(pageNumber));
        }
        attendanceCriteria.setPageSize(20);
        String deptId = request.getParameter("deptId");
        if (deptId == null) {
            EmpSessionDto empSessionDto = us.getCurrentUser(request);
            attendanceCriteria.setDepId(empSessionDto.getDeptId());
//            attendanceCriteria.setDepId(65L);
        } else if (!deptId.equals("0")) {
            attendanceCriteria.setDepId(Long.parseLong(deptId));
        }

        String loginName = URI.deURI(request.getParameter("loginName"));
        String begin_Date = request.getParameter("beginDate");
        String end_Date = request.getParameter("endDate");

        if (!StringUtils.isNullOrWhiteSpace(loginName)) {
            attendanceCriteria.setEmpName(loginName);
        }
        if (!StringUtils.isNullOrWhiteSpace(begin_Date)) {
            attendanceCriteria.setBeginDate(DateHelper.parse(begin_Date + " 00:00:00", "yyyy-MM-dd HH:mm:ss"));
        } else {
            attendanceCriteria.setBeginDate(DateHelper.parse(DateHelper.formatDate(DateHelper.setDay(1), "yyyy-MM-dd 00:00:00")));
        }

        if (!StringUtils.isNullOrWhiteSpace(end_Date)) {
            attendanceCriteria.setEndDate(DateHelper.parse(end_Date + " 23:59:59", "yyyy-MM-dd HH:mm:ss"));
        } else {
            attendanceCriteria.setEndDate(DateHelper.parse(DateHelper.formatDate(DateHelper.setDay(DateHelper.getDay(new Date())), "yyyy-MM-dd 23:59:59")));
        }

        List<EmpAttendsDto> result = attendService.pageFindSimpleByDepId(attendanceCriteria);
        //分页
        int total = result.size();
        List<EmpAttendsDto> list = new ArrayList<>();
        int min = attendanceCriteria.getPageSize() * attendanceCriteria.getPageNumber() > total ? total : attendanceCriteria.getPageSize() * attendanceCriteria.getPageNumber();
        if (min > 0) {
            for (int i = (attendanceCriteria.getPageNumber() - 1) * attendanceCriteria.getPageSize(); i < min; i++) {
                list.add(result.get(i));
            }
        }
        PageEmpDynamicsModel empDynamicsModel = new PageEmpDynamicsModel(attendanceCriteria.getPageNumber(), attendanceCriteria.getPageSize(), total);
        Map<String, Object> map = new HashMap<>();

        empDynamicsModel.setMap(map);//在页面tpl中已经使用了map，虽然已经被注释，但是这里使用这个就是为了模板不错
        empDynamicsModel.setResultList(list);

        empDynamicsModel.setDayModels(getDayMap(attendanceCriteria.getBeginDate(), attendanceCriteria.getEndDate()));
        String json = JsonHelper.toJson(empDynamicsModel);
        System.out.println(json);
        AjaxResponse.write(response, json);
    }

    private Boolean getWeek(String year, String month, Integer day) {
        Calendar cal = Calendar.getInstance();
        cal.set(Integer.valueOf(year), Integer.valueOf(month) - 1, day);
        int week = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (week == 6 || week == 0) {
            return true;
        } else {
            return false;
        }
    }

    private Integer getDayByYearAndMonth(String year, String month) {

        int day = -1;
        switch (month) {
            case "1":
            case "3":
            case "5":
            case "7":
            case "8":
            case "10":
            case "12":
                day = 31;
                break;
            case "2":
                if (DateHelper.isLeapYear(Integer.valueOf(year))) {
                    day = 29;
                } else {
                    day = 28;
                }
                ;
                break;
            case "4":
            case "6":
            case "9":
            case "11":
                day = 30;
                break;
        }
        return day;

    }

    private DayModel dayModel(int temp, String year, String month) {
        DayModel dayModel = new DayModel();
        dayModel.setDay(temp);
        dayModel.setWeekCheck(getWeek(year, month, temp));
        return dayModel;
    }


    private List<DayModel> getDayMap(Date beginDate, Date endDate) {
        List<DayModel> dayModels = new ArrayList<>();
        int beginYear = DateHelper.getYear(beginDate);
        int beginMonth = DateHelper.getMonth(beginDate);
        int beginDay = DateHelper.getDay(beginDate);
        int endYear = DateHelper.getYear(endDate);
        int endMonth = DateHelper.getMonth(endDate);
        int endDay = DateHelper.getDay(endDate);

        if (beginMonth == endMonth) {
            int temp = beginDay;
            while (temp <= endDay) {
                dayModels.add(dayModel(temp, String.valueOf(beginYear), String.valueOf(beginMonth)));
                temp++;
            }
        } else {
            int beginDaySum = getDayByYearAndMonth(String.valueOf(beginYear), String.valueOf(beginMonth));
            int temp = beginDay;
            while (temp <= beginDaySum) {
                dayModels.add(dayModel(temp, String.valueOf(beginYear), String.valueOf(beginMonth)));
                temp++;
            }
            int tempBeginMonth = beginMonth + 1;
            while (tempBeginMonth <= endMonth) {
                if (tempBeginMonth == endMonth) {
                    for (int i = 1; i <= endDay; i++)
                        dayModels.add(dayModel(i, String.valueOf(endYear), String.valueOf(endMonth)));
                    break;
                }
                int tempCounDay = getDayByYearAndMonth(String.valueOf(beginYear), String.valueOf(tempBeginMonth));
                for (int i = 1; i <= tempCounDay; i++) {
                    dayModels.add(dayModel(i, String.valueOf(beginYear), String.valueOf(tempBeginMonth)));
                }
                tempBeginMonth++;
            }
        }
        return dayModels;
    }


    @RequestMapping(value = "sra_a_attendanceStatistics")
    public ModelAndView attendanceStatistics(Model model, HttpServletRequest request, HttpServletResponse response) {

        String beginDate = DateHelper.formatDate(DateHelper.setDay(1), "yyyy-MM-dd");
        String endDate = DateHelper.formatDate(new Date(), "yyyy-MM-dd");
        model.addAttribute("beginDate", beginDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("isDept", true);
        return view(layout, "attendance/attendanceStatistics", model);
    }

    @RequestMapping(value = "sra_r_attendanceStatistics")
    public ModelAndView attendanceStatistics2(Model model, HttpServletRequest request, HttpServletResponse response) {

        String beginDate = DateHelper.formatDate(DateHelper.setDay(1), "yyyy-MM-dd");
        String endDate = DateHelper.formatDate(new Date(), "yyyy-MM-dd");
        model.addAttribute("beginDate", beginDate);
        model.addAttribute("endDate", endDate);
        DeptmentCriteria deptmentCriteria = new DeptmentCriteria();
        deptmentCriteria.setDeptName("");
        deptmentCriteria.setDeptNo("");
        List<DeptmentDto> list = deptmentService.getAllDept();

        model.addAttribute("NowTime", DateHelper.formatDate(new Date(), "yyyy-MM-dd"));
        model.addAttribute("deptAll", list);
        model.addAttribute("isDept", false);
        return view(layout, "attendance/attendanceStatistics", model);
    }


    @RequestMapping(value = "ajax_attendanceStatistics_Query_list")
    public void ajax_attendanceStatistics_Query_list(HttpServletRequest request, HttpServletResponse response) {

        AttendanceCriteria attendanceCriteria = new AttendanceCriteria();
        String pageNumber = request.getParameter("pageNumber");
        if (!StringUtils.isNullOrWhiteSpace(pageNumber)) {
            attendanceCriteria.setPageNumber(Integer.parseInt(pageNumber));
        }
        attendanceCriteria.setPageSize(20);
        String deptId = request.getParameter("deptId");
        if (deptId == null) {
            EmpSessionDto empSessionDto = us.getCurrentUser(request);
            attendanceCriteria.setDepId(empSessionDto.getDeptId());
//            attendanceCriteria.setDepId(65l);
        } else if (!deptId.equals("0")) {
            attendanceCriteria.setDepId(Long.parseLong(deptId));
        }
        String empName = request.getParameter("empName");
        if (!StringUtils.isNullOrWhiteSpace(empName))
            attendanceCriteria.setEmpName(URI.deURI(empName));
        String beginDate = request.getParameter("beginDate");
        String endDate = request.getParameter("endDate");
        if (!StringUtils.isNullOrWhiteSpace(beginDate)) {
            attendanceCriteria.setBeginDate(DateHelper.parse(beginDate + " 00:00:00", "yyyy-MM-dd HH:mm:ss"));
        } else {
            attendanceCriteria.setBeginDate(DateHelper.parse(DateHelper.formatDate(DateHelper.setDay(1), "yyyy-MM-dd 00:00:00")));
        }
        if (!StringUtils.isNullOrWhiteSpace(endDate)) {
            attendanceCriteria.setEndDate(DateHelper.parse(endDate + " 23:59:59", "yyyy-MM-dd HH:mm:ss"));
        } else {
            attendanceCriteria.setEndDate(DateHelper.parse(DateHelper.formatDate(DateHelper.setDay(DateHelper.getDay(new Date())), "yyyy-MM-dd 23:59:59")));
        }
        PagedResult<AttendSummeryResultDto> pResult = attendService.PageBySummeryDto(attendanceCriteria);
        String json = JsonHelper.toJson(pResult);
        System.out.println(json);
        AjaxResponse.write(response, json);
    }

    @RequestMapping(value = "ajax_download_statistics", method = RequestMethod.POST)
    public void ajax_download_statistics(HttpServletRequest request, HttpServletResponse response) {

        AttendanceCriteria attendanceCriteria = new AttendanceCriteria();
        String deptId = request.getParameter("deptId1");
        if (StringUtils.isNullOrWhiteSpace(deptId)) {
            EmpSessionDto empSessionDto = us.getCurrentUser(request);
            attendanceCriteria.setDepId(empSessionDto.getDeptId());
//            attendanceCriteria.setDepId(65l);
        } else if (!"0".equals(deptId)) {
            attendanceCriteria.setDepId(Long.parseLong(deptId));
        }
        String empName = request.getParameter("LName");
        if (!StringUtils.isNullOrWhiteSpace(empName))
            attendanceCriteria.setEmpName(URI.deURI(empName));
        String date = request.getParameter("date1");
        if (!StringUtils.isNullOrWhiteSpace(date)) {
            String s = "%3A";
            String temp = date.replaceAll(s, ":");
            attendanceCriteria.setBeginDate(DateHelper.parse(temp, "yyyy-MM"));
        } else {
            attendanceCriteria.setBeginDate(new Date());
        }
        attendanceCriteria.setPageSize(Integer.MAX_VALUE);
        PagedResult<AttendanceSummeryDowloadDto> pagedResult = attendanceService.PageBySummerDowloadDto(attendanceCriteria);

        ExpotExcel<AttendanceSummeryDowloadDto> expotExcel = new ExpotExcel<>();
        String[] header = new String[]{"姓名", "出勤", "公休加班", "节日加班", "野外", "出差", "学历学习", "脱产学习", "带薪休假", "疗养",
                "探亲", "婚丧", "工伤", "产假", "产后休长假", "护理/陪护/计划生育假", "病假", "事假", "旷工", "拘留", "戒毒", "劳保", "补休"};
        expotExcel.doExportExcel("单位月考勤表", header, pagedResult.getItems(), "yyyy-MM-dd", response);
    }

    @RequestMapping(value = "sra_r_addOrUpdateHoliday")
    public ModelAndView addOrUpdateHoliday(Model model, HttpServletRequest request, HttpServletResponse response) {

        return view(layout, "attendance/addOrUpdateHoliday", model);
    }

    @RequestMapping(value = "ajax_addOrUpdateHoliday_Query_list")
    public void ajax_addOrUpdateHoliday_Query_list(HolidayInfoCriteria holidayInfoCriteria, HttpServletRequest request, HttpServletResponse response) {

        String name = URI.deURI(request.getParameter("name"));
        holidayInfoCriteria.setHname(name);

        PagedResult pagedResult = holidayInfoService.findHolidayInfosPaged(holidayInfoCriteria);
        String json = JsonHelper.toJson(pagedResult);
        AjaxResponse.write(response, json);
    }

    @RequestMapping(value = "_addHoliday")
    public ModelAndView addMenuForm(Model model, HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        if (!StringUtils.isNullOrWhiteSpace(id)) {
            HolidayInfoDto holidayInfoDto = holidayInfoService.getByHolidayInfoId(Long.parseLong(id));
            model.addAttribute("bean", holidayInfoDto);
        }
        return view("attendance/_addHoliday", model);
    }

    @RequestMapping(value = "add_update_Holiday")
    public void add_update_Holiday(HolidayInfoDto holidayInfoDto, Model model, HttpServletRequest request, HttpServletResponse response) {

        CommonResult commonResult = null;
        if (holidayInfoDto.getId() != null && holidayInfoDto.getId() > 0) {
            commonResult = holidayInfoService.updateHolidayInfo(holidayInfoDto);
        } else {
            commonResult = holidayInfoService.addHolidayInfo(holidayInfoDto);
        }


        String time = "2017-2-5";
        String time_2 = "2017-2-18";
        Date beginTime = DateHelper.parse(time, "yyyy-MM-dd");
        Date endTime = DateHelper.parse(time_2, "yyyy-MM-dd");

        holidayInfoService.getTotalWorkDays(beginTime, endTime);
        String str = holidayInfoService.getEmpWorkStateByHoliday(beginTime);
        formLeaveService.getEmpsStatusByLeave(beginTime);
        formEvectionService.getEmpStatusByBusiness(endTime);


        String json = JsonHelper.toJson(commonResult);
        System.out.println(commonResult);
        AjaxResponse.write(response, json);
    }

    @RequestMapping(value = "ajax_deleteHoliday")
    @ResponseBody
    public String deleteHolidaies(HttpServletRequest request) {
        String id = request.getParameter("ids");
        String[] ids = id.split(",");
        List<Long> list = new ArrayList<>();
        for (int i = 0; i < ids.length; i++) {
            list.add(Long.valueOf(ids[i]));
        }
        holidayInfoService.deleteHolidayInfo(list);
        return "success";
    }


    @RequestMapping(value = "sra_r_adSituation")
    public ModelAndView adSituation(Model model, HttpServletRequest request, HttpServletResponse response) {

        return view(layout, "attendance/adSituation", model);
    }

    @RequestMapping(value = "ajax_adSituation_Query_list")
    public void ajax_adSituation_Query_list(HttpServletRequest request, HttpServletResponse response) {

        Date date = new Date();
        List<NearlyFiveDayAttendanceInfoDto> list = attendanceEnsureService.getNearlyFiveDayAttendance();
        SevenDayModel dayModel = new SevenDayModel();
        dayModel.setList(list);
        int n = 1;
        for (int i = 1; i <= 7; i++) {
            dayModel.setAddSuccess(false);
            Date sdate = DateHelper.addDay(date, i - 7);
            dayModel = SetModel(dayModel, n, sdate);
            if (dayModel.getAddSuccess()) {
                n = n + 1;
            }
        }
        String json = JsonHelper.toJson(dayModel);
        System.out.println(json);
        AjaxResponse.write(response, json);
    }

    private SevenDayModel SetModel(SevenDayModel model, int attr, Date date) {
        if (!(DateHelper.getWeek(date) == 1 || DateHelper.getWeek(date) == 7)) {
            String value = DateHelper.formatDate(date, "yyyy-MM-dd");
            switch (attr) {
                case 1:
                    model.setD1(value);
                    break;
                case 2:
                    model.setD2(value);
                    break;
                case 3:
                    model.setD3(value);
                    break;
                case 4:
                    model.setD4(value);
                    break;
                case 5:
                    model.setD5(value);
                    break;
                default:
                    return model;
            }

            model.setAddSuccess(true);
        }

        return model;
    }

    @RequestMapping(value = "sra_a_takeOrOverTimeStatistics")
    public ModelAndView takeOrOverTimeStatistics(Model model, HttpServletRequest request, HttpServletResponse response) {
        DeptmentCriteria deptmentCriteria = new DeptmentCriteria();
        deptmentCriteria.setDeptName("");
        deptmentCriteria.setDeptNo("");

        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM");
        model.addAttribute("NowTime", dateFormat.format(date));
        model.addAttribute("isDept", true);
        return view(layout, "attendance/takeOrOverTimeStatistics", model);
    }

    @RequestMapping(value = "sra_r_takeOrOverTimeStatistics")
    public ModelAndView takeOrOverTimeStatistics2(Model model, HttpServletRequest request, HttpServletResponse response) {
        DeptmentCriteria deptmentCriteria = new DeptmentCriteria();
        deptmentCriteria.setDeptName("");
        deptmentCriteria.setDeptNo("");
//        PagedResult deptmentDto = deptmentService.findDeptsPaged(deptmentCriteria);
        List<DeptmentDto> list = deptmentService.getAllDept();

        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM");
        model.addAttribute("NowTime", dateFormat.format(date));
        model.addAttribute("isDept", false);
        model.addAttribute("deptAll", list);
        return view(layout, "attendance/takeOrOverTimeStatistics", model);
    }

    @RequestMapping(value = "ajax_download_attend", method = RequestMethod.POST)
    public void ajax_download_tots(HttpServletRequest request, HttpServletResponse response) {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM");
        String month = dateFormat.format(date);
        String selectMonth = request.getParameter("mh");
        EmpAttendanceWorkCriteria empAttendanceWorkCriteria = new EmpAttendanceWorkCriteria();
        if (selectMonth == null || "0".equals(selectMonth)) {
            empAttendanceWorkCriteria.setMonth(Integer.parseInt(month));
        } else {
            empAttendanceWorkCriteria.setMonth(Integer.parseInt(selectMonth));
        }
        empAttendanceWorkCriteria.setYear(DateHelper.getYear(new Date()));

        String deptId = request.getParameter("deptId1");
        if (deptId == null || "".equals(deptId)) {
            EmpSessionDto empSessionDto = us.getCurrentUser(request);
            empAttendanceWorkCriteria.setDeptId(empSessionDto.getDeptId());
        } else if (!deptId.equals("0")) {
            empAttendanceWorkCriteria.setDeptId(Long.parseLong(deptId));
        }

        String empName = request.getParameter("LName");
        if (!StringUtils.isNullOrWhiteSpace(empName))
            empAttendanceWorkCriteria.setEmpName(URI.deURI(empName));
        List<EmpAttendanceWorkDLoadDto> list = attendanceEnsureService.getEmpRestDLoadInfo(empAttendanceWorkCriteria);
        ExpotExcel<EmpAttendanceWorkDLoadDto> expotExcel = new ExpotExcel<>();
        String[] header = new String[]{"姓名", "参加工作时间", "工龄", "应享受休假天数", "当月休假", "累计休假", "未休假天数", "疗养", "休疗累计天数", "补休(天)"};
        expotExcel.doExportExcel("休假、加班、补休统计表", header, list, "yyyy-MM-dd", response);
    }

    @RequestMapping(value = "ajax_takeOrOverTimeStatistics_Query_list")
    public void ajax_takeOrOverTimeStatistics_Query_list(HttpServletRequest request, HttpServletResponse response) {

        String selectMonth = request.getParameter("selectMonth");
        EmpAttendanceWorkCriteria empAttendanceWorkCriteria = new EmpAttendanceWorkCriteria();
        empAttendanceWorkCriteria.setMonth(Integer.parseInt(selectMonth));
        empAttendanceWorkCriteria.setYear(DateHelper.getYear(new Date()));

        String deptId = request.getParameter("deptId");
        if (deptId == null) {
            EmpSessionDto empSessionDto = us.getCurrentUser(request);
            empAttendanceWorkCriteria.setDeptId(empSessionDto.getDeptId());
        } else if (!deptId.equals("0")) {
            empAttendanceWorkCriteria.setDeptId(Long.parseLong(deptId));
        }

        String empName = request.getParameter("empName");
        if (!StringUtils.isNullOrWhiteSpace(empName))
            empAttendanceWorkCriteria.setEmpName(URI.deURI(empName));
        List<EmpAttendanceWorkDto> list = attendanceEnsureService.getEmpRestInfo(empAttendanceWorkCriteria);
        PagedResult pagedResult = new PagedResult(list, 1, 10, list == null ? 0 : list.size());
        String json = JsonHelper.toJson(pagedResult);
        System.out.println(json);
        AjaxResponse.write(response, json);
    }

    /**
     * 个人出勤表
     *
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "myAttendance")
    public ModelAndView workAtt(Model model, HttpServletRequest request, HttpServletResponse response) {
        EmpSessionDto user = us.getCurrentUser(request);
        model.addAttribute("currentUser", user);
        int time = DateHelper.getMonth(new Date());
        model.addAttribute("time", time);
        List<String> list = holidayInfoService.getHnameByHolidayInfo();
        model.addAttribute("holidayName", list);

        return view(layout, "attendance/myAttendance", model);
    }

    /**
     * 查询个人考勤表
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "ajax_get_myAttendance")
    public void ajax_get_myAttendance(HttpServletRequest request, HttpServletResponse response) {
        EmpSessionDto user = us.getCurrentUser(request);
        String beginTim = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        List<AttendanceCalendar> list = attendanceService.findOneAttendance(user.getId(), beginTim, endTime);
        AjaxResponse.write(response, JsonHelper.toJson(list));
    }

    /**
     * 检查某个日期是否是周末
     *
     * @param string
     * @return
     */
    private int isWeekday(String string) {
        Date date = DateHelper.parse(string, "yyyy-MM-dd");
        int weekday = DateHelper.getWeek(date) - 1;
        if (weekday == 0 || weekday == 6) return 1;
        return 0;

    }
}
