package com.sunesoft.lemon.webapp.controller.attendance;

import com.sunesoft.lemon.ay.equipment.application.factory.DtoFactory;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.utils.DateHelper;
import com.sunesoft.lemon.fr.utils.JsonHelper;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.fr.utils.excel.ExpotExcel;
import com.sunesoft.lemon.syms.eHr.application.AttendService;
import com.sunesoft.lemon.syms.eHr.application.AttendTypeService;
import com.sunesoft.lemon.syms.eHr.application.DeptmentService;
import com.sunesoft.lemon.syms.eHr.application.HolidayInfoService;
import com.sunesoft.lemon.syms.eHr.application.criteria.AttendCriteria;
import com.sunesoft.lemon.syms.eHr.application.criteria.AttendSumCtriteria;
import com.sunesoft.lemon.syms.eHr.application.criteria.DeptmentCriteria;
import com.sunesoft.lemon.syms.eHr.application.criteria.EmpAttendanceWorkCriteria;
import com.sunesoft.lemon.syms.eHr.application.dtos.*;
import com.sunesoft.lemon.syms.eHr.application.factory.AttendDownloadFactory;
import com.sunesoft.lemon.syms.eHr.domain.attend.AttendType;
import com.sunesoft.lemon.syms.eHr.domain.dept.Deptment;
import com.sunesoft.lemon.webapp.controller.Layout;
import com.sunesoft.lemon.webapp.function.UserSession;
import com.sunesoft.lemon.webapp.model.SevenDayModel;
import com.sunesoft.lemon.webapp.model.partyGroup.NewSevenDayModel;
import com.sunesoft.lemon.webapp.utils.AjaxResponse;
import com.sunesoft.lemon.webapp.utils.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by xiazl on 2017/3/3.
 */
@Controller
public class AttendController extends Layout {
    @Autowired
    UserSession us;
    @Autowired
    AttendService attendService;
    @Autowired
    AttendTypeService attendTypeService;
    @Autowired
    DeptmentService deptmentService;
    @Autowired
    HolidayInfoService holidayInfoService;

