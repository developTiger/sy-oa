package com.sunesoft.lemon.webapp.controller.carInfo;

import com.sunesoft.lemon.deanery.car.application.CarService;
import com.sunesoft.lemon.deanery.car.application.CommonDriverService;
import com.sunesoft.lemon.deanery.car.application.criteria.CarReportCriteria;
import com.sunesoft.lemon.deanery.car.application.dtos.CarReportDto;
import com.sunesoft.lemon.fr.utils.JsonHelper;
import com.sunesoft.lemon.webapp.controller.Layout;
import com.sunesoft.lemon.webapp.utils.AjaxResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * 车辆信息统计 报表
 * Created by liulin on 2016/6/22.
 */
@Controller
public class CarReportController extends Layout {

    @Autowired
    CarService carService;

    @Autowired
    CommonDriverService commonDriverService;

    /**
     * 首页
     * @param model
     * @param carReportCriteria
     * @return
     */
    @RequestMapping(value = "sra_c_carReport")
    public ModelAndView index(Model model,CarReportCriteria carReportCriteria){

        CarReportDto carReportDto = carService.carReport(carReportCriteria);
        model.addAttribute("beans",carReportDto);


        return view(layout,"carInfo/carReport",model);
    }

    /**
     * 页面数据展示
     * @param request
     * @param response
     * @param carReportCriteria
     */
    @RequestMapping(value = "ajax_carReport_query_list")
    public void queryCarReport(HttpServletRequest request,HttpServletResponse response,CarReportCriteria carReportCriteria) throws UnsupportedEncodingException {
        carReportCriteria.setCarType(URLDecoder.decode(carReportCriteria.getCarType(),"UTF-8"));//由于前端是用get方法提交且编码使用UTF-8处理
        CarReportDto carReportDto = carService.carReport(carReportCriteria);
        String json = JsonHelper.toJson(carReportDto);
        AjaxResponse.write(response, json);

    }









}
