package com.sunesoft.lemon.webapp.controller.car_driver_flow;

import com.sunesoft.lemon.deanery.car.application.CarService;
import com.sunesoft.lemon.deanery.car.application.CommonDriverService;
import com.sunesoft.lemon.deanery.car.application.DriverService;
import com.sunesoft.lemon.deanery.car.application.dtos.DriverDto;
import com.sunesoft.lemon.deanery.car.application.factory.DeaneryUtil;
import com.sunesoft.lemon.deanery.car.domain.Car;
import com.sunesoft.lemon.deanery.car.domain.CommonDriver;
import com.sunesoft.lemon.deanery.car.domain.Driver;
import com.sunesoft.lemon.deanery.carWorkFlow.application.CarApplyService;
import com.sunesoft.lemon.deanery.carWorkFlow.application.dtos.CarApplyDto;
import com.sunesoft.lemon.deanery.carWorkFlow.domain.CarApply;
import com.sunesoft.lemon.deanery.carWorkFlow.domain.CarApplyRepository;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.utils.DateHelper;
import com.sunesoft.lemon.fr.utils.JsonHelper;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.syms.eHr.application.EmployeeService;
import com.sunesoft.lemon.syms.eHr.application.dtos.EmpDto;
import com.sunesoft.lemon.syms.eHr.application.dtos.EmpSessionDto;
import com.sunesoft.lemon.syms.eHr.domain.empInfo.Employee;
import com.sunesoft.lemon.syms.hrForms.application.Dtos.FormLeaveDto;
import com.sunesoft.lemon.syms.hrForms.application.formleave.FormLeaveService;
import com.sunesoft.lemon.syms.workflow.application.FormListService;
import com.sunesoft.lemon.syms.workflow.application.dtos.FormListDto;
import com.sunesoft.lemon.webapp.controller.Layout;
import com.sunesoft.lemon.webapp.function.UserSession;
import com.sunesoft.lemon.webapp.utils.AjaxResponse;
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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by zy on 2016/7/26.
 */
@Controller
public class CarApplyController extends Layout {
    @Autowired
    CarApplyService carApplyService;

    @Autowired
    UserSession us;

    @Autowired
    CommonDriverService commonDriverService;

    @Autowired
    DriverService driverService;

    @Autowired
    CarService carService;

    @Autowired
    FormListService formListService;

    @Autowired
    EmployeeService employeeService;

    @RequestMapping("syy_yw_lc01_a")
    public ModelAndView applyInit(Model model, HttpServletRequest request) {
        Map<String,Object> map = new HashMap<String,Object>();
        EmpSessionDto empSessionDto = us.getCurrentUser(request);
        map.put("user",empSessionDto);
        FormListDto dto =formListService.getFormListInfo("SYY_YW_LC01");
        model.addAttribute("formKind", dto.getFormKind());
        model.addAttribute("formKindName", dto.getFormName());
        model.addAttribute("date", DateHelper.formatDate(new Date(), "yyyy-MM-dd"));
        model.addAttribute("beans",map);
        String formNo=request.getParameter("formNo");
        if(!StringUtils.isNullOrWhiteSpace(formNo)){
            CarApplyDto carApplyDto=carApplyService.getFormByFormNo(Long.parseLong(formNo));
            model.addAttribute("carApplyDto",carApplyDto);
        }
        return view(applyLayout,"car_driver_flow/syy_yw_lc01_a",model);
    }


    @RequestMapping(value = "ajax_add_update_carapply",method = RequestMethod.POST)
    @ResponseBody
    public CommonResult apply(CarApplyDto carApplyDto,HttpServletRequest request) {
        EmpSessionDto empSessionDto = us.getCurrentUser(request);
        String formNo = request.getParameter("formNo");
        String id = request.getParameter("id");
        CommonResult result = null;
        String predictGoStartTime = request.getParameter("predictGoStartTime");
        String predictGoArriveTime = request.getParameter("predictGoArriveTime");
        String address=request.getParameter("address");
//        if(!StringUtils.isNullOrWhiteSpace(id)){
//            carApplyDto.setPredictGoStartDate(predictGoStartTime);
//            carApplyDto.setPredictGoArriveDate(predictGoArriveTime);
//            result = carApplyService.updateByDto(carApplyDto);
//        }else{
            carApplyDto.setApplyer(empSessionDto.getId());
            carApplyDto.setApplyerName(empSessionDto.getName());
            carApplyDto.setDeptId(empSessionDto.getDeptId());
            carApplyDto.setDeptName(empSessionDto.getDeptName());
            carApplyDto.setPredictGoStartDate(predictGoStartTime);
            carApplyDto.setPredictGoArriveDate(predictGoArriveTime);
            carApplyDto.setAddress(address);
            result = carApplyService.addByDto(carApplyDto);
//        }

        return carApplyService.submitForm(result.getId(), carApplyDto.getFormKind());
    }


