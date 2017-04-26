package com.sunesoft.lemon.webapp.controller.carInfo;

import com.sun.org.apache.xpath.internal.operations.Mod;
import com.sunesoft.lemon.deanery.car.application.CarService;
import com.sunesoft.lemon.deanery.car.application.CommonDriverService;
import com.sunesoft.lemon.deanery.car.application.criteria.CommDriverCriteria;
import com.sunesoft.lemon.deanery.car.application.dtos.CarDto;
import com.sunesoft.lemon.deanery.car.application.dtos.CommDriverDto;
import com.sunesoft.lemon.deanery.car.domain.Car;
import com.sunesoft.lemon.deanery.car.domain.CommonDriver;
import com.sunesoft.lemon.deanery.car.domain.Driver;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.utils.JsonHelper;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.webapp.controller.Layout;
import com.sunesoft.lemon.webapp.utils.AjaxResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 默认司机
 * Created by liulin on 2016/6/21.
 */
@Controller
public class CommonDriverController extends Layout {

    @Autowired
    CommonDriverService commonDriverService;

    @Autowired
    CarService carService;

    /**
     * 首页
     * @param model
     * @return
     */
    @RequestMapping(value = "sra_c_commonDriver")
    public ModelAndView commonDriverInfo(Model model){
        List<Car> list = commonDriverService.getAllCar();
        model.addAttribute("cars",list);

        List<Driver> list1 = commonDriverService.getAllDriver();
        model.addAttribute("drivers",list1);
        return view(layout,"carInfo/commonDriver",model);
    }

    /**
     * 新增弹窗
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value ="_addCommonDriverForm" )
    public ModelAndView addCommonDriverForm(Model model,HttpServletRequest request){
        String id = request.getParameter("id");
        if(!StringUtils.isNullOrWhiteSpace(id)){
            CommDriverDto c = commonDriverService.get(Long.parseLong(id));
            model.addAttribute("commonDriver",c);

        }

        List<Car> list = commonDriverService.getCar();
        model.addAttribute("cars",list);

        List<Driver> list1 = commonDriverService.getDriver();
        model.addAttribute("drivers",list1);

        return view("carInfo/_addCommonDriverForm",model);

    }

    /**
     * 页面数据显示
     * @param commDriverCriteria
     * @param request
     * @param response
     */
    @RequestMapping(value = "ajax_commonDriver_query_list")
    public void queryCarInfo(CommDriverCriteria commDriverCriteria,HttpServletRequest request,HttpServletResponse response){

        /*List<CommDriverDto> list = new ArrayList<>();
        for(int i=0;i<10;i++){
            CommDriverDto dto = new CommDriverDto();

            Car c = new Car();
            c.setCarNo("110"+i);
            c.setCompanyName("车辆租赁有限公司"+i);

            dto.setCar(c);
            list.add(dto);
        }*/
        PagedResult pagedResult = commonDriverService.getCommonDrivers(commDriverCriteria);
//        PagedResult pagedResult = new PagedResult(list,1,10,1);
        String json = JsonHelper.toJson(pagedResult);
        AjaxResponse.write(response, json);

    }

    /**
     * 弹窗新增 提交
     * @param commDriverDto
     * @param request
     * @param response
     */
    @RequestMapping(value = "ajax_add_update_commonDriver",method = RequestMethod.POST)
    public void addOrUpdateCommonDriver(CommDriverDto commDriverDto,HttpServletRequest request,HttpServletResponse response){
        String carId = request.getParameter("carNo");
        String driverId = request.getParameter("driverName");

        Car car = new Car();
        car.setId(Long.parseLong(carId));

        Driver driver = new Driver();
        driver.setId(Long.parseLong(driverId));

        CommDriverDto commDriverDto1 = new CommDriverDto();
        commDriverDto1.setCar(car);
        commDriverDto1.setDriver(driver);

        String result = commonDriverService.addAndUpdate(commDriverDto1) > 0 ? "success" : "error";
        AjaxResponse.write(response, "text", result);

    }

    /**
     * 页面批量删除
     * @param request
     * @param response
     */
    @RequestMapping(value = "ajax_deleteCommonDriver")
    public void deletePermission(HttpServletRequest request, HttpServletResponse response) {
        String ids = request.getParameter("ids");
        List<Long> listid = new ArrayList<>();
        String[] pids = ids.split(",");
        for (String id : pids) {
            listid.add(Long.parseLong(id));
        }
        if (listid.size() == 0) {
            AjaxResponse.write(response, "text", "请选择要操作的菜单");
        } else {
            Boolean result = commonDriverService.delCommonDriver((listid.toArray(new Long[listid.size()])));//后面的是进行格式转换
            AjaxResponse.write(response, "text", result ? "success" : "error");
        }
    }

    /**
     * 修改弹窗
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "_modifyCommonDriverForm")
    public ModelAndView modifyCommonDriver(Model model,HttpServletRequest request,HttpServletResponse response){
        String id = request.getParameter("id");
        if(!StringUtils.isNullOrWhiteSpace(id)){
            CommDriverDto c = commonDriverService.get(Long.parseLong(id));
            model.addAttribute("commonDriver",c);
        }

        List<Car> list = commonDriverService.getCar(Long.parseLong(id));
        model.addAttribute("cars",list);

        List<Driver> list1 = commonDriverService.getDriver(Long.parseLong(id));
        model.addAttribute("drivers", list1);

        return view("carInfo/_modifyCommonDriverForm",model);

    }

    /**
     *详细信息弹窗
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "_detailCommonDriverForm")
    public ModelAndView detailCommonDriverForm(Model model,HttpServletRequest request,HttpServletResponse response){
        String id = request.getParameter("id");
        if(!StringUtils.isNullOrWhiteSpace(id)){
            CommDriverDto c = commonDriverService.get(Long.parseLong(id));
            model.addAttribute("commonDriver",c);
        }

        List<Car> list = commonDriverService.getCar(Long.parseLong(id));
        model.addAttribute("cars",list);

        List<Driver> list1 = commonDriverService.getDriver(Long.parseLong(id));
        model.addAttribute("drivers", list1);

        return view("carInfo/_detailCommonDriverForm",model);

    }
    @RequestMapping(value = "ajax_modify_commonDriver",method = RequestMethod.POST)
    public void addMofifyCommonDriver(HttpServletRequest request,HttpServletResponse response){

        String driverId = request.getParameter("driverName");
        String carId = request.getParameter("carId");
        String id = request.getParameter("id");

        CommDriverDto commDriverDto = new CommDriverDto();
        Driver driver = new Driver();
        driver.setId(Long.parseLong(driverId));
        Car car = new Car();
        car.setId(Long.parseLong(carId));
        commDriverDto.setDriver(driver);
        commDriverDto.setCar(car);
        commDriverDto.setId(Long.parseLong(id));

        String result = commonDriverService.addAndUpdate(commDriverDto) > 0 ? "modify_success" : "modify_error";
        System.out.println(result);
        AjaxResponse.write(response, "text", result);
    }
}
