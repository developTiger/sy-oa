package com.sunesoft.lemon.webapp.controller.Driver;

import com.google.gson.Gson;
import com.sunesoft.lemon.deanery.car.application.CarService;
import com.sunesoft.lemon.deanery.car.application.DriverService;
import com.sunesoft.lemon.deanery.car.application.criteria.DriverCriteria;
import com.sunesoft.lemon.deanery.car.application.criteria.DriverReportCriteria;
import com.sunesoft.lemon.deanery.car.application.dtos.DriverDto;
import com.sunesoft.lemon.deanery.car.application.static_common.static_common;
import com.sunesoft.lemon.deanery.car.domain.Company;
import com.sunesoft.lemon.deanery.patentCG.application.dtos.PatentDto;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.utils.JsonHelper;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.syms.eHr.application.dtos.EmpSessionDto;
import com.sunesoft.lemon.webapp.controller.Layout;
import com.sunesoft.lemon.webapp.function.UserSession;
import com.sunesoft.lemon.webapp.utils.AjaxResponse;
import com.sunesoft.lemon.webapp.utils.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by zy on 2016/6/15.
 */
@Controller
public class DriverInformController extends Layout {
    @Autowired
    CarService carService;
    @Autowired
    DriverService driverService;
    @Autowired
    UserSession us;
    SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" );
//跳出整体框架
    @RequestMapping(value = "sra_c_driver")
    public ModelAndView carInfo(Model model){
        List<Company> companys = driverService.getComs();
        model.addAttribute("driverTypes",static_common.DRIVER_TYPE);
        model.addAttribute("companys",companys);

        return view(layout,"driver/driverInfo",model);
    }
//跳出新增和修改页面
    @RequestMapping(value = "_addDriverInfoForm")
    public ModelAndView addDriverInfoForm(Model model, HttpServletRequest request, HttpServletResponse response) {
        List<Company> companys = driverService.getComs();
        String id = request.getParameter("id");
        if (!StringUtils.isNullOrWhiteSpace(id)) {
             Long id1 =Long.parseLong(id);
            DriverDto driver=driverService.getByIdDriver(id1);


            model.addAttribute("beans", driver);
        }
        model.addAttribute("driverTypes",static_common.DRIVER_TYPE);
        model.addAttribute("companys",companys);
        return view("/driver/_addDriverInfoForm", model);
    }
//修改页面
    @RequestMapping(value = "_repaireDriverInfoForm")
    public ModelAndView repaireDriverInfoForm(Model model, HttpServletRequest request, HttpServletResponse response) {
        List<Company> companys = driverService.getComs();
        String id = request.getParameter("id");
        if (!StringUtils.isNullOrWhiteSpace(id)) {
            Long id1 =Long.parseLong(id);
            DriverDto driver=driverService.getByIdDriver(id1);
            //出生  //雇佣  //取证时间   //创建时间
            Date date =driver.getBornTime();
            Date date1 =driver.getHrieTime();
            Date date2 =driver.getObEvidtime();
            Date date3= driver.getCreateDateTime();
            driver.setBornTime_Str(sdf.format(date));
            driver.setHrieTime_Str(sdf.format(date1));
            driver.setObEvidtime_Str(sdf.format(date2));
            driver.setCreateDateTime_Str(sdf.format(date3));
            model.addAttribute("beans", driver);
        }
        model.addAttribute("driverTypes",static_common.DRIVER_TYPE);
        model.addAttribute("companys",companys);
        return view("/driver/_repaireDriverInfoForm", model);
    }