    @RequestMapping("ajax_syy_yw_lc01_data")
    public void applyView(Model model, HttpServletRequest request,HttpServletResponse resp) {
        String formKind = request.getParameter("formNo");
        String viewOnly = request.getParameter("viewOnly");
        CarApplyDto carApplyDto = carApplyService.getFormByFormNo(Long.parseLong(formKind));
        carApplyDto.setIsViewOnly(Boolean.valueOf(viewOnly));
//        String [] peopleId=carApplyDto.getGoRidePeoples().split(",");
//        String peopleName="";
//        String json2="\"peoples\":[";
//        for(int i=0;i<peopleId.length;i++){
//            EmpDto dto=employeeService.getEmpById(Long.parseLong(peopleId[i]));
//            peopleName+=dto.getName()+",";
//            json2+="{\"id\":\""+peopleId[i]+"\",\"name\":\""+dto.getName()+"\"},";
//        }
//        peopleName=peopleName.substring(0,peopleName.length()-1);
//        json2=json2.substring(0,json2.length()-1)+"]";
        String json= JsonHelper.toJson(carApplyDto);
//        json=json.substring(0,1)+"\"peoplename\":\""+peopleName+"\","+json2+","+json.substring(1,json.length());



        AjaxResponse.write(resp, json);
    }

   /* *//**
     * 第二步领导审批初始化页面
     * @return
     *//*
    @RequestMapping(value = "syy_yw_lc01_v")
    public ModelAndView approve(Model model, HttpServletRequest request){
        Map<String,Object> map = new HashMap<String,Object>();

        List<Car> cars =  carService.getAllCar();
        List<DriverDto> drivers = driverService.getAllIsActive();
        map.put("cars",cars);

        return view(layout,"car_driver_flow/syy_yw_lc01_2", model);
    }

    *//**
     * 第二步保存数据
     *//*
    @RequestMapping(value = "syy_yw_lc01_2_save")
    @ResponseBody
    public CommonResult save_step2(Model model, HttpServletRequest request){
        EmpSessionDto empSessionDto = us.getCurrentUser(request);
        Map<String, Object> params = DeaneryUtil.converToMap(request);
        CommonResult cr = carApplyService.saveCarApplyData_step2(params,String.valueOf(empSessionDto.getId()));
        return cr;
    }

    *//**
     * 第三步反馈信息
     * @return
     *//*
    @RequestMapping(value = "syy_yw_lc01_3")
    public ModelAndView feedback(Model model,HttpServletRequest request){
        Map<String,Object> map = new HashMap<String,Object>();
        String processId = request.getParameter("processId");
        String taskId = request.getParameter("taskId");
        String orderId = request.getParameter("orderId");
        map.put("processId",processId);
        map.put("taskId",taskId);
        map.put("orderId",orderId);
        model.addAttribute("beans",map);
        return view(layout,"car_driver_flow/syy_yw_lc01_3", model);
    }

    *//**
     * 第三步反馈信息
     * @return
     *//*
    @RequestMapping(value = "syy_yw_lc01_3_save")
    @ResponseBody
    public CommonResult save_step3(Model model,HttpServletRequest request){
        EmpSessionDto empSessionDto = us.getCurrentUser(request);
        Map<String, Object> params = DeaneryUtil.converToMap(request);
        CommonResult cr = carApplyService.saveCarApplyData_step3(params,String.valueOf(empSessionDto.getId()));
        return cr;
    }

    @RequestMapping("ajax_syy_yw_lc01_data")
    @ResponseBody
    public CarApplyDto applyView(Model model, HttpServletRequest request) {
        String formKind = request.getParameter("formNo");
        CarApplyDto carApplyDto = carApplyService.getFormByFormNo(Long.parseLong(formKind));
        return carApplyDto;
    }*/

    @RequestMapping("ajax_syy_yw_lc01_driver_data")
    @ResponseBody
    public Map<String,List<DriverDto>> getDriverData(Model model,HttpServletRequest request){
        String carid = request.getParameter("carId");
        //根据车的ID查询是否有常用司机
        CommonDriver cdByCarid =
            commonDriverService.getCommonDriverByCarId(Long.parseLong(carid));
        Map<String,List<DriverDto>> map = new HashMap<String,List<DriverDto>>();
        List<DriverDto> dds = new ArrayList<DriverDto>();
        dds =  driverService.getAllIsActive();
        if ( null != cdByCarid ){
            List<DriverDto> driverDtos =
                    driverService.isActiveByDriverId(cdByCarid.getDriverId());
            if ( null != driverDtos ){
                map.put("common",driverDtos);
            }
        }
        map.put("select",dds);
        return map;
    }


}
