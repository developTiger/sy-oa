package com.sunesoft.lemon.webapp.controller.Driver;

import com.sunesoft.lemon.deanery.car.application.CarService;
import com.sunesoft.lemon.deanery.car.application.DriverService;
import com.sunesoft.lemon.deanery.car.application.criteria.DriverCriteria;
import com.sunesoft.lemon.deanery.car.application.criteria.DriverReportCriteria;
import com.sunesoft.lemon.deanery.car.application.dtos.DriverDto;
import com.sunesoft.lemon.deanery.car.application.dtos.DriverReportDto;
import com.sunesoft.lemon.deanery.car.application.static_common.static_common;
import com.sunesoft.lemon.deanery.car.domain.Company;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.utils.JsonHelper;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.syms.uAuth.domain.SysUser;
import com.sunesoft.lemon.webapp.controller.Layout;
import com.sunesoft.lemon.webapp.function.UserSession;
import com.sunesoft.lemon.webapp.utils.AjaxResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zy on 2016/6/15.
 */
@Controller
public class DriverCountInformController extends Layout {

    @Autowired
    DriverService driverService;
    @Autowired
    UserSession us;

    @RequestMapping(value = "sra_c_driverCount")
    public ModelAndView driverCountInfo(Model model){

        List<Company> companys = driverService.getComs();
        model.addAttribute("driverTypes",static_common.DRIVER_TYPE);
        model.addAttribute("companys",companys);
        return view(layout,"driver/driverCountInfo",model);
    }


    @RequestMapping(value = "ajax_driverCount_query_list", method = RequestMethod.GET)
    public void getDriverlist(HttpServletRequest request, HttpServletResponse response, DriverReportCriteria driverReportCriteria, Model model) {
        DriverReportDto driverReportDto=driverService.driverReport(driverReportCriteria);
        String json = JsonHelper.toJson(driverReportDto);
        AjaxResponse.write(response, json);
    }
}