    //展示详情页面
    @RequestMapping(value = "_detailDriverInfoForm")
    public ModelAndView detailDriverInfoForm(Model model, HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        EmpSessionDto user = us.getCurrentUser(request);
        model.addAttribute("user", user);
        List<Company> companys = driverService.getComs();
        if (!StringUtils.isNullOrWhiteSpace(id)) {
            Long id2 =Long.parseLong(id);
            DriverDto driver=driverService.getByIdDriver(id2);
            //出生  //雇佣  //取证时间   //创建时间
            Date date =driver.getBornTime();
            Date date1 =driver.getHrieTime();
            Date date2 =driver.getObEvidtime();
            Date date3= driver.getCreateDateTime();
            driver.setBornTime_Str(sdf.format(date));
            driver.setHrieTime_Str(sdf.format(date1));
            driver.setObEvidtime_Str(sdf.format(date2));
            driver.setCreateDateTime_Str(sdf.format(date3));
            model.addAttribute("beans", driver);
            List<Map<String, Object>> driverinfo=driverService.driverinfo(id);
            //String json = JsonHelper.toJson(driverinfo);
            model.addAttribute("driverFlowCount",driverinfo);
       }
        model.addAttribute("driverTypes",static_common.DRIVER_TYPE);
        model.addAttribute("companys",companys);
        return view("/driver/_detailDriverInfoForm", model);
    }
//表格数据
    @RequestMapping(value = "ajax_driver_query_list", method = RequestMethod.GET)
    public void getDriver(HttpServletRequest request, HttpServletResponse response,DriverCriteria driverCriteria,Model model) {
        driverCriteria.setName(URI.deURI(driverCriteria.getName()));

        String beginTime1= URI.deURI(driverCriteria.getBeginTime());
      if(beginTime1!=null && "" != beginTime1) {
          driverCriteria.setBeginTime(beginTime1);
      }
        String endTime1= URI.deURI(driverCriteria.getEndTime());
        if(endTime1!=null & "" != endTime1) {
            driverCriteria.setEndTime(endTime1);
        }
        PagedResult pagedResult = driverService.getByDriver(driverCriteria);

        String json = JsonHelper.toJson(pagedResult);
        AjaxResponse.write(response, json);
    }
//添加信息
 @RequestMapping(value = "ajax_add_update_driver", method = RequestMethod.POST)
    public void addOrUpdatePermission(HttpServletRequest request, HttpServletResponse response) {
        Map<String,Object> res = new HashMap<String,Object>();
        res.put("flag",0);
        String status = request.getParameter("status");
        String companyId = request.getParameter("companyId");
        String companyName = request.getParameter("companyName");
        String drivername = request.getParameter("driverName");
        String healthStatus = request.getParameter("healthStatus");
        String gender = request.getParameter("gender");
        String hrieTime= request.getParameter("hrieTime");
        String bornTime= request.getParameter("bornTime");
        String obEvidtime= request.getParameter("obEvidtime");
        String phone = request.getParameter("phone");
        String docType = request.getParameter("docType");
        String id = request.getParameter("id");
        status = (id != null && !"".equals(id)) ? status : request.getParameter("hiddenStatus");
        DriverDto driverDto = new DriverDto();
        driverDto.setDriverName(drivername);
       /* driverDto.setCompanyId(Long.valueOf(companyId));*/
       /* driverDto.setCompanyName(companyName);*/
        driverDto.setPhone(phone);
        driverDto.setHealthStatus(healthStatus);
        driverDto.setGender(Integer.parseInt(gender));
        driverDto.setDocType(docType);
        driverDto.setStatus(Integer.parseInt(status));
        driverDto.setCreateDateTime(new Date());

        try {
            Date hrietime= sdf.parse(hrieTime);
            driverDto.setHrieTime(hrietime);
            Date obevidtime= sdf.parse(obEvidtime);
            driverDto.setObEvidtime(obevidtime);
            Date borntime= sdf.parse(bornTime);
            driverDto.setBornTime(borntime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (!StringUtils.isNullOrWhiteSpace(id)) {
            driverDto.setId(Long.parseLong(id));
            res.put("flag",1);
        }
        String result = driverService.addOrUpdate(driverDto) > 0 ? "success" : "error";
        res.put("res_info",result);
        AjaxResponse.write(response, "text", new Gson().toJson(res));
    }

    @RequestMapping(value = "ajax_deleteDriver")
    public void deleteDriver(HttpServletRequest request, HttpServletResponse response) {
        String ids = request.getParameter("ids");
        List<Long> listid = new ArrayList<>();
        String[] pids = ids.split(",");
        for (String id : pids) {
            listid.add(Long.parseLong(id));
        }
        if (listid.size() == 0) {
            AjaxResponse.write(response, "text", "请选择要操作的菜单");
        } else {
            Boolean result = driverService.delDriver(listid.toArray(new Long[listid.size()]));//后面的是进行格式转换
            AjaxResponse.write(response, "text", result ? "success" : "error");
        }
    }

}
