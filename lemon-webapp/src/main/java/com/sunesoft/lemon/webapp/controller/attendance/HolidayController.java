package com.sunesoft.lemon.webapp.controller.attendance;

import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.ListResult;
import com.sunesoft.lemon.fr.utils.DateHelper;
import com.sunesoft.lemon.fr.utils.JsonHelper;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.syms.eHr.application.DateDetailService;
import com.sunesoft.lemon.syms.eHr.application.HolidayInfoService;
import com.sunesoft.lemon.syms.eHr.application.dtos.HolidayInfoDto;
import com.sunesoft.lemon.syms.eHr.application.dtos.RuleDateDto;
import com.sunesoft.lemon.syms.eHr.domain.attendance.DateDetail;
import com.sunesoft.lemon.webapp.controller.Layout;
import com.sunesoft.lemon.webapp.utils.AjaxResponse;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by liulin on 2016/11/3.
 */
@Controller
public class HolidayController extends Layout {

    @Autowired
    HolidayInfoService holidayInfoService;

    @Autowired
    DateDetailService dateDetailService;

    @RequestMapping(value = "workAttendance")
    public ModelAndView workAtt(Model model, HttpServletRequest request, HttpServletResponse response) {

        String time = request.getParameter("time");
        String id = request.getParameter("id");
        model.addAttribute("id",id);
        model.addAttribute("time",time);

        List<String> list=holidayInfoService.getHnameByHolidayInfo();
        model.addAttribute("holidayName",list);

        return view(layout, "attendance/workAttendance", model);
    }

    @RequestMapping(value = "ajax_get_holiday_info", method = RequestMethod.POST)
    public void test(HttpServletRequest request, HttpServletResponse response) {

        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");



        List<RuleDateDto> list= holidayInfoService.getHolidayInfoDtoByDate(startTime,endTime);


        String json = JsonHelper.toJson(new ListResult(list));
        AjaxResponse.write(response, json);

    }

    @RequestMapping(value = "ajax_add_holiday_info",method = RequestMethod.POST)
    @ResponseBody
    public CommonResult addHolidayInfo(HttpServletRequest request,RuleDateDto dateDto){
        String holidayId = request.getParameter("holidayId");
        String title = request.getParameter("title");

        //获取时间区间里的每一天的具体时间
        Date d1 = dateDto.getStartTime();//定义起始日期
        Date d2 = dateDto.getEndTime();//定义结束日期
        Calendar dd = Calendar.getInstance();//定义日期实例
        dd.setTime(d1);//设置日期起始时间
        List<DateDetail> list = new ArrayList<>();
        while(dd.getTime().before(d2)) {//判断是否到结束日期
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String str = sdf.format(dd.getTime());

            //节假日每一天数据 供考勤获取
            DateDetail dateDetail = new DateDetail();
            dateDetail.setrDate(DateHelper.parse(str,"yyyy-MM-dd"));
            dateDetail.setHolidayType(title);
            dateDetail.setDescription(dateDto.getDescription());
            CommonResult commonResult=dateDetailService.addDateDetail(dateDetail);


            list.add(dateDetail);

            dd.add(Calendar.DAY_OF_YEAR,1);
        }


        dateDto.setHolidayType(title);
        dateDto.setInfoId(Long.parseLong(holidayId));
        dateDto.setDateDetail(list);

        CommonResult result=holidayInfoService.addRuleDateInfo(dateDto);
        return result;
    }

    @RequestMapping(value = "ajax_update_holiday_info",method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateHolidayInfo(HttpServletRequest request,RuleDateDto dateDto){
        String id = request.getParameter("editId");
        String title = request.getParameter("title2");
        String description = request.getParameter("description2");

        dateDto.setId(Long.parseLong(id));
        dateDto.setHolidayType(title);
        dateDto.setDescription(description);
        CommonResult result = holidayInfoService.updateRuleDateInfo(dateDto);

        return result;
    }

    @RequestMapping(value = "ajax_delete_holiday_info")
    @ResponseBody
    public CommonResult deleteHolidayInfo(HttpServletRequest request){
        String id = request.getParameter("id");
        CommonResult commonResult = null;
        if (!StringUtils.isNullOrWhiteSpace(id))
            commonResult=holidayInfoService.deleteRuleDateInfo(Long.parseLong(id));
        return commonResult;
    }

}