    @RequestMapping(value = "sra_a_playAttend")
    public ModelAndView playAttendance(Model model, HttpServletRequest request, HttpServletResponse response) {
        AttendCriteria criteria = new AttendCriteria();
        criteria.setPageNumber(1);
        criteria.setPageSize(20);
//        String pageNumber=request.getParameter("pageNumber");
//        if(!StringUtils.isNullOrWhiteSpace(pageNumber))
//            criteria.setPageNumber(Integer.parseInt(pageNumber));//接受页码
        String name = request.getParameter("name");
        if (!StringUtils.isNullOrWhiteSpace(name)) {
            criteria.setEmpName(URI.deURI(name.trim()).trim());
            model.addAttribute("name", URI.deURI(name.trim()));
        }
        String date = request.getParameter("attDate");
        if (!StringUtils.isNullOrWhiteSpace(date)) {
            Date d = DateHelper.parse(date, "yyyy-MM-dd");
            criteria.setAttDate(d);
            model.addAttribute("NowTime", date);
            Date t = DateHelper.parse(DateHelper.formatDate(new Date()), "yyyy-MM-dd");
            if (d.before(t)) {
                model.addAttribute("canSure", 0);
            }
        } else {
            String s = DateHelper.formatDate(new Date(), "yyyy-MM-dd");
            criteria.setAttDate(DateHelper.parse(s, "yyyy-MM-dd"));
            model.addAttribute("NowTime", DateHelper.formatDate(new Date(), "yyyy-MM-dd"));
        }

        criteria.setDepId(us.getCurrentUser(request).getDeptId());
//        criteria.setDepId(65l);
        PagedResult<AttendDto> pagedResult = null;
        try {
            criteria.setPageSize(Integer.MAX_VALUE);
            pagedResult = attendService.page(criteria);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<AttendDto> list=new ArrayList<>();
        if (null != pagedResult && pagedResult.getItems() != null && pagedResult.getItems().size() > 0) {
            model.addAttribute("attList", pagedResult.getItems());
            //静态页面的分页查询不会了
//            List<AttendDto> ds=pagedResult.getItems();
//            criteria.setPageSize(20);
//            int total=pagedResult.getItems().size();
//            if(criteria.getPageSize()*criteria.getPageNumber()>total){
//                for (int i = (criteria.getPageNumber()-1)*criteria.getPageSize(); i <total ; i++) {
//                    list.add(ds.get(i));
//                }
//            }else {
//                for (int i = (criteria.getPageNumber()-1)*criteria.getPageSize(); i < criteria.getPageSize()*criteria.getPageNumber(); i++) {
//                    list.add(ds.get(i));
//
//                }
//            }
//           model.addAttribute("pageResult",new  PagedResult<AttendDto>(list,criteria.getPageNumber(),criteria.getPageSize(),total));
        }
        List<AttendType> typeList = attendTypeService.getByName("");//得到所有出勤类型
        if (null != typeList && !typeList.isEmpty())
            model.addAttribute("typeList", typeList);
        return view(layout, "attend/playAttend", model);
    }

    /**
     * 个人出勤表
     *
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "sra_a_myAttend")
    public ModelAndView workAtt(Model model, HttpServletRequest request, HttpServletResponse response) {
        EmpSessionDto user = us.getCurrentUser(request);
        model.addAttribute("currentUser", user);
        int time = DateHelper.getMonth(new Date());
        model.addAttribute("time", time);
        return view(layout, "attend/myAttend", model);
    }

    /**
     * 查询个人考勤表
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "ajax_get_myAttend")
    public void ajax_get_myAttendance(HttpServletRequest request, HttpServletResponse response) {
        EmpSessionDto user = us.getCurrentUser(request);
        String beginTim = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        List<AttendCalendar> list = attendService.findOneAttend(user.getId(), beginTim, endTime);
        AjaxResponse.write(response, JsonHelper.toJson(list));
    }


    @RequestMapping(value = "sra_a_takeAttendStatistics")
    public ModelAndView getAttendHolidays(Model model, HttpServletRequest request, HttpServletResponse response) {
        Date d = new Date();
        model.addAttribute("endTime", DateHelper.formatDate(d, "yyyy-MM-dd"));
        d = DateHelper.addMonth(d, -1);
        d = DateHelper.addDay(d, 1);
        model.addAttribute("beginTime", DateHelper.formatDate(d, "yyyy-MM-dd"));
        return view(layout, "attend/takeAttendSummery", model);
    }


    /**
     * 给单个员工打考勤
     *
     * @param request
     * @param response
     */
    @ResponseBody
    @RequestMapping(value = "ajax_doPlayOneAttend")
    public CommonResult ajax_doPlayOneAttend(HttpServletRequest request, HttpServletResponse response, AttendDto dto) {
        CommonResult result = attendService.ensureOne(dto);
        return result;
    }

    @RequestMapping(value = "_edit_attend_view")
    public ModelAndView getEditAttendView(HttpServletRequest request, Model model) {
        List<AttendType> typeList = attendTypeService.getByName("");//得到所有出勤类型
        model.addAttribute("types", typeList);
        String id = request.getParameter("id");
        if (!StringUtils.isNullOrWhiteSpace(id)) {
            AttendDto dto = attendService.get(Long.parseLong(id));
            model.addAttribute("bean", dto);
        }
        return view("attend/_editAttend", model);

    }

    /**
     * 修改考勤
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "ajax_update_attend")
    @ResponseBody
    public CommonResult editAttend(HttpServletRequest request, AttendDto dto) {
        CommonResult result = attendService.update(dto);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "ajax_doPlayAttend", method = RequestMethod.POST)
    public CommonResult ajax_doPlayAttend(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "attIds[]") List<String> attIds, @RequestParam(value = "empIds[]") List<String> empIds, @RequestParam(value = "att_type[]") List<String> list_types) {
        String attDate = request.getParameter("sdateTime");//有用的

        if (empIds != null && empIds.size() > 0) {
            List<AttendDto> attendDtos = new ArrayList<>();
            for (int i = 0; i < empIds.size(); i++) {
                AttendDto dto = new AttendDto();
                if (!StringUtils.isNullOrWhiteSpace(attIds.get(i))) {
                    dto.setId(Long.parseLong(attIds.get(i)));
                }
                if (!StringUtils.isNullOrWhiteSpace(empIds.get(i))) {
                    dto.setEmpId(Long.parseLong(empIds.get(i)));
                }

                String typeId = null;
                try {
                    typeId = list_types.get(i);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                dto.setTypeId(Long.parseLong(typeId));
                dto.setSdateTime(attDate);
                attendDtos.add(dto);
            }
            return attendService.ensureDept(attendDtos);
        } else {
            //没有人员
            return new CommonResult(false, "没有人员");
        }
    }

    /**
     * 获取统计数据
     *
     * @param request
     * @param response
     * @param criteria
     */
    @RequestMapping(value = "ajax_attendSum_Query_list")
    public void ajax_AttendSum_Query_list(HttpServletRequest request, HttpServletResponse response, AttendSumCtriteria criteria) {
        String beginTime = request.getParameter("beginTime");
        String endTime = request.getParameter("endTime");
        String name = request.getParameter("empName");
        criteria.setBegin(beginTime);
        criteria.setEnd(endTime);
        if (!StringUtils.isNullOrWhiteSpace(name)) {
            criteria.setName(URI.deURI(name).trim());
        }
        String dept = request.getParameter("deptId");
        if (!StringUtils.isNullOrWhiteSpace(dept)) {
            criteria.setDeptId(Long.parseLong(dept));
        } else {
            criteria.setDeptId(us.getCurrentUser(request).getDeptId());
        }
        PagedResult<AttendSumloadDto> pagedResult = attendService.AttendSumPage(criteria);
        String json = JsonHelper.toJson(pagedResult);
        AjaxResponse.write(response, json);
    }

    /**
     * 下载
     *
     * @param request
     * @param response
     * @param criteria
     */
    @RequestMapping(value = "downloadAttendSum_statistics")
    public void ajax_downloadAttendSum_statistics(HttpServletRequest request, HttpServletResponse response, AttendSumCtriteria criteria) {

        String beginTime = request.getParameter("begin");
        String endTime = request.getParameter("end");
        String name = request.getParameter("name");
        criteria.setBegin(beginTime);
        criteria.setEnd(endTime);
        if (!StringUtils.isNullOrWhiteSpace(name)) {
            criteria.setName(URI.deURI(name).trim());
        }

        String dept = request.getParameter("deptId");
        if (!StringUtils.isNullOrWhiteSpace(dept)) {
            criteria.setDeptId(Long.parseLong(dept));
        } else {
            criteria.setDeptId(us.getCurrentUser(request).getDeptId());
        }
        PagedResult<AttendSumloadDto> pagedResult = attendService.AttendSumPage(criteria);
        ExpotExcel<AttendSumloadDto> expotExcel = new ExpotExcel<>();
        String[] header = new String[]{"姓名", "出勤", "公休加班", "节日加班", "野外", "出差", "学历学习", "脱产学习", "带薪休假", "疗养",
                "探亲", "婚丧", "工伤", "产假", "产后休长假", "护理/陪护/计划生育假", "病假", "事假", "旷工", "拘留", "戒毒", "劳保", "补休"};
        expotExcel.doExportExcel("单位考勤表", header, pagedResult.getItems(), "yyyy-MM-dd", response);
    }


    /**
     * 加班补休统计
     *
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "sra_a_takeOrOverAttendStatistics")
    public ModelAndView takeOrOverAttendTimeStatistics(Model model, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute("end", DateHelper.formatDate(new Date(), "yyyy-MM-dd"));
        model.addAttribute("begin", DateHelper.formatDate(DateHelper.addMonth(new Date(), -1), "yyyy-MM-dd"));
        return view(layout, "attend/takeOrOverAttendTimeStatistics", model);
    }


    /**
     * 加班补休统计
     *
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "sra_a_rentakeOrOverAttendStatistics")
    public ModelAndView rentakeOrOverAttendTimeStatistics(Model model, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute("end", DateHelper.formatDate(new Date(), "yyyy-MM-dd"));
        model.addAttribute("begin", DateHelper.formatDate(DateHelper.addMonth(new Date(), -1), "yyyy-MM-dd"));
        List<DeptmentDto> list = deptmentService.getAllSimpleDept();
        model.addAttribute("deptAll", list);
        return view(layout, "attend/rentakeOrOverAttendTimeStatistics", model);
    }

    /**
     * @param request
     * @param response
     * @param criteria
     */
    @RequestMapping(value = "ajax_takeOrOverAttendTimeStatistics_Query_list")
    public void ajax_AttendOverSum_Query_list(HttpServletRequest request, HttpServletResponse response, AttendSumCtriteria criteria) {
        String beginTime = request.getParameter("begin");
        String endTime = request.getParameter("end");
        String name = request.getParameter("empName");
        String dept = request.getParameter("deptId");
        if (!StringUtils.isNullOrWhiteSpace(dept)) {
            if (dept.equals("000")) {
                criteria.setDeptId(null);
            } else
                criteria.setDeptId(Long.parseLong(dept));
        } else {
            criteria.setDeptId(us.getCurrentUser(request).getDeptId());
//            criteria.setDeptId(65L);
        }
        criteria.setBegin(beginTime);
        criteria.setEnd(endTime);
        if (!StringUtils.isNullOrWhiteSpace(name)) {
            criteria.setName(URI.deURI(name).trim());
        }
        criteria.setPageSize(20);
        PagedResult<EmpAttendWorkDto> pagedResult = attendService.AttendOverPage(criteria);
        String json = JsonHelper.toJson(pagedResult);
        AjaxResponse.write(response, json);
    }

    /**
     * 下载
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "ajax_download_attend")
    public void ajax_download_tots(HttpServletRequest request, HttpServletResponse response, AttendSumCtriteria criteria) {
        String beginTime = request.getParameter("begin");
        String endTime = request.getParameter("end");
        String name = request.getParameter("empName");
        String dept = request.getParameter("deptId");
        if (!StringUtils.isNullOrWhiteSpace(dept)) {
            if (dept.equals("000")) {
                criteria.setDeptId(null);
            } else
                criteria.setDeptId(Long.parseLong(dept));
        } else {
            criteria.setDeptId(us.getCurrentUser(request).getDeptId());
//            criteria.setDeptId(65l);
        }
        criteria.setBegin(beginTime);
        criteria.setEnd(endTime);
        if (!StringUtils.isNullOrWhiteSpace(name)) {
            criteria.setName(URI.deURI(name).trim());
        }
        criteria.setPageSize(Integer.MAX_VALUE);
        PagedResult<EmpAttendWorkDto> pagedResult = attendService.AttendOverPage(criteria);
        List<EmpAttendWorkDto> list = pagedResult.getItems();
        List<EmpAttendWorkDto1> list1 = new ArrayList<>();
        if (list.size() > 0) {
            for (EmpAttendWorkDto dto : list) {
                EmpAttendWorkDto1 dto1 = new EmpAttendWorkDto1();
                dto1 = DtoFactory.convert(dto, dto1);
                if (dto.getSpaAndRestDays() != null && dto.getSpaAndRestDays() == 0)
                    dto1.setSpaAndRestDays(null);
                if (dto.getWorkAge() != null) {
                    if (dto.getWorkAge() < 1) {
                    } else if (dto.getWorkAge() <= 5) {
                        dto1.setYearDay(4);
                    } else if (dto.getWorkAge() <= 10) {
                        dto1.setYearDay(7);
                    } else if (dto.getWorkAge() <= 15) {
                        dto1.setYearDay(10);
                    } else {
                        dto1.setYearDay(14);
                    }
                }
                list1.add(dto1);
            }
        }
        ExpotExcel<EmpAttendWorkDto1> expotExcel = new ExpotExcel<>();
        String[] header = new String[]{"姓名", "参加工作时间", "工龄", "应享受年假天数", "未休假天数", "累计休假", "疗养", "休疗累计天数", "补休天数", "公休加班天数", "节日加班天数"};
        expotExcel.doExportExcel("休假、加班、补休统计表", header, list1, "yyyy-MM-dd", response);
    }

    /**
     * 查看近七日部门考勤
     *
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "sra_r_attendSituation")
    public ModelAndView attendSituation(Model model, HttpServletRequest request, HttpServletResponse response) {

        return view(layout, "attend/attendSituation", model);
    }

    @RequestMapping(value = "ajax_attendSituation_Query_list")
    public void ajax_attendSituation_Query_list(HttpServletResponse response) {
        Date date = new Date();
        List<NewNearSevenDay> list = attendService.getNearlySevenDayAttendance();
        NewSevenDayModel dayModel = new NewSevenDayModel();
        dayModel.setList(list);
        dayModel.setD6(DateHelper.formatDate(date, "yyyy-MM-dd"));
        dayModel.setD5(DateHelper.formatDate(DateHelper.addDay(date, -1), "yyyy-MM-dd"));
        dayModel.setD4(DateHelper.formatDate(DateHelper.addDay(date, -2), "yyyy-MM-dd"));
        dayModel.setD3(DateHelper.formatDate(DateHelper.addDay(date, -3), "yyyy-MM-dd"));
        dayModel.setD2(DateHelper.formatDate(DateHelper.addDay(date, -4), "yyyy-MM-dd"));
        dayModel.setD1(DateHelper.formatDate(DateHelper.addDay(date, -5), "yyyy-MM-dd"));
        dayModel.setD0(DateHelper.formatDate(DateHelper.addDay(date, -6), "yyyy-MM-dd"));
        String json = JsonHelper.toJson(dayModel);
        System.out.println(json);
        AjaxResponse.write(response, json);
    }

    @RequestMapping(value = "ajax_attendSituation_down_list")
    public void ajax_attendSituation_down_list(HttpServletResponse response) {
        List<NewNearSevenDay> list = attendService.getNearlySevenDayAttendance();
        Date date = new Date();
        ExpotExcel<NearSevenDay> expotExcel = new ExpotExcel<>();
        List<NearSevenDay> nears = getNears(list, date);
        String[] header = new String[]{"部门名称", DateHelper.formatDate(DateHelper.addDay(date, -6), "yyyy-MM-dd"), DateHelper.formatDate(DateHelper.addDay(date, -5), "yyyy-MM-dd"), DateHelper.formatDate(DateHelper.addDay(date, -4), "yyyy-MM-dd"), DateHelper.formatDate(DateHelper.addDay(date, -3), "yyyy-MM-dd"), DateHelper.formatDate(DateHelper.addDay(date, -2), "yyyy-MM-dd"), DateHelper.formatDate(DateHelper.addDay(date, -1), "yyyy-MM-dd"), DateHelper.formatDate(date, "yyyy-MM-dd")};
        expotExcel.doExportExcel("近7日部门考勤统计表", header, nears, "yyyy-MM-dd", response);
    }

    private List<NearSevenDay> getNears(List<NewNearSevenDay> list, Date date) {
        List<NearSevenDay> nears = new ArrayList<>();
        if (list != null && list.size() > 0) {

            for (NewNearSevenDay day : list) {
                NearSevenDay nsd = new NearSevenDay();
                nsd.setDeptName(day.getDeptName());
                Map<String, Integer> map = day.getDateToInt();
                nsd.setD6(getStr(map.get(DateHelper.formatDate(date, "yyyy-MM-dd"))));
                nsd.setD5(getStr(map.get(DateHelper.formatDate(DateHelper.addDay(date, -1), "yyyy-MM-dd"))));
                nsd.setD4(getStr(map.get(DateHelper.formatDate(DateHelper.addDay(date, -2), "yyyy-MM-dd"))));
                nsd.setD3(getStr(map.get(DateHelper.formatDate(DateHelper.addDay(date, -3), "yyyy-MM-dd"))));
                nsd.setD2(getStr(map.get(DateHelper.formatDate(DateHelper.addDay(date, -4), "yyyy-MM-dd"))));
                nsd.setD1(getStr(map.get(DateHelper.formatDate(DateHelper.addDay(date, -5), "yyyy-MM-dd"))));
                nsd.setD0(getStr(map.get(DateHelper.formatDate(DateHelper.addDay(date, -6), "yyyy-MM-dd"))));
                nears.add(nsd);
            }
        }
        return nears;
    }

    private String getStr(Integer t) {
        if (t == 0) return "未打";
        else if (t == 1) return "已打考勤";
        else return "休息";
    }

}
