package com.sunesoft.lemon.webapp.controller.car_driver_flow;

import com.sunesoft.lemon.deanery.car.application.DriverService;
import com.sunesoft.lemon.deanery.car.application.criteria.CarApplyCriteria;
import com.sunesoft.lemon.deanery.car.application.criteria.CarReportCriteria;
import com.sunesoft.lemon.deanery.car.application.criteria.DriverStatisticsCriteria;
import com.sunesoft.lemon.deanery.car.domain.CarCount;
import com.sunesoft.lemon.deanery.car.domain.Company;
import com.sunesoft.lemon.deanery.car.domain.Driver;
import com.sunesoft.lemon.deanery.car.domain.DriverCount;
import com.sunesoft.lemon.deanery.carWorkFlow.application.dtos.CarApplyDto;
import com.sunesoft.lemon.deanery.carWorkFlow.domain.CarApply;
import com.sunesoft.lemon.fr.results.ListResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.utils.JsonHelper;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.fr.utils.excel.ExpotExcel;
import com.sunesoft.lemon.syms.eHr.domain.dept.Deptment;
import com.sunesoft.lemon.syms.hrForms.application.Dtos.DeptAppraiseDetailDto;
import com.sunesoft.lemon.syms.workflow.application.FormListService;
import com.sunesoft.lemon.syms.workflow.application.dtos.FormListDto;
import com.sunesoft.lemon.webapp.controller.Layout;
import com.sunesoft.lemon.webapp.utils.AjaxResponse;
import com.sunesoft.lemon.webapp.utils.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by swb on 2016/7/29.
 */
@Controller
public class CarListController extends Layout{
    @Autowired
    DriverService driverService;
    @Autowired
    FormListService formListService;

    SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" );
    @RequestMapping("sra_flow_car")
    public ModelAndView carListInfo(Model model,CarReportCriteria carReportCriteria){
        List<Company> companys = driverService.getComs();
        List<Deptment> findAllDept = driverService.findAllDept();
        model.addAttribute("companys",companys);
        model.addAttribute("findAllDept",findAllDept);
        return view(layout, "car_driver_flow/car_flow_info", model);
    }
    @RequestMapping("ajax_carListQuery_list")
    public void ajax_carListQuery_list(CarApplyCriteria carApplyCriteria,HttpServletRequest request,HttpServletResponse response,Model model){

        String drivername = URI.deURI(request.getParameter("driverName"));
        if(!StringUtils.isNullOrWhiteSpace(drivername)){
            carApplyCriteria.setDriverName(drivername);
        }
        String deptid=request.getParameter("deptid");
        if (deptid!=null) {
            String deptid1 = URI.deURI(deptid);
            if (!StringUtils.isNullOrWhiteSpace(deptid)) {
                carApplyCriteria.setDeptId(Long.parseLong(deptid));
            }
        }
        String companyId=request.getParameter("companyId");
        if(!StringUtils.isNullOrWhiteSpace(companyId)){
            carApplyCriteria.setCompanyId(Long.parseLong(companyId));
        }

        String carNo = URI.deURI(request.getParameter("carNo"));
        carApplyCriteria.setCarNo(carNo);

        String beginTime = request.getParameter("beginDate");
        String endTime = request.getParameter("endDate");
        if(!StringUtils.isNullOrWhiteSpace(beginTime)){
            carApplyCriteria.setStartTime(beginTime);
        }
        if(!StringUtils.isNullOrWhiteSpace(endTime)){
            carApplyCriteria.setEndTime(endTime);
        }

        ListResult<Map<String,Object>> list=new ListResult<Map<String,Object>>(driverService.CarApplyInfo(carApplyCriteria));
        String json= JsonHelper.toJson(list);
        AjaxResponse.write(response,json);
    }


}
